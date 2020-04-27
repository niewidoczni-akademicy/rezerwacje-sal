package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.AddExamTermRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.AddExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.GetExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.GetExamTermsResponse;
import org.niewidoczniakademicy.rezerwacje.service.ExamTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "exam-terms")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExamTermController {

    private final ExamTermService examTermService;

    @GetMapping
    public ResponseEntity<GetExamTermsResponse> getAll() {
        GetExamTermsResponse response = examTermService.getAllResponse();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<GetExamTermResponse> getOne(@PathVariable Long id) {
        GetExamTermResponse response = examTermService.getOneResponse(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "room/{id}")
    public ResponseEntity<GetExamTermsResponse> getByRoomId(@PathVariable Long id) {
        GetExamTermsResponse response = examTermService.getByRoomIdResponse(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "cos/{id}")
    public ResponseEntity<GetExamTermsResponse> getByCourseOfStudyRepositoryId(@PathVariable Long id) {
        GetExamTermsResponse response = examTermService.getByCourseOfStudyRepositoryIdResponse(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = {"room/{roomId}/cos/{cosId}", "cos/{cosId}/room/{roomId}"})
    public ResponseEntity<GetExamTermResponse> getOneByRoomIdAndCourseOfStudyId(@PathVariable Long roomId,
                                                                                @PathVariable Long cosId) {
        GetExamTermResponse response = examTermService.getByRoomIdAndCourseOfStudyRepositoryIdResponse(roomId, cosId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddExamTermResponse> addExamTerm(@RequestBody AddExamTermRequest addExamTermRequest) {
        AddExamTermResponse response = examTermService.getAddExamTermResponse(addExamTermRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
