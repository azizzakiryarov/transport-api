package se.knowit.iz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.knowit.iz.constant.Urls;
import se.knowit.iz.dto.PrivateVehicleDTO;
import se.knowit.iz.exception.NotFoundVehicleException;
import se.knowit.iz.utils.Crawler;

import java.io.IOException;

@Service
@Slf4j
public class TransportService {

    private Crawler crawler;
    private Urls urls;

    public TransportService(Crawler crawler, Urls urls) {
        this.crawler = crawler;
        this.urls = urls;
    }

    public ResponseEntity<PrivateVehicleDTO> getPrivateVehicleByRegistrationNumber(String registrationNumber) {
        try {
            return new ResponseEntity<>(crawler.findVehiclesDetails(urls.getBASE_URL() + registrationNumber), HttpStatus.OK);
        } catch (NotFoundVehicleException e) {
            log.error("Vehicle with registration-number: {} didn't found from service! Error: {}", registrationNumber , e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            log.error("Vehicle with registration-number: {} didn't get from service! Error: {}", registrationNumber , e);
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }
}
