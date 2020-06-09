package org.niewidoczniakademicy.rezerwacje.service.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.niewidoczniakademicy.rezerwacje.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.database.ExamTerm;
import org.niewidoczniakademicy.rezerwacje.model.database.Faculty;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentPeriod;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentRoom;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RecruitmentPdfBuilder {

    private static final int MAX = 100;
    private static final int BIG_FONT = 18;
    private static final int MEDIUM_FONT = 16;
    private static final int SMALL_FONT = 14;
    private static final int SEARCH_LIMIT = 5;
    private static final int PERCENT_MULTIPLIER = 100;


    private static Font titleFont;
    private static Font subtitleFont;
    private static Font plainFont;
    private static Font sectionFont;

    private Document document;
    private ByteArrayOutputStream outputStream;

    private void setFonts() throws IOException, DocumentException {
        // utf-8 workaround
        BaseFont base = BaseFont.createFont("times.ttf", BaseFont.IDENTITY_H, true);
        titleFont = new Font(base, BIG_FONT, Font.BOLD, BaseColor.BLACK);
        subtitleFont = new Font(base, SMALL_FONT, Font.BOLD, BaseColor.GRAY);
        plainFont = new Font(base, SMALL_FONT, Font.NORMAL, BaseColor.BLACK);
        sectionFont = new Font(base, MEDIUM_FONT, Font.BOLD, BaseColor.BLACK);
    }

    public RecruitmentPdfBuilder() throws DocumentException, IOException {
        setFonts();
        document = new Document();
        outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();
    }

    public RecruitmentPdfBuilder withMetaData(Recruitment recruitment) {
        document.addTitle(
                String.format("Rekrutacja %s", recruitment.getName())
        );
        return this;
    }

    public RecruitmentPdfBuilder withTitle(Recruitment recruitment) throws DocumentException {
        Paragraph preface = new Paragraph();
        preface.setAlignment(Element.ALIGN_CENTER);
        preface.add(new Paragraph(
                String.format("Rekrutacja %s", recruitment.getName()),
                titleFont
        ));
        preface.add(new Paragraph(
                String.format("(%s - %s)", recruitment.getStartTime(), recruitment.getEndTime()),
                subtitleFont
        ));
        addEmptyLine(preface);
        document.add(preface);
        return this;
    }

    public RecruitmentPdfBuilder withRecruitmentData(Recruitment recruitment) throws DocumentException {
        Paragraph preface = new Paragraph();

        preface.add(new Paragraph(
                String.format("Statystyki dla rekrutacji %s", recruitment.getName()),
                sectionFont
        ));

        addEmptyLine(preface);
        document.add(preface);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(MAX);

        int cycleCount = recruitment.
                getRecruitmentPeriods()
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba cykli w rekrutacji", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(cycleCount), plainFont)));

        int examCount = getExamCount(recruitment.getRecruitmentPeriods());

        table.addCell(new PdfPCell(new Phrase("Liczba odbytych egzaminów", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(examCount), plainFont)));

        int availableRoomCount = recruitment
                .getRecruitmentRooms()
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba dostępnych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(availableRoomCount), plainFont)));

        int usedRoomCount = getUsedRoomCount(recruitment.getRecruitmentPeriods());

        table.addCell(new PdfPCell(new Phrase("Liczba wykorzystanych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(usedRoomCount), plainFont)));

        int cosCount = getCourseOfStudyCount(recruitment.getRecruitmentPeriods());

        table.addCell(new PdfPCell(new Phrase("Liczba kierunków biorących udział w rekrutacji", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(cosCount), plainFont)));

        int facultyCount = getFacultyCount(recruitment.getRecruitmentPeriods());

        table.addCell(new PdfPCell(new Phrase("Liczba wydziałów biorących udział w rekrutacji", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(facultyCount), plainFont)));

        Map<RecruitmentRoom, Long> mostUsed = mostUsedRooms(recruitment.getRecruitmentPeriods());

        for (Map.Entry<RecruitmentRoom, Long> entry : mostUsed.entrySet()) {
            table.addCell(new PdfPCell(new Phrase("Liczba egzaminów odbytych w sali "
                    + entry.getKey().getRoom().getName(), plainFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(entry.getValue()), plainFont)));
        }

        double meanRoomUsage = meanRoomUsage(recruitment.getRecruitmentPeriods());

        table.addCell(new PdfPCell(new Phrase("Średni procent zapełnienia sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", meanRoomUsage) + "%", plainFont)));

        Map<String, Double> seatsByRoom = seatsByRoom(recruitment.getRecruitmentPeriods());
        addUsagePercentage(table, seatsByRoom);

        document.add(table);
        document.newPage();
        return this;
    }

    public RecruitmentPdfBuilder withRecruitmentPeriodData(RecruitmentPeriod recruitmentPeriod)
            throws DocumentException {

        Paragraph preface = new Paragraph();

        preface.add(new Paragraph(
                String.format("Statystyki dla cyklu rekrutacyjnego %d", recruitmentPeriod.getId()),
                sectionFont
        ));

        preface.add(new Paragraph(
                String.format("(%s - %s)", recruitmentPeriod.getStartDate(), recruitmentPeriod.getEndDate()),
                subtitleFont
        ));

        addEmptyLine(preface);
        document.add(preface);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(MAX);

        Set<RecruitmentPeriod> wrappedRecruitmentPeriod = Set.of(recruitmentPeriod);

        int examCount = getExamCount(wrappedRecruitmentPeriod);

        table.addCell(new PdfPCell(new Phrase("Liczba odbytych egzaminów", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(examCount), plainFont)));

        int availableRoomCount = recruitmentPeriod
                .getRecruitment()
                .getRecruitmentRooms()
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba dostępnych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(availableRoomCount), plainFont)));

        int usedRoomCount = getUsedRoomCount(wrappedRecruitmentPeriod);

        table.addCell(new PdfPCell(new Phrase("Liczba wykorzystanych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(usedRoomCount), plainFont)));

        int cosCount = getCourseOfStudyCount(wrappedRecruitmentPeriod);

        table.addCell(new PdfPCell(new Phrase("Liczba kierunków biorących udział w cyklu rekrutacyjnym", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(cosCount), plainFont)));

        int facultyCount = getFacultyCount(wrappedRecruitmentPeriod);

        table.addCell(new PdfPCell(new Phrase("Liczba wydziałów biorących udział w cyklu rekrutacyjnym", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(facultyCount), plainFont)));

        Map<RecruitmentRoom, Long> mostUsed = mostUsedRooms(wrappedRecruitmentPeriod);

        for (Map.Entry<RecruitmentRoom, Long> entry : mostUsed.entrySet()) {
            table.addCell(new PdfPCell(new Phrase("Liczba egzaminów odbytych w sali "
                    + entry.getKey().getRoom().getName(), plainFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(entry.getValue()), plainFont)));
        }

        double meanRoomUsage = meanRoomUsage(wrappedRecruitmentPeriod);

        table.addCell(new PdfPCell(new Phrase("Średni procent zapełnienia sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", meanRoomUsage) + "%", plainFont)));

        Map<String, Double> seatsByRoom = seatsByRoom(wrappedRecruitmentPeriod);
        addUsagePercentage(table, seatsByRoom);

        document.add(table);
        document.newPage();
        return this;
    }


    public RecruitmentPdfBuilder withCourseOfStudyData(Recruitment recruitment, CourseOfStudy courseOfStudy)
            throws DocumentException {

        Paragraph preface = new Paragraph();

        preface.add(new Paragraph(
                String.format("Statystyki dla kierunku %s", courseOfStudy.getName()),
                sectionFont
        ));

        addEmptyLine(preface);
        document.add(preface);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(MAX);

        Set<CourseOfStudy> wrappedCourseOfStudy = Set.of(courseOfStudy);

        int examCount = getExamCount(wrappedCourseOfStudy, recruitment);

        table.addCell(new PdfPCell(new Phrase("Liczba odbytych egzaminów", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(examCount), plainFont)));

        int availableRoomCount = recruitment
                .getRecruitmentRooms()
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba dostępnych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(availableRoomCount), plainFont)));

        int usedRoomCount = getUsedRoomCount(wrappedCourseOfStudy, recruitment);

        table.addCell(new PdfPCell(new Phrase("Liczba wykorzystanych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(usedRoomCount), plainFont)));

        Map<RecruitmentRoom, Long> mostUsed = mostUsedRooms(wrappedCourseOfStudy, recruitment);

        for (Map.Entry<RecruitmentRoom, Long> entry : mostUsed.entrySet()) {
            table.addCell(new PdfPCell(new Phrase("Liczba egzaminów odbytych w sali "
                    + entry.getKey().getRoom().getName(), plainFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(entry.getValue()), plainFont)));
        }

        double meanRoomUsage = meanRoomUsage(wrappedCourseOfStudy, recruitment);

        table.addCell(new PdfPCell(new Phrase("Średni procent zapełnienia sal przez kierunek", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", meanRoomUsage) + "%", plainFont)));

        Map<String, Double> seatsByRoom = seatsByRoom(wrappedCourseOfStudy, recruitment);
        addUsagePercentage(table, seatsByRoom);

        document.add(table);
        document.newPage();
        return this;
    }

    public RecruitmentPdfBuilder withFacultyData(Recruitment recruitment, Faculty faculty)
            throws DocumentException {

        Paragraph preface = new Paragraph();

        preface.add(new Paragraph(
                String.format("Statystyki dla wydziału %s", faculty.getName()),
                sectionFont
        ));

        addEmptyLine(preface);
        document.add(preface);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(MAX);

        int examCount = getExamCount(faculty.getCourseOfStudies(), recruitment);

        table.addCell(new PdfPCell(new Phrase("Liczba odbytych egzaminów", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(examCount), plainFont)));

        int availableRoomCount = recruitment
                .getRecruitmentRooms()
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba dostępnych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(availableRoomCount), plainFont)));

        int usedRoomCount = getUsedRoomCount(faculty.getCourseOfStudies(), recruitment);

        table.addCell(new PdfPCell(new Phrase("Liczba wykorzystanych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(usedRoomCount), plainFont)));

        Map<RecruitmentRoom, Long> mostUsed = mostUsedRooms(faculty.getCourseOfStudies(), recruitment);

        for (Map.Entry<RecruitmentRoom, Long> entry : mostUsed.entrySet()) {
            table.addCell(new PdfPCell(new Phrase("Liczba egzaminów odbytych w sali "
                    + entry.getKey().getRoom().getName(), plainFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(entry.getValue()), plainFont)));
        }

        double meanRoomUsage = meanRoomUsage(faculty.getCourseOfStudies(), recruitment);

        table.addCell(new PdfPCell(new Phrase("Średni procent zapełnienia sal przez wydział", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", meanRoomUsage) + "%", plainFont)));

        Map<String, Double> seatsByRoom = seatsByRoom(faculty.getCourseOfStudies(), recruitment);
        addUsagePercentage(table, seatsByRoom);

        document.add(table);
        document.newPage();
        return this;
    }

    public RecruitmentPdfBuilder withRoomData(RecruitmentRoom recruitmentRoom)
            throws DocumentException {

        Paragraph preface = new Paragraph();

        preface.add(new Paragraph(
                String.format("Statystyki dla sali %s", recruitmentRoom.getRoom().getName()),
                sectionFont
        ));

        addEmptyLine(preface);
        document.add(preface);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(MAX);

        int examCount = recruitmentRoom
                .getExamTerms()
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba odbytych egzaminów", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(examCount), plainFont)));

        int participatingPeriods = recruitmentRoom
                .getExamTerms()
                .stream()
                .map(ExamTerm::getRecruitmentPeriod)
                .collect(Collectors.toSet())
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba cykli korzystających z sali", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(participatingPeriods), plainFont)));

        int participatingCos = recruitmentRoom
                .getExamTerms()
                .stream()
                .map(ExamTerm::getCourseOfStudy)
                .collect(Collectors.toSet())
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba kierunków korzystających z sali", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(participatingCos), plainFont)));

        int participatingFaculty = recruitmentRoom
                .getExamTerms()
                .stream()
                .map(ExamTerm::getCourseOfStudy)
                .map(CourseOfStudy::getFaculty)
                .collect(Collectors.toSet())
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba wydziałów korzystających z sali", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(participatingFaculty), plainFont)));


        List<Double> doubleStream = recruitmentRoom
                .getExamTerms()
                .stream()
                .map(et -> PERCENT_MULTIPLIER * (double) et.getSeats()
                        / et.getRecruitmentRoom().getRoom().getCapacity())
                .collect(Collectors.toList());

        double avg = doubleStream.stream().mapToDouble(x -> x).average().orElse(Double.NaN);
        double min = doubleStream.stream().mapToDouble(x -> x).min().orElse(Double.NaN);
        double max = doubleStream.stream().mapToDouble(x -> x).max().orElse(Double.NaN);

        table.addCell(new PdfPCell(new Phrase("Średnie zapełnienie sali", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", avg) + "%", plainFont)));

        table.addCell(new PdfPCell(new Phrase("Największe zapełnienie sali", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", max) + "%", plainFont)));

        table.addCell(new PdfPCell(new Phrase("Najmniejsze zapełnienie sali", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", min) + "%", plainFont)));

        document.add(table);
        document.newPage();
        return this;
    }

    public InputStreamResource build() {
        document.close();
        ByteArrayInputStream bis = new ByteArrayInputStream(outputStream.toByteArray());
        return new InputStreamResource(bis);
    }

    private Set<ExamTerm> getExams(Set<RecruitmentPeriod> recruitmentPeriods) {
        return recruitmentPeriods
                .stream()
                .flatMap(rp -> rp.getExamTerms().stream())
                .collect(Collectors.toSet());
    }

    private int getExamCount(Set<RecruitmentPeriod> recruitmentPeriods) {
        return getExams(recruitmentPeriods).size();
    }

    private int getExamCount(Set<CourseOfStudy> courseOfStudies, Recruitment recruitment) {

        Set<ExamTerm> recruitmentSet = getExams(recruitment.getRecruitmentPeriods());
        Set<ExamTerm> cosSet = courseOfStudies
                .stream()
                .flatMap(rp -> rp.getExamTerms().stream())
                .collect(Collectors.toSet());
        cosSet.retainAll(recruitmentSet);
        return cosSet.size();
    }

    private Set<RecruitmentRoom> getUsedRoom(Set<RecruitmentPeriod> recruitmentPeriods) {
        return recruitmentPeriods
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .map(ExamTerm::getRecruitmentRoom)
                )
                .collect(Collectors.toSet());
    }

    private int getUsedRoomCount(Set<RecruitmentPeriod> recruitmentPeriods) {
        return getUsedRoom(recruitmentPeriods).size();
    }

    private int getUsedRoomCount(Set<CourseOfStudy> courseOfStudies, Recruitment recruitment) {
        Set<RecruitmentRoom> recruitmentRooms = getUsedRoom(recruitment.getRecruitmentPeriods());
        Set<RecruitmentRoom> cosSet = courseOfStudies
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .map(ExamTerm::getRecruitmentRoom))
                .collect(Collectors.toSet());
        cosSet.retainAll(recruitmentRooms);
        return cosSet.size();
    }

    private int getCourseOfStudyCount(Set<RecruitmentPeriod> recruitmentPeriods) {
        return recruitmentPeriods
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .map(ExamTerm::getCourseOfStudy)
                )
                .collect(Collectors.toSet())
                .size();
    }

    private int getFacultyCount(Set<RecruitmentPeriod> recruitmentPeriods) {
        return recruitmentPeriods
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .map(ExamTerm::getCourseOfStudy)
                        .map(CourseOfStudy::getFaculty)
                )
                .collect(Collectors.toSet())
                .size();
    }

    private Map<RecruitmentRoom, Long> mostUsedRooms(Set<RecruitmentPeriod> recruitmentPeriods) {
        return recruitmentPeriods
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .map(ExamTerm::getRecruitmentRoom)
                )
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors
                        .groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<RecruitmentRoom, Long>comparingByValue().reversed())
                .limit(SEARCH_LIMIT)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Set<ExamTerm> getUsedExamTerms(Recruitment recruitment) {
        return recruitment
                .getRecruitmentPeriods()
                .stream()
                .flatMap(rp -> rp.getExamTerms().stream())
                .collect(Collectors.toSet());
    }

    private Map<RecruitmentRoom, Long> mostUsedRooms(Set<CourseOfStudy> courseOfStudies, Recruitment recruitment) {

        Set<ExamTerm> recruitmentTerms = getUsedExamTerms(recruitment);

        return courseOfStudies
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .filter(recruitmentTerms::contains)
                        .map(ExamTerm::getRecruitmentRoom)
                )
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors
                        .groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<RecruitmentRoom, Long>comparingByValue().reversed())
                .limit(SEARCH_LIMIT)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private double meanRoomUsage(Set<RecruitmentPeriod> recruitmentPeriods) {
        return recruitmentPeriods
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .filter(et -> et.getSeats() != null && et.getSeats() > 0)
                )
                .collect(Collectors.toList())
                .stream()
                .map(et -> PERCENT_MULTIPLIER * (double) et.getSeats()
                        / et.getRecruitmentRoom().getRoom().getCapacity())
                .mapToDouble(x -> x)
                .average()
                .orElse(Double.NaN);
    }

    private double meanRoomUsage(Set<CourseOfStudy> courseOfStudies, Recruitment recruitment) {

        Set<ExamTerm> recruitmentTerms = getUsedExamTerms(recruitment);

        return courseOfStudies
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .filter(recruitmentTerms::contains)
                        .filter(et -> et.getSeats() != null && et.getSeats() > 0)
                )
                .collect(Collectors.toList())
                .stream()
                .map(et -> PERCENT_MULTIPLIER * (double) et.getSeats()
                        / et.getRecruitmentRoom().getRoom().getCapacity())
                .mapToDouble(x -> x)
                .average()
                .orElse(Double.NaN);
    }

    private Map<String, Double> seatsByRoom(Set<RecruitmentPeriod> recruitmentPeriods) {
        return recruitmentPeriods
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .filter(et -> et.getSeats() != null && et.getSeats() > 0)
                )
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors
                        .groupingBy(et -> et.getRecruitmentRoom().getRoom().getName(),
                                Collectors.averagingDouble(et -> PERCENT_MULTIPLIER * (double) et.getSeats()
                                        / et.getRecruitmentRoom().getRoom().getCapacity())));
    }

    private Map<String, Double> seatsByRoom(Set<CourseOfStudy> courseOfStudies, Recruitment recruitment) {

        Set<ExamTerm> recruitmentTerms = getUsedExamTerms(recruitment);

        return courseOfStudies
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .filter(recruitmentTerms::contains)
                        .filter(et -> et.getSeats() != null && et.getSeats() > 0)
                )
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors
                        .groupingBy(et -> et.getRecruitmentRoom().getRoom().getName(),
                                Collectors.averagingDouble(et -> PERCENT_MULTIPLIER * (double) et.getSeats()
                                        / et.getRecruitmentRoom().getRoom().getCapacity())));
    }

    private void addUsagePercentage(PdfPTable table, Map<String, Double> seatsByRoom) {
        Map<String, Double> seatsByRoomTop = seatsByRoom
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(SEARCH_LIMIT)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<String, Double> entry : seatsByRoomTop.entrySet()) {
            table.addCell(new PdfPCell(new Phrase("Średni procent zapełnienia sali "
                    + entry.getKey(), plainFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f", entry.getValue()) + "%", plainFont)));
        }

        int botLimit = seatsByRoom.size() >= 2 * SEARCH_LIMIT ? SEARCH_LIMIT // make top and bot disjoint
                : seatsByRoom.size() < SEARCH_LIMIT ? 0 : seatsByRoom.size() - SEARCH_LIMIT;

        Map<String, Double> seatsByRoomBot = seatsByRoom
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(botLimit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<String, Double> entry : seatsByRoomBot.entrySet()) {
            table.addCell(new PdfPCell(new Phrase("Średni procent zapełnienia sali "
                    + entry.getKey(), plainFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f", entry.getValue()) + "%", plainFont)));
        }
    }

    private static void addEmptyLine(Paragraph paragraph) {
        paragraph.add(new Paragraph(" "));
    }

}
