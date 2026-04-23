package federation.agricole.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class MemberPayment {
    private String id;
    private String memberId;
    private String membershipFeeId;
    private FinancialAccount accountCredited;
    private BigDecimal amount;
    private PaymentModeEnum paymentMode;
    private LocalDate creationDate;

    public MemberPayment() {}

    public MemberPayment(String id, String memberId, String membershipFeeId, FinancialAccount accountCredited, BigDecimal amount, PaymentModeEnum paymentMode, LocalDate creationDate) {
        this.id = id;
        this.memberId = memberId;
        this.membershipFeeId = membershipFeeId;
        this.accountCredited = accountCredited;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.creationDate = creationDate;
    }

}

