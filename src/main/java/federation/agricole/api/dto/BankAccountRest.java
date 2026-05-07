package federation.agricole.api.dto;

import federation.agricole.api.entity.BankEnum;

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

    public String getId() { return id; }
    public String getHolderName() { return holderName; }
    public BankEnum getBankName() { return bankName; }
    public Integer getBankCode() { return bankCode; }
    public Integer getBankBranchCode() { return bankBranchCode; }
    public Integer getBankAccountNumber() { return bankAccountNumber; }
    public Integer getBankAccountKey() { return bankAccountKey; }
    public Double getAmount() { return amount; }
}
