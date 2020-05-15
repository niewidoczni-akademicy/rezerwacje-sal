package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.database.Room;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.RecruitmentAndRecruitmentPeriodConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.other.RecruitmentAndRoomConnectionResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.AddRecruitmentRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.AddRecruitmentResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.recruitment.GetRecruitmentResponse;
import org.niewidoczniakademicy.rezerwacje.service.converter.ConversionService;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.exception.StartDateNotBeforeEndDateException;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentPeriodRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentPeriodRepository recruitmentPeriodRepository;

    private final ConversionService conversionService;
    private final RecruitmentPeriodService recruitmentPeriodService;

    public AddRecruitmentResponse saveRecruitment(final AddRecruitmentRequest request) {
        validateAddRecruitmentRequest(request);

        final Recruitment recruitment = conversionService.convert(request);
        recruitmentRepository.save(recruitment);

        return AddRecruitmentResponse.builder()
                .recruitmentId(recruitment.getId())
                .build();
    }

    public GetRecruitmentResponse getRecruitmentByName(final String name) {
        final Recruitment recruitment = getRecruitmentFromDatabaseByName(name);

        return GetRecruitmentResponse.builder()
                .recruitment(recruitment)
                .build();
    }

    public RecruitmentAndRecruitmentPeriodConnectionResponse connectRecruitmentAndRecruitmentPeriod(
            final Long recruitmentId,
            final Long recruitmentPeriodId) {

        final Recruitment recruitment = getRecruitmentFromDatabaseById(recruitmentId);
        final RecruitmentPeriod recruitmentPeriod = recruitmentPeriodService
                .getRecruitmentPeriodFromDatabaseById(recruitmentPeriodId);

        recruitmentPeriod.addRecruitment(recruitment);
        recruitmentPeriodRepository.save(recruitmentPeriod);

        return RecruitmentAndRecruitmentPeriodConnectionResponse.builder()
                .recruitmentId(recruitment.getId())
                .recruitmentPeriodId(recruitmentPeriod.getId())
                .build();
    }

    public Recruitment getRecruitmentFromDatabaseById(final Long recruitmentId) {
        return recruitmentRepository
                .findById(recruitmentId)
                .orElseThrow(() ->
                        new RecruitmentNotFoundException("Recruitment with id: " + recruitmentId + " does not exist"));
    }

    private void validateAddRecruitmentRequest(final AddRecruitmentRequest request) {
        if (request.getStartTime().isAfter(request.getEndTime())
                || request.getStartTime().isEqual(request.getEndTime())) {
            throw new StartDateNotBeforeEndDateException("End time is not after start time");
        }
    }

    private Recruitment getRecruitmentFromDatabaseByName(final String name) {
        return recruitmentRepository
                .findByName(name)
                .orElseThrow(() ->
                        new RecruitmentNotFoundException("Recruitment with name: " + name + " does not exist"));
    }
}
