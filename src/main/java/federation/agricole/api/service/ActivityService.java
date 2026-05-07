package federation.agricole.api.service;

import federation.agricole.api.dto.CreateActivityMemberAttendanceRest;
import federation.agricole.api.dto.CreateCollectivityActivityRest;
import federation.agricole.api.entity.ActivityMemberAttendance;
import federation.agricole.api.entity.AttendanceStatusEnum;
import federation.agricole.api.entity.CollectivityActivity;
import federation.agricole.api.entity.Member;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.repository.ActivityRepository;
import federation.agricole.api.repository.CollectivityRepository;
import federation.agricole.api.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {
    ActivityRepository activityRepository;
    CollectivityRepository collectivityRepository;
    MemberRepository memberRepository;

    public ActivityService(ActivityRepository activityRepository,
                           CollectivityRepository collectivityRepository,
                           MemberRepository memberRepository) {
        this.activityRepository = activityRepository;
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
    }

    public List<CollectivityActivity> getActivities(String collectivityId) {
        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }
        return activityRepository.findAllByCollectivityId(collectivityId);
    }

    public List<CollectivityActivity> createActivities(String collectivityId,
                                                        List<CreateCollectivityActivityRest> dtoList) {
        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }

        for (CreateCollectivityActivityRest dto : dtoList) {
            if (dto.getExecutiveDate() != null && dto.getRecurrenceRule() != null) {
                throw new BadRequestException(
                    "Cannot provide both executiveDate and recurrenceRule at the same time");
            }
            if (dto.getActivityType() == null) {
                throw new BadRequestException("Activity type is required");
            }
        }

        List<CollectivityActivity> created = new ArrayList<>();
        for (CreateCollectivityActivityRest dto : dtoList) {
            CollectivityActivity activity = new CollectivityActivity();
            activity.setId(UUID.randomUUID().toString());
            activity.setCollectivityId(collectivityId);
            activity.setLabel(dto.getLabel());
            activity.setActivityType(dto.getActivityType());
            activity.setMemberOccupationConcerned(dto.getMemberOccupationConcerned());
            activity.setExecutiveDate(dto.getExecutiveDate());
            if (dto.getRecurrenceRule() != null) {
                activity.setWeekOrdinal(dto.getRecurrenceRule().getWeekOrdinal());
                activity.setDayOfWeek(dto.getRecurrenceRule().getDayOfWeek());
            }
            created.add(activityRepository.save(activity));
        }
        return created;
    }

    public List<ActivityMemberAttendance> createAttendance(String collectivityId, String activityId,
                                                           List<CreateActivityMemberAttendanceRest> dtoList) {
        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }
        if (activityRepository.findById(activityId).isEmpty()) {
            throw new NotFoundException("Activity.id=" + activityId + " is not found");
        }

        for (CreateActivityMemberAttendanceRest dto : dtoList) {
            Optional<Member> optMember = memberRepository.findById(dto.getMemberIdentifier());
            if (optMember.isEmpty()) {
                throw new NotFoundException("Member.id=" + dto.getMemberIdentifier() + " is not found");
            }
            Optional<ActivityMemberAttendance> existing = activityRepository
                    .findAttendanceByActivityAndMember(activityId, dto.getMemberIdentifier());
            if (existing.isPresent()) {
                AttendanceStatusEnum currentStatus = existing.get().getAttendanceStatus();
                if (currentStatus == AttendanceStatusEnum.ATTENDED
                        || currentStatus == AttendanceStatusEnum.MISSING) {
                    throw new BadRequestException(
                        "Attendance for Member.id=" + dto.getMemberIdentifier() +
                        " is already confirmed and cannot be modified");
                }
            }
        }

        List<ActivityMemberAttendance> created = new ArrayList<>();
        for (CreateActivityMemberAttendanceRest dto : dtoList) {
            ActivityMemberAttendance attendance = new ActivityMemberAttendance();
            attendance.setId(UUID.randomUUID().toString());
            attendance.setActivityId(activityId);
            attendance.setAttendanceStatus(dto.getAttendanceStatus());
            memberRepository.findById(dto.getMemberIdentifier()).ifPresent(attendance::setMember);
            created.add(activityRepository.saveAttendance(attendance));
        }
        return created;
    }

    public List<ActivityMemberAttendance> getAttendance(String collectivityId, String activityId) {
        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + collectivityId + " is not found");
        }
        if (activityRepository.findById(activityId).isEmpty()) {
            throw new NotFoundException("Activity.id=" + activityId + " is not found");
        }
        return activityRepository.findAttendanceByActivityId(activityId);
    }
}
