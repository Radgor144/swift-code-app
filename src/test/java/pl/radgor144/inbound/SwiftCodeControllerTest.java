package pl.radgor144.inbound;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.radgor144.swiftcode.SwiftCodeProcessingFacade;
import pl.radgor144.swiftcode.swiftcodebycountryiso2code.GetSwiftCodeByCountryISO2CodeResponse;
import pl.radgor144.swiftcode.swiftcodebycountryiso2code.SwiftCodeResponse;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static pl.radgor144.fixtures.GetSwiftCodeByCountryISO2CodeResponseFixture.*;

@ExtendWith(MockitoExtension.class)
public class SwiftCodeControllerTest {

    private static final String URL = "http://localhost:8080/v1/swift-codes/country/{countryISO2code}";

    @Mock
    private SwiftCodeProcessingFacade swiftCodeProcessingFacade;

    @ParameterizedTest
    @MethodSource("pl.radgor144.fixtures.GetSwiftCodeByCountryISO2CodeResponseFixture#provideSwiftCodeByCountryISO2TestData")
    void shouldSuccessfullyReturnCountryResponse(String countryISO2, String countryName, List<SwiftCodeResponse> swiftCodes) {
        //GIVEN
        GetSwiftCodeByCountryISO2CodeResponse expectedResult = createGetSwiftCodeByCountryISO2CodeResponse(countryISO2, countryName, swiftCodes);

        when(swiftCodeProcessingFacade.getSwiftCodesByCountryISO2code(countryISO2)).thenReturn(expectedResult);
        WebTestClient webTestClient = WebTestClient.bindToController(new SwiftCodeController(swiftCodeProcessingFacade)).build();

        // WHEN
        webTestClient.get()
                .uri(URL, countryISO2)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetSwiftCodeByCountryISO2CodeResponse.class)
                .value(actualResult -> {
                    // THEN
                    assertThat(actualResult).usingRecursiveComparison().isEqualTo(expectedResult);
                });
    }
}
