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
        String facultyErrorMsg = String.format("Faculty with id %d not found!", dto.getFacultyId());
        Faculty opFaculty = facultyRepository.findFacultyById(dto.getFacultyId())
                .orElseThrow(() ->
                        new FacultyNotFoundException(facultyErrorMsg));

        String userErrorMsg = String.format("User with id %d not found!", dto.getContactPerson1Id());
        SystemUser opUser1 = userRepository.findById(dto.getContactPerson1Id())
                .orElseThrow(() ->
                        new UserNotFoundException(userErrorMsg));

        SystemUser opUser2;
        Long opUser2Id = dto.getContactPerson2Id();

        if (opUser2Id == null) {
            opUser2 = null;
        } else {
            opUser2 = userRepository.findById(opUser2Id)
                    .orElseThrow(() ->
                            new UserNotFoundException(String.format("User with id %d not found!", opUser2Id)));
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
