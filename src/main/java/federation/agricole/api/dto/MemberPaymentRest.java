package federation.agricole.api.dto;

import federation.agricole.api.entity.FinancialAccount;
import federation.agricole.api.entity.PaymentModeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MemberPaymentRest {
    private String id;
    private BigDecimal amount;
    private PaymentModeEnum paymentMode;
    private FinancialAccount accountCredited;
    private LocalDate creationDate;

    public MemberPaymentRest() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentModeEnum getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentModeEnum paymentMode) { this.paymentMode = paymentMode; }

    public FinancialAccount getAccountCredited() { return accountCredited; }
    public void setAccountCredited(FinancialAccount accountCredited) { this.accountCredited = accountCredited; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
}

