package buildrun.roadeye.rest.config;

import buildrun.roadeye.domain.repository.*;
import buildrun.roadeye.service.AddressService;
import buildrun.roadeye.service.implementation.AddressServiceImplementation;
import buildrun.roadeye.service.implementation.GoogleGeocodingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public AddressService addressService(AddressRepository addressRepository, UserRepository userRepository, GoogleGeocodingService geocodingService, UserAddressRepository userAddressRepository, SchoolAddressRepository schoolAddressRepository, SchoolRepository schoolRepository) {
        return new AddressServiceImplementation(addressRepository, userRepository, geocodingService, userAddressRepository, schoolAddressRepository, schoolRepository);
    }
}
