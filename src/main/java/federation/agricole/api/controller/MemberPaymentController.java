package federation.agricole.api.controller;

import federation.agricole.api.dto.*;
import federation.agricole.api.entity.FinancialAccount;
import federation.agricole.api.exception.BadRequestException;
import federation.agricole.api.exception.NotFoundException;
import federation.agricole.api.service.MemberPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberPaymentController {
    MemberPaymentService memberPaymentService;

    public MemberPaymentController(MemberPaymentService memberPaymentService) {
        this.memberPaymentService = memberPaymentService;
    }

    @PostMapping("/members/{id}/payments")
    public ResponseEntity<?> createPayments(@PathVariable String id,
                                            @RequestBody List<CreateMemberPaymentRest> dtoList) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(memberPaymentService.createPayments(id, dtoList).stream()
                            .map(payment -> new MemberPaymentRest(
                                    payment.getId(),
                                    payment.getAmount(),
                                    payment.getPaymentMode(),
                                    toAccountRest(payment.getAccountCredited()),
                                    payment.getCreationDate()
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
}
