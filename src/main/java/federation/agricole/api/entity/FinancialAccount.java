package federation.agricole.api.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
