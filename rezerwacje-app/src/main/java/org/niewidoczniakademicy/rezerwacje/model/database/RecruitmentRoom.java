package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recruitment_id", "room_id"})
})
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "room_id")
    private Room room;

    public final void addRecruitment(final Recruitment recruitment) {
        this.recruitment = recruitment;
        this.recruitment.getRecruitmentRooms().add(this);
    }

    public final void addRoom(final Room room) {
        this.room = room;
        this.room.getRecruitmentRooms().add(this);
    }
}
