package federation.agricole.api.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MembershipFee {
    private String id;
    private String collectivityId;
    private LocalDate eligibleFrom;
    private FrequencyEnum frequency;
    private Double amount;
    private String label;
    private ActivityStatusEnum status;

    public MembershipFee() {}

}
