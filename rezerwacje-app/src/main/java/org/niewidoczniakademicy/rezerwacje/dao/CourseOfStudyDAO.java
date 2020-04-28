package org.niewidoczniakademicy.rezerwacje.dao;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.dao.repository.CourseOfStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class CourseOfStudyDAO implements GenericDAO<CourseOfStudy> {
    private final CourseOfStudyRepository courseOfStudyRepository;

    @Override
    public CourseOfStudy save(CourseOfStudy entity) {
        return courseOfStudyRepository.save(entity);
    }

    @Override
    public List<CourseOfStudy> save(Iterable<CourseOfStudy> entities) {
        return courseOfStudyRepository.saveAll(entities);
    }

    @Override
    public CourseOfStudy update(CourseOfStudy entity) {
        return courseOfStudyRepository.save(entity);
    }

    @Override
    public void delete(CourseOfStudy entity) {
        courseOfStudyRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        courseOfStudyRepository.deleteById(id);
    }

    @Override
    public void delete(List<CourseOfStudy> entities) {
        courseOfStudyRepository.deleteInBatch(entities);
    }

    @Override
    public Optional<CourseOfStudy> find(Long id) {
        return courseOfStudyRepository.findById(id);
    }

    @Override
    public List<CourseOfStudy> findAll() {
        return courseOfStudyRepository.findAll();
    }
}
