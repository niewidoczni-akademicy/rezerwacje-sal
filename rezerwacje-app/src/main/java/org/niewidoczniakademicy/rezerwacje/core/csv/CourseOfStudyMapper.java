package org.niewidoczniakademicy.rezerwacje.core.csv;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.csv.DatabaseException;
import org.niewidoczniakademicy.rezerwacje.core.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Faculty;
import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.dao.repository.FacultyRepository;
import org.niewidoczniakademicy.rezerwacje.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CourseOfStudyMapper {

    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;

    public CourseOfStudy convert(org.niewidoczniakademicy.rezerwacje.core.model.csv.CourseOfStudy cos) {
        return CourseOfStudy.builder().build();
    }

    public List<CourseOfStudy> convert(List<org.niewidoczniakademicy.rezerwacje.core.model.csv.CourseOfStudy> cos)
            throws DatabaseException {
        Set<String> logins = collectLogins(cos);
        Set<String> faculties = collectFaculties(cos);

        Map<String, SystemUser> login2user = collectUsers(logins);
        Map<String, Faculty> name2faculty = collectFaculties(faculties);

        return cos.stream().map(course ->
            CourseOfStudy.builder()
                    .name(course.getName())
                    .faculty(name2faculty.get(course.getName()))
                    .courseType(course.getCourseType())
                    .contactPerson1(login2user.get(course.getContactPerson1Login()))
                    .contactPerson2(login2user.getOrDefault(course.getContactPerson1Login(), null))
                    .isJoined(course.getIsJoined())
                    .remarks(course.getRemarks())
                    .build()
        ).collect(Collectors.toList());
    }

    private Set<String> collectLogins(List<org.niewidoczniakademicy.rezerwacje.core.model.csv.CourseOfStudy> cos) {
        Set<String> logins = new HashSet<>();
        cos.forEach(c ->  {
            logins.add(c.getContactPerson1Login());
            if (c.getContactPerson2Login() != null) {
                logins.add(c.getContactPerson2Login());
            }
        });
        return logins;
    }

    private Set<String> collectFaculties(List<org.niewidoczniakademicy.rezerwacje.core.model.csv.CourseOfStudy> cos) {
        Set<String> faculties = new HashSet<>();
        cos.forEach(c -> faculties.add(c.getFaculty()));
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
