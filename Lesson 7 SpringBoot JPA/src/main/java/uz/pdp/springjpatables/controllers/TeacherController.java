package uz.pdp.springjpatables.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springjpatables.entity.Subject;
import uz.pdp.springjpatables.entity.Teacher;
import uz.pdp.springjpatables.payload.TeacherDto;
import uz.pdp.springjpatables.repository.SubjectRepository;
import uz.pdp.springjpatables.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;

    //all
    @GetMapping
    public List<Teacher> getTeacher(){
        List<Teacher> teacherList = teacherRepository.findAll();
        return teacherList;
    }
    //by id
    @GetMapping("/{id}")
    public Teacher getTeacher(@PathVariable Integer id){
        Optional<Teacher> teacherId = teacherRepository.findById(id);
        if (teacherId.isPresent()){
            Teacher teacher = teacherId.get();
            return teacher;
        }else
            return new Teacher();
    }

    @PostMapping
    public String addTeacher(@RequestBody TeacherDto teacherDto){
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());

        Subject subject = new Subject();
        subject.setName(teacherDto.getName());
        subjectRepository.save(subject);

        teacher.setSubject(subject);
        teacherRepository.save(teacher);
        return "Teacher info saved";
    }
    @PutMapping("/{id}")
    public String editTeacher(@PathVariable Integer id, @RequestBody TeacherDto teacherDto){
        Optional<Teacher> teacherId = teacherRepository.findById(id);
        Optional<Subject> subjectId = subjectRepository.findById(id);

        if (teacherId.isPresent()){
            Teacher editingTeacher = teacherId.get();
            editingTeacher.setFirstName(teacherDto.getFirstName());
            editingTeacher.setLastName(teacherDto.getLastName());

            Subject editingSubject = subjectId.get();
            editingSubject.setName(teacherDto.getName());
            subjectRepository.save(editingSubject);

            teacherRepository.save(editingTeacher);
            return "Teacher info edited";
        }else{
            return "Teacher not found";
        }

    }
    @DeleteMapping("/{id}")
    public String deleteTeacher(@PathVariable Integer id){
        teacherRepository.deleteById(id);
        return "Teacher info deleted";
    }




}
