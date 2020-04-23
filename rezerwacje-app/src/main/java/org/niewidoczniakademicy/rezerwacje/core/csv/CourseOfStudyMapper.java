package org.niewidoczniakademicy.rezerwacje.core.csv;

import org.niewidoczniakademicy.rezerwacje.core.model.database.CourseOfStudy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseOfStudyMapper {
    public CourseOfStudy convert(org.niewidoczniakademicy.rezerwacje.core.model.csv.CourseOfStudy cos) {
        return CourseOfStudy.builder().build();
    }

    public List<CourseOfStudy> convert(List<org.niewidoczniakademicy.rezerwacje.core.model.csv.CourseOfStudy> cos) {
        return new ArrayList<>();  // TODO: gather users and faculties from DB...
    }
}
