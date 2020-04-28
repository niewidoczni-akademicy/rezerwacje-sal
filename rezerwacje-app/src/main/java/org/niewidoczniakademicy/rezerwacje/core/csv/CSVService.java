package org.niewidoczniakademicy.rezerwacje.core.csv;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.niewidoczniakademicy.rezerwacje.core.model.database.CourseOfStudy;
import org.niewidoczniakademicy.rezerwacje.core.model.database.Room;
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

    private <T> List<T> parseFile(MultipartFile file, Class<T> clazz) throws ParseException {
        try {
            if (file.isEmpty()) {
                return new ArrayList<>();
            }

            try (InputStream inputStream = file.getInputStream();
                 InputStreamReader reader = new InputStreamReader(inputStream)) {

                BeanListProcessor<T> rowProcessor = new BeanListProcessor<T>(clazz);

                CsvParserSettings parserSettings = new CsvParserSettings();
                parserSettings.setLineSeparatorDetectionEnabled(true);
                parserSettings.setProcessor(rowProcessor);
                parserSettings.setHeaderExtractionEnabled(true);

                CsvParser parser = new CsvParser(parserSettings);
                parser.parse(reader);

                return rowProcessor.getBeans();
            }
        } catch (IOException | RuntimeException e) {
            logger.info("Error parsing CSV: " + e.getLocalizedMessage());
            throw new ParseException("", 0);
        }
    }

    public List<Room> parseRoomsFile(MultipartFile file) throws ParseException {
        return parseFile(file, Room.class);
    }

    public List<CourseOfStudy> parseCoursesOfStudy(MultipartFile file) throws ParseException {
        return parseFile(file, CourseOfStudy.class);
    }
}
