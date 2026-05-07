package federation.agricole.api.controller;

import federation.agricole.api.dto.BankAccountRest;
import federation.agricole.api.dto.CashAccountRest;
import federation.agricole.api.dto.MobileBankingAccountRest;
import federation.agricole.api.entity.FinancialAccount;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.service.FinancialAccountService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class FinancialAccountController {
    FinancialAccountService financialAccountService;

    public FinancialAccountController(FinancialAccountService financialAccountService) {
        this.financialAccountService = financialAccountService;
    }

    @GetMapping("/collectivities/{id}/financialAccounts")
    public ResponseEntity<?> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(financialAccountService.getFinancialAccounts(id, at).stream()
                            .map(this::toAccountRest)
                            .toList());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private Object toAccountRest(FinancialAccount account) {
        return switch (account.getAccountType()) {
            case CASH -> new CashAccountRest(account.getId(), account.getAmount());
            case MOBILE_BANKING -> new MobileBankingAccountRest(
                    account.getId(), account.getHolderName(),
                    account.getMobileBankingService(), account.getMobileNumber(), account.getAmount());
            case BANK -> new BankAccountRest(
                    account.getId(), account.getHolderName(), account.getBankName(),
                    account.getBankCode(), account.getBankBranchCode(),
                    account.getBankAccountNumber(), account.getBankAccountKey(), account.getAmount());
        };
    }
}
