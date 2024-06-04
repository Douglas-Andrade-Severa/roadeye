package buildrun.roadeye.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class OutputMessageWebSocket {
    private final String latitude;
    private final String longitude;
    private final LocalDateTime date;
}
