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
import org.niewidoczniakademicy.rezerwacje.service.repository.CourseOfStudyRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.ExamTermRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExamTermService { // Validations will be fixed in [NIAK -25]

    private final ExamTermRepository examTermRepository;
    private final CourseOfStudyRepository courseOfStudyRepository;
    private final RoomRepository roomRepository;

    public GetExamTermsResponse getAllResponse() {
        Set<ExamTerm> examTerms = new HashSet<ExamTerm>(this.examTermRepository.findAll());

        return GetExamTermsResponse.builder()
                .examTerms(examTerms)
                .build();
    }

    public GetExamTermResponse getOneResponse(Long id) {
        ExamTerm examTerm = this.examTermRepository.getOne(id);

        return GetExamTermResponse.builder()
                .examTerm(examTerm)
                .build();
    }

    public GetExamTermsResponse getByRoomIdResponse(Long id) {
        Set<ExamTerm> examTerms = this.roomRepository.findById(id).map(Room::getExamTerms).get();

        return GetExamTermsResponse.builder()
                .examTerms(examTerms)
                .build();
    }

    public GetExamTermsResponse getByCourseOfStudyRepositoryIdResponse(Long id) {
        Set<ExamTerm> examTerms = this.courseOfStudyRepository.findById(id).map(CourseOfStudy::getExamTerms).get();

        return GetExamTermsResponse.builder()
                .examTerms(examTerms)
                .build();
    }

    public GetExamTermResponse getByRoomIdAndCourseOfStudyRepositoryIdResponse(Long roomId, Long cosId) {
        ExamTerm examTerm = this.examTermRepository.findByRoomIdAndCourseOfStudyId(roomId, cosId).get();

        return GetExamTermResponse.builder()
                .examTerm(examTerm)
                .build();
    }

    public AddExamTermResponse getAddExamTermResponse(AddExamTermRequest addExamTermRequest) {
        CourseOfStudy courseOfStudy = courseOfStudyRepository
                .findById(addExamTermRequest.getCosId())
                .get();
        Room room = roomRepository
                .findById(addExamTermRequest.getRoomId())
                .get();

        ExamTerm examTerm = new ExamTerm(
                addExamTermRequest.getDay(),
                addExamTermRequest.getTimeStart(),
                addExamTermRequest.getTimeEnd(),
                courseOfStudy,
                room);

        Long examTermId = examTermRepository.save(examTerm).getId();

        return AddExamTermResponse.builder()
                .examTermId(examTermId)
                .build();

    }


}
