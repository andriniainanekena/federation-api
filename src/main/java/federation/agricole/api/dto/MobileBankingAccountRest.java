package federation.agricole.api.dto;

import federation.agricole.api.entity.MobileBankingServiceEnum;

public class MobileBankingAccountRest {
    private String id;
    private String holderName;
    private MobileBankingServiceEnum mobileBankingService;
    private String mobileNumber;
    private Double amount;

    public MobileBankingAccountRest(String id, String holderName,
                                    MobileBankingServiceEnum mobileBankingService,
                                    String mobileNumber, Double amount) {
        this.id = id;
        this.holderName = holderName;
        this.mobileBankingService = mobileBankingService;
        this.mobileNumber = mobileNumber;
        this.amount = amount;
    }

    public String getId() { return id; }
    public String getHolderName() { return holderName; }
    public MobileBankingServiceEnum getMobileBankingService() { return mobileBankingService; }
    public String getMobileNumber() { return mobileNumber; }
    public Double getAmount() { return amount; }
}
