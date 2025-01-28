package pl.radgor144.inbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.swiftcode.SwiftCodeProcessingFacade;
import pl.radgor144.swiftcode.swiftcodebycountryiso2code.GetSwiftCodeByCountryISO2CodeResponse;
import pl.radgor144.utils.JsonFileReader;
import pl.radgor144.utils.RequestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SwiftCodeControllerTest {

    private static final String COUNTRY_RESOURCE_PATH = "src/test/java/pl/radgor144/Responses/CountryResponse.json";
    private static final String URL = "http://localhost:8080/v1/swift-codes/country/{countryISO2code}";

    List<SwiftCodeEntity> swiftCodeEntities = new ArrayList<>();

    @Mock
    private SwiftCodeProcessingFacade swiftCodeProcessingFacade;

    @Test
    void shouldSuccessfullyReturnCountryResponse() throws IOException {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String countryISO2code = "PL";
        GetSwiftCodeByCountryISO2CodeResponse expectedResult = JsonFileReader.readJson(objectMapper, COUNTRY_RESOURCE_PATH, GetSwiftCodeByCountryISO2CodeResponse.class);

        swiftCodeEntities.add(swiftCodeEntity1);
        swiftCodeEntities.add(swiftCodeEntity2);
        swiftCodeEntities.add(swiftCodeEntity3);

        when(swiftCodeProcessingFacade.getSwiftCodesByCountryISO2code(countryISO2code)).thenReturn(expectedResult);
        WebTestClient webTestClient = WebTestClient.bindToController(new SwiftCodeController(swiftCodeProcessingFacade)).build();

        // WHEN
        webTestClient.get()
                .uri(URL, countryISO2code)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetSwiftCodeByCountryISO2CodeResponse.class)
                .value(actualResult -> {
                    // THEN
                    assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
                });
    }

    SwiftCodeEntity swiftCodeEntity1 = SwiftCodeEntity.builder()
            .id(UUID.randomUUID())
            .address("STRZEGOMSKA 42C WROCLAW, DOLNOSLASKIE, 53-611")
            .bankName("SANTANDER CONSUMER BANK SPOLKA AKCYJNA")
            .countryISO2("PL")
            .countryName("POLAND")
            .isHeadquarter(true)
            .swiftCode("AIPOPLP1XXX")
            .build();

    SwiftCodeEntity swiftCodeEntity2 = SwiftCodeEntity.builder()
            .id(UUID.randomUUID())
            .address("WARSZAWA, MAZOWIECKIE")
            .bankName("ALIOR BANK SPOLKA AKCYJNA")
            .countryISO2("PL")
            .countryName("POLAND")
            .isHeadquarter(false)
            .swiftCode("ALBPPLP1BMW")
            .build();

    SwiftCodeEntity swiftCodeEntity3 = SwiftCodeEntity.builder()
            .id(UUID.randomUUID())
            .address("LOPUSZANSKA BUSINESS PARK LOPUSZANSKA 38 D WARSZAWA, MAZOWIECKIE, 02-232")
            .bankName("ALIOR BANK SPOLKA AKCYJNA")
            .countryISO2("PL")
            .countryName("POLAND")
            .isHeadquarter(true)
            .swiftCode("ALBPPLPWXXX")
            .build();
}
