package federation.agricole.api.dto;


import federation.agricole.api.entity.MobileBankingServiceEnum;
import lombok.Getter;

@Getter
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

}
