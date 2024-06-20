package buildrun.roadeye.service;

import buildrun.roadeye.rest.dto.AddressCoordinatesDto;

import java.util.UUID;

public interface CoordinatesService {
    void processCoordinates(AddressCoordinatesDto coordinates);
    void subscribeCoordinatesUpdates(UUID studentId);
}
