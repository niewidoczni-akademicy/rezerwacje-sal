package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.core.model.database.ExamTerm;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.dao.CourseOfStudyDAO;
import org.niewidoczniakademicy.rezerwacje.dao.ExamTermDAO;
import org.niewidoczniakademicy.rezerwacje.dao.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(path = "exam-terms")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ExamTermApi {

    private final ExamTermDAO examTermDAO;
    private final CourseOfStudyDAO courseOfStudyDAO;
    private final RoomDAO roomDAO;

    @GetMapping
    public List<ExamTerm> getAll() {
        return examTermDAO.findAll();
    }

    @GetMapping(path = "{id}")
    public ExamTerm getOne(@PathVariable Long id) {
        return examTermDAO.find(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource " + id));
    }

    @GetMapping(path = "room/{id}")
    public Set<ExamTerm> getByRoom(@PathVariable Long id) {
        return roomDAO.find(id).map(Room::getExamTerms)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource " + id));
    }

    @GetMapping(path = "cos/{id}")
    public Set<ExamTerm> getByExam(@PathVariable Long id) {
        return courseOfStudyDAO.find(id).map(CourseOfStudy::getExamTerms)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource " + id));
    }

    @GetMapping(path = "cos/{cosId}/room/{roomId}")
    public ExamTerm getOneByRoomAndExam(@PathVariable Long roomId,
                                        @PathVariable Long cosId) {
        return examTermDAO.find(roomId, cosId)
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
        CourseOfStudy cos = courseOfStudyDAO.find(cosId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find Course Of Study with id " + cosId));
        Room room = roomDAO.find(roomId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find Room with id " + roomId));

        ExamTerm examTerm = new ExamTerm(day, timeStart, timeEnd, cos, room);
        examTermDAO.save(examTerm);

    }
}
