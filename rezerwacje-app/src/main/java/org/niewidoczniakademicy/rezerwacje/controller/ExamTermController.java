package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.AddExamTermRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.AddExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.DeleteExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.GetExamTermResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.examterm.GetExamTermsResponse;
import org.niewidoczniakademicy.rezerwacje.service.ExamTermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "exam-terms")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ExamTermController {

    private final ExamTermService examTermService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetExamTermsResponse getAll() {
        return examTermService.getAllResponse();
    }

    @GetMapping(path = "{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetExamTermResponse getOne(@PathVariable final Long id) {
        return examTermService.getOneResponse(id);
    }

    @GetMapping(path = "room/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetExamTermsResponse getByRoomId(@PathVariable final Long id) {
        return examTermService.getByRoomIdResponse(id);
    }

    @GetMapping(path = "cos/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetExamTermsResponse getByCourseOfStudyRepositoryId(@PathVariable final Long id) {
        return examTermService.getByCourseOfStudyRepositoryIdResponse(id);
    }

    @GetMapping(path = {"room/{roomId}/cos/{cosId}", "cos/{cosId}/room/{roomId}"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetExamTermResponse getOneByRoomIdAndCourseOfStudyId(@PathVariable final Long roomId,
                                                                @PathVariable final Long cosId) {
        return examTermService.getByRoomIdAndCourseOfStudyRepositoryIdResponse(roomId, cosId);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public AddExamTermResponse addExamTerm(@RequestBody final AddExamTermRequest addExamTermRequest) {
        return examTermService.getAddExamTermResponse(addExamTermRequest);
    }

    @PostMapping(path = "/set-deleted/{examTermId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public DeleteExamTermResponse setExamTermAsDeleted(@PathVariable final Long examTermId) {
        return examTermService.setExamTermAsDeleted(examTermId);
    }
}
