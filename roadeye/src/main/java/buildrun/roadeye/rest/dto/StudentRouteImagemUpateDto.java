package buildrun.roadeye.rest.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record StudentRouteImagemUpateDto(
        MultipartFile image) {
}
