package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.FatherSonRelationship;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.repository.FatherSonRelationshipRepository;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.FatherSonRelationshipDto;
import buildrun.roadeye.service.FatherSonRelationshipService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class FatherSonRelationshipServiceImplementation implements FatherSonRelationshipService {
    private final FatherSonRelationshipRepository fatherSonRelationshipRepository;
    private final UserRepository userRepository;

    public FatherSonRelationshipServiceImplementation(FatherSonRelationshipRepository fatherSonRelationshipRepository, UserRepository userRepository) {
        this.fatherSonRelationshipRepository = fatherSonRelationshipRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<FatherSonRelationship> getAllFatherSonRelationship() {
        return fatherSonRelationshipRepository.findAll();
    }

    @Override
    @Transactional
    public FatherSonRelationship getFatherSonRelationshipById(Long fatherSonRelationshipId) {
        return fatherSonRelationshipRepository.findById(fatherSonRelationshipId).orElseThrow(() -> new EntityNotFoundException("Father Son RelationshipId not found"));
    }

    @Transactional
    public List<FatherSonRelationship> findFatherSonRelationshipByUserId(FatherSonRelationshipDto fatherSonRelationshipDto) {
        return fatherSonRelationshipRepository.findByResponsible_IdOrStudent_Id(fatherSonRelationshipDto.responsible().getId(), fatherSonRelationshipDto.student().getId());
    }

    @Override
    @Transactional
    public void deleteFatherSonRelationship(Long fatherSonRelationshipId) {
        FatherSonRelationship relationship = fatherSonRelationshipRepository.findById(fatherSonRelationshipId).orElseThrow(() -> new EntityNotFoundException("Responsible or Student not found"));
        fatherSonRelationshipRepository.delete(relationship);
    }

    @Override
    @Transactional
    public FatherSonRelationship updateFatherSonRelationship(Long fatherSonRelationshipId, FatherSonRelationshipDto fatherSonRelationshipDto) {
        User userResponsible = new User();
        User userStudent     = new User();

        UUID userResponsibleId = fatherSonRelationshipDto.responsible().getId();
        UUID userStudentId = fatherSonRelationshipDto.student().getId();

        Optional<User> optionalUserResponsible = userRepository.findById(userResponsibleId);
        if (optionalUserResponsible.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User responsible not found");
        }else{
            userResponsible = optionalUserResponsible.get();
            if (userResponsible.getRole() != RoleEnum.RESPONSIBLE) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User responsible is not a father");
            }
        }

        Optional<User> optionalUserStudent = userRepository.findById(userStudentId);
        if (optionalUserStudent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User student not found");
        }else{
            userStudent = optionalUserStudent.get();
            if (userStudent.getRole() != RoleEnum.STUDENT) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User student is not a son");
            }
        }

        Optional<FatherSonRelationship> optionalFatherSonRelationship = fatherSonRelationshipRepository.findById(fatherSonRelationshipId);
        if (optionalFatherSonRelationship.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Father Son Relationship not found");
        }

        FatherSonRelationship fatherSonRelationship = optionalFatherSonRelationship.get();
        fatherSonRelationship.setResponsible(userResponsible);
        fatherSonRelationship.setStudent(userStudent);

        return fatherSonRelationshipRepository.save(fatherSonRelationship);
    }
}
