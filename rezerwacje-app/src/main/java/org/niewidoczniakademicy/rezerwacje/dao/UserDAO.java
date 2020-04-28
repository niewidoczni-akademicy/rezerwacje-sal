package org.niewidoczniakademicy.rezerwacje.dao;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.SystemUser;
import org.niewidoczniakademicy.rezerwacje.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class UserDAO implements GenericDAO<SystemUser> {
    private final UserRepository userRepository;

    @Override
    public SystemUser save(SystemUser entity) {
        return userRepository.save(entity);
    }

    @Override
    public List<SystemUser> save(Iterable<SystemUser> entities) {
        return userRepository.saveAll(entities);
    }

    @Override
    public SystemUser update(SystemUser entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(SystemUser entity) {
        userRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(List<SystemUser> entities) {
        userRepository.deleteInBatch(entities);
    }

    @Override
    public Optional<SystemUser> find(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<SystemUser> findAll() {
        return userRepository.findAll();
    }
}
