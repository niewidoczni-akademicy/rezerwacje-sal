package org.niewidoczniakademicy.rezerwacje.model.database;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Table
@Entity
@Builder
@ToString
@EqualsAndHashCode(exclude = {"rooms", "recruitmentPeriods"})
@NoArgsConstructor
@AllArgsConstructor
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NonNull
    private String name;

    @NonNull
    private LocalDateTime startTime;

    @NonNull
    private LocalDateTime endTime;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Recruitment_Room",
            joinColumns = {@JoinColumn(name = "recruitment_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private final Set<Room> rooms = new HashSet<>();

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitment")
    private final Set<RecruitmentPeriod> recruitmentPeriods = new HashSet<>();

    public final void addRoom(final Room room) {
        rooms.add(room);
    }

    public final void addRecruitmentPeriod(final RecruitmentPeriod recruitmentPeriod) {
        recruitmentPeriods.add(recruitmentPeriod);
    }

}
