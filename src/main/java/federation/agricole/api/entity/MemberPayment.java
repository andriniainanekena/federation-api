package federation.agricole.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MemberPayment {
    private String id;
    private String memberId;
    private Double amount;
    private PaymentModeEnum paymentMode;
    private FinancialAccount accountCredited;
    private MembershipFee membershipFee;
    private LocalDate creationDate;

    public MemberPayment() {}

}
