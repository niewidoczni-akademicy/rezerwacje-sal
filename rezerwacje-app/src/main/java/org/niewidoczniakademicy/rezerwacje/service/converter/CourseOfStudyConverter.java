package org.niewidoczniakademicy.rezerwacje.service.converter;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.Faculty;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.AddCourseOfStudyRequest;
import org.niewidoczniakademicy.rezerwacje.model.shared.CourseType;
import org.niewidoczniakademicy.rezerwacje.service.exception.FacultyNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.exception.UserNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.FacultyRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.UserRepository;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public final class CourseOfStudyConverter
        implements GenericConverter<AddCourseOfStudyRequest, CourseOfStudy> {

    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;

    @Override
    public CourseOfStudy createFrom(final AddCourseOfStudyRequest dto) {
        Faculty opFaculty = facultyRepository.findFacultyByName(dto.getFaculty())
                .orElseThrow(() ->
                        new FacultyNotFoundException(String.format("Faculty %s not found!", dto.getFaculty())));

        SystemUser opUser1 = userRepository.findByLogin(dto.getContactPerson1())
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User %s not found!", dto.getContactPerson1())));
        SystemUser opUser2;
        String opUser2Login = dto.getContactPerson2();
        if (opUser2Login != null) {
            opUser2 = null;
        } else {
            opUser2 = userRepository.findByLogin(opUser2Login)
                    .orElseThrow(() ->
                            new UserNotFoundException(String.format("User %s not found!", opUser2Login)));
        }
        CourseType type = dto.getCourseType().equals("FULL_TIME") ? CourseType.FULL_TIME : CourseType.EXTERNAL;


        return CourseOfStudy.builder()
                .name(dto.getName())
                .faculty(opFaculty)
                .courseType(type)
                .contactPerson1(opUser1)
                .contactPerson2(opUser2)
                .isJoined(dto.getIsJoined())
                .remarks(dto.getRemarks())
                .build();
    }
}
