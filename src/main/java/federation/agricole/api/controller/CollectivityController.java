package federation.agricole.api.controller;


import federation.agricole.api.dto.CollectivityRest;
import federation.agricole.api.dto.CollectivityStructureRest;
import federation.agricole.api.dto.CreateCollectivityRest;
import federation.agricole.api.dto.MemberRest;
import federation.agricole.api.entity.Collectivity;
import federation.agricole.api.entity.Member;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.repository.CollectivityRepository;
import federation.agricole.api.service.CollectivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CollectivityController {
    CollectivityService collectivityService;
    CollectivityRepository collectivityRepository;

    public CollectivityController(CollectivityService collectivityService,
                                  CollectivityRepository collectivityRepository) {
        this.collectivityService = collectivityService;
        this.collectivityRepository = collectivityRepository;
    }

    @PostMapping("/collectivities")
    public ResponseEntity<?> createCollectivities(
            @RequestBody List<CreateCollectivityRest> createCollectivityRestList) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(collectivityService.createCollectivities(createCollectivityRestList).stream()
                            .map(this::toCollectivityRest)
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}")
    public ResponseEntity<?> getCollectivityById(@PathVariable String id) {
        try {
            Collectivity collectivity = collectivityRepository.findByIdFull(id)
                    .orElseThrow(() -> new NotFoundException("Collectivity.id=" + id + " is not found"));
            return ResponseEntity.status(HttpStatus.OK).body(toCollectivityRest(collectivity));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private CollectivityRest toCollectivityRest(Collectivity collectivity) {
        CollectivityStructureRest structureRest = null;
        if (collectivity.getStructure() != null) {
            structureRest = new CollectivityStructureRest(
                    toMemberRest(collectivity.getStructure().getPresident()),
                    toMemberRest(collectivity.getStructure().getVicePresident()),
                    toMemberRest(collectivity.getStructure().getTreasurer()),
                    toMemberRest(collectivity.getStructure().getSecretary())
            );
        }
        List<MemberRest> members = collectivity.getMembers() != null
                ? collectivity.getMembers().stream().map(this::toMemberRest).toList()
                : List.of();

        return new CollectivityRest(
                collectivity.getId(),
                collectivity.getName(),
                collectivity.getNumber(),
                collectivity.getLocation(),
                structureRest,
                members
        );
    }

    private MemberRest toMemberRest(Member member) {
        if (member == null) return null;
        return new MemberRest(
                member.getId(), member.getFirstName(), member.getLastName(),
                member.getBirthDate(), member.getGender(), member.getAddress(),
                member.getProfession(), member.getPhoneNumber(), member.getEmail(),
                member.getOccupation(), List.of()
        );
    }
}
