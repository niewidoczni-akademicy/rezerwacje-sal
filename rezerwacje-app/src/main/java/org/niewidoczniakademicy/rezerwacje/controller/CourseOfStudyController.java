package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCourseOfStudiesResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.CourseAndUserConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.service.CourseOfStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "course-of-study")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class CourseOfStudyController {

    private final CourseOfStudyService courseOfStudyService;

    @GetMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetCourseOfStudiesResponse getAll() {
        return courseOfStudyService.getAllResponse();
    }

    @PostMapping(path = "upload")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public GetCourseOfStudiesResponse uploadCourseOfStudies(final @RequestParam MultipartFile file) {
        return courseOfStudyService.uploadCourseOfStudiesResponse(file);
    }

    @PostMapping(path = "connect", params = {"login", "courseOfStudyId"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public CourseAndUserConnectionResponse addCourseOfStudyToUser(@RequestParam final String login,
                                                                  @RequestParam final Long courseOfStudyId) {

        return courseOfStudyService.connectCourseOfStudyWithSystemUser(login, courseOfStudyId);
    }
}
