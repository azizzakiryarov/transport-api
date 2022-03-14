package se.knowit.iz.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import se.knowit.iz.dto.PrivateVehicleDTO;
import java.io.IOException;

@Slf4j
@Component
public class Crawler {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    PrivateVehicleDTO privateVehicleDTO;

    public PrivateVehicleDTO findVehiclesDetails(String url) throws IOException {
        if (Jsoup.isValid(url, Safelist.basic())) {
            Document doc;
            privateVehicleDTO = new PrivateVehicleDTO();
            try {
                doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
            } catch (IOException e) {
                throw new IOException("Something is wrong: " + url);
            }

            Elements labels = doc.select("ul.list-data > li > span.label");
            Elements values = doc.select("ul.list-data > li > span.value");

            if (!labels.isEmpty() && !values.isEmpty()) {
                for (int i = 0; i < labels.size(); i++) {
                    String label = labels.get(i).text();
                    String value = values.get(i).text();

                    switch (label) {
                        case "Fabrikat":
                            privateVehicleDTO.setBrand(value);
                            break;
                        case "Modell":
                            privateVehicleDTO.setModel(value);
                            break;
                        case "Fordonsår / Modellår":
                            privateVehicleDTO.setYear(value);
                            break;
                        case "Registreringsnummer":
                            privateVehicleDTO.setRegistrationNumber(value);
                            break;
                        case "CO2-utsläpp (NEDC)":
                            try {
                                privateVehicleDTO.setCO2Emissions(Integer.parseInt(value.split(" ")[0]));
                            } catch (NumberFormatException e) {
                                log.debug(e.getMessage());
                                throw new NumberFormatException(e.getMessage());
                            }
                            break;
                        default:
                            break;
                    }
                }
            } else {
                log.info("There is no elements at all!");
            }
        }
        return privateVehicleDTO;
    }
}