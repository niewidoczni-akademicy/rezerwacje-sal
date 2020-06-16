package org.niewidoczniakademicy.rezerwacje.controller;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.CourseAndUserConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.RecruitmentAndRecruitmentPeriodConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom.ConnectRecruitmentAndRoomsRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom.RecruitmentAndRoomConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom.RecruitmentAndRoomsConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentroom.ConnectRecruitmentAndRoomRequest;
import org.niewidoczniakademicy.rezerwacje.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "connection")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping(path = "connect", params = {"userId", "courseOfStudyId"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public CourseAndUserConnectionResponse addCourseOfStudyToUser(@RequestParam final Long userId,
                                                                  @RequestParam final Long courseOfStudyId) {

        return connectionService.connectCourseOfStudyWithSystemUser(userId, courseOfStudyId);
    }

    @PostMapping(path = "connect-recruitment-and-room")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public RecruitmentAndRoomConnectionResponse addRoomToRecruitment(
            @RequestBody final ConnectRecruitmentAndRoomRequest request) {

        return connectionService.connectRecruitmentWithRoom(
                request.getRecruitmentId(),
                request.getRoomId()
        );
    }

    @PostMapping(path = "connect-recruitment-and-rooms")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public RecruitmentAndRoomsConnectionResponse addRoomsToRecruitment(
            @RequestBody final ConnectRecruitmentAndRoomsRequest request) {

        return connectionService.connectRecruitmentWithRooms(
                request.getRecruitmentId(),
                request.getRooms().getRoomsIds()
        );
    }

    @PostMapping(path = "connect", params = {"recruitmentId", "recruitmentPeriodId"})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public RecruitmentAndRecruitmentPeriodConnectionResponse addRecruitmentPeriodToRecruitment(
            @RequestParam final Long recruitmentId,
            @RequestParam final Long recruitmentPeriodId) {

        return connectionService.connectRecruitmentWithRecruitmentPeriod(recruitmentId, recruitmentPeriodId);
    }
}
