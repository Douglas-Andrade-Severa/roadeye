package buildrun.roadeye.rest.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageWebSocketDto {
    private final String latitude;
    private final String longitude;
}
