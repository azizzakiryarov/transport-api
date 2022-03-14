package se.knowit.iz.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import se.knowit.iz.dto.PrivateVehicleDTO;
import se.knowit.iz.utils.Crawler;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class TransportServiceTest {

    public TransportServiceTest() {
    }

    private static final String VALID_URL = "https://biluppgifter.se/fordon/";


    private static final String VALID_REGISTRATION_NUMBER = "GRR824";

    TransportService transportService;

    @Mock
    Crawler crawler;

    @Mock
    PrivateVehicleDTO mockPrivateVehicleDTO;

    @Before
    public void init(){

        mockPrivateVehicleDTO = new PrivateVehicleDTO(
                "Volkswagen",
                "Touareg 3.0 V6 TDi",
                "2015 / 2016",
                "GRR824",
                174);

        transportService = new TransportService(crawler);
    }

    @Test
    public void getPrivateVehicleByRegistrationNumberSuccessfully() throws IOException {

        ResponseEntity<PrivateVehicleDTO> expectedPrivateVehicleDTOResponseEntity = new ResponseEntity<>(mockPrivateVehicleDTO, HttpStatus.OK);

        Mockito.when(crawler.findVehiclesDetails(VALID_URL + mockPrivateVehicleDTO.getRegistrationNumber())).thenReturn(mockPrivateVehicleDTO);

        ResponseEntity<PrivateVehicleDTO> actualPrivateVehicleDTOResponseEntity = transportService.getPrivateVehicleByRegistrationNumber(VALID_REGISTRATION_NUMBER);

        Assert.assertEquals(expectedPrivateVehicleDTOResponseEntity, actualPrivateVehicleDTOResponseEntity);

    }
}