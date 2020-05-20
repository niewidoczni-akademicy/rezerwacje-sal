package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.database.Faculty;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.room.GetFacultiesResponse;
import org.niewidoczniakademicy.rezerwacje.service.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class FacultyService {

    private final FacultyRepository facultyRepository;

    public GetFacultiesResponse getAllResponse() {
        Set<Faculty> faculties = new HashSet<>(this.facultyRepository.findAll());

        return GetFacultiesResponse.builder()
                .faculties(faculties)
                .build();
    }
}
