package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.repository.SchoolRepository;
import buildrun.roadeye.domain.repository.StudentSchoolRepository;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.StudentSchoolDto;
import buildrun.roadeye.service.StudentSchoolService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentSchoolServiceImplementation implements StudentSchoolService {
    private final StudentSchoolRepository studentSchoolRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public StudentSchoolServiceImplementation(StudentSchoolRepository studentSchoolRepository, UserRepository userRepository, SchoolRepository schoolRepository) {
        this.studentSchoolRepository = studentSchoolRepository;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public ResponseEntity<?> getAllStudentSchool() {
        List<StudentSchool> studentSchools = studentSchoolRepository.findAll();
        if (!studentSchools.isEmpty()) {
            return ResponseEntity.ok(studentSchools);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No Student Schools found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> createSchoolUser(StudentSchoolDto schoolDto, UUID userId) {
        try {
            User student = new User();
            School school = new School();

            Optional<User> userStudent = userRepository.findById(userId);
            Optional<School> schools = schoolRepository.findById(schoolDto.school());

            if(userStudent.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User responsible not found."));
            }else{
                student = userStudent.get();
                if (student.getRole() != RoleEnum.STUDENT) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User is not a " + RoleEnum.STUDENT.toString().toLowerCase()));
                }
            }

            if(schools.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("School responsible not found."));
            }else{
                school = schools.get();
            }
            StudentSchool studentSchool = new StudentSchool();
            studentSchool.setUser(student);
            studentSchool.setSchool(school);
            return ResponseEntity.ok(studentSchoolRepository.save(studentSchool));
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }

    @Override
    public List<StudentSchool> findSchoolByUser_Id(UUID userId) {
        return studentSchoolRepository.findByUser_Id(userId);
    }

    @Override
    public ResponseEntity<?> deleteStudentSchool(Long studentSchoolId) {
        Optional<StudentSchool> optionalStudentSchool = studentSchoolRepository.findById(studentSchoolId);
        if (optionalStudentSchool.isPresent()) {
            studentSchoolRepository.deleteById(studentSchoolId);
            ErrorResponse errorResponse = new ErrorResponse("School/Student deleted successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("School/Student does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateStudentSchool(Long studentSchoolId, StudentSchoolDto schoolDto) {
        Optional<StudentSchool> optionalStudentSchool = studentSchoolRepository.findById(studentSchoolId);
        if (optionalStudentSchool.isPresent()) {
            StudentSchool studentSchool = optionalStudentSchool.get();
            User student = new User();
            School school = new School();

            Optional<User> userStudent = userRepository.findById(schoolDto.student());
            Optional<School> schools = schoolRepository.findById(schoolDto.school());

            if(userStudent.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User responsible not found."));
            }else{
                student = userStudent.get();
                if (student.getRole() != RoleEnum.STUDENT) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User is not a " + RoleEnum.STUDENT.toString().toLowerCase()));
                }
            }

            if(schools.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("School responsible not found."));
            }else{
                school = schools.get();
            }

            studentSchool.setUser(student);
            studentSchool.setSchool(school);
            return ResponseEntity.ok(studentSchoolRepository.save(studentSchool));
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Student/School not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
