package org.niewidoczniakademicy.rezerwacje.core;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.niewidoczniakademicy.rezerwacje.core.model.Room;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVService {

    public List<Room> parseRoomsFile(MultipartFile file) throws ParseException {
        try {
            if (file.isEmpty()) {
                return new ArrayList<>();
            }

            try (
                    InputStream inputStream = file.getInputStream();
                    InputStreamReader reader = new InputStreamReader(inputStream)
            ) {
                CSVFormat format = CSVFormat.RFC4180.withHeader("building", "name");
                CSVParser parser = new CSVParser(reader, format);
                List<Room> result = new ArrayList<>();
                parser.iterator().forEachRemaining(record -> {
                    Room r = new Room(record.get("name"));
                    result.add(r);
                });
                return result;
            }
        }
        catch (IOException e) {
            throw new ParseException("CSV shit!", 0);
        }
    }
}
