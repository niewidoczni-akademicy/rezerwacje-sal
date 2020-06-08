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
import org.niewidoczniakademicy.rezerwacje.model.database.ExamTerm;
import org.niewidoczniakademicy.rezerwacje.model.database.Recruitment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class RecruitmentPdfBuilder {

    private static final int MAX = 100;
    private static final int BIGFONT = 18;
    private static final int MEDIUMFONT = 16;
    private static final int SMALLFONT = 14;


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
        titleFont = new Font(base, BIGFONT, Font.BOLD, BaseColor.BLACK);
        subtitleFont = new Font(base, SMALLFONT, Font.BOLD, BaseColor.GRAY);
        plainFont = new Font(base, SMALLFONT, Font.NORMAL, BaseColor.BLACK);
        boldFont = new Font(base, SMALLFONT, Font.BOLD, BaseColor.BLACK);
        sectionFont = new Font(base, MEDIUMFONT, Font.BOLD, BaseColor.BLACK);
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
                )
                .collect(Collectors.toSet())
                .size();

        table.addCell(new PdfPCell(new Phrase("Liczba wydziałów biorących udział w rekrutacji", plainFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(facultyCount), plainFont)));

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
