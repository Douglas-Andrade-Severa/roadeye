package buildrun.roadeye.rest.config;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.entity.Vehicle;
import buildrun.roadeye.domain.enums.ColorEnum;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.domain.enums.TypeVehicleEnum;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.domain.repository.VehicleRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class InitialSystemConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final VehicleRespository vehicleRespository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public InitialSystemConfig(UserRepository userRepository, VehicleRespository vehicleRespository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.vehicleRespository = vehicleRespository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //Create Users
        createUserIfNotExist("admin", "Administrator", "123", RoleEnum.ADMIN, "LastNameAdmin1", "admin1@example.com", "999.999.999-99", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("user", "User 1", "123", RoleEnum.USER, "LastName1", "user1@example.com", "999.999.999-98", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("driver", "Driver 1", "123", RoleEnum.DRIVER, "LastNameDriver1", "driver1@example.com", "999.999.999-97", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("responsible", "Responsible 1", "123", RoleEnum.RESPONSIBLE, "LastNameResponsible1", "responsible1@example.com", "999.999.999-96", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student1", "Student 1", "123", RoleEnum.STUDENT, "LastNameStudent1", "student1@example.com", "199.999.991-91", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student2", "Student 2", "123", RoleEnum.STUDENT, "LastNameStudent2", "student2@example.com", "289.998.992-92", "47 9 9999-9999", "foto2.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student3", "Student 3", "123", RoleEnum.STUDENT, "LastNameStudent3", "student3@example.com", "379.997.993-93", "47 9 9999-9999", "foto3.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student4", "Student 4", "123", RoleEnum.STUDENT, "LastNameStudent4", "student4@example.com", "469.996.994-94", "47 9 9999-9999", "foto4.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student5", "Student 5", "123", RoleEnum.STUDENT, "LastNameStudent5", "student5@example.com", "559.995.995-95", "47 9 9999-9999", "foto5.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student6", "Student 6", "123", RoleEnum.STUDENT, "LastNameStudent6", "student6@example.com", "699.994.996-96", "47 9 9999-9999", "foto6.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student7", "Student 7", "123", RoleEnum.STUDENT, "LastNameStudent7", "student7@example.com", "739.993.997-97", "47 9 9999-9999", "foto7.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student8", "Student 8", "123", RoleEnum.STUDENT, "LastNameStudent8", "student8@example.com", "829.992.998-98", "47 9 9999-9999", "foto8.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student9", "Student 9", "123", RoleEnum.STUDENT, "LastNameStudent9", "student9@example.com", "919.991.999-99", "47 9 9999-9999", "foto9.png", StatusEnum.ACTIVATE);
        //Create Vehicle
        createVehicleIfNotExist(1L,"Mercedes-Benz", "Sprinter","PQR1234", "246810135",2020,TypeVehicleEnum.VAN,ColorEnum.YELLOW,StatusEnum.ACTIVATE);
        createVehicleIfNotExist(2L,"Fiat", "Ducato","STU5678", "9876543210",2019,TypeVehicleEnum.VAN,ColorEnum.BLUE,StatusEnum.ACTIVATE);
        createVehicleIfNotExist(3L,"Volkswagen", "Volksbus","UVW9012", "111111111",2021,TypeVehicleEnum.MICRO_BUS,ColorEnum.GREEN,StatusEnum.ACTIVATE);
        createVehicleIfNotExist(4L,"Iveco", "Daily","XYZ3456", "222222222",2021,TypeVehicleEnum.MICRO_BUS,ColorEnum.WHITE,StatusEnum.ACTIVATE);
    }

    private User createUserIfNotExist(String login, String name, String password, RoleEnum role, String lastName, String email, String cpf, String phone, String photo, StatusEnum status) {
        Optional<User> existingUser = userRepository.findByCpf(cpf);
        if (existingUser.isPresent()) {
            return null;
        }
        User user = userRepository.findByLogin(login).orElse(null);
        if (user == null) {
            user = new User();
            user.setName(name);
            user.setLogin(login);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setCpf(cpf);
            user.setPhone(phone);
            user.setPhoto(photo);
            user.setStatusEnum(status);
            user = userRepository.save(user);
        }
        return user;
    }

    private void createVehicleIfNotExist(Long id, String brand, String model, String licensePlate, String numberRenavam, int yearManufacturing, TypeVehicleEnum typeVehicleEnum, ColorEnum colorEnum, StatusEnum statusEnum) {
        Vehicle vehicle = vehicleRespository.findById(id).orElse(null);
        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setBrand(brand);
            vehicle.setModel(model);
            vehicle.setLicensePlate(licensePlate);
            vehicle.setNumberRenavan(numberRenavam);
            vehicle.setYearManufacturing(yearManufacturing);
            vehicle.setTypeVehicleEnum(typeVehicleEnum);
            vehicle.setColorEnum(colorEnum);
            vehicle.setStatusEnum(statusEnum);
            vehicleRespository.save(vehicle);
        }
    }
}
