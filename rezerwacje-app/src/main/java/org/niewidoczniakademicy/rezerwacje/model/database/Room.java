package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.univocity.parsers.annotations.Parsed;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"building", "name"})
})
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"examTerms"})
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Parsed
    @NonNull
    private String building;

    @Parsed
    @NonNull
    private String name;

    @Parsed
    @NonNull
    private Integer capacity;

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private Set<ExamTerm> examTerms = new HashSet<>();

    private void addExamTerm(ExamTerm examTerm) {
        examTerm.setRoom(this);
        this.examTerms.add(examTerm);
    }
}
