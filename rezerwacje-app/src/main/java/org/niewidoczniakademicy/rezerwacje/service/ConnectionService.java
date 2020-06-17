package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.CourseAndUserConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.RecruitmentAndRecruitmentPeriodConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom.RecruitmentAndRoomConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom.RecruitmentAndRoomsConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom.RoomsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ConnectionService {

    private final UserService userService;
    private final RoomService roomService;
    private final UserCourseService userCourseService;
    private final RecruitmentService recruitmentService;
    private final CourseOfStudyService courseOfStudyService;
    private final RecruitmentRoomService recruitmentRoomService;
    private final RecruitmentPeriodService recruitmentPeriodService;
    private final RecruitmentPeriodRecruitmentService recruitmentPeriodRecruitmentService;

    public CourseAndUserConnectionResponse connectCourseOfStudyWithSystemUser(final Long userId,
                                                                              final Long courseOfStudyId) {

        final SystemUser systemUser = userService.getSystemUserById(userId);
        final CourseOfStudy courseOfStudy = courseOfStudyService.getCourseOfStudyFromDatabaseById(courseOfStudyId);

        return userCourseService.connectUserAndCourse(systemUser, courseOfStudy);
    }

    public CourseAndUserConnectionResponse disconnectCourseOfStudyWithSystemUser(final Long userId,
                                                                              final Long courseOfStudyId) {

        final SystemUser systemUser = userService.getSystemUserFromDatabaseById(userId);
        final CourseOfStudy courseOfStudy = courseOfStudyService.getCourseOfStudyFromDatabaseById(courseOfStudyId);

        return userCourseService.disconnectUserAndCourse(systemUser, courseOfStudy);
    }

    public RecruitmentAndRoomConnectionResponse connectRecruitmentWithRoom(final Long recruitmentId,
                                                                           final Long roomId) {
        final Recruitment recruitment = recruitmentService.getRecruitmentFromDatabaseById(recruitmentId);
        final Room room = roomService.getRoomFromDatabaseById(roomId);

        return recruitmentRoomService.connectRecruitmentAndRoom(recruitment, room);
    }

    public RecruitmentAndRoomsConnectionResponse connectRecruitmentWithRooms(final Long recruitmentId,
                                                                             final List<Long> roomsIds) {

        final Recruitment recruitment = recruitmentService.getRecruitmentFromDatabaseById(recruitmentId);
        for (Long roomId : roomsIds) {
            final Room room = roomService.getRoomFromDatabaseById(roomId);
            recruitmentRoomService.connectRecruitmentAndRoom(recruitment, room);
        }

        return RecruitmentAndRoomsConnectionResponse.builder()
                .recruitmentId(recruitmentId)
                .rooms(RoomsList.builder()
                        .roomsIds(roomsIds)
                        .build())
                .build();
    }

    public RecruitmentAndRecruitmentPeriodConnectionResponse connectRecruitmentWithRecruitmentPeriod(
            final Long recruitmentId,
            final Long recruitmentPeriodId) {

        final Recruitment recruitment
                = recruitmentService.getRecruitmentFromDatabaseById(recruitmentId);
        final RecruitmentPeriod recruitmentPeriod
                = recruitmentPeriodService.getRecruitmentPeriodFromDatabaseById(recruitmentPeriodId);

        return recruitmentPeriodRecruitmentService
                .connectRecruitmentWithRecruitmentPeriod(recruitment, recruitmentPeriod);
    }


}
