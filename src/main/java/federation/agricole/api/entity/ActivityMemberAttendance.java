package federation.agricole.api.entity;

public class ActivityMemberAttendance {
    private String id;
    private String activityId;
    private Member member;
    private AttendanceStatusEnum attendanceStatus;

    public ActivityMemberAttendance() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getActivityId() { return activityId; }
    public void setActivityId(String activityId) { this.activityId = activityId; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public AttendanceStatusEnum getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(AttendanceStatusEnum attendanceStatus) { this.attendanceStatus = attendanceStatus; }
}
