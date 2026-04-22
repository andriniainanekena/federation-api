package federation.agricole.api.service;

import federation.agricole.api.dto.CreateCollectivityRest;
import federation.agricole.api.dto.CreateCollectivityStructureRest;
import federation.agricole.api.entity.Collectivity;
import federation.agricole.api.entity.CollectivityStructure;
import federation.agricole.api.entity.Member;
import federation.agricole.api.entity.MemberOccupationEnum;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.repository.CollectivityRepository;
import federation.agricole.api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CollectivityService {
    CollectivityRepository collectivityRepository;
    MemberRepository memberRepository;

    public CollectivityService(CollectivityRepository collectivityRepository,
                               MemberRepository memberRepository) {
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
    }

    public List<Collectivity> createCollectivities(List<CreateCollectivityRest> createCollectivityRestList) {
        for (CreateCollectivityRest item : createCollectivityRestList) {
            validate(item);
        }
        List<Collectivity> createdCollectivities = new ArrayList<>();
        for (CreateCollectivityRest item : createCollectivityRestList) {
            createdCollectivities.add(persist(item));
        }
        return createdCollectivities;
    }

    private void validate(CreateCollectivityRest createCollectivityRest) {
        if (!createCollectivityRest.isFederationApproval()) {
            throw new BadRequestException("Federation approval is required to create a collectivity");
        }

        CreateCollectivityStructureRest structureRest = createCollectivityRest.getStructure();
        if (structureRest == null
                || structureRest.getPresident() == null
                || structureRest.getVicePresident() == null
                || structureRest.getTreasurer() == null
                || structureRest.getSecretary() == null) {
            throw new BadRequestException(
                "Collectivity structure is incomplete: president, vicePresident, treasurer and secretary are required"
            );
        }


        List<String> memberIds = createCollectivityRest.getMembers();
        if (memberIds == null || memberIds.size() < 10) {
            throw new BadRequestException(
                "A collectivity requires at least 10 members, got: " +
                (memberIds == null ? 0 : memberIds.size())
            );
        }

        if (!memberIds.contains(structureRest.getPresident())) {
            throw new BadRequestException(
                "President Member.id=" + structureRest.getPresident() + " must be included in the members list"
            );
        }
        if (!memberIds.contains(structureRest.getVicePresident())) {
            throw new BadRequestException(
                "VicePresident Member.id=" + structureRest.getVicePresident() + " must be included in the members list"
            );
        }
        if (!memberIds.contains(structureRest.getTreasurer())) {
            throw new BadRequestException(
                "Treasurer Member.id=" + structureRest.getTreasurer() + " must be included in the members list"
            );
        }
        if (!memberIds.contains(structureRest.getSecretary())) {
            throw new BadRequestException(
                "Secretary Member.id=" + structureRest.getSecretary() + " must be included in the members list"
            );
        }

        List<Member> members = new ArrayList<>();
        for (String memberId : memberIds) {
            Optional<Member> optionalMember = memberRepository.findById(memberId);
            if (optionalMember.isEmpty()) {
                throw new NotFoundException("Member.id=" + memberId + " is not found");
            }
            members.add(optionalMember.get());
        }


        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        long seniorFederationMembersCount = members.stream()
                .filter(m -> m.getAdhesionDate() != null
                        && !m.getAdhesionDate().isAfter(sixMonthsAgo)
                        && m.getCollectivityId() != null)
                .count();

        if (seniorFederationMembersCount < 5) {
            throw new BadRequestException(
                "At least 5 members must have been in the federation for 6 months or more, got: "
                + seniorFederationMembersCount
            );
        }

        resolveMember(structureRest.getPresident(), "president");
        resolveMember(structureRest.getVicePresident(), "vicePresident");
        resolveMember(structureRest.getTreasurer(), "treasurer");
        resolveMember(structureRest.getSecretary(), "secretary");
    }

    private Collectivity persist(CreateCollectivityRest createCollectivityRest) {
        CreateCollectivityStructureRest structureRest = createCollectivityRest.getStructure();
        List<String> memberIds = createCollectivityRest.getMembers();

        List<Member> members = new ArrayList<>();
        for (String memberId : memberIds) {
            memberRepository.findById(memberId).ifPresent(members::add);
        }

        Member president     = resolveMember(structureRest.getPresident(),    "president");
        Member vicePresident = resolveMember(structureRest.getVicePresident(), "vicePresident");
        Member treasurer     = resolveMember(structureRest.getTreasurer(),    "treasurer");
        Member secretary     = resolveMember(structureRest.getSecretary(),    "secretary");

        String collectivityId = UUID.randomUUID().toString();
        Collectivity collectivity = new Collectivity(
                collectivityId,
                createCollectivityRest.getLocation(),
                LocalDate.now(),
                null,
                members
        );
        collectivityRepository.save(collectivity);

        CollectivityStructure structure = new CollectivityStructure(
                collectivityId, president, vicePresident, treasurer, secretary
        );
        collectivityRepository.saveStructure(structure);
        collectivity.setStructure(structure);

        memberRepository.updateOccupation(president.getId(),     MemberOccupationEnum.PRESIDENT);
        memberRepository.updateOccupation(vicePresident.getId(), MemberOccupationEnum.VICE_PRESIDENT);
        memberRepository.updateOccupation(treasurer.getId(),     MemberOccupationEnum.TREASURER);
        memberRepository.updateOccupation(secretary.getId(),     MemberOccupationEnum.SECRETARY);

        for (Member member : members) {
            memberRepository.updateCollectivityId(member.getId(), collectivityId);
            member.setCollectivityId(collectivityId);
        }

        return collectivity;
    }

    private Member resolveMember(String memberId, String role) {
        Optional<Member> optional = memberRepository.findById(memberId);
        if (optional.isEmpty()) {
            throw new NotFoundException(
                "Member.id=" + memberId + " assigned as " + role + " is not found"
            );
        }
        return optional.get();
    }
}
