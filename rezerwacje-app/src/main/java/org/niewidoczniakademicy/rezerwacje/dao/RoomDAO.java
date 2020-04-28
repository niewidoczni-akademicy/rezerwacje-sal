package org.niewidoczniakademicy.rezerwacje.dao;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.dao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RoomDAO implements GenericDAO<Room> {
    private final RoomRepository roomRepository;

    @Override
    public Room save(Room entity) {
        return roomRepository.save(entity);
    }

    @Override
    public List<Room> save(Iterable<Room> entities) {
        return roomRepository.saveAll(entities);
    }

    @Override
    public Room update(Room entity) {
        return roomRepository.save(entity);
    }

    @Override
    public void delete(Room entity) {
        roomRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public void delete(List<Room> entities) {
        roomRepository.deleteInBatch(entities);
    }

    @Override
    public Optional<Room> find(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }
}
