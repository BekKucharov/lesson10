package uz.pdp.springjpatables.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springjpatables.entity.Address;
import uz.pdp.springjpatables.entity.Groups;
import uz.pdp.springjpatables.entity.Student;
import uz.pdp.springjpatables.entity.Subject;
import uz.pdp.springjpatables.payload.StudentDto;
import uz.pdp.springjpatables.repository.AddressRepository;
import uz.pdp.springjpatables.repository.GroupsRepository;
import uz.pdp.springjpatables.repository.StudentRepository;
import uz.pdp.springjpatables.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupsRepository groupsRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    SubjectRepository subjectRepository;

    //lesson 10
    // ministry
    @GetMapping("/forMinistry")
    public Page<Student> getStudentAllPages(@RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> students = studentRepository.findAll(pageable);
        return students;
    }
    // university
    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentByUniversityPages(@PathVariable Integer universityId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroups_Faculty_University_Id(universityId, pageable);
        return studentPage;
    }
    //faculty
    @GetMapping("/byFaculty/{facultyId}")
    public Page<Student> getStudentByFacultyPage(@PathVariable Integer facultyId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findAllByGroups_Faculty_Id(facultyId, pageable);
        return studentPage;
    }
    //group
    @GetMapping("/byGroup/{groupId}")
    public Page<Student> getStudentByGroup(@PathVariable Integer groupId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<Student> studentPage = studentRepository.findAllByGroups_Id(groupId, pageable);
        return studentPage;
    }

    //all
    @GetMapping
    public List<Student> getStudent() {
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    //any by id
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Integer id) {
        Optional<Student> studentId = studentRepository.findById(id);
        if (studentId.isPresent()) {
            Student student = studentId.get();
            return student;
        } else {
            return new Student();
        }
    }

    //by group id
    @GetMapping("/groupId/{group_id}")
    public List<Student> getStudentByGroup(@PathVariable Integer group_id) {
        List<Student> allByGroup_id = studentRepository.findAllByGroups_Id(group_id);
        return allByGroup_id;
    }

    //by Faculty id
    @GetMapping("/facultyId/{faculty_id}")
    public List<Student> getStudentByFaculty(@PathVariable Integer faculty_id) {
        List<Student> allByGroup_faculty_id = studentRepository.findAllByGroups_Faculty_Id(faculty_id);
        return allByGroup_faculty_id;
    }

    //by University id
    @GetMapping("/universityId/{university_id}")
    public List<Student> getStudentByUniversity(@PathVariable Integer university_id) {
        return studentRepository.findAllByGroups_Faculty_University_Id(university_id);
    }

    @PostMapping
    public String addStudent(@RequestBody StudentDto studentDto) {
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());

        Address address = new Address();
        address.setContinent(studentDto.getContinent());
        address.setCountry(studentDto.getCountry());
        address.setCity(studentDto.getCity());
        address.setDistrict(studentDto.getDistrict());
        address.setStreet(studentDto.getStreet());
        addressRepository.save(address);

        student.setAddress(address);

        Optional<Groups> groupId = groupsRepository.findById(studentDto.getGroupId());

        if (!groupId.isPresent()) return "Group id not found!";

        student.setGroups(groupId.get());

        List<Subject> subjectList = new ArrayList<>();
        List<Integer> subjectIds = studentDto.getSubjectIds();

        for (Integer subjectId : subjectIds) {

            Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
            if (!optionalSubject.isPresent()) return "Subject id is not found!";

            Subject subject = optionalSubject.get();
            subjectList.add(subject);

        }

        student.setSubjects(subjectList);

        studentRepository.save(student);
        return "Student saved";
    }

    @PutMapping("/{id}")
    public String editStudent(@PathVariable Integer id, @RequestBody StudentDto studentDto) {
        Optional<Student> studentId = studentRepository.findById(id);
        Optional<Address> addressId = addressRepository.findById(id);
        Optional<Groups> groupId = groupsRepository.findById(studentDto.getGroupId());

        if (studentId.isPresent()) {
            Student editingStudent = studentId.get();
            editingStudent.setFirstName(studentDto.getFirstName());
            editingStudent.setLastName(studentDto.getLastName());

            Address editingAddress = addressId.get();
            editingAddress.setContinent(studentDto.getContinent());
            editingAddress.setCountry(studentDto.getCountry());
            editingAddress.setCity(studentDto.getCity());
            editingAddress.setDistrict(studentDto.getDistrict());
            editingAddress.setStreet(studentDto.getStreet());
            addressRepository.save(editingAddress);

            editingStudent.setGroups(groupId.get());

            List<Subject> subjectList = new ArrayList<>();
            List<Integer> subjectIds = studentDto.getSubjectIds();

            for (Integer subjectId : subjectIds) {

                Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
                if (!optionalSubject.isPresent()) return "Subject id is not found!";

                Subject subject = optionalSubject.get();
                subjectList.add(subject);

            }

            editingStudent.setSubjects(subjectList);


            studentRepository.save(editingStudent);
            return "Student info edited";
        } else {
            return "Student not found";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentRepository.deleteById(id);
        return "Student deleted";
    }
}























