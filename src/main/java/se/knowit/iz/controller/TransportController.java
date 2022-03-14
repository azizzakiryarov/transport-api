package se.knowit.iz.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.knowit.iz.dto.PrivateVehicleDTO;
import se.knowit.iz.service.TransportService;

@RestController
@RequestMapping("api/v1/transports")
public class TransportController {

    TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/get/{registration-number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PrivateVehicleDTO> getPrivateVehicleByRegistrationNumber(
            @PathVariable(value = "registration-number") String registrationNumber) {
        return transportService.getPrivateVehicleByRegistrationNumber(registrationNumber);
    }
}