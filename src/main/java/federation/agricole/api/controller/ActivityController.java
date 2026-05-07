package federation.agricole.api.controller;

import federation.agricole.api.dto.*;
import federation.agricole.api.entity.ActivityMemberAttendance;
import federation.agricole.api.entity.CollectivityActivity;
import federation.agricole.api.entity.Member;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityController {
    ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/collectivities/{id}/activities")
    public ResponseEntity<?> getActivities(@PathVariable String id) {
        try {
            return ResponseEntity.ok(
                activityService.getActivities(id).stream()
                    .map(this::toActivityRest).toList());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/collectivities/{id}/activities")
    public ResponseEntity<?> createActivities(@PathVariable String id,
                                               @RequestBody List<CreateCollectivityActivityRest> dtoList) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                            activityService.createActivities(id, dtoList).stream()
                                    .map(this::toActivityRest)
                                    .toList()
                    );
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/collectivities/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> createAttendance(@PathVariable String id,
                                               @PathVariable String activityId,
                                               @RequestBody List<CreateActivityMemberAttendanceRest> dtoList) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                activityService.createAttendance(id, activityId, dtoList).stream()
                    .map(this::toAttendanceRest).toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> getAttendance(@PathVariable String id,
                                            @PathVariable String activityId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                activityService.getAttendance(id, activityId).stream()
                    .map(this::toAttendanceRest).toList());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private CollectivityActivityRest toActivityRest(CollectivityActivity a) {
        MonthlyRecurrenceRuleRest recurrence = null;
        if (a.getWeekOrdinal() != null || a.getDayOfWeek() != null) {
            recurrence = new MonthlyRecurrenceRuleRest(a.getWeekOrdinal(), a.getDayOfWeek());
        }
        return new CollectivityActivityRest(a.getId(), a.getLabel(), a.getActivityType(),
                a.getMemberOccupationConcerned(), recurrence, a.getExecutiveDate());
    }

    private ActivityMemberAttendanceRest toAttendanceRest(ActivityMemberAttendance att) {
        Member m = att.getMember();
        MemberDescriptionRest desc = m != null ? new MemberDescriptionRest(
                m.getId(), m.getFirstName(), m.getLastName(),
                m.getEmail(), m.getOccupation().name()) : null;
        return new ActivityMemberAttendanceRest(att.getId(), desc, att.getAttendanceStatus());
    }
}
