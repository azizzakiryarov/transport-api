package se.knowit.iz.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class Urls {

    public String BASE_URL = "https://biluppgifter.se/fordon/";
    public Urls() {}
}