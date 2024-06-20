package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.*;
import buildrun.roadeye.domain.enums.*;
import buildrun.roadeye.domain.repository.SchoolRepository;
import buildrun.roadeye.domain.repository.StudentRouteRepository;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.rest.dto.StudentRouteDto;
import buildrun.roadeye.rest.dto.StudentRouteUpdateDto;
import buildrun.roadeye.rest.dto.StudentRouteWithAddresses;
import buildrun.roadeye.service.StudentRouteService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.*;

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
            // Verificar se o usuário existe e se é um estudante
            Optional<User> userStudent = userRepository.findById(userId);
            if (userStudent.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User responsible not found."));
            }
            User student = userStudent.get();
            if (student.getRole() != RoleEnum.STUDENT) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("User is not a " + RoleEnum.STUDENT.toString().toLowerCase()));
            }

            // Verificar se a escola existe
            Optional<School> schoolOpt = schoolRepository.findById(routeDto.school());
            if (schoolOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("School responsible not found."));
            }
            School school = schoolOpt.get();

            // Verificar se o enum de status do estudante é válido
            if (!StudentStatusEnum.isStudentStatusValid(routeDto.studentStatusEnum())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("The student status entered is invalid."));
            }

            // Verificar se o enum de período é válido
            if (!PeriodEnum.isPeriodValid(routeDto.periodEnum())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("The period entered is invalid."));
            }

            // Verificar se a data foi informada
            if (routeDto.localDate() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("The date entered is invalid."));
            }

            // Criar e salvar a associação entre o usuário e a escola
            StudentRoute studentRoute = new StudentRoute();
            studentRoute.setUser(student);
            studentRoute.setSchool(school);
            studentRoute.setLocalDate(routeDto.localDate());
            studentRoute.setPeriodEnum(routeDto.periodEnum());
            studentRoute.setStatusRouteEnum(StatusRouteEnum.WAITINGTOSTART);
            studentRoute.setConfimationStudentEnum(ConfimationStudentEnum.ABSENT);

            // Ajustar status e confirmação baseado no status do estudante
            if (routeDto.studentStatusEnum() == StudentStatusEnum.IWONTGO) {
                studentRoute.setStatusRouteEnum(StatusRouteEnum.ROUTEFINISHED);
                studentRoute.setConfimationStudentEnum(ConfimationStudentEnum.CANCEL);
            }

            // Tratamento especial para ROUNDTRIP
            if (routeDto.studentStatusEnum() == StudentStatusEnum.ROUNDTRIP) {
                // Primeira instância para ONEWAYONLY
                StudentRoute oneWayRoute = new StudentRoute();
                oneWayRoute.setUser(student);
                oneWayRoute.setSchool(school);
                oneWayRoute.setLocalDate(routeDto.localDate());
                oneWayRoute.setPeriodEnum(routeDto.periodEnum());
                oneWayRoute.setStatusRouteEnum(StatusRouteEnum.WAITINGTOSTART);
                oneWayRoute.setConfimationStudentEnum(ConfimationStudentEnum.ABSENT);
                oneWayRoute.setStudentStatusEnum(StudentStatusEnum.ONEWAYONLY);
                studentRouteRepository.save(oneWayRoute);

                // Segunda instância para ONLYAROUND
                StudentRoute returnRoute = new StudentRoute();
                returnRoute.setUser(student);
                returnRoute.setSchool(school);
                returnRoute.setLocalDate(routeDto.localDate());
                returnRoute.setPeriodEnum(routeDto.periodEnum());
                returnRoute.setStatusRouteEnum(StatusRouteEnum.WAITINGTOSTART);
                returnRoute.setConfimationStudentEnum(ConfimationStudentEnum.ABSENT);
                returnRoute.setStudentStatusEnum(StudentStatusEnum.ONLYAROUND);
                studentRouteRepository.save(returnRoute);

                return ResponseEntity.ok().build();  // Retorna uma resposta de sucesso vazia
            } else {
                studentRoute.setStudentStatusEnum(routeDto.studentStatusEnum());
                studentRouteRepository.save(studentRoute);
                return ResponseEntity.ok(studentRoute);  // Retorna a rota salva
            }
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
    public ResponseEntity<?> updateStudentRouteImagem(byte[] imageBytes, Long routeId) {
        if (imageBytes == null || imageBytes.length == 0) {
            ErrorResponse errorResponse = new ErrorResponse("The image cannot be null or empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Verifica se a rota existe
        Optional<StudentRoute> optionalStudentRoute = studentRouteRepository.findById(routeId);
        if (optionalStudentRoute.isPresent()) {
            StudentRoute studentRoute = optionalStudentRoute.get();
            try {
                // Define a imagem e a data de upload na rota
                studentRoute.setImageData(imageBytes);
                studentRoute.setConfimationStudentEnum(ConfimationStudentEnum.CONFIRM);
                // Atualiza a rota no banco de dados
                studentRouteRepository.save(studentRoute);

                return ResponseEntity.ok("Student image updated successfully.");
            } catch (Exception e) {
                ErrorResponse errorResponse = new ErrorResponse("Error processing the image.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Student/Route not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @Override
    public List<StudentRouteWithAddresses> getStudentRoutesByPeriodAndDate(PeriodEnum periodEnum, LocalDate localDate, StudentStatusEnum studentStatusEnum) {
        List<Object[]> results = studentRouteRepository.findWithAddressesByPeriodEnumAndLocalDateAndConfimationStudentEnumAndStudentStatusEnumNot(
                periodEnum.name(), localDate, ConfimationStudentEnum.ABSENT.name(), studentStatusEnum.name());
        List<StudentRouteWithAddresses> studentRoutesWithAddresses = new ArrayList<>();

        for (Object[] result : results) {
            StudentRoute studentRoute = mapToStudentRoute(result);
            Address schoolAddress = mapToAddress(result, false);
            Address userAddress = mapToAddress(result, true);
            studentRoutesWithAddresses.add(new StudentRouteWithAddresses(studentRoute, schoolAddress, userAddress));
        }

        return studentRoutesWithAddresses;
    }

    private StudentRoute mapToStudentRoute(Object[] result) {
        StudentRoute studentRoute = new StudentRoute();
        studentRoute.setId((Long) result[0]);
        studentRoute.setConfimationStudentEnum(ConfimationStudentEnum.valueOf((String) result[1]));
        studentRoute.setImageData((byte[]) result[2]);
        studentRoute.setLocalDate(((java.sql.Date) result[3]).toLocalDate());
        studentRoute.setStatusRouteEnum(StatusRouteEnum.valueOf((String) result[4]));
        studentRoute.setStudentStatusEnum(StudentStatusEnum.valueOf((String) result[5]));
        studentRoute.setSchool(mapToSchool((Long) result[6]));
        System.out.println("Tipo de objeto no resultado: " + result[7]);
        byte[] byteArray = (byte[]) result[7];
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        long mostSigBits = byteBuffer.getLong();
        long leastSigBits = byteBuffer.getLong();
        UUID userId = new UUID(mostSigBits, leastSigBits);
        System.out.println(userId);
        studentRoute.setUser(mapToUser(userId));
        studentRoute.setPeriodEnum(PeriodEnum.valueOf((String) result[8]));
        return studentRoute;
    }

    private Address mapToAddress(Object[] result, boolean user) {
        Address address = new Address();
        if(!user){
            address.setStreet((String) result[9]);
            address.setCity((String) result[10]);
            address.setState((String) result[11]);
            address.setPostCode((String) result[12]);
            address.setLatitude((Double) result[13]);
            address.setLongitude((Double) result[14]);
            address.setId((Long) result[15]);
            address.setNeighborhood((String) result[16]);
            address.setCountry((String) result[17]);
            address.setComplement((String) result[18]);
            address.setNumber((Long) result[19]);
            address.setStatusEnum(StatusEnum.valueOf((String) result[20]));
        }else{
            address.setStreet((String) result[21]);
            address.setCity((String) result[22]);
            address.setState((String) result[23]);
            address.setPostCode((String) result[24]);
            address.setLatitude((Double) result[25]);
            address.setLongitude((Double) result[26]);
            address.setId((Long) result[27]);
            address.setNeighborhood((String) result[28]);
            address.setCountry((String) result[29]);
            address.setComplement((String) result[30]);
            address.setNumber((Long) result[31]);
            address.setStatusEnum(StatusEnum.valueOf((String) result[32]));
        }
        return address;
    }

    private User mapToUser(UUID userId) {
        System.out.println(userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.get();
    }

    private School mapToSchool(Long schoolId) {
        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        return optionalSchool.get();
    }
}
