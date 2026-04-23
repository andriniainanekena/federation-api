package federation.agricole.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CollectivityTransaction {
    private String id;
    private String collectivityId;
    private LocalDate creationDate;
    private Double amount;
    private PaymentModeEnum paymentMode;
    private FinancialAccount accountCredited;
    private Member memberDebited;

    public CollectivityTransaction() {}


}
