package uz.pdp.springjpatables.payload;

import lombok.Data;
import uz.pdp.springjpatables.entity.Subject;

import java.util.List;

@Data
public class StudentDto {
    private String firstName;
    private String lastName;
    private String continent;
    private String country;
    private String city;
    private String district;
    private String street;
    private Integer groupId;

    private List<Integer> subjectIds;

}
