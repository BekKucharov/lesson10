package uz.pdp.springjpatables.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.springjpatables.entity.University;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
}
