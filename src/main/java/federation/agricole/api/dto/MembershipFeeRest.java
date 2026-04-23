package federation.agricole.api.dto;

import federation.agricole.api.entity.ActivityStatusEnum;
import federation.agricole.api.entity.FrequencyEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class MembershipFeeRest {
    private String id;
    private LocalDate eligibleFrom;
    private FrequencyEnum frequency;
    private BigDecimal amount;
    private String label;
    private ActivityStatusEnum status;

    public MembershipFeeRest() {}

}

