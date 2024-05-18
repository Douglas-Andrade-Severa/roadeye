package buildrun.roadeye.rest.dto.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.rest.dto.service.FatherSonRelationshipService;
import buildrun.roadeye.domain.repository.FatherSonRelationshipRepository;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.FatherSonRelationshipDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FatherSonRelationshipServiceImplementation implements FatherSonRelationshipService {
    private final FatherSonRelationshipRepository fatherSonRelationshipRepository;
    private final UserRepository userRepository;

    public FatherSonRelationshipServiceImplementation(FatherSonRelationshipRepository fatherSonRelationshipRepository, UserRepository userRepository) {
        this.fatherSonRelationshipRepository = fatherSonRelationshipRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getAllFatherSonRelationship() {
        List<FatherSonRelationship> relationships = fatherSonRelationshipRepository.findAll();
        if (!relationships.isEmpty()) {
            return ResponseEntity.ok(relationships);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No RelationshipId found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> findFatherSonRelationshipByUserId(FatherSonRelationshipDto fatherSonRelationshipDto) {
        List<FatherSonRelationship> relationships = fatherSonRelationshipRepository.findByResponsible_IdOrStudent_Id(fatherSonRelationshipDto.responsible(), fatherSonRelationshipDto.student());
        if (!relationships.isEmpty()) {
            return ResponseEntity.ok(relationships);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No RelationshipId found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> getFatherSonRelationshipById(Long fatherSonRelationshipId) {
        FatherSonRelationship relationship = fatherSonRelationshipRepository.findById(fatherSonRelationshipId).orElse(null);
        if (relationship != null){
            return ResponseEntity.ok(relationship);
        }else {
            ErrorResponse errorResponse = new ErrorResponse("Father Son RelationshipId not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> deleteFatherSonRelationship(Long fatherSonRelationshipId) {
        Optional<FatherSonRelationship> optionalFatherSonRelationship = fatherSonRelationshipRepository.findById(fatherSonRelationshipId);
        if(optionalFatherSonRelationship.isPresent()){
            fatherSonRelationshipRepository.deleteById(fatherSonRelationshipId);
            ErrorResponse errorResponse = new ErrorResponse("Father Son Relationship deleted successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Father Son Relationship Id does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @Override
    public ResponseEntity<?> updateFatherSonRelationship(Long fatherSonRelationshipId, FatherSonRelationshipDto fatherSonRelationshipDto) {
        User responsible = new User();
        User student = new User();
        FatherSonRelationship relationship =  new FatherSonRelationship();
        Optional<FatherSonRelationship> fatherSonRelationship = fatherSonRelationshipRepository.findById(fatherSonRelationshipId);
        Optional<User> userResponsible = userRepository.findById(fatherSonRelationshipDto.responsible());
        Optional<User> userStudent = userRepository.findById(fatherSonRelationshipDto.student());
        if(fatherSonRelationship.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Father Son Relationship not found."));
        }
        if(userResponsible.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User responsible not found."));
        }else{
            responsible = userResponsible.get();
            if (responsible.getRole() != RoleEnum.RESPONSIBLE) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User is not a " + RoleEnum.RESPONSIBLE.toString().toLowerCase()));
            }
        }
        if(userStudent.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User responsible not found."));
        }else{
            student = userStudent.get();
            if (student.getRole() != RoleEnum.STUDENT) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User is not a " + RoleEnum.STUDENT.toString().toLowerCase()));
            }
        }
        relationship.setResponsible(responsible);
        relationship.setStudent(student);
        return ResponseEntity.ok(fatherSonRelationshipRepository.save(relationship));
    }
    @Override
    public ResponseEntity<?> createFatherSonRelationship(FatherSonRelationshipDto fatherSonRelationshipDto) {
        User responsible = new User();
        User student = new User();
        FatherSonRelationship relationship =  new FatherSonRelationship();
        Optional<User> userResponsible = userRepository.findById(fatherSonRelationshipDto.responsible());
        Optional<User> userStudent = userRepository.findById(fatherSonRelationshipDto.student());

        if(userResponsible.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User responsible not found."));
        }else{
            responsible = userResponsible.get();
            if (responsible.getRole() != RoleEnum.RESPONSIBLE) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User is not a " + RoleEnum.RESPONSIBLE.toString().toLowerCase()));
            }
        }
        if(userStudent.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User responsible not found."));
        }else{
            student = userStudent.get();
            if (student.getRole() != RoleEnum.STUDENT) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User is not a " + RoleEnum.STUDENT.toString().toLowerCase()));
            }
        }

        FatherSonRelationship fatherSonRelationship = new FatherSonRelationship();
        fatherSonRelationship.setResponsible(responsible);
        fatherSonRelationship.setStudent(student);
        return ResponseEntity.ok(fatherSonRelationshipRepository.save(fatherSonRelationship));
    }
}
