package org.niewidoczniakademicy.rezerwacje.service.csv;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.csv.CsvCourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.csv.DatabaseException;
import org.niewidoczniakademicy.rezerwacje.service.repository.FacultyRepository;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.Faculty;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CourseOfStudyMapper {

    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;

    public final Set<CourseOfStudy> convert(List<CsvCourseOfStudy> courseOfStudies)
            throws DatabaseException {
        Set<String> logins = collectLogins(courseOfStudies);
        Set<String> faculties = collectFaculties(courseOfStudies);

        Map<String, SystemUser> login2user = collectUsers(logins);
        Map<String, Faculty> name2faculty = collectFaculties(faculties);

        return courseOfStudies.stream().map(course ->
            CourseOfStudy.builder()
                    .name(course.getName())
                    .faculty(name2faculty.get(course.getFaculty()))
                    .courseType(course.getCourseType())
                    .contactPerson1(login2user.get(course.getContactPerson1Login()))
                    .contactPerson2(login2user.getOrDefault(course.getContactPerson1Login(), null))
                    .isJoined(course.getIsJoined())
                    .remarks(course.getRemarks())
                    .build()
        ).collect(Collectors.toSet());
    }

    private Set<String> collectLogins(List<CsvCourseOfStudy> courseOfStudies) {
        Set<String> logins = new HashSet<>();
        courseOfStudies.forEach(c ->  {
            logins.add(c.getContactPerson1Login());
            if (c.getContactPerson2Login() != null) {
                logins.add(c.getContactPerson2Login());
            }
        });
        return logins;
    }

    private Set<String> collectFaculties(List<CsvCourseOfStudy> courseOfStudies) {
        Set<String> faculties = new HashSet<>();
        courseOfStudies.forEach(c -> faculties.add(c.getFaculty()));
        return faculties;
    }

    private Map<String, SystemUser> collectUsers(Set<String> logins) throws DatabaseException {
        List<SystemUser> users = userRepository.findSystemUsersByLoginIn(logins);
        Map<String, SystemUser> login2user = new HashMap<>();

        users.forEach(user -> {
            login2user.put(user.getLogin(), user);
        });

        if (login2user.size() < logins.size()) {
            Set<String> missing = new HashSet<>(logins);
            missing.removeAll(login2user.keySet());
            throw new DatabaseException("Users not found in database: " + missing.toString());
        }
        return login2user;
    }

    private Map<String, Faculty> collectFaculties(Set<String> facultyNames) throws DatabaseException {
        List<Faculty> faculties = facultyRepository.findFacultiesByNameIn(facultyNames);
        Map<String, Faculty> name2faculty = new HashMap<>();

        faculties.forEach(faculty -> {
            name2faculty.put(faculty.getName(), faculty);
        });

        if (name2faculty.size() < facultyNames.size()) {
            Set<String> missing = new HashSet<>(facultyNames);
            missing.removeAll(name2faculty.keySet());
            throw new DatabaseException("Faculties not found in database: " + missing.toString());
        }
        return name2faculty;
    }
}
