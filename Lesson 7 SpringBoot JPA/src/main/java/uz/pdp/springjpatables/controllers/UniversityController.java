package uz.pdp.springjpatables.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springjpatables.entity.Address;
import uz.pdp.springjpatables.entity.University;
import uz.pdp.springjpatables.payload.UniversityDto;
import uz.pdp.springjpatables.repository.AddressRepository;
import uz.pdp.springjpatables.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {
    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AddressRepository addressRepository;

    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversity(){
        List<University> universityList = universityRepository.findAll();
        return universityList;
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.GET)
    public University getUniversity(@PathVariable Integer id){
        Optional<University> universityOpt = universityRepository.findById(id);
        if (universityOpt.isPresent()){
            University university = universityOpt.get();
            return university;
        }else {
            return new University();
        }
    }

    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto){
        Address address = new Address();
        address.setContinent(universityDto.getContinent());
        address.setCountry(universityDto.getCountry());
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        Address savedAddress = addressRepository.save(address);

        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);
        universityRepository.save(university);

        return "University added";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversity(@PathVariable Integer id){
        universityRepository.deleteById(id);
        return "University deleted";
    }

    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto){
        Optional<University> universityId = universityRepository.findById(id);
        Optional<Address> addressId = addressRepository.findById(id);

        if (universityId.isPresent() && addressId.isPresent()){

            Address address = addressId.get();
            address.setContinent(universityDto.getContinent());
            address.setCountry(universityDto.getCountry());
            address.setCity(universityDto.getCity());
            address.setDistrict(universityDto.getDistrict());
            address.setStreet(universityDto.getStreet());
            Address savedAddress = addressRepository.save(address);

            University editingUni = universityId.get();
            editingUni.setName(universityDto.getName());
            editingUni.setAddress(savedAddress);
            universityRepository.save(editingUni);

            return "University edited";
        }else {
            return "University not found";
        }
    }

}












