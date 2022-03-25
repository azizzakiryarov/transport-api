package se.knowit.iz.service;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import se.knowit.iz.constant.Urls;
import se.knowit.iz.dto.PrivateVehicleDTO;
import se.knowit.iz.exception.NotFoundVehicleException;
import se.knowit.iz.utils.Crawler;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TransportServiceTest {

    public TransportServiceTest() {
    }

    private static final String VALID_URL = "https://biluppgifter.se/fordon/";
    private static final String INVALID_URL = "https://biluppgifter.s/fordo/";
    private static final String VALID_REGISTRATION_NUMBER = "GRR824";
    private static final String INVALID_REGISTRATION_NUMBER = "GRR82";

    TransportService transportService;
    Crawler crawler;

    @Mock
    Urls mockUrl;

    @Mock
    Crawler mockCrawler;

    @Mock
    PrivateVehicleDTO mockPrivateVehicleDTO;

    @Before
    public void init() {

        crawler = new Crawler();

        mockPrivateVehicleDTO = new PrivateVehicleDTO(
                "Volkswagen",
                "Touareg 3.0 V6 TDi",
                "2015 / 2016",
                "GRR824",
                174);

        mockUrl = new Urls();

        transportService = new TransportService(mockCrawler, mockUrl);
    }

    @Test
    public void getPrivateVehicleByRegistrationNumberSuccessfully() throws IOException, NotFoundVehicleException {
        ResponseEntity<PrivateVehicleDTO> expectedPrivateVehicleDTOResponseEntity = new ResponseEntity<>(mockPrivateVehicleDTO, HttpStatus.OK);
        Mockito.when(mockCrawler.findVehiclesDetails(VALID_URL + mockPrivateVehicleDTO.getRegistrationNumber())).thenReturn(mockPrivateVehicleDTO);
        ResponseEntity<PrivateVehicleDTO> actualPrivateVehicleDTOResponseEntity = transportService.getPrivateVehicleByRegistrationNumber(VALID_REGISTRATION_NUMBER);
        Assert.assertEquals(expectedPrivateVehicleDTOResponseEntity, actualPrivateVehicleDTOResponseEntity);
    }

    @Test
    public void getPrivateVehicleByRegistrationNumberThrowIOException() throws IOException, NotFoundVehicleException {
        ResponseEntity<PrivateVehicleDTO> expectedPrivateVehicleDTOResponseEntity = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        Mockito.when(mockCrawler.findVehiclesDetails(any())).thenThrow(new IOException());
        ResponseEntity<PrivateVehicleDTO> actualPrivateVehicleDTOResponseEntity = transportService.getPrivateVehicleByRegistrationNumber(VALID_REGISTRATION_NUMBER);
        Assert.assertEquals(expectedPrivateVehicleDTOResponseEntity, actualPrivateVehicleDTOResponseEntity);
    }

    @Test
    public void getPrivateVehicleByRegistrationNumberThrowNotFoundVehicleException() throws IOException, NotFoundVehicleException {
        ResponseEntity<PrivateVehicleDTO> expectedPrivateVehicleDTOResponseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Mockito.when(mockCrawler.findVehiclesDetails(any())).thenThrow(new NotFoundVehicleException());
        ResponseEntity<PrivateVehicleDTO> actualPrivateVehicleDTOResponseEntity = transportService.getPrivateVehicleByRegistrationNumber(INVALID_REGISTRATION_NUMBER);
        Assert.assertEquals(expectedPrivateVehicleDTOResponseEntity, actualPrivateVehicleDTOResponseEntity);
    }

    @Test
    public void getPrivateVehicleByRegistrationNumberWrongURL() {
        Assertions.assertThatThrownBy(() -> crawler.findVehiclesDetails(INVALID_URL + VALID_REGISTRATION_NUMBER))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Something is wrong: " + INVALID_URL + VALID_REGISTRATION_NUMBER);
    }

    @Test
    public void getPrivateVehicleByRegistrationNumberWrongRegistrationNumberThrowNotFoundVehicleException() {
        Assertions.assertThatThrownBy(() -> crawler.findVehiclesDetails(VALID_URL + INVALID_REGISTRATION_NUMBER))
                .isInstanceOf(NotFoundVehicleException.class)
                .hasMessageContaining("You are typing wrong registration number!");
    }
}