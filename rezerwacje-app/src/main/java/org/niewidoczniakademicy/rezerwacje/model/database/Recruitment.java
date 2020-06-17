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
import org.niewidoczniakademicy.rezerwacje.model.shared.RecruitmentStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@EqualsAndHashCode(exclude = {"recruitmentRooms", "recruitmentPeriods"})
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

    @NonNull
    @Enumerated(EnumType.STRING)
    private RecruitmentStatus recruitmentStatus;

    @Builder.Default
    @ToString.Exclude
    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitment")
    private Set<RecruitmentRoom> recruitmentRooms = new HashSet<>();

    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitment")
    private final Set<RecruitmentPeriod> recruitmentPeriods = new HashSet<>();

}
