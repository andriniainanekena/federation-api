package federation.agricole.api.dto;

import federation.agricole.api.entity.GenderEnum;
import federation.agricole.api.entity.MemberOccupationEnum;

import java.time.LocalDate;
import java.util.List;

public class MemberRest {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private GenderEnum gender;
    private String address;
    private String profession;
    private String phoneNumber;
    private String email;
    private MemberOccupationEnum occupation;
    private List<MemberRest> referees;

    public MemberRest(String id, String firstName, String lastName, LocalDate birthDate,
                      GenderEnum gender, String address, String profession, String phoneNumber,
                      String email, MemberOccupationEnum occupation, List<MemberRest> referees) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.occupation = occupation;
        this.referees = referees;
    }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public GenderEnum getGender() { return gender; }
    public String getAddress() { return address; }
    public String getProfession() { return profession; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public MemberOccupationEnum getOccupation() { return occupation; }
    public List<MemberRest> getReferees() { return referees; }
}
