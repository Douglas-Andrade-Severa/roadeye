package buildrun.roadeye.config;

import buildrun.roadeye.domain.entity.User;
import buildrun.roadeye.domain.enums.RoleEnum;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.domain.repository.UserRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AdminUserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        User userAdmin = userRepository.findByLogin("admin").orElse(null);
        if (userAdmin == null) {
            userAdmin = new User();
            userAdmin.setName("Administrator");
            userAdmin.setLogin("admin");
            userAdmin.setPassword(passwordEncoder.encode("123"));
            userAdmin.setRole(RoleEnum.ADMIN);
            userAdmin.setLastName("CEO");
            userAdmin.setEmail("adm@adm.com.br");
            userAdmin.setCpf("999.999.999-99");
            userAdmin.setPhone("47 9 9999-9999");
            userAdmin.setPhoto("semFoto");
            userAdmin.setStatusEnum(StatusEnum.ACTIVATE);
            userRepository.save(userAdmin);
        }
    }
}
