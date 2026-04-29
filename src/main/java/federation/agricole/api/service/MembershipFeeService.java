package federation.agricole.api.service;

import federation.agricole.api.dto.CreateMembershipFeeRest;
import federation.agricole.api.entity.ActivityStatusEnum;
import federation.agricole.api.entity.MembershipFee;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.repository.CollectivityRepository;
import federation.agricole.api.repository.MembershipFeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MembershipFeeService {
    MembershipFeeRepository membershipFeeRepository;
    CollectivityRepository collectivityRepository;

    public MembershipFeeService(MembershipFeeRepository membershipFeeRepository,
                                 CollectivityRepository collectivityRepository) {
        this.membershipFeeRepository = membershipFeeRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<MembershipFee> getMembershipFees(String collectivityId) {
        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }
        return membershipFeeRepository.findAllByCollectivityId(collectivityId);
    }

    public List<MembershipFee> createMembershipFees(String collectivityId,
                                                     List<CreateMembershipFeeRest> dtoList) {
        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }
        for (CreateMembershipFeeRest dto : dtoList) {
            if (dto.getAmount() == null || dto.getAmount() < 0) {
                throw new BadRequestException("Membership fee amount must be >= 0");
            }
            if (dto.getFrequency() == null) {
                throw new BadRequestException("Membership fee frequency is required");
            }
            if (dto.getEligibleFrom() == null) {
                throw new BadRequestException("Membership fee eligibleFrom date is required");
            }
        }

        List<MembershipFee> created = new ArrayList<>();
        for (CreateMembershipFeeRest dto : dtoList) {
            MembershipFee fee = new MembershipFee();
            fee.setId(UUID.randomUUID().toString());
            fee.setCollectivityId(collectivityId);
            fee.setEligibleFrom(dto.getEligibleFrom());
            fee.setFrequency(dto.getFrequency());
            fee.setAmount(dto.getAmount());
            fee.setLabel(dto.getLabel());
            fee.setStatus(ActivityStatusEnum.ACTIVE);
            created.add(membershipFeeRepository.save(fee));
        }
        return created;
    }
}
