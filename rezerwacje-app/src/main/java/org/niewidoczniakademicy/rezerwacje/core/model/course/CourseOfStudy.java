package org.niewidoczniakademicy.rezerwacje.core.model.course;

import com.univocity.parsers.annotations.EnumOptions;
import com.univocity.parsers.annotations.Nested;
import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.niewidoczniakademicy.rezerwacje.core.csv.ContactPersonHeaderTransformer;
import org.niewidoczniakademicy.rezerwacje.core.model.Faculty;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Person;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Parsed(field = "course name")
    @NonNull
    private String name;

    @Nested
    @NonNull
    @ManyToOne
    private Faculty faculty;

    @Parsed(field = "course type")
    @EnumOptions(customElement = "typeCode")
    @NonNull
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @Nested(headerTransformer  = ContactPersonHeaderTransformer.class, args = "c1")
    @NonNull
    @ManyToOne
    @JoinColumn(name = "contact_person1_id")
    private Person contactPerson1;    // TODO: does it have to be a User in DB or just anyone?

    @Nested(headerTransformer  = ContactPersonHeaderTransformer.class, args = "c2")
    @ManyToOne
    @JoinColumn(name = "contact_person2_id")
    private Person contactPerson2;

    @Parsed(field = "joined")
    @NonNull
    private Boolean isJoined;

    @Parsed
    private String remarks;         // TODO: separate table?

    // TODO: typ egzaminu???
}
