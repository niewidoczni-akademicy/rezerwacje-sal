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
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.niewidoczniakademicy.rezerwacje.model.database.RecruitmentRoom;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
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
    private static Font boldFont;
    private static Font sectionFont;

    private Document document;
    private ByteArrayOutputStream outputStream;

    private void setFonts() throws IOException, DocumentException {
        // utf-8 workaround
        BaseFont base = BaseFont.createFont("times.ttf", BaseFont.IDENTITY_H, true);
        titleFont = new Font(base, BIG_FONT, Font.BOLD, BaseColor.BLACK);
        subtitleFont = new Font(base, SMALL_FONT, Font.BOLD, BaseColor.GRAY);
        plainFont = new Font(base, SMALL_FONT, Font.NORMAL, BaseColor.BLACK);
        boldFont = new Font(base, SMALL_FONT, Font.BOLD, BaseColor.BLACK);
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

        int examCount = recruitment
                .getRecruitmentPeriods()
                .stream()
                .flatMap(rp -> rp.getExamTerms().stream())
                .collect(Collectors.toSet())
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba odbytych egzaminów", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(examCount), plainFont)));

        int availableRoomCount = recruitment
                .getRecruitmentRooms()
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba dostępnych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(availableRoomCount), plainFont)));

        int usedRoomCount = recruitment
                .getRecruitmentPeriods()
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .map(ExamTerm::getRecruitmentRoom)
                )
                .collect(Collectors.toSet())
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba wykorzystanych sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(usedRoomCount), plainFont)));

        int cosCount = recruitment
                .getRecruitmentPeriods()
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .map(ExamTerm::getCourseOfStudy)
                )
                .collect(Collectors.toSet())
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba kierunków biorących udział w rekrutacji", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(cosCount), plainFont)));

        int facultyCount = recruitment
                .getRecruitmentPeriods()
                .stream()
                .flatMap(rp -> rp.getExamTerms()
                        .stream()
                        .map(ExamTerm::getCourseOfStudy)
                        .map(CourseOfStudy::getFaculty)
                )
                .collect(Collectors.toSet())
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba wydziałów biorących udział w rekrutacji", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(facultyCount), plainFont)));

        Map<RecruitmentRoom, Long> mostUsed = recruitment
                .getRecruitmentPeriods()
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

        for (Map.Entry<RecruitmentRoom, Long> entry : mostUsed.entrySet()) {
            table.addCell(new PdfPCell(new Phrase("Liczba egzaminów odbytych w sali "
                    + entry.getKey().getRoom().getName(), plainFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(entry.getValue()), plainFont)));
        }

        double meanRoomUsage = recruitment
                .getRecruitmentPeriods()
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

        table.addCell(new PdfPCell(new Phrase("Średni procent zapełnienia sal", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.format("%.2f", meanRoomUsage) + "%", plainFont)));

        Map<String, Double> seatsByRoom = recruitment
                .getRecruitmentPeriods()
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

        document.add(table);
        return this;
    }

    public InputStreamResource build() {
        document.close();
        ByteArrayInputStream bis = new ByteArrayInputStream(outputStream.toByteArray());
        return new InputStreamResource(bis);
    }

    private static void addEmptyLine(Paragraph paragraph) {
        paragraph.add(new Paragraph(" "));
    }


}
