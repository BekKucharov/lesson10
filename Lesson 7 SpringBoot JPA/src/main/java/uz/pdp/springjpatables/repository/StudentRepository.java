package uz.pdp.springjpatables.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springjpatables.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAllByGroups_Id(Integer group_id);
    List<Student> findAllByGroups_Faculty_Id(Integer group_faculty_id);
    List<Student> findAllByGroups_Faculty_University_Id(Integer group_faculty_university_id);
    Page<Student> findAllByGroups_Faculty_University_Id(Integer groups_faculty_university_id, Pageable pageable);
    Page<Student> findAllByGroups_Faculty_Id(Integer groups_faculty_id, Pageable pageable);
    Page<Student> findAllByGroups_Id(Integer groups_id, Pageable pageable);

}
