package federation.agricole.api.dto;

import federation.agricole.api.entity.PaymentModeEnum;

import java.time.LocalDate;

public class MemberPaymentRest {
    private String id;
    private Double amount;
    private PaymentModeEnum paymentMode;
    private Object accountCredited;
    private LocalDate creationDate;

    public MemberPaymentRest(String id, Double amount, PaymentModeEnum paymentMode,
                             Object accountCredited, LocalDate creationDate) {
        this.id = id;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.accountCredited = accountCredited;
        this.creationDate = creationDate;
    }

    public String getId() { return id; }
    public Double getAmount() { return amount; }
    public PaymentModeEnum getPaymentMode() { return paymentMode; }
    public Object getAccountCredited() { return accountCredited; }
    public LocalDate getCreationDate() { return creationDate; }
}
