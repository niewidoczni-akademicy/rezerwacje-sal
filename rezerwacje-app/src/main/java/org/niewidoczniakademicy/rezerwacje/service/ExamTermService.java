package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.ExamTerm;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.AddExamTermRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.AddExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.GetExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.GetExamTermsResponse;
import org.niewidoczniakademicy.rezerwacje.service.exception.CourseOfStudyNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.exception.ExamTermNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.exception.ExamTermTimeEndBeforeTimeStartException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RoomNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.CourseOfStudyRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.ExamTermRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ExamTermService {

    private final ExamTermRepository examTermRepository;
    private final CourseOfStudyRepository courseOfStudyRepository;
    private final RoomRepository roomRepository;

    public GetExamTermsResponse getAllResponse() {
        Set<ExamTerm> examTerms = new HashSet<>(this.examTermRepository.findAll());

        return GetExamTermsResponse.builder()
                .examTerms(examTerms)
                .build();
    }

    public GetExamTermResponse getOneResponse(Long id) {
        ExamTerm examTerm = this.examTermRepository.findById(id)
                .orElseThrow(() -> new ExamTermNotFoundException("No exam term with id " + id));

        return GetExamTermResponse.builder()
                .examTerm(examTerm)
                .build();
    }

    public GetExamTermsResponse getByRoomIdResponse(Long id) {
        Set<ExamTerm> examTerms = this.roomRepository.findById(id)
                .map(Room::getExamTerms)
                .orElseThrow(() -> new RoomNotFoundException("No room with id " + id));

        return GetExamTermsResponse.builder()
                .examTerms(examTerms)
                .build();
    }

    public GetExamTermsResponse getByCourseOfStudyRepositoryIdResponse(Long id) {
        Set<ExamTerm> examTerms = this.courseOfStudyRepository.findById(id)
                .map(CourseOfStudy::getExamTerms)
                .orElseThrow(() -> new CourseOfStudyNotFoundException("No course of study with id " + id));

        return GetExamTermsResponse.builder()
                .examTerms(examTerms)
                .build();
    }

    public GetExamTermResponse getByRoomIdAndCourseOfStudyRepositoryIdResponse(Long roomId, Long cosId) {
        ExamTerm examTerm = this.examTermRepository.findByRoomIdAndCourseOfStudyId(roomId, cosId)
                .orElseThrow(() -> new ExamTermNotFoundException(String.format(
                        "No exam term with room id %d and course of study id %d", roomId, cosId)));

        return GetExamTermResponse.builder()
                .examTerm(examTerm)
                .build();
    }

    public AddExamTermResponse getAddExamTermResponse(AddExamTermRequest addExamTermRequest) {
        Long cosId = addExamTermRequest.getCosId();
        Long roomId = addExamTermRequest.getRoomId();
        LocalDate day = addExamTermRequest.getDay();
        LocalTime timeStart = addExamTermRequest.getTimeStart();
        LocalTime timeEnd = addExamTermRequest.getTimeEnd();
        validateExamTermTime(timeStart, timeEnd);

        CourseOfStudy courseOfStudy = courseOfStudyRepository
                .findById(cosId)
                .orElseThrow(() -> new CourseOfStudyNotFoundException("No course of study with id " + cosId));

        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("No room with id " + roomId));


        ExamTerm examTerm = new ExamTerm(
                day,
                timeStart,
                timeEnd,
                courseOfStudy,
                room);

        Long examTermId = examTermRepository.save(examTerm).getId();

        return AddExamTermResponse.builder()
                .examTermId(examTermId)
                .build();

    }

    private void validateExamTermTime(LocalTime timeStart, LocalTime timeEnd) {
        if (timeEnd.isBefore(timeStart)) {
            final String exceptionMessage = String.format(
                    "Exam ending time %s cannot be before starting time %s", timeEnd.toString(), timeStart.toString());
            throw new ExamTermTimeEndBeforeTimeStartException(exceptionMessage);
        }
    }


}
