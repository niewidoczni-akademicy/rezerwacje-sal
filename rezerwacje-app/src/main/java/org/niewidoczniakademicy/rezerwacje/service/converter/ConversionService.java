package org.niewidoczniakademicy.rezerwacje.service.converter;

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

import java.util.List;

public interface ConversionService {

    SystemUser convert(AddSystemUserRequest request);

    RecruitmentPeriod convert(AddRecruitmentPeriodRequest request);

    Room convert(AddRoomRequest request);

    Recruitment convert(AddRecruitmentRequest request);

    CourseOfStudy convert(AddCourseOfStudyRequest request);

    CourseOfStudy convert(EditCourseOfStudyRequest request);

    List<Hours> convert(AddHoursRequest request);
}
