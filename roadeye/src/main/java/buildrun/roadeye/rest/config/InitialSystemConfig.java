package buildrun.roadeye.rest.config;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.entity.Vehicle;
import buildrun.roadeye.domain.enums.ColorEnum;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.domain.enums.TypeVehicleEnum;
import buildrun.roadeye.domain.repository.UserRepository;
import buildrun.roadeye.domain.repository.VehicleRespository;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
        createUserIfNotExist("student", "Student 1", "123", RoleEnum.STUDENT, "LastNameStudent1", "student1@example.com", "999.999.999-95", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
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
            vehicle.setNumberRenavam(numberRenavam);
            vehicle.setYearManufacturing(yearManufacturing);
            vehicle.setTypeVehicleEnum(typeVehicleEnum);
            vehicle.setColorEnum(colorEnum);
            vehicle.setStatusEnum(statusEnum);
            vehicleRespository.save(vehicle);
        }
    }
}
