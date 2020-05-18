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

import java.util.Optional;


@AllArgsConstructor
@Component
public final class CourseOfStudyConverter
        implements GenericConverter<AddCourseOfStudyRequest, CourseOfStudy> {

    private final UserRepository userRepository;
    private final FacultyRepository facultyRepository;

    @Override
    public CourseOfStudy createFrom(final AddCourseOfStudyRequest dto) {
        Optional<Faculty> opFaculty = facultyRepository.findFacultyByName(dto.getFaculty());
        Optional<SystemUser> opUser1 = userRepository.findByLogin(dto.getContactPerson1());
        Optional<SystemUser> opUser2 = userRepository.findByLogin(dto.getContactPerson2());
        CourseType type = dto.getCourseType().equals("FULL_TIME") ? CourseType.FULL_TIME : CourseType.EXTERNAL;

        if (opFaculty.isPresent() && opUser1.isPresent()) {
            // Check if Contact Person 2 was given but is missing in DB
            if (!dto.getContactPerson2().equals("") && opUser2.isEmpty()) {
                String msg = String.format("User %s not found!", dto.getContactPerson2());
                throw new UserNotFoundException(msg);
            }

            return CourseOfStudy.builder()
                    .name(dto.getName())
                    .faculty(opFaculty.get())
                    .courseType(type)
                    .contactPerson1(opUser1.get())
                    .contactPerson2(opUser2.orElse(null))
                    .isJoined(dto.getIsJoined())
                    .remarks(dto.getRemarks())
                    .build();
        } else {
            // Either Contact Person 1 or Faculty is missing
            if (opUser1.isEmpty()) {
                String msg = String.format("User %s not found!", dto.getContactPerson1());
                throw new UserNotFoundException(msg);
            }

            String msg = String.format("Faculty %s not found!", dto.getFaculty());
            throw new FacultyNotFoundException(msg);
        }
    }
}
