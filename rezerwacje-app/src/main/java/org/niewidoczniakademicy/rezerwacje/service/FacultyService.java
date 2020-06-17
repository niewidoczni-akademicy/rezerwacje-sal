package org.niewidoczniakademicy.rezerwacje.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niewidoczniakademicy.rezerwacje.model.database.Faculty;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.AddFacultyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.EditFacultyRequest;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.GetFacultiesResponse;
import org.niewidoczniakademicy.rezerwacje.model.rest.faculty.GetFacultyResponse;
import org.niewidoczniakademicy.rezerwacje.service.exception.FacultyAlreadyExistsException;
import org.niewidoczniakademicy.rezerwacje.service.exception.FacultyNotFoundException;
import org.niewidoczniakademicy.rezerwacje.service.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public final class FacultyService {

    private final FacultyRepository facultyRepository;

    public GetFacultiesResponse getAllResponse() {
        Set<Faculty> faculties = new HashSet<>(this.facultyRepository.findAll());
        Set<Faculty> predecessors = new HashSet<>();

        for (Faculty faculty : faculties) {
            Faculty predecessor = faculty.getPredecessor();
            if (predecessor != null) {
                predecessors.add(predecessor);
            }
        }

        faculties.removeAll(predecessors);

        return GetFacultiesResponse.builder()
                .faculties(faculties)
                .build();
    }

    public GetFacultyResponse saveFaculty(AddFacultyRequest request) {
        List<Faculty> prev = facultyRepository.findFacultiesByNameIn(new HashSet<>() {{ add(request.getName()); }});
        if (!prev.isEmpty()) {
            throw new FacultyAlreadyExistsException("Faculty " + request.getName() + " already exists");
        }

        Faculty faculty = Faculty
                .builder()
                .name(request.getName())
                .build();

        faculty = facultyRepository.save(faculty);

        return GetFacultyResponse
                .builder()
                .faculty(faculty)
                .build();
    }

    public GetFacultyResponse editFaculty(EditFacultyRequest request) {
        Faculty oldFaculty = facultyRepository.findById(request.getId())
                .orElseThrow(() -> new FacultyNotFoundException("Faculty with id " + request.getId() + " not found"));

        Faculty faculty = Faculty
                .builder()
                .name(request.getName())
                .predecessor(oldFaculty)
                .build();

        faculty = facultyRepository.save(faculty);

        return GetFacultyResponse
                .builder()
                .faculty(faculty)
                .build();
    }

    public GetFacultiesResponse getHistory(Long id) {
        Faculty root = facultyRepository
                .findById(id)
                .orElse(null);
        Set<Faculty> faculties = new HashSet<>();
        while (root != null) {
            faculties.add(root);
            root = root.getPredecessor();
        }

        return GetFacultiesResponse.builder()
                .faculties(faculties)
                .build();
    }

}
