package org.niewidoczniakademicy.rezerwacje.service;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.ExamTerm;
import org.niewidoczniakademicy.rezerwacje.model.database.Faculty;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentRoom;
import org.niewidoczniakademicy.rezerwacje.model.rest.report.GetReportRecruitmentRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.report.GetReportsInfoResponse;
import org.niewidoczniakademicy.rezerwacje.service.exception.InvalidInputException;
import org.niewidoczniakademicy.rezerwacje.service.exception.PdfGenerationFailureException;
import org.niewidoczniakademicy.rezerwacje.service.exception.RecruitmentNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.report.RecruitmentPdfBuilder;
import org.niewidoczniakademicy.rezerwacje.service.repository.CourseOfStudyRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.FacultyRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentPeriodRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.RecruitmentRepository;
import org.niewidoczniakademicy.rezerwacje.service.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReportService {

    private RecruitmentRepository recruitmentRepository;
    private RecruitmentPeriodRepository recruitmentPeriodRepository;
    private CourseOfStudyRepository courseOfStudyRepository;
    private FacultyRepository facultyRepository;
    private RoomRepository roomRepository;


    private final List<String> currentReports = Arrays.asList(
            "recruitment", "recruitmentPeriods", "courseOfStudies", "faculties", "rooms"
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

    public InputStreamResource getDetailedRecruitmentPdf(Long id, GetReportRecruitmentRequest request) {
        Recruitment recruitment = recruitmentRepository
                .findById(id)
                .orElseThrow(() -> new RecruitmentNotFoundException("Recruitment with id " + id + " not found"));

        List<RecruitmentPeriod> recruitmentPeriods = recruitment
                .getRecruitmentPeriods()
                .stream()
                .filter(rp -> request.getRecruitmentPeriods().contains(rp.getId()))
                .distinct()
                .collect(Collectors.toList());


        List<CourseOfStudy> courseOfStudies = recruitment
                .getRecruitmentPeriods()
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .filter(et -> et.getSeats() != null && et.getSeats() > 0)
                        .map(ExamTerm::getCourseOfStudy)
                        .filter(cos -> request.getCourseOfStudies().contains(cos.getId()))
                )
                .distinct()
                .collect(Collectors.toList());

        List<Faculty> faculties = recruitment
                .getRecruitmentPeriods()
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .filter(et -> et.getSeats() != null && et.getSeats() > 0)
                        .map(ExamTerm::getCourseOfStudy)
                        .map(CourseOfStudy::getFaculty)
                        .filter(cos -> request.getFaculties().contains(cos.getId()))
                )
                .distinct()
                .collect(Collectors.toList());

        List<RecruitmentRoom> recruitmentRooms = recruitment
                .getRecruitmentRooms()
                .stream()
                .filter(rr -> request.getRooms().contains(rr.getRoom().getId()))
                .distinct()
                .collect(Collectors.toList());

        if (recruitmentPeriods.size() + courseOfStudies.size() + faculties.size() + recruitmentRooms.size() == 0) {
            throw new InvalidInputException("Provided request is either empty or "
                    + "any of provided id's was not found within recruitment " + id);
        }

        try {
            RecruitmentPdfBuilder recruitmentPdfBuilder = new RecruitmentPdfBuilder()
                    .withMetaData(recruitment)
                    .withTitle(recruitment);

            for (RecruitmentPeriod recruitmentPeriod : recruitmentPeriods) {
                recruitmentPdfBuilder = recruitmentPdfBuilder.withRecruitmentPeriodData(recruitmentPeriod);
            }

            for (CourseOfStudy courseOfStudy : courseOfStudies) {
                recruitmentPdfBuilder = recruitmentPdfBuilder.withCourseOfStudyData(recruitment, courseOfStudy);
            }

            for (Faculty faculty : faculties) {
                recruitmentPdfBuilder = recruitmentPdfBuilder.withFacultyData(recruitment, faculty);
            }

            for (RecruitmentRoom recruitmentRoom : recruitmentRooms) {
                recruitmentPdfBuilder = recruitmentPdfBuilder.withRoomData(recruitmentRoom);
            }

            return recruitmentPdfBuilder
                    .build();

        } catch (DocumentException | IOException de) {
            log.warn(de.getMessage());
            throw new PdfGenerationFailureException();
        }

    }
}
