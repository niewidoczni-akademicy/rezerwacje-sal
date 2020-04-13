package org.niewidoczniakademicy.rezerwacje.dao;

import lombok.AllArgsConstructor;
import org.niewidoczniakademicy.rezerwacje.core.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.dao.repository.RecruitmentPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RecruitmentPeriodDAO implements GenericDAO<RecruitmentPeriod> {
    private final RecruitmentPeriodRepository recruitmentPeriodRepository;

    @Override
    public RecruitmentPeriod save(RecruitmentPeriod entity) {
        return recruitmentPeriodRepository.save(entity);
    }

    @Override
    public List<RecruitmentPeriod> save(Iterable<RecruitmentPeriod> entities) {
        return recruitmentPeriodRepository.saveAll(entities);
    }

    @Override
    public RecruitmentPeriod update(RecruitmentPeriod entity) {
        return recruitmentPeriodRepository.save(entity);
    }

    @Override
    public void delete(RecruitmentPeriod entity) {
        recruitmentPeriodRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        recruitmentPeriodRepository.deleteById(id);
    }

    @Override
    public void delete(List<RecruitmentPeriod> entities) {
        recruitmentPeriodRepository.deleteInBatch(entities);
    }

    @Override
    public Optional<RecruitmentPeriod> find(Long id) {
        return recruitmentPeriodRepository.findById(id);
    }

    @Override
    public List<RecruitmentPeriod> findAll() {
        return recruitmentPeriodRepository.findAll();
    }
}
