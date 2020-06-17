package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recruitment_id", "room_id"})
})
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"recruitment", "room", "examTerms"})
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @NonNull
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "room_id")
    private Room room;

    @Basic
    @NonNull
    private LocalTime availableFrom;

    @Basic
    @NonNull
    private LocalTime availableTo;

    @Builder.Default
    @ToString.Exclude
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitmentRoom")
    private Set<ExamTerm> examTerms = new HashSet<>();

    private void addExamTerm(final ExamTerm examTerm) {
        examTerm.setRecruitmentRoom(this);
        this.examTerms.add(examTerm);
    }

    public final void addRecruitment(final Recruitment recruitment) {
        this.recruitment = recruitment;
        this.recruitment.getRecruitmentRooms().add(this);
    }

    public final void addRoom(final Room room) {
        this.room = room;
        this.room.getRecruitmentRooms().add(this);
    }
}
