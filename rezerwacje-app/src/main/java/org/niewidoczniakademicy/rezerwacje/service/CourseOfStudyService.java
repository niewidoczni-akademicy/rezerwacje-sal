package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.csv.CsvCourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.csv.DatabaseException;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.AddCourseOfStudyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCourseOfStudiesResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.GetCourseOfStudyResponse;
import org.niewidoczniakademicy.rezerwacje.service.converter.ConversionService;
import org.niewidoczniakademicy.rezerwacje.service.csv.CSVService;
import org.niewidoczniakademicy.rezerwacje.service.csv.CourseOfStudyMapper;
import org.niewidoczniakademicy.rezerwacje.service.exception.CourseOfStudyNotFoundException;
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

    private final ConversionService conversionService;
    private final CourseOfStudyRepository courseOfStudyRepository;
    private final CSVService csvService;
    private final CourseOfStudyMapper courseOfStudyMapper;

    public GetCourseOfStudiesResponse getAllResponse() {
        Set<CourseOfStudy> courseOfStudies = new HashSet<>(this.courseOfStudyRepository.findAll());

        return GetCourseOfStudiesResponse.builder()
                .courseOfStudies(courseOfStudies)
                .build();
    }

    public GetCourseOfStudiesResponse uploadCourseOfStudiesResponse(final MultipartFile file) {
        // TODO: provide better error messages
        try {
            List<CsvCourseOfStudy> csvCourseOfStudies = csvService.parseCoursesOfStudy(file);
            Set<CourseOfStudy> courseOfStudies = courseOfStudyMapper.convert(csvCourseOfStudies);
            courseOfStudyRepository.saveAll(courseOfStudies);

            return GetCourseOfStudiesResponse.builder()
                    .courseOfStudies(courseOfStudies)
                    .build();
        } catch (ParseException e) {
            // TODO: extract more details from CSV parser
            throw new InvalidInputException("Error occurred while parsing CSV file!");
        } catch (DatabaseException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    public CourseOfStudy getCourseOfStudyFromDatabaseById(final Long courseOfStudyId) {
        return courseOfStudyRepository
                .findById(courseOfStudyId)
                .orElseThrow(
                        () -> new CourseOfStudyNotFoundException("No course of study with id: " + courseOfStudyId));
    }

    public GetCourseOfStudyResponse saveCourse(final AddCourseOfStudyRequest request) {
        CourseOfStudy course = conversionService.convert(request);
        courseOfStudyRepository.save(course);

        return GetCourseOfStudyResponse.builder()
                .courseOfStudy(course)
                .build();
    }
}
