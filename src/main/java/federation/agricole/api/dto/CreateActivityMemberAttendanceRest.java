package federation.agricole.api.dto;

import federation.agricole.api.entity.AttendanceStatusEnum;

public class CreateActivityMemberAttendanceRest {
    private String memberIdentifier;
    private AttendanceStatusEnum attendanceStatus;

    public CreateActivityMemberAttendanceRest() {}

    public String getMemberIdentifier() { return memberIdentifier; }
    public void setMemberIdentifier(String memberIdentifier) { this.memberIdentifier = memberIdentifier; }
    public AttendanceStatusEnum getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(AttendanceStatusEnum attendanceStatus) { this.attendanceStatus = attendanceStatus; }
}
