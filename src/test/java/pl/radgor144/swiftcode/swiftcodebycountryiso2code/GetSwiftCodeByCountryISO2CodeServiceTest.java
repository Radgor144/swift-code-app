package pl.radgor144.swiftcode.swiftcodebycountryiso2code;


import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.persistence.SwiftCodeRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetSwiftCodeByCountryISO2CodeServiceTest {

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @InjectMocks
    private GetSwiftCodeByCountryISO2CodeService getSwiftCodeByCountryISO2CodeService;

    @ParameterizedTest
    @MethodSource("pl.radgor144.fixtures.SwiftCodeEntityFixture#provideSwiftCodeBy")
    void shouldReturnGetSwiftCodeByCountryISO2CodeResponse(String countryISO2, String countryName, List<SwiftCodeEntity> swiftCodeEntities) {
        //GIVEN
        final List<SwiftCodeResponse> swiftCodeResponses = swiftCodeEntities.stream()
                .map(this::aSwiftCodeResponse)
                .toList();

        GetSwiftCodeByCountryISO2CodeResponse expectedResult = new GetSwiftCodeByCountryISO2CodeResponse(
                countryISO2,
                countryName,
                swiftCodeResponses
        );

        when(swiftCodeRepository.findByCountryISO2(countryISO2)).thenReturn(swiftCodeEntities);

        //WHEN
        GetSwiftCodeByCountryISO2CodeResponse result = getSwiftCodeByCountryISO2CodeService.getSwiftCodesByCountryISO2code(countryISO2);

        //THEN
        assertEquals(expectedResult, result);
    }

    private SwiftCodeResponse aSwiftCodeResponse(SwiftCodeEntity swiftCodeEntity) {
        return new SwiftCodeResponse(swiftCodeEntity.getAddress(), swiftCodeEntity.getBankName(), swiftCodeEntity.getCountryISO2(), swiftCodeEntity.isHeadquarter(), swiftCodeEntity.getSwiftCode());
    }
}
