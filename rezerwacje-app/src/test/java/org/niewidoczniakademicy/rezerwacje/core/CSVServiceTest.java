package org.niewidoczniakademicy.rezerwacje.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.niewidoczniakademicy.rezerwacje.core.csv.CSVService;
import org.niewidoczniakademicy.rezerwacje.core.model.csv.CsvCourseOfStudy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@SpringBootTest
class CSVServiceTest {

    @Autowired
    CSVService service;

    @Test
    void parseRoomsFile() throws IOException, ParseException {
        MockMultipartFile mf = new MockMultipartFile(
                "test_data/room.csv",
                this.getClass().getResourceAsStream("/" + "test_data/room.csv"));

        service.parseRoomsFile(mf);
    }

    @Test
    void parseCoursesOfStudy() throws IOException, ParseException {
        MockMultipartFile mf = new MockMultipartFile(
                "test_data/cos.csv",
                this.getClass().getResourceAsStream("/" + "test_data/cos.csv"));

        List<CsvCourseOfStudy> cosList = service.parseCoursesOfStudy(mf);
        assert cosList.size() == 2;
    }

    @Test
    void parseCoursesOfStudyCRLF() throws IOException, ParseException {
        MockMultipartFile mf = new MockMultipartFile(
                "test_data/cos_crlf.csv",
                this.getClass().getResourceAsStream("/" + "test_data/cos_crlf.csv"));

        List<CsvCourseOfStudy> cosList = service.parseCoursesOfStudy(mf);
        assert cosList.size() == 2;
    }

    @Test
    void parseCoursesOfStudyCR() throws IOException, ParseException {
        MockMultipartFile mf = new MockMultipartFile(
                "test_data/cos_cr.csv",
                this.getClass().getResourceAsStream("/" + "test_data/cos_cr.csv"));

        List<CsvCourseOfStudy> cosList = service.parseCoursesOfStudy(mf);
        assert cosList.size() == 2;
    }

    @Test
    void parseCoursesOfStudyErr() throws IOException {
        MockMultipartFile mf = new MockMultipartFile(
                "test_data/cos_bad.csv",
                this.getClass().getResourceAsStream("/" + "test_data/cos_bad.csv"));

        Assertions.assertThrows(ParseException.class, () -> {
            List<CsvCourseOfStudy> cosList = service.parseCoursesOfStudy(mf);
        });
    }
}
