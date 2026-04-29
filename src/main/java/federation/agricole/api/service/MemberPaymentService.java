package federation.agricole.api.service;

import federation.agricole.api.dto.CreateMemberPaymentRest;
import federation.agricole.api.entity.*;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberPaymentService {
    MemberPaymentRepository memberPaymentRepository;
    MemberRepository memberRepository;
    MembershipFeeRepository membershipFeeRepository;
    FinancialAccountRepository financialAccountRepository;
    CollectivityTransactionRepository transactionRepository;

    public MemberPaymentService(MemberPaymentRepository memberPaymentRepository,
                                MemberRepository memberRepository,
                                MembershipFeeRepository membershipFeeRepository,
                                FinancialAccountRepository financialAccountRepository,
                                CollectivityTransactionRepository transactionRepository) {
        this.memberPaymentRepository = memberPaymentRepository;
        this.memberRepository = memberRepository;
        this.membershipFeeRepository = membershipFeeRepository;
        this.financialAccountRepository = financialAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<MemberPayment> createPayments(String memberId, List<CreateMemberPaymentRest> dtoList) {
        // Le membre doit exister
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new NotFoundException("Member.id=" + memberId + " is not found");
        }
        Member member = optionalMember.get();

        // Passe 1 : validation
        for (CreateMemberPaymentRest dto : dtoList) {
            validate(member, dto);
        }

        // Passe 2 : persistance
        List<MemberPayment> created = new ArrayList<>();
        for (CreateMemberPaymentRest dto : dtoList) {
            created.add(persist(member, dto));
        }
        return created;
    }

    private void validate(Member member, CreateMemberPaymentRest dto) {
        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new BadRequestException("Payment amount must be positive");
        }
        if (dto.getPaymentMode() == null) {
            throw new BadRequestException("Payment mode is required");
        }
        // La cotisation doit exister
        if (membershipFeeRepository.findById(dto.getMembershipFeeIdentifier()).isEmpty()) {
            throw new NotFoundException("MembershipFee.id=" + dto.getMembershipFeeIdentifier() + " is not found");
        }
        // Le compte credite doit exister
        if (financialAccountRepository.findById(dto.getAccountCreditedIdentifier()).isEmpty()) {
            throw new NotFoundException("FinancialAccount.id=" + dto.getAccountCreditedIdentifier() + " is not found");
        }
    }

    private MemberPayment persist(Member member, CreateMemberPaymentRest dto) {
        MembershipFee fee = membershipFeeRepository.findById(dto.getMembershipFeeIdentifier()).get();
        FinancialAccount account = financialAccountRepository.findById(dto.getAccountCreditedIdentifier()).get();

        // Creation du paiement
        MemberPayment payment = new MemberPayment();
        payment.setId(UUID.randomUUID().toString());
        payment.setMemberId(member.getId());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMode(dto.getPaymentMode());
        payment.setAccountCredited(account);
        payment.setMembershipFee(fee);
        payment.setCreationDate(LocalDate.now());
        memberPaymentRepository.save(payment);

        // Mise a jour du solde du compte credite
        financialAccountRepository.creditAccount(account.getId(), dto.getAmount());
        account.setAmount(account.getAmount() + dto.getAmount());

        // Creation automatique de la transaction de la collectivite
        CollectivityTransaction transaction = new CollectivityTransaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setCollectivityId(member.getCollectivityId());
        transaction.setCreationDate(LocalDate.now());
        transaction.setAmount(dto.getAmount());
        transaction.setPaymentMode(dto.getPaymentMode());
        transaction.setAccountCredited(account);
        transaction.setMemberDebited(member);
        transactionRepository.save(transaction);

        return payment;
    }
}
