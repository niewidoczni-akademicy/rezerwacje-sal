package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.ExamTerm;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentRoom;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.AddExamTermRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.AddExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.DeleteOrUpdateExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.GetExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.GetExamTermsResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.UpdateExamTermRequest;
import org.niewidoczniakademicy.rezerwacje.service.exception.CourseOfStudyNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.exception.ExamTermNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.exception.ExamTermTimeEndBeforeTimeStartException;
import org.niewidoczniakademicy.rezerwacje.service.exception.ExamTermsIntersectionException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentPeriodNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RoomNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.CourseOfStudyRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.ExamTermRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentPeriodRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ExamTermService {

    private final ExamTermRepository examTermRepository;
    private final CourseOfStudyRepository courseOfStudyRepository;
    private final RecruitmentRoomRepository recruitmentRoomRepository;
    private final RecruitmentPeriodRepository recruitmentPeriodRepository;

    public GetExamTermsResponse getAllResponse() {
        final Set<ExamTerm> examTerms = new HashSet<>(this.examTermRepository.findAll())
                .stream()
                .filter(e -> !e.getDeleted())
                .collect(toSet());

        if (examTerms.isEmpty()) {
            throw new ExamTermNotFoundException("No exam terms found");
        }

        return GetExamTermsResponse.builder()
                .examTerms(examTerms)
                .build();
    }

    public GetExamTermResponse getOneResponse(final Long id) {
        final ExamTerm examTerm = this.examTermRepository.findById(id)
                .orElseThrow(() -> new ExamTermNotFoundException("No exam term with id " + id));

        if (examTerm.getDeleted()) {
            throw new ExamTermNotFoundException("No exam term with id " + id);
        }

        return GetExamTermResponse.builder()
                .examTerm(examTerm)
                .build();
    }

    public GetExamTermsResponse getByRoomIdResponse(final Long id) {
        final Set<ExamTerm> examTerms = this.recruitmentRoomRepository.findById(id)
                .map(RecruitmentRoom::getExamTerms)
                .orElseThrow(() -> new RoomNotFoundException("No room with id " + id))
                .stream()
                .filter(e -> !e.getDeleted())
                .collect(toSet());

        if (examTerms.isEmpty()) {
            throw new ExamTermNotFoundException("No exam terms found");
        }

        return GetExamTermsResponse.builder()
                .examTerms(examTerms)
                .build();
    }

    public GetExamTermsResponse getByCourseOfStudyRepositoryIdResponse(final Long id) {
        final Set<ExamTerm> examTerms = this.courseOfStudyRepository.findById(id)
                .map(CourseOfStudy::getExamTerms)
                .orElseThrow(() -> new CourseOfStudyNotFoundException("No course of study with id " + id))
                .stream()
                .filter(e -> !e.getDeleted())
                .collect(toSet());

        if (examTerms.isEmpty()) {
            throw new ExamTermNotFoundException("No exam terms found");
        }

        return GetExamTermsResponse.builder()
                .examTerms(examTerms)
                .build();
    }

    public GetExamTermResponse getByRoomIdAndCourseOfStudyRepositoryIdResponse(final Long roomId, final Long cosId) {
        final ExamTerm examTerm = this.examTermRepository.findByRecruitmentRoomIdAndCourseOfStudyId(roomId, cosId)
                .orElseThrow(() -> new ExamTermNotFoundException(String.format(
                        "No exam term with room id %d and course of study id %d", roomId, cosId)));

        if (examTerm.getDeleted()) {
            throw new ExamTermNotFoundException("No exam term found");
        }

        return GetExamTermResponse.builder()
                .examTerm(examTerm)
                .build();
    }

    public AddExamTermResponse getAddExamTermResponse(final AddExamTermRequest addExamTermRequest) {
        Long cosId = addExamTermRequest.getCourseOfStudyId();
        Long rRoomId = addExamTermRequest.getRecruitmentRoomId();
        Long recruitmentPeriodId = addExamTermRequest.getRecruitmentPeriodId();
        Integer seats = addExamTermRequest.getSeats();
        LocalDate day = addExamTermRequest.getDay();
        LocalTime timeStart = addExamTermRequest.getTimeStart();
        LocalTime timeEnd = addExamTermRequest.getTimeEnd();
        validateExamTermTime(timeStart, timeEnd);

        CourseOfStudy courseOfStudy = courseOfStudyRepository
                .findById(cosId)
                .orElseThrow(() -> new CourseOfStudyNotFoundException("No course of study with id " + cosId));

        RecruitmentRoom rRoom = recruitmentRoomRepository
                .findById(rRoomId)
                .orElseThrow(() -> new RoomNotFoundException("No room with id " + rRoomId));

        RecruitmentPeriod recruitmentPeriod = recruitmentPeriodRepository
                .findById(recruitmentPeriodId)
                .orElseThrow(() -> new RecruitmentPeriodNotFoundException(
                        "No course of study with id " + recruitmentPeriodId));

        List<Long> interSectionIds = examTermRepository.getExamTermIdsByDates(
                recruitmentPeriodId,
                day,
                timeStart,
                timeEnd
        );
        if (interSectionIds.size() != 0) {
            throw new ExamTermsIntersectionException(interSectionIds.size() + " exams already found in provided term");
        }

        ExamTerm examTerm = new ExamTerm(
                day,
                timeStart,
                timeEnd,
                recruitmentPeriod,
                courseOfStudy,
                rRoom,
                seats);

        Long examTermId = examTermRepository.save(examTerm).getId();

        return AddExamTermResponse.builder()
                .examTermId(examTermId)
                .build();

    }

    public DeleteOrUpdateExamTermResponse setExamTermAsDeleted(final Long examTermId) {
        final ExamTerm examTerm = examTermRepository.findById(examTermId)
                .orElseThrow(() -> new ExamTermNotFoundException("No exam term with room id: " + examTermId));

        examTerm.setDeleted(true);

        examTermRepository.save(examTerm);

        return DeleteOrUpdateExamTermResponse.builder()
                .examTermId(examTermId)
                .build();
    }

    public DeleteOrUpdateExamTermResponse updateExamTerm(final UpdateExamTermRequest request) {
        examTermRepository.findById(request.getId())
                .orElseThrow(() -> new ExamTermNotFoundException("No exam term with room id: " + request.getId()));

        final Integer seats = request.getSeats();
        final Long cosId = request.getCourseOfStudyId();
        final Long rRoomId = request.getRecruitmentRoomId();
        final Long recruitmentPeriodId = request.getRecruitmentPeriodId();
        final LocalDate day = request.getDay();
        final LocalTime timeStart = request.getTimeStart();
        final LocalTime timeEnd = request.getTimeEnd();
        validateExamTermTime(timeStart, timeEnd);

        final CourseOfStudy courseOfStudy = courseOfStudyRepository
                .findById(cosId)
                .orElseThrow(() -> new CourseOfStudyNotFoundException("No course of study with id " + cosId));

        final RecruitmentRoom rRoom = recruitmentRoomRepository
                .findById(rRoomId)
                .orElseThrow(() -> new RoomNotFoundException("No room with id " + rRoomId));

        final RecruitmentPeriod recruitmentPeriod = recruitmentPeriodRepository
                .findById(recruitmentPeriodId)
                .orElseThrow(() -> new RecruitmentPeriodNotFoundException(
                        "No course of study with id " + recruitmentPeriodId));

        final ExamTerm examTerm = new ExamTerm(
                day,
                timeStart,
                timeEnd,
                request.getDeleted(),
                recruitmentPeriod,
                courseOfStudy,
                rRoom,
                seats
        );

        examTermRepository.save(examTerm);

        return DeleteOrUpdateExamTermResponse.builder()
                .examTermId(examTerm.getId())
                .build();
    }

    private void validateExamTermTime(final LocalTime timeStart, final LocalTime timeEnd) {
        if (timeEnd.isBefore(timeStart)) {
            final String exceptionMessage = String.format(
                    "Exam ending time %s cannot be before starting time %s", timeEnd.toString(), timeStart.toString());
            throw new ExamTermTimeEndBeforeTimeStartException(exceptionMessage);
        }
    }


}
