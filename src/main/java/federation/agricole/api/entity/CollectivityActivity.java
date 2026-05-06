package federation.agricole.api.entity;

import java.time.LocalDate;
import java.util.List;

public class CollectivityActivity {
    private String id;
    private String collectivityId;
    private String label;
    private ActivityTypeEnum activityType;
    private List<MemberOccupationEnum> memberOccupationConcerned;
    private Integer weekOrdinal;
    private DayOfWeekEnum dayOfWeek;
    private LocalDate executiveDate;

    public CollectivityActivity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public ActivityTypeEnum getActivityType() { return activityType; }
    public void setActivityType(ActivityTypeEnum activityType) { this.activityType = activityType; }
    public List<MemberOccupationEnum> getMemberOccupationConcerned() { return memberOccupationConcerned; }
    public void setMemberOccupationConcerned(List<MemberOccupationEnum> memberOccupationConcerned) { this.memberOccupationConcerned = memberOccupationConcerned; }
    public Integer getWeekOrdinal() { return weekOrdinal; }
    public void setWeekOrdinal(Integer weekOrdinal) { this.weekOrdinal = weekOrdinal; }
    public DayOfWeekEnum getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeekEnum dayOfWeek) { this.dayOfWeek = dayOfWeek; }
    public LocalDate getExecutiveDate() { return executiveDate; }
    public void setExecutiveDate(LocalDate executiveDate) { this.executiveDate = executiveDate; }
}
