package federation.agricole.api.entity;


import java.time.LocalDate;


public class MemberPayment {
    private String id;
    private String memberId;
    private Double amount;
    private PaymentModeEnum paymentMode;
    private FinancialAccount accountCredited;
    private MembershipFee membershipFee;
    private LocalDate creationDate;

    public MemberPayment() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentModeEnum getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentModeEnum paymentMode) {
        this.paymentMode = paymentMode;
    }

    public FinancialAccount getAccountCredited() {
        return accountCredited;
    }

    public void setAccountCredited(FinancialAccount accountCredited) {
        this.accountCredited = accountCredited;
    }

    public MembershipFee getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(MembershipFee membershipFee) {
        this.membershipFee = membershipFee;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
