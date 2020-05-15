package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentRoom;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.RecruitmentAndRoomConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RecruitmentRoomService {

    private final RecruitmentRoomRepository recruitmentRoomRepository;

    public RecruitmentAndRoomConnectionResponse connectRecruitmentAndRoom(final Recruitment recruitment,
                                                                          final Room room) {

        final RecruitmentRoom recruitmentRoom = RecruitmentRoom.builder()
                .recruitment(recruitment)
                .room(room)
                .build();

        recruitmentRoomRepository.save(recruitmentRoom);

        return RecruitmentAndRoomConnectionResponse.builder()
                .recruitmentId(recruitment.getId())
                .roomId(room.getId())
                .build();
    }

}
