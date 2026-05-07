package federation.agricole.api.controller;

import federation.agricole.api.dto.CreateMembershipFeeRest;
import federation.agricole.api.dto.MembershipFeeRest;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.service.MembershipFeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MembershipFeeController {
    MembershipFeeService membershipFeeService;

    public MembershipFeeController(MembershipFeeService membershipFeeService) {
        this.membershipFeeService = membershipFeeService;
    }

    @GetMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> getMembershipFees(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(membershipFeeService.getMembershipFees(id).stream()
                            .map(fee -> new MembershipFeeRest(fee.getId(), fee.getEligibleFrom(),
                                    fee.getFrequency(), fee.getAmount(), fee.getLabel(), fee.getStatus()))
                            .toList());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> createMembershipFees(@PathVariable String id,
                                                   @RequestBody List<CreateMembershipFeeRest> dtoList) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(membershipFeeService.createMembershipFees(id, dtoList).stream()
                            .map(fee -> new MembershipFeeRest(fee.getId(), fee.getEligibleFrom(),
                                    fee.getFrequency(), fee.getAmount(), fee.getLabel(), fee.getStatus()))
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
