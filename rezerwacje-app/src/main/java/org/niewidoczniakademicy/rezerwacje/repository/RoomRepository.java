package org.niewidoczniakademicy.rezerwacje.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Override
    <S extends Room> S save(S entity);
}
