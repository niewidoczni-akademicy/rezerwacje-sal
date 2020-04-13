package org.niewidoczniakademicy.rezerwacje.repository;

import org.niewidoczniakademicy.rezerwacje.core.model.database.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Override
    <S extends Room> S save(S entity);

    @Override
    <S extends Room> List<S> saveAll(Iterable<S> rooms);

    @Override
    List<Room> findAll();
}
