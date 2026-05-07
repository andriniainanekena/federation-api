package federation.agricole.api.entity;

public class FinancialAccount {
    private String id;
    private AccountTypeEnum accountType;
    private Double amount;

    private String holderName;
    private BankEnum bankName;
    private Integer bankCode;
    private Integer bankBranchCode;
    private Integer bankAccountNumber;
    private Integer bankAccountKey;

    private MobileBankingServiceEnum mobileBankingService;
    private String mobileNumber;

    private String collectivityId;

    public FinancialAccount() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public AccountTypeEnum getAccountType() { return accountType; }
    public void setAccountType(AccountTypeEnum accountType) { this.accountType = accountType; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }
    public BankEnum getBankName() { return bankName; }
    public void setBankName(BankEnum bankName) { this.bankName = bankName; }
    public Integer getBankCode() { return bankCode; }
    public void setBankCode(Integer bankCode) { this.bankCode = bankCode; }
    public Integer getBankBranchCode() { return bankBranchCode; }
    public void setBankBranchCode(Integer bankBranchCode) { this.bankBranchCode = bankBranchCode; }
    public Integer getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(Integer bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }
    public Integer getBankAccountKey() { return bankAccountKey; }
    public void setBankAccountKey(Integer bankAccountKey) { this.bankAccountKey = bankAccountKey; }
    public MobileBankingServiceEnum getMobileBankingService() { return mobileBankingService; }
    public void setMobileBankingService(MobileBankingServiceEnum mobileBankingService) { this.mobileBankingService = mobileBankingService; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }
}
