package uz.pdp.springjpatables.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springjpatables.entity.Subject;
import uz.pdp.springjpatables.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
    @Autowired
    SubjectRepository subjectRepository;

    @PostMapping
    public String addSubject(@RequestBody Subject subject) {
        boolean exists = subjectRepository.existsByName(subject.getName());
        if (exists) {
            return "This subject is already exist";
        } else {
            subjectRepository.save(subject);
            return "Subject added";
        }
    }

    @GetMapping
    public List<Subject> getSubject(){
        List<Subject> subjectList = subjectRepository.findAll();
        return subjectList;
    }

    @GetMapping(value = "/{id}")
    public Subject getSubject(@PathVariable Integer id){
        Optional<Subject> subjectId = subjectRepository.findById(id);
        if (subjectId.isPresent()){
            Subject subject = subjectId.get();
            return subject;
        }else
            return new Subject();
    }

    @DeleteMapping(value = "/{id}")
    public String delSubject(@PathVariable Integer id){
        subjectRepository.deleteById(id);
        return "Subject deleted";
    }

    @PutMapping(value = "/{id}")
    public String editSubject(@PathVariable Integer id, @RequestBody Subject subject){
        Optional<Subject> subjectId = subjectRepository.findById(id);
        if (subjectId.isPresent()){
            Subject editingSubject = subjectId.get();

            editingSubject.setName(subject.getName());
            subjectRepository.save(editingSubject);
            return "Subject edited";
        }else{
            return "Subject not found";
        }
    }


}
