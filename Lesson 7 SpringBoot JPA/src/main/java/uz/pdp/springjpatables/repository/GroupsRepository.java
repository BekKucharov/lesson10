package uz.pdp.springjpatables.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.springjpatables.entity.Groups;

import java.util.List;

public interface GroupsRepository extends JpaRepository<Groups, Integer> {

    List<Groups> findAllByFaculty_University_Id(Integer faculty_university_id);
//
//    @Query("select gr from groups gr where gr.faculty.university.id=:universityId")
//    List<Groups> getGroupsByUniversityId(Integer universityId);
//
//    @Query(value = "select * from groups g join faculty f on f.id=g.faculty_id join university u on u.id = f.university_id " +
//            "where u.id=:universityId", nativeQuery = true)
//    List<Groups> getGroupsByUniversityIdNative(Integer universityId);
}
