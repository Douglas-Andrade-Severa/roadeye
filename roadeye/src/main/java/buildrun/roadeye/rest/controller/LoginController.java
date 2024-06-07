package buildrun.roadeye.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Isso retornará o nome do arquivo HTML sem a extensão, supondo que o arquivo está localizado em src/main/resources/templates/
    }
}
