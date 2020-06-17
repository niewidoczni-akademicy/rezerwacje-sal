package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.AddCourseOfStudyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.EditCourseOfStudyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCourseOfStudiesResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCourseOfStudyResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCoursesOfStudiesForUserResponse;
import org.niewidoczniakademicy.rezerwacje.service.CourseOfStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "course-of-study")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CourseOfStudyController {

    private final CourseOfStudyService courseOfStudyService;

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetCourseOfStudiesResponse getAll() {
        return courseOfStudyService.getAllResponse();
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @PostMapping(path = "upload")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public GetCourseOfStudiesResponse uploadCourseOfStudies(final @RequestParam MultipartFile file) {
        return courseOfStudyService.uploadCourseOfStudiesResponse(file);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public GetCourseOfStudyResponse addCourseOfStudy(@RequestBody final AddCourseOfStudyRequest request) {
        return courseOfStudyService.saveCourse(request);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @PutMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetCourseOfStudyResponse editCourseOfStudy(@RequestBody final EditCourseOfStudyRequest request) {
        return courseOfStudyService.editCourse(request);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping(path = "history/{id}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetCourseOfStudiesResponse getHistory(@PathVariable Long id) {
        return courseOfStudyService.getHistory(id);
    }

    @Secured({"ROLE_STANDARD", "ROLE_SUPERVISOR", "ROLE_ADMINISTRATOR"})
    @GetMapping(path = "courses", params = {"userId"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public GetCoursesOfStudiesForUserResponse getCoursesOfStudiesForUser(final Long userId) {
        return courseOfStudyService.getCoursesOfStudiesForUser(userId);
    }
}
