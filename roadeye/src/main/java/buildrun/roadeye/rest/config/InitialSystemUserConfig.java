package buildrun.roadeye.rest.config;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class InitialSystemUserConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public InitialSystemUserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createUserIfNotExist("admin", "Administrator", "123", RoleEnum.ADMIN, "LastNameAdmin1", "admin1@example.com", "999.999.999-99", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("user", "User 1", "123", RoleEnum.USER, "LastName1", "user1@example.com", "999.999.999-98", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("driver", "Driver 1", "123", RoleEnum.DRIVER, "LastNameDriver1", "driver1@example.com", "999.999.999-97", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("responsible", "Responsible 1", "123", RoleEnum.RESPONSIBLE, "LastNameResponsible1", "responsible1@example.com", "999.999.999-96", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
        createUserIfNotExist("student", "Student 1", "123", RoleEnum.STUDENT, "LastNameStudent1", "student1@example.com", "999.999.999-95", "47 9 9999-9999", "foto1.png", StatusEnum.ACTIVATE);
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
}
