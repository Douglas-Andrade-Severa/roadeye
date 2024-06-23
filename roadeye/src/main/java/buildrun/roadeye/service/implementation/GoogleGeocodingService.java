package buildrun.roadeye.service.implementation;

import buildrun.roadeye.domain.entity.Address;
import buildrun.roadeye.domain.entity.GeolocationResponse;
import buildrun.roadeye.domain.enums.StatusEnum;
import buildrun.roadeye.rest.dto.GeolocationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class GoogleGeocodingService {
    @Value("${google.geocoding.api.key}")
    private String apiKey;
    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private final RestTemplate restTemplate;

    public GoogleGeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeolocationDto getGeolocation(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=" + apiKey;
        GeolocationResponse response = restTemplate.getForObject(url, GeolocationResponse.class);
        if (response != null && response.getStatus().equals("OK") && response.getResults().size() > 0) {
            GeolocationResponse.Result result = response.getResults().get(0);
            GeolocationResponse.Geometry geometry = result.getGeometry();
            GeolocationResponse.Location location = geometry.getLocation();
            return new GeolocationDto(location.getLat(), location.getLng());
        } else {
            return null;
        }
    }

    public Address getAddressFromCoordinates(double latitude, double longitude) {
        String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_API_URL)
                .queryParam("latlng", latitude + "," + longitude)
                .queryParam("key", apiKey)
                .toUriString();
        GeolocationResponse response = restTemplate.getForObject(url, GeolocationResponse.class);
        if (response != null && "OK".equals(response.getStatus())) {
            System.out.println("Response: " + response);
            GeolocationResponse.Result result = response.getResults().get(0);
            return mapToAddress(result.getAddressComponents(), latitude, longitude);
        } else {
            throw new RuntimeException("Não foi possível obter o endereço.");
        }
    }

    private Address mapToAddress(List<GeolocationResponse.AddressComponent> addressComponents, double latitude, double longitude) {
        Address address = new Address();
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        address.setStatusEnum(StatusEnum.ACTIVATE);
        address.setComplement("");
        for (GeolocationResponse.AddressComponent component : addressComponents) {
            for (String type : component.getTypes()) {
                switch (type) {
                    case "street_number":
                        address.setNumber(Long.parseLong(component.getLongName()));
                        System.out.println("Street Number: " + Long.parseLong(component.getLongName()));
                        break;
                    case "route":
                        address.setStreet(component.getLongName());
                        System.out.println("Street: " + component.getLongName());
                        break;
                    case "sublocality_level_1":
                        address.setNeighborhood(component.getLongName());
                        System.out.println("City: " + component.getLongName());
                        break;
                    case "administrative_area_level_2":
                        address.setCity(component.getLongName());
                        System.out.println("City: " + component.getLongName());
                        break;
                    case "administrative_area_level_1":
                        address.setState(component.getLongName());
                        System.out.println("State: " + component.getLongName());
                        break;
                    case "country":
                        address.setCountry(component.getLongName());
                        System.out.println("Country: " + component.getLongName());
                        break;
                    case "postal_code":
                        address.setPostCode(component.getLongName());
                        System.out.println("Postal Code: " + component.getLongName());
                        break;
                }
            }
        }
        return address;
    }
}
