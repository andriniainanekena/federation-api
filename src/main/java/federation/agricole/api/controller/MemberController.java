package federation.agricole.api.controller;

import federation.agricole.api.dto.CreateMemberRest;
import federation.agricole.api.dto.MemberRest;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {
    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<?> createMembers(@RequestBody List<CreateMemberRest> createMemberRestList) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(memberService.createMembers(createMemberRestList).stream()
                            .map(member -> new MemberRest(
                                    member.getId(),
                                    member.getFirstName(),
                                    member.getLastName(),
                                    member.getBirthDate(),
                                    member.getGender(),
                                    member.getAddress(),
                                    member.getProfession(),
                                    member.getPhoneNumber(),
                                    member.getEmail(),
                                    member.getOccupation(),
                                    member.getReferees() == null ? List.of() :
                                            member.getReferees().stream()
                                                    .map(referee -> new MemberRest(
                                                            referee.getId(),
                                                            referee.getFirstName(),
                                                            referee.getLastName(),
                                                            referee.getBirthDate(),
                                                            referee.getGender(),
                                                            referee.getAddress(),
                                                            referee.getProfession(),
                                                            referee.getPhoneNumber(),
                                                            referee.getEmail(),
                                                            referee.getOccupation(),
                                                            List.of()
                                                    )).toList()
                            )).toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
