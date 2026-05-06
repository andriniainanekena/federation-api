package federation.agricole.api.dto;

import federation.agricole.api.entity.AttendanceStatusEnum;

public class ActivityMemberAttendanceRest {
    private String id;
    private MemberDescriptionRest memberDescription;
    private AttendanceStatusEnum attendanceStatus;

    public ActivityMemberAttendanceRest(String id, MemberDescriptionRest memberDescription,
                                        AttendanceStatusEnum attendanceStatus) {
        this.id = id;
        this.memberDescription = memberDescription;
        this.attendanceStatus = attendanceStatus;
    }

    public String getId() { return id; }
    public MemberDescriptionRest getMemberDescription() { return memberDescription; }
    public AttendanceStatusEnum getAttendanceStatus() { return attendanceStatus; }
}
