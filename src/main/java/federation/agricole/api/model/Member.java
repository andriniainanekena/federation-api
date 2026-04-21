package federation.agricole.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import federation.agricole.api.model.enums.*;

@Entity
@Table(name = "member")
public class Member {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;
    private String profession;
    private String phoneNumber;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberOccupation occupation;

    private LocalDate adhesionDate;

    private String collectivityId;

    @ManyToMany
    @JoinTable(
        name = "member_referees",
        joinColumns = @JoinColumn(name = "member_id"),
        inverseJoinColumns = @JoinColumn(name = "referee_id")
    )
    private List<Member> referees;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MemberOccupation getOccupation() {
        return occupation;
    }

    public void setOccupation(MemberOccupation occupation) {
        this.occupation = occupation;
    }

    public LocalDate getAdhesionDate() {
        return adhesionDate;
    }

    public void setAdhesionDate(LocalDate adhesionDate) {
        this.adhesionDate = adhesionDate;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public List<Member> getReferees() {
        return referees;
    }

    public void setReferees(List<Member> referees) {
        this.referees = referees;
    }
}
