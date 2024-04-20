package buildrun.roadeye.rest.service.implementation;

import buildrun.roadeye.domain.entity.FatherSonRelationship;
import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.repository.FatherSonRelationshipRepository;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.FatherSonRelationshipDto;
import buildrun.roadeye.rest.service.FatherSonRelationshipService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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
    public List<FatherSonRelationship> getAllFatherSonRelationship() {
        return fatherSonRelationshipRepository.findAll();
    }

    @Override
    public FatherSonRelationship getFatherSonRelationshipById(Long fatherSonRelationshipId) {
        return fatherSonRelationshipRepository.findById(fatherSonRelationshipId)
                .orElseThrow(() -> new EntityNotFoundException("Father Son RelationshipId not found"));
    }

    @Override
    public List<FatherSonRelationship> findFatherSonRelationshipByUserId(FatherSonRelationshipDto fatherSonRelationshipDto) {
        return fatherSonRelationshipRepository.findByResponsible_IdOrStudent_Id(
                fatherSonRelationshipDto.responsible().getId(), fatherSonRelationshipDto.student().getId());
    }

    @Override
    public void deleteFatherSonRelationship(Long fatherSonRelationshipId) {
        fatherSonRelationshipRepository.deleteById(fatherSonRelationshipId);
    }

    @Override
    public FatherSonRelationship updateFatherSonRelationship(Long fatherSonRelationshipId, FatherSonRelationshipDto fatherSonRelationshipDto) {
        FatherSonRelationship fatherSonRelationship = fatherSonRelationshipRepository.findById(fatherSonRelationshipId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Father Son Relationship not found"));

        User userResponsible = getUserById(fatherSonRelationshipDto.responsible().getId(), RoleEnum.RESPONSIBLE);
        User userStudent = getUserById(fatherSonRelationshipDto.student().getId(), RoleEnum.STUDENT);

        fatherSonRelationship.setResponsible(userResponsible);
        fatherSonRelationship.setStudent(userStudent);

        return fatherSonRelationshipRepository.save(fatherSonRelationship);
    }

    @Override
    public FatherSonRelationship createFatherSonRelationship(FatherSonRelationshipDto fatherSonRelationshipDto) {
        User userResponsible = getUserById(fatherSonRelationshipDto.responsible().getId(), RoleEnum.RESPONSIBLE);
        User userStudent = getUserById(fatherSonRelationshipDto.student().getId(), RoleEnum.STUDENT);

        FatherSonRelationship fatherSonRelationship = new FatherSonRelationship();
        fatherSonRelationship.setResponsible(userResponsible);
        fatherSonRelationship.setStudent(userStudent);

        return fatherSonRelationshipRepository.save(fatherSonRelationship);
    }

    private User getUserById(UUID userId, RoleEnum role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (user.getRole() != role) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not a " + role.toString().toLowerCase());
        }
        return user;
    }
}
