package federation.agricole.api.dto;


import federation.agricole.api.entity.PaymentModeEnum;

import java.time.LocalDate;

public class CollectivityTransactionRest {
    private String id;
    private LocalDate creationDate;
    private Double amount;
    private PaymentModeEnum paymentMode;
    private Object accountCredited;
    private MemberRest memberDebited;

    public CollectivityTransactionRest(String id, LocalDate creationDate, Double amount,
                                       PaymentModeEnum paymentMode, Object accountCredited,
                                       MemberRest memberDebited) {
        this.id = id;
        this.creationDate = creationDate;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.accountCredited = accountCredited;
        this.memberDebited = memberDebited;
    }

    public String getId() { return id; }
    public LocalDate getCreationDate() { return creationDate; }
    public Double getAmount() { return amount; }
    public PaymentModeEnum getPaymentMode() { return paymentMode; }
    public Object getAccountCredited() { return accountCredited; }
    public MemberRest getMemberDebited() { return memberDebited; }
}
