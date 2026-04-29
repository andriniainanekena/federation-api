package federation.agricole.api.controller;

import federation.agricole.api.dto.CollectivityInformationRest;
import federation.agricole.api.dto.CollectivityRest;
import federation.agricole.api.dto.CollectivityStructureRest;
import federation.agricole.api.dto.MemberRest;
import federation.agricole.api.entity.Collectivity;
import federation.agricole.api.entity.Member;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.service.CollectivityInformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectivityInformationController {
    CollectivityInformationService collectivityInformationService;

    public CollectivityInformationController(CollectivityInformationService collectivityInformationService) {
        this.collectivityInformationService = collectivityInformationService;
    }

    @PutMapping("/collectivities/{id}/informations")
    public ResponseEntity<?> updateInformation(@PathVariable String id,
                                               @RequestBody CollectivityInformationRest dto) {
        try {
            Collectivity collectivity = collectivityInformationService.updateInformation(id, dto);
            return ResponseEntity.status(HttpStatus.OK).body(toCollectivityRest(collectivity));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private CollectivityRest toCollectivityRest(Collectivity collectivity) {
        return new CollectivityRest(
                collectivity.getId(),
                collectivity.getName(),
                collectivity.getNumber(),
                collectivity.getLocation(),
                collectivity.getStructure() != null ? new CollectivityStructureRest(
                        toMemberRest(collectivity.getStructure().getPresident()),
                        toMemberRest(collectivity.getStructure().getVicePresident()),
                        toMemberRest(collectivity.getStructure().getTreasurer()),
                        toMemberRest(collectivity.getStructure().getSecretary())
                ) : null,
                collectivity.getMembers() != null
                        ? collectivity.getMembers().stream().map(this::toMemberRest).toList()
                        : List.of()
        );
    }

    private MemberRest toMemberRest(Member member) {
        if (member == null) return null;
        return new MemberRest(member.getId(), member.getFirstName(), member.getLastName(),
                member.getBirthDate(), member.getGender(), member.getAddress(),
                member.getProfession(), member.getPhoneNumber(), member.getEmail(),
                member.getOccupation(), List.of());
    }
}
