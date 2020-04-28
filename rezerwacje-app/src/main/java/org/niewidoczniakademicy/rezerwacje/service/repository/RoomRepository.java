package org.niewidoczniakademicy.rezerwacje.service.repository;

import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
