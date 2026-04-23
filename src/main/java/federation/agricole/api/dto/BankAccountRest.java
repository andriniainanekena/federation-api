package federation.agricole.api.dto;


import federation.agricole.api.entity.BankEnum;
import lombok.Getter;

@Getter
public class BankAccountRest {
    private String id;
    private String holderName;
    private BankEnum bankName;
    private Integer bankCode;
    private Integer bankBranchCode;
    private Integer bankAccountNumber;
    private Integer bankAccountKey;
    private Double amount;

    public BankAccountRest(String id, String holderName, BankEnum bankName,
                           Integer bankCode, Integer bankBranchCode,
                           Integer bankAccountNumber, Integer bankAccountKey, Double amount) {
        this.id = id;
        this.holderName = holderName;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.bankBranchCode = bankBranchCode;
        this.bankAccountNumber = bankAccountNumber;
        this.bankAccountKey = bankAccountKey;
        this.amount = amount;
    }

}
