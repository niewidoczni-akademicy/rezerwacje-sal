package org.niewidoczniakademicy.rezerwacje.dao;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.ExamTerm;
import org.niewidoczniakademicy.rezerwacje.dao.repository.ExamTermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExamTermDAO implements GenericDAO<ExamTerm> {
    private final ExamTermRepository examTermRepository;

    @Override
    public ExamTerm save(ExamTerm entity) {
        return examTermRepository.save(entity);
    }

    @Override
    public List<ExamTerm> save(Iterable<ExamTerm> entities) {
        return examTermRepository.saveAll(entities);
    }

    @Override
    public ExamTerm update(ExamTerm entity) {
        return examTermRepository.save(entity);
    }

    @Override
    public void delete(ExamTerm entity) {
        examTermRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        examTermRepository.deleteById(id);
    }

    @Override
    public void delete(List<ExamTerm> entities) {
        examTermRepository.deleteInBatch(entities);
    }

    @Override
    public Optional<ExamTerm> find(Long id) {
        return examTermRepository.findById(id);
    }

    @Override
    public List<ExamTerm> findAll() {
        return examTermRepository.findAll();
    }

    public Optional<ExamTerm> find(Long roomId, Long cosId) {
        return examTermRepository.findByRoomIdAndCourseOfStudyId(roomId, cosId);
    }
}
