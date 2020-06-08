package org.niewidoczniakademicy.rezerwacje.service;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.rest.report.GetReportsInfoResponse;
import org.niewidoczniakademicy.rezerwacje.service.exception.PdfGenerationFailureException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.report.RecruitmentPdfBuilder;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReportService {

    private RecruitmentRepository recruitmentRepository;
    private final List<String> currentReports = Arrays.asList(
            "recruitment", "recruitmentPeriod", "courseOfStudy", "faculty" // TODO in builder
    );

    public GetReportsInfoResponse getReportsInfoResponse() {

        return GetReportsInfoResponse.builder()
                .currentReports(currentReports)
                .build();
    }

    public InputStreamResource getRecruitmentPdf(Long id) {
        Recruitment recruitment = recruitmentRepository
                .findById(id)
                .orElseThrow(() -> new RecruitmentNotFoundException("Recruitment with id " + id + " not found"));
        try {
            return new RecruitmentPdfBuilder()
                    .withMetaData(recruitment)
                    .withTitle(recruitment)
                    .withRecruitmentData(recruitment)
                    .build();

        } catch (DocumentException | IOException de) {
            log.warn(de.getMessage());
            throw new PdfGenerationFailureException();
        }

    }
}
