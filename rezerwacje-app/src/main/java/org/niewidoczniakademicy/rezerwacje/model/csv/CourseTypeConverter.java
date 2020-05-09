package org.niewidoczniakademicy.rezerwacje.model.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.niewidoczniakademicy.rezerwacje.model.shared.CourseType;

import java.util.HashMap;
import java.util.Map;

public class CourseTypeConverter extends AbstractBeanField<CourseType> {

    private final Map<String, CourseType> conversion = new HashMap<>() {{
        put(CourseType.FULL_TIME.getTypeCode(), CourseType.FULL_TIME);
        put(CourseType.EXTERNAL.getTypeCode(), CourseType.EXTERNAL);
    }};

    @Override
    protected final Object convert(final String value)
            throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        CourseType courseType = conversion.get(value);

        if (courseType != null) {
            return courseType;
        } else {
            throw new CsvDataTypeMismatchException();
        }
    }
}
