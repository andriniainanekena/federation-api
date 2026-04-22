package federation.agricole.api.controller;

import federation.agricole.api.dto.CollectivityRest;
import federation.agricole.api.dto.CollectivityStructureRest;
import federation.agricole.api.dto.CreateCollectivityRest;
import federation.agricole.api.dto.MemberRest;
import federation.agricole.api.entity.Member;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.service.CollectivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectivityController {
    CollectivityService collectivityService;

    public CollectivityController(CollectivityService collectivityService) {
        this.collectivityService = collectivityService;
    }

    @PostMapping("/collectivities")
    public ResponseEntity<?> createCollectivities(
            @RequestBody List<CreateCollectivityRest> createCollectivityRestList) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(collectivityService.createCollectivities(createCollectivityRestList).stream()
                            .map(collectivity -> new CollectivityRest(
                                    collectivity.getId(),
                                    collectivity.getLocation(),
                                    new CollectivityStructureRest(
                                            toMemberRest(collectivity.getStructure().getPresident()),
                                            toMemberRest(collectivity.getStructure().getVicePresident()),
                                            toMemberRest(collectivity.getStructure().getTreasurer()),
                                            toMemberRest(collectivity.getStructure().getSecretary())
                                    ),
                                    collectivity.getMembers().stream()
                                            .map(this::toMemberRest)
                                            .toList()
                            )).toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private MemberRest toMemberRest(Member member) {
        return new MemberRest(
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
                List.of()
        );
    }
}
