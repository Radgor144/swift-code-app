package pl.radgor144.swiftcode.swiftcodebycountryiso2code;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.persistence.SwiftCodeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSwiftCodeByCountryISO2CodeService {

    private final SwiftCodeRepository swiftCodeRepository;

    public GetSwiftCodeByCountryISO2CodeResponse getSwiftCodesByCountryISO2code(String countryISO2code) {
        final List<SwiftCodeEntity> swiftCodeEntities = swiftCodeRepository.findByCountryISO2(countryISO2code);
        if (swiftCodeEntities.isEmpty()) {
            throw new CountryISO2CodeNotFoundException(countryISO2code);
        }
        final SwiftCodeEntity firstSwiftCodeEntity = swiftCodeEntities.getFirst();
        final List<SwiftCodeResponse> swiftCodeResponses = swiftCodeEntities.stream()
                .map(this::aSwiftCodeResponse)
                .toList();
        return new GetSwiftCodeByCountryISO2CodeResponse(firstSwiftCodeEntity.getCountryISO2(), firstSwiftCodeEntity.getCountryName(), swiftCodeResponses);
    }

    private SwiftCodeResponse aSwiftCodeResponse(SwiftCodeEntity swiftCodeEntity) {
        return new SwiftCodeResponse(swiftCodeEntity.getAddress(), swiftCodeEntity.getBankName(), swiftCodeEntity.getCountryISO2(), swiftCodeEntity.isHeadquarter(), swiftCodeEntity.getSwiftCode());
    }
}
