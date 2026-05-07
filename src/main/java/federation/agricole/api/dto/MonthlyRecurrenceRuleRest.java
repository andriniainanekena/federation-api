package federation.agricole.api.dto;

import federation.agricole.api.entity.DayOfWeekEnum;

public class MonthlyRecurrenceRuleRest {
    private Integer weekOrdinal;
    private DayOfWeekEnum dayOfWeek;

    public MonthlyRecurrenceRuleRest() {}
    public MonthlyRecurrenceRuleRest(Integer weekOrdinal, DayOfWeekEnum dayOfWeek) {
        this.weekOrdinal = weekOrdinal;
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getWeekOrdinal() { return weekOrdinal; }
    public void setWeekOrdinal(Integer weekOrdinal) { this.weekOrdinal = weekOrdinal; }
    public DayOfWeekEnum getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeekEnum dayOfWeek) { this.dayOfWeek = dayOfWeek; }
}
