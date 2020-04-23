package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.api.exception.InvalidInputException;
import org.niewidoczniakademicy.rezerwacje.core.csv.CSVService;
import org.niewidoczniakademicy.rezerwacje.core.csv.CourseOfStudyMapper;
import org.niewidoczniakademicy.rezerwacje.core.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.dao.CourseOfStudyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping(path = "course-of-study")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CourseOfStudyApi {

    private final CourseOfStudyDAO courseOfStudyDAO;
    private final CSVService csvService;
    private final CourseOfStudyMapper courseOfStudyMapper;

    @GetMapping
    public List<CourseOfStudy> getAll() {
        return courseOfStudyDAO.findAll();
    }

    @PostMapping(path = "upload")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CourseOfStudy> uploadCourseOfStudies(@RequestParam MultipartFile file) {
        try {
            List<org.niewidoczniakademicy.rezerwacje.core.model.csv.CourseOfStudy> csvCourseOfStudies =
                    csvService.parseCoursesOfStudy(file);
            List<CourseOfStudy> courseOfStudies = courseOfStudyMapper.convert(csvCourseOfStudies);
            return courseOfStudyDAO.save(courseOfStudies);
        } catch (ParseException e) {                // TODO: handle database errors
            throw new InvalidInputException();
        }
    }
}
