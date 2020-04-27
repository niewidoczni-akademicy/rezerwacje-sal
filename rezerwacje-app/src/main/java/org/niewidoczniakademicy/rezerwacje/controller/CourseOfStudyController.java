package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCourseOfStudiesResponse;
import org.niewidoczniakademicy.rezerwacje.service.CourseOfStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "course-of-study")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CourseOfStudyController {

    private final CourseOfStudyService courseOfStudyService;

    @GetMapping
    public ResponseEntity<GetCourseOfStudiesResponse> getAll() {
        GetCourseOfStudiesResponse response = courseOfStudyService.getAllResponse();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "upload")
    public ResponseEntity<GetCourseOfStudiesResponse> uploadCourseOfStudies(@RequestParam MultipartFile file) {
        GetCourseOfStudiesResponse response = courseOfStudyService.uploadCourseOfStudiesResponse(file);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
