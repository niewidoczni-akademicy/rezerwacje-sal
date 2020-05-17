package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.AddCourseOfStudyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCourseOfStudiesResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCourseOfStudyResponse;
import org.niewidoczniakademicy.rezerwacje.service.CourseOfStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public GetCourseOfStudyResponse addRoom(@RequestBody final AddCourseOfStudyRequest request) {
        return courseOfStudyService.saveCourse(request);
    }
}
