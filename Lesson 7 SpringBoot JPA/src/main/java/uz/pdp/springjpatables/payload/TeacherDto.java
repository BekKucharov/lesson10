package uz.pdp.springjpatables.payload;

import lombok.Data;

@Data
public class TeacherDto {
    private String firstName;
    private String lastName;
    private String name;

    private Integer subjectId;
}
