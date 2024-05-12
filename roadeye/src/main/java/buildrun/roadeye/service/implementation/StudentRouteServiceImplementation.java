package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.domain.enums.*;
import buildrun.roadeye.domain.repository.SchoolRepository;
import buildrun.roadeye.domain.repository.StudentRouteRepository;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.StudentRouteDto;
import buildrun.roadeye.rest.dto.StudentRouteUpdateDto;
import buildrun.roadeye.service.StudentRouteService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentRouteServiceImplementation implements StudentRouteService {
    private final StudentRouteRepository studentRouteRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public StudentRouteServiceImplementation(StudentRouteRepository studentRouteRepository, UserRepository userRepository, SchoolRepository schoolRepository) {
        this.studentRouteRepository = studentRouteRepository;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public ResponseEntity<?> getAllStudentRoute() {
        List<StudentRoute> studentRoutes = studentRouteRepository.findAll();
        if (!studentRoutes.isEmpty()) {
            return ResponseEntity.ok(studentRoutes);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("No Student Schools found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> createStudentRoute(StudentRouteDto routeDto, UUID userId) {
        try {
            User student = new User();
            School school = new School();

            // Verificar se o usuário existe e se é um estudante
            Optional<User> userStudent = userRepository.findById(userId);
            if(userStudent.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User responsible not found."));
            }
            student = userStudent.get();
            if (student.getRole() != RoleEnum.STUDENT) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User is not a " + RoleEnum.STUDENT.toString().toLowerCase()));
            }

            // Verificar se a escola existe
            Optional<School> schools = schoolRepository.findById(routeDto.school());
            if(schools.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("School responsible not found."));
            }
            school = schools.get();

//            // Verificar se já existe uma associação entre o usuário e a escola
//            boolean alreadyExists = studentSchoolRepository.existsByUserAndSchool(student, school);
//            if (alreadyExists) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User is already associated with the school."));
//            }

            //Verificar se o enum existe
            if (StudentStatus.isStudentStatusValid(routeDto.studentStatus()) == false) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("The student status entered is invalid."));
            }

            // Criar e salvar a associação entre o usuário e a escola
            StudentRoute studentRoute = new StudentRoute();
            studentRoute.setUser(student);
            studentRoute.setSchool(school);
            studentRoute.setLocalDate(LocalDate.now());
            studentRoute.setStudentStatus(routeDto.studentStatus());
            studentRoute.setStatusRoute(StatusRoute.WAITINGTOSTART);
            studentRoute.setConfimationStudentEnum(ConfimationStudentEnum.ABSENT);
            if(studentRoute.getStudentStatus() == StudentStatus.IWONTGO){
                studentRoute.setStatusRoute(StatusRoute.ROUTEFINISHED);
                studentRoute.setConfimationStudentEnum(ConfimationStudentEnum.CANCEL);
            }
            return ResponseEntity.ok(studentRouteRepository.save(studentRoute));
        } catch (ResponseStatusException ex) {
            throw new ResponseStatusException(ex.getStatusCode(), ex.getMessage());
        }
    }


    @Override
    public List<StudentRoute> findSchoolByUser_Id(UUID userId) {
        return studentRouteRepository.findByUser_Id(userId);
    }

    @Override
    public ResponseEntity<?> deleteStudentRoute(Long studentSchoolId) {
        Optional<StudentRoute> optionalStudentSchool = studentRouteRepository.findById(studentSchoolId);
        if (optionalStudentSchool.isPresent()) {
            studentRouteRepository.deleteById(studentSchoolId);
            ErrorResponse errorResponse = new ErrorResponse("School/Student deleted successfully");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("School/Student does not exist.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<?> updateStudentRoute(Long studentSchoolId, StudentRouteUpdateDto schoolDto) {
        Optional<StudentRoute> optionalStudentSchool = studentRouteRepository.findById(studentSchoolId);
        if (optionalStudentSchool.isPresent()) {
            StudentRoute studentRoute = optionalStudentSchool.get();
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

            studentRoute.setUser(student);
            studentRoute.setSchool(school);
            return ResponseEntity.ok(studentRouteRepository.save(studentRoute));
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Student/School not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Override
    public List<StudentRoute> findSchoolBySchool_Id(Long schoolId) {
        return studentRouteRepository.findBySchool_Id(schoolId);
    }

    @Override
    public ResponseEntity<?> updateStudentRouteImagem(MultipartFile file, Long routeId) {
        System.out.println("Imagem name: "+ file);
        if(file == null){
            ErrorResponse errorResponse = new ErrorResponse("The image cannot be null.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        StudentRoute studentRoute = new StudentRoute();
        // Verifica se a rota existe
        Optional<StudentRoute> optionalStudentRoute = studentRouteRepository.findById(routeId);
        if (optionalStudentRoute.isPresent()) {
            studentRoute = optionalStudentRoute.get();
            try {
                // Converte a imagem para um array de bytes
                byte[] imageData = file.getBytes();
                // Define a imagem e a data de upload na rota
                studentRoute.setImageData(imageData);
                studentRoute.setConfimationStudentEnum(ConfimationStudentEnum.CONFIRM);
                // Atualiza a rota no banco de dados
                studentRouteRepository.save(studentRoute);

                return ResponseEntity.ok("Student image updated successfully.");
            } catch (IOException e) {
                ErrorResponse errorResponse = new ErrorResponse("Error processing the image.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Student/Route not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
