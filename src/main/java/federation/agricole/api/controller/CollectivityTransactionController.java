package federation.agricole.api.controller;

import federation.agricole.api.dto.*;
import federation.agricole.api.entity.FinancialAccount;
import federation.agricole.api.entity.Member;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.service.CollectivityTransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CollectivityTransactionController {
    CollectivityTransactionService transactionService;

    public CollectivityTransactionController(CollectivityTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/collectivities/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(transactionService.getTransactions(id, from, to).stream()
                            .map(t -> new CollectivityTransactionRest(
                                    t.getId(),
                                    t.getCreationDate(),
                                    t.getAmount(),
                                    t.getPaymentMode(),
                                    toAccountRest(t.getAccountCredited()),
                                    toMemberRest(t.getMemberDebited())
                            )).toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private Object toAccountRest(FinancialAccount account) {
        if (account == null) return null;
        return switch (account.getAccountType()) {
            case CASH -> new CashAccountRest(account.getId(), account.getAmount());
            case MOBILE_BANKING -> new MobileBankingAccountRest(account.getId(),
                    account.getHolderName(), account.getMobileBankingService(),
                    account.getMobileNumber(), account.getAmount());
            case BANK -> new BankAccountRest(account.getId(), account.getHolderName(),
                    account.getBankName(), account.getBankCode(), account.getBankBranchCode(),
                    account.getBankAccountNumber(), account.getBankAccountKey(), account.getAmount());
        };
    }

    private MemberRest toMemberRest(Member member) {
        if (member == null) return null;
        return new MemberRest(member.getId(), member.getFirstName(), member.getLastName(),
                member.getBirthDate(), member.getGender(), member.getAddress(),
                member.getProfession(), member.getPhoneNumber(), member.getEmail(),
                member.getOccupation(), List.of());
    }
}
