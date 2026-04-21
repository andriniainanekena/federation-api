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

    // getters + setters
}
