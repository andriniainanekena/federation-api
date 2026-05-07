package federation.agricole.api.dto;

import federation.agricole.api.entity.ActivityTypeEnum;
import federation.agricole.api.entity.MemberOccupationEnum;

import java.time.LocalDate;
import java.util.List;

public class CreateCollectivityActivityRest {
    private String label;
    private ActivityTypeEnum activityType;
    private List<MemberOccupationEnum> memberOccupationConcerned;
    private MonthlyRecurrenceRuleRest recurrenceRule;
    private LocalDate executiveDate;

    public CreateCollectivityActivityRest() {}

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public ActivityTypeEnum getActivityType() { return activityType; }
    public void setActivityType(ActivityTypeEnum activityType) { this.activityType = activityType; }
    public List<MemberOccupationEnum> getMemberOccupationConcerned() { return memberOccupationConcerned; }
    public void setMemberOccupationConcerned(List<MemberOccupationEnum> memberOccupationConcerned) { this.memberOccupationConcerned = memberOccupationConcerned; }
    public MonthlyRecurrenceRuleRest getRecurrenceRule() { return recurrenceRule; }
    public void setRecurrenceRule(MonthlyRecurrenceRuleRest recurrenceRule) { this.recurrenceRule = recurrenceRule; }
    public LocalDate getExecutiveDate() { return executiveDate; }
    public void setExecutiveDate(LocalDate executiveDate) { this.executiveDate = executiveDate; }
}
