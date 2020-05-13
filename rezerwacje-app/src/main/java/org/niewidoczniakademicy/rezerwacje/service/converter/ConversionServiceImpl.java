package org.niewidoczniakademicy.rezerwacje.service.converter;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitmentperiod.AddRecruitmentPeriodRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.room.AddRoomRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.systemuser.AddSystemUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class ConversionServiceImpl implements ConversionService {

    private final SystemUserConverter systemUserConverter;
    private final RecruitmentPeriodConverter recruitmentPeriodConverter;
    private final RoomConverter roomConverter;

    @Override
    public SystemUser convert(final AddSystemUserRequest request) {
        return systemUserConverter.createFrom(request);
    }

    @Override
    public RecruitmentPeriod convert(final AddRecruitmentPeriodRequest request) {
        return recruitmentPeriodConverter.createFrom(request);
    }

    @Override
    public Room convert(AddRoomRequest request) {
        return roomConverter.createFrom(request);
    }

}
