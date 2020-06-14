package org.niewidoczniakademicy.rezerwacje.service.converter;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.Hours;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.AddCourseOfStudyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.courseofstudy.EditCourseOfStudyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.hours.AddHoursRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.AddRecruitmentRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.AddRoomRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ConversionServiceImpl implements ConversionService {

    private final SystemUserConverter systemUserConverter;
    private final RecruitmentPeriodConverter recruitmentPeriodConverter;
    private final RoomConverter roomConverter;
    private final RecruitmentConverter recruitmentConverter;
    private final CourseOfStudyConverter courseOfStudyConverter;
    private final HoursConverter hoursConverter;

    @Override
    public SystemUser convert(final AddSystemUserRequest request) {
        return systemUserConverter.createFrom(request);
    }

    @Override
    public RecruitmentPeriod convert(final AddRecruitmentPeriodRequest request) {
        return recruitmentPeriodConverter.createFrom(request);
    }

    @Override
    public Room convert(final AddRoomRequest request) {
        return roomConverter.createFrom(request);
    }

    @Override
    public Recruitment convert(final AddRecruitmentRequest request) {
        return recruitmentConverter.createFrom(request);
    }

    @Override
    public CourseOfStudy convert(final AddCourseOfStudyRequest request) {
        return courseOfStudyConverter.createFrom(request);
    }

    @Override
    public CourseOfStudy convert(EditCourseOfStudyRequest request) {
        AddCourseOfStudyRequest addRequest = AddCourseOfStudyRequest.builder()
                .name(request.getName())
                .faculty(request.getFaculty())
                .courseType(request.getCourseType())
                .contactPerson1(request.getContactPerson1())
                .contactPerson2(request.getContactPerson2())
                .isJoined(request.getIsJoined())
                .remarks(request.getRemarks())
                .build();
        return courseOfStudyConverter.createFrom(addRequest);
    }

    @Override
    public List<Hours> convert(AddHoursRequest request) {
        return hoursConverter.createFrom(request);
    }

}
