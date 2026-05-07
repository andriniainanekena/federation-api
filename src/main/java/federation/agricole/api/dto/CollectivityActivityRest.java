package federation.agricole.api.dto;

import federation.agricole.api.entity.ActivityTypeEnum;
import federation.agricole.api.entity.MemberOccupationEnum;

import java.time.LocalDate;
import java.util.List;

public class CollectivityActivityRest {
    private String id;
    private String label;
    private ActivityTypeEnum activityType;
    private List<MemberOccupationEnum> memberOccupationConcerned;
    private MonthlyRecurrenceRuleRest recurrenceRule;
    private LocalDate executiveDate;

    public CollectivityActivityRest(String id, String label, ActivityTypeEnum activityType,
                                    List<MemberOccupationEnum> memberOccupationConcerned,
                                    MonthlyRecurrenceRuleRest recurrenceRule, LocalDate executiveDate) {
        this.id = id;
        this.label = label;
        this.activityType = activityType;
        this.memberOccupationConcerned = memberOccupationConcerned;
        this.recurrenceRule = recurrenceRule;
        this.executiveDate = executiveDate;
    }

    public String getId() { return id; }
    public String getLabel() { return label; }
    public ActivityTypeEnum getActivityType() { return activityType; }
    public List<MemberOccupationEnum> getMemberOccupationConcerned() { return memberOccupationConcerned; }
    public MonthlyRecurrenceRuleRest getRecurrenceRule() { return recurrenceRule; }
    public LocalDate getExecutiveDate() { return executiveDate; }
}
