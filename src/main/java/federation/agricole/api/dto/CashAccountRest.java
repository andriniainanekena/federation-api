package federation.agricole.api.dto;

public class CashAccountRest {
    private String id;
    private Double amount;

    public CashAccountRest(String id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() { return id; }
    public Double getAmount() { return amount; }
}
