package uz.pdp.springjpatables.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springjpatables.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
