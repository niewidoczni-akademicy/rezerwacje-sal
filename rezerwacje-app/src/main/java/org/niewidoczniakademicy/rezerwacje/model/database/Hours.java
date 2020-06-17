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
import org.niewidoczniakademicy.rezerwacje.model.shared.DayOfWeek;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalTime;

@Entity
@Table
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"room"})
@NoArgsConstructor
@AllArgsConstructor
public class Hours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Basic
    @NonNull
    private LocalTime timeStart;

    @Basic
    @NonNull
    private LocalTime timeEnd;

    @NonNull
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "room_id")
    private Room room;
}
