package federation.agricole.api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getMembershipFeeId() { return membershipFeeId; }
    public void setMembershipFeeId(String membershipFeeId) { this.membershipFeeId = membershipFeeId; }

    public FinancialAccount getAccountCredited() { return accountCredited; }
    public void setAccountCredited(FinancialAccount accountCredited) { this.accountCredited = accountCredited; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentModeEnum getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentModeEnum paymentMode) { this.paymentMode = paymentMode; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
}

