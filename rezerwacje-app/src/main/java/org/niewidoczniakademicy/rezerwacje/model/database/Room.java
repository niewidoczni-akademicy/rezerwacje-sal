package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String building;

    @NonNull
    private String name;

    @NonNull
    private Integer capacity;

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private Set<ExamTerm> examTerms = new HashSet<>();

    private void addExamTerm(final ExamTerm examTerm) {
        examTerm.setRoom(this);
        this.examTerms.add(examTerm);
    }
}
