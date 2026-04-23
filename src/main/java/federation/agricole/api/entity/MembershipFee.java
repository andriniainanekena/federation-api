package federation.agricole.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class MembershipFee {
    private String id;
    private String collectivityId;
    private LocalDate eligibleFrom;
    private FrequencyEnum frequency;
    private BigDecimal amount;
    private String label;
    private ActivityStatusEnum status;

    public MembershipFee() {}

    public MembershipFee(String id, String collectivityId, LocalDate eligibleFrom, FrequencyEnum frequency, BigDecimal amount, String label, ActivityStatusEnum status) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.eligibleFrom = eligibleFrom;
        this.frequency = frequency;
        this.amount = amount;
        this.label = label;
        this.status = status;
    }

}

