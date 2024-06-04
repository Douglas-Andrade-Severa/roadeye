package buildrun.roadeye.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageWebSocketDto {
    private final double latitude;
    private final double longitude;
}
