package uz.pdp.springjpatables.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springjpatables.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    boolean existsByName(String name);

}
