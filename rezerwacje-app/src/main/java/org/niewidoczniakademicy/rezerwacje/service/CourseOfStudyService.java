package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCourseOfStudiesResponse;
import org.niewidoczniakademicy.rezerwacje.service.csv.CSVService;
import org.niewidoczniakademicy.rezerwacje.service.exception.InvalidInputException;
import org.niewidoczniakademicy.rezerwacje.service.repository.CourseOfStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class CourseOfStudyService {

    private final CourseOfStudyRepository courseOfStudyRepository;
    private final CSVService csvService;

    public GetCourseOfStudiesResponse getAllResponse() {
        Set<CourseOfStudy> courseOfStudies = new HashSet<>(this.courseOfStudyRepository.findAll());

        return GetCourseOfStudiesResponse.builder()
                .courseOfStudies(courseOfStudies)
                .build();
    }

    public GetCourseOfStudiesResponse uploadCourseOfStudiesResponse(MultipartFile file) {
        try {
            List<CourseOfStudy> courseOfStudies = csvService.parseCoursesOfStudy(file);
            courseOfStudies = courseOfStudyRepository.saveAll(courseOfStudies);
            return GetCourseOfStudiesResponse.builder()
                    .courseOfStudies(new HashSet<>(courseOfStudies))
                    .build();
        } catch (ParseException parseException) {
            throw new InvalidInputException();
        }
    }
}
