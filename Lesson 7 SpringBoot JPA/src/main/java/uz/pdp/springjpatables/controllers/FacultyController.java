package uz.pdp.springjpatables.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springjpatables.entity.Faculty;
import uz.pdp.springjpatables.entity.University;
import uz.pdp.springjpatables.payload.FacultyDto;
import uz.pdp.springjpatables.repository.FacultyRepository;
import uz.pdp.springjpatables.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/faculty")
public class FacultyController {
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto) {

        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists)
            return "Faculty in this university exist";

        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        Optional<University> universityId = universityRepository.findById(facultyDto.getUniversityId());

        if (universityId.isPresent()) {
            faculty.setUniversity(universityId.get());
            facultyRepository.save(faculty);
            return "Faculty added";
        }else
            return "University not found";
        }

    @GetMapping
    public List<Faculty> getFaculty(){
        return facultyRepository.findAll();
    }
    @GetMapping(value = "/{id}")
    public Faculty getFaculty(@PathVariable Integer id){
        Optional<Faculty> facultyId = facultyRepository.findById(id);
        if (facultyId.isPresent()){
            Faculty faculty = facultyId.get();
            return faculty;
        }else
            return new Faculty();
    }
    @GetMapping("byUniversityId/{universityId}")
    public List<Faculty> getFacultyByUniversityId(@PathVariable Integer universityId){
        List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
        return allByUniversityId;
    }

    @DeleteMapping(value = "/{id}")
    public String deleteFaculty(@PathVariable Integer id){
        Optional<Faculty> facultyId = facultyRepository.findById(id);
        if (!facultyId.isPresent()) return "Faculty not found";

        facultyRepository.deleteById(id);
        return "Faculty deleted";
    }

    @PutMapping(value = "/{id}")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto){

        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists)
            return "Faculty in this university exist";

        Optional<Faculty> facultyId = facultyRepository.findById(id);
        Optional<University> universityId = universityRepository.findById(facultyDto.getUniversityId());

        if (facultyId.isPresent() && universityId.isPresent()){

            Faculty editingFaculty = facultyId.get();
            editingFaculty.setName(facultyDto.getName());
            editingFaculty.setUniversity(universityId.get());
            facultyRepository.save(editingFaculty);
            return "Faculty edited";
        }else{
            return "Faculty or University not found";
        }
    }






    }
