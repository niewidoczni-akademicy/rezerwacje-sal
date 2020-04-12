package org.niewidoczniakademicy.rezerwacje.api;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.api.exception.InvalidInputException;
import org.niewidoczniakademicy.rezerwacje.core.csv.CSVService;
import org.niewidoczniakademicy.rezerwacje.core.model.course.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.repository.CourseOfStudyRepository;
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

    private final CourseOfStudyRepository cosRepository;
    private final CSVService csvService;

    @GetMapping(path = "all")
    public List<CourseOfStudy> getAll() {
        return cosRepository.findAll();
    }

    @PostMapping(path = "upload")
    @ResponseStatus(value = HttpStatus.OK)
    public void addRoom(@RequestParam MultipartFile file) {
        try {
            List<CourseOfStudy> rooms = csvService.parseCoursesOfStudy(file);
            rooms.forEach(cosRepository::save);    // TODO: to single transaction
        } catch (ParseException e) {                // TODO: handle database errors
            throw new InvalidInputException();
        }
    }
}
