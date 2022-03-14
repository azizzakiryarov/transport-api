package se.knowit.iz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.knowit.iz.dto.PrivateVehicleDTO;
import se.knowit.iz.utils.Crawler;

import java.io.IOException;

@Service
@Slf4j
public class TransportService {

    Crawler crawler;

    public TransportService(Crawler crawler) {
        this.crawler = crawler;
    }

    public ResponseEntity<PrivateVehicleDTO> getPrivateVehicleByRegistrationNumber(String registrationNumber) {
        try {
            return new ResponseEntity<>(crawler.findVehiclesDetails("https://biluppgifter.se/fordon/" + registrationNumber), HttpStatus.OK);
        } catch (IOException e) {
            log.error("Vehicle with registration-number: {} didn't get from service! Error: {}", registrationNumber , e);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
}
