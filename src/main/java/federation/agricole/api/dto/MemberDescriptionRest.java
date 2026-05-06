package federation.agricole.api.dto;

public class MemberDescriptionRest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String occupation;

    public MemberDescriptionRest(String id, String firstName, String lastName, String email, String occupation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.occupation = occupation;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getOccupation() { return occupation; }
}
