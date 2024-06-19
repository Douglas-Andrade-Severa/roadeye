package buildrun.roadeye.rest.dto.service.implementation;

import buildrun.roadeye.rest.dto.AddressCoordinatesDto;
import buildrun.roadeye.rest.dto.service.CoordinatesService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CoordinatesServiceImplementation implements CoordinatesService {
    private final SimpMessagingTemplate messagingTemplate;

    public CoordinatesServiceImplementation(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void processCoordinates(AddressCoordinatesDto coordinates) {
        messagingTemplate.convertAndSend("/canal/coordinates", coordinates);
    }

    @Override
    public void subscribeCoordinatesUpdates(UUID studentId) {
        String destination = "/canal/coordinates/" + studentId;
        messagingTemplate.convertAndSend(destination, "Subscribed to coordinates updates");
    }

}
