package org.niewidoczniakademicy.rezerwacje.service.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.niewidoczniakademicy.rezerwacje.model.csv.CsvCourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.model.csv.CsvRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public final class CSVService {

    private Logger logger = LoggerFactory.getLogger(CSVService.class);

    private <T> List<T> parseFile(final MultipartFile file, final Class<T> clazz) throws ParseException {
        try {
            if (file.isEmpty()) {
                return new ArrayList<>();
            }

            try (InputStream inputStream = file.getInputStream();
                 InputStreamReader reader = new InputStreamReader(inputStream)) {

                CsvToBean<T> toBean = new CsvToBeanBuilder<T>(reader)
                        .withType(clazz)
                            .build();
                    return toBean.parse();
            }
        } catch (IOException | RuntimeException e) {
            logger.info("Error parsing CSV: " + e.getLocalizedMessage());
            throw new ParseException("", 0);
        }
    }

    public List<CsvRoom> parseRoomsFile(final MultipartFile file) throws ParseException {
        return parseFile(file, CsvRoom.class);
    }

    public List<CsvCourseOfStudy> parseCoursesOfStudy(final MultipartFile file) throws ParseException {
        return parseFile(file, CsvCourseOfStudy.class);
    }
}
