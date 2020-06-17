package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.database.UserCourses;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.CourseAndUserConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.service.exception.ConnectionNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.UserCoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class UserCourseService {

    private final UserCoursesRepository userCoursesRepository;

    public CourseAndUserConnectionResponse connectUserAndCourse(final SystemUser systemUser,
                                                                final CourseOfStudy courseOfStudy) {
        final UserCourses userCourses = UserCourses.builder()
                .systemUser(systemUser)
                .courseOfStudy(courseOfStudy)
                .build();

        userCourses.addSystemUser(systemUser);
        userCourses.addCourseOfStudy(courseOfStudy);
        userCoursesRepository.save(userCourses);

        return CourseAndUserConnectionResponse.builder()
                .courseOfStudyId(courseOfStudy.getId())
                .systemUserId(systemUser.getId())
                .build();
    }

    public CourseAndUserConnectionResponse disconnectUserAndCourse(final SystemUser systemUser,
                                                                   final CourseOfStudy courseOfStudy) {

        final UserCourses userCourses = userCoursesRepository
                .findBySystemUserAndCourseOfStudy(systemUser, courseOfStudy)
                .orElseThrow(() ->
                        new ConnectionNotFoundException("Connection between " + systemUser.getId()
                        + " and " + courseOfStudy.getId() + " not found"));


        userCoursesRepository.delete(userCourses);

        return CourseAndUserConnectionResponse.builder()
                .courseOfStudyId(courseOfStudy.getId())
                .systemUserId(systemUser.getId())
                .build();
    }


}
