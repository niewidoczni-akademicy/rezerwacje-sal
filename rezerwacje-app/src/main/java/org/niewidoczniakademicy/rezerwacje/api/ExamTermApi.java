package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.core.model.database.ExamTerm;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.repository.CourseOfStudyRepository;
import org.niewidoczniakademicy.rezerwacje.repository.ExamTermRepository;
import org.niewidoczniakademicy.rezerwacje.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "exam-terms")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExamTermApi {

    private final ExamTermRepository examTermRepository;
    private final CourseOfStudyRepository cosRepository;
    private final RoomRepository roomRepository;

    @GetMapping
    public List<ExamTerm> getAll() {
        return examTermRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public ExamTerm getOne(@PathVariable Long id) {
        return examTermRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource " + id));
    }

    @GetMapping(path = "room/{id}")
    public Set<ExamTerm> getByRoom(@PathVariable Long id) {
        return roomRepository.findById(id).map(Room::getExamTerms)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource " + id));
    }

    @GetMapping(path = "cos/{id}")
    public Set<ExamTerm> getByExam(@PathVariable Long id) {
        return cosRepository.findById(id).map(CourseOfStudy::getExamTerms)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource " + id));
    }

    @GetMapping(path = "cos/{cosId}/room/{roomId}")
    public ExamTerm getOneByRoomAndExam(@PathVariable Long roomId,
                                        @PathVariable Long cosId) {
        return examTermRepository.findByRoomIdAndCourseOfStudyId(roomId, cosId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                        "Unable to find resource with room id " + roomId + " and course of study id" + cosId));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addExamTerm(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate day, // TODO Global config
                            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime timeStart,
                            @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime timeEnd,
                            @RequestParam Long cosId,
                            @RequestParam Long roomId
    ) {
        // TODO date/time validations
        CourseOfStudy cos = cosRepository.findById(cosId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find Course Of Study with id " + cosId));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find Room with id " + roomId));

        ExamTerm examTerm = new ExamTerm(day, timeStart, timeEnd, cos, room);
        examTermRepository.save(examTerm);

    }
}
