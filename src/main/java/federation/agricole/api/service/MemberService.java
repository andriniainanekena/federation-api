package federation.agricole.api.service;

import federation.agricole.api.dto.CreateMemberRest;
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
public class MemberService {
    MemberRepository memberRepository;
    CollectivityRepository collectivityRepository;

    public MemberService(MemberRepository memberRepository,
                         CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<Member> createMembers(List<CreateMemberRest> createMemberRestList) {
        for (CreateMemberRest item : createMemberRestList) {
            validate(item);
        }
        List<Member> createdMembers = new ArrayList<>();
        for (CreateMemberRest item : createMemberRestList) {
            createdMembers.add(persist(item));
        }
        return createdMembers;
    }

    private void validate(CreateMemberRest createMemberRest) {
        if (collectivityRepository.findById(createMemberRest.getCollectivityIdentifier()).isEmpty()) {
            throw new NotFoundException(
                "Collectivity.id=" + createMemberRest.getCollectivityIdentifier() + " is not found"
            );
        }

        if (!createMemberRest.isRegistrationFeePaid()) {
            throw new BadRequestException("Registration fee must be paid before admission");
        }

        if (!createMemberRest.isMembershipDuesPaid()) {
            throw new BadRequestException("Membership dues must be paid before admission");
        }
        List<String> refereeIds = createMemberRest.getReferees();
        if (refereeIds == null || refereeIds.size() < 2) {
            throw new BadRequestException("At least 2 referees are required for admission");
        }

        List<Member> referees = new ArrayList<>();
        for (String refereeId : refereeIds) {
            Optional<Member> optionalReferee = memberRepository.findById(refereeId);
            if (optionalReferee.isEmpty()) {
                throw new NotFoundException("Member.id=" + refereeId + " is not found");
            }
            referees.add(optionalReferee.get());
        }
        for (Member referee : referees) {
            if (!MemberOccupationEnum.SENIOR.equals(referee.getOccupation())) {
                throw new BadRequestException(
                    "Referee Member.id=" + referee.getId() + " is not a confirmed member (SENIOR)"
                );
            }
        }

        String collectivityId = createMemberRest.getCollectivityIdentifier();
        long internalCount = referees.stream()
                .filter(r -> collectivityId.equals(r.getCollectivityId()))
                .count();
        long externalCount = referees.size() - internalCount;

        if (internalCount < externalCount) {
            throw new BadRequestException(
                "Internal referees (" + internalCount + ") must be >= external referees (" + externalCount + ")"
            );
        }
    }

    private Member persist(CreateMemberRest createMemberRest) {
        String collectivityId = createMemberRest.getCollectivityIdentifier();

        List<Member> referees = new ArrayList<>();
        for (String refereeId : createMemberRest.getReferees()) {
            memberRepository.findById(refereeId).ifPresent(referees::add);
        }

        Member member = new Member(
                UUID.randomUUID().toString(),
                createMemberRest.getFirstName(),
                createMemberRest.getLastName(),
                createMemberRest.getBirthDate(),
                createMemberRest.getGender(),
                createMemberRest.getAddress(),
                createMemberRest.getProfession(),
                createMemberRest.getPhoneNumber(),
                createMemberRest.getEmail(),
                createMemberRest.getOccupation(),
                LocalDate.now(),
                collectivityId
        );

        memberRepository.save(member);

        for (Member referee : referees) {
            memberRepository.saveReferee(member.getId(), referee.getId());
        }

        member.setReferees(referees);
        return member;
    }
}
