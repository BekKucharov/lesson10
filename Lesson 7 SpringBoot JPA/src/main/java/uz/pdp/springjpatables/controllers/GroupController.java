package uz.pdp.springjpatables.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springjpatables.entity.Faculty;
import uz.pdp.springjpatables.entity.Groups;
import uz.pdp.springjpatables.payload.GroupsDto;
import uz.pdp.springjpatables.repository.FacultyRepository;
import uz.pdp.springjpatables.repository.GroupsRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/group")
public class GroupController {
    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    FacultyRepository facultyRepository;

    //all
    @GetMapping
    public List<Groups> getGroup(){
        List<Groups> groupsList = groupsRepository.findAll();
        return groupsList;
    }
    //by uni id
    @GetMapping("/byUniversityId/{universityId}")
    public List<Groups> getGroupsByUniversityId(@PathVariable Integer universityId){
        List<Groups> allByFaculty_university_id = groupsRepository.findAllByFaculty_University_Id(universityId);

        return allByFaculty_university_id;
    }

    @PostMapping
    public String addGroup(@RequestBody GroupsDto groupsDto){
        Groups groups = new Groups();
        groups.setName(groupsDto.getName());

        Optional<Faculty> facultyId = facultyRepository.findById(groupsDto.getFacultyId());

        if (!facultyId.isPresent()){
            return "Faculty not found";
        }
        groups.setFaculty(facultyId.get());
        groupsRepository.save(groups);
        return "Group Added";
    }

    @PutMapping("/{id}")
    public String editGroup(@PathVariable Integer id, @RequestBody GroupsDto groupsDto){
        Optional<Groups> groupId = groupsRepository.findById(id);
        Optional<Faculty> facultyId = facultyRepository.findById(groupsDto.getFacultyId());

        if (groupId.isPresent()){
            Groups editingGroups = groupId.get();
            editingGroups.setName(groupsDto.getName());
            editingGroups.setFaculty(facultyId.get());
            groupsRepository.save(editingGroups);
            return "Group edited";
        }else{
            return "Group not found";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable Integer id) {
        Optional<Groups> groupId = groupsRepository.findById(id);
        if (groupId.isPresent()) {
            groupsRepository.deleteById(id);
            return "Group deleted";
        }else{
            return "Group not found";
        }
    }





}






