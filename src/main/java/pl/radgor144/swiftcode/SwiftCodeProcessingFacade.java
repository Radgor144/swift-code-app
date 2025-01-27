package pl.radgor144.swiftcode;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.radgor144.swiftcode.createswiftcode.CreateSwiftCodeRequest;
import pl.radgor144.swiftcode.createswiftcode.CreateSwiftCodeResponse;
import pl.radgor144.swiftcode.createswiftcode.CreateSwiftCodeService;
import pl.radgor144.swiftcode.deleteswiftcode.DeleteBySwiftCodeResponse;
import pl.radgor144.swiftcode.deleteswiftcode.DeleteSwiftCodeService;
import pl.radgor144.swiftcode.swiftcodebycountryiso2code.GetSwiftCodeByCountryISO2CodeResponse;
import pl.radgor144.swiftcode.swiftcodebycountryiso2code.GetSwiftCodeByCountryISO2CodeService;
import pl.radgor144.swiftcode.swiftcodebyswiftcode.GetSwiftCodeBySwiftCodeResponse;
import pl.radgor144.swiftcode.swiftcodebyswiftcode.GetSwiftCodeBySwiftCodeService;

import static pl.radgor144.config.WeatherClientCacheConfig.CACHENAME;

@Service
@RequiredArgsConstructor
public class SwiftCodeProcessingFacade {

    private final GetSwiftCodeBySwiftCodeService getSwiftCodeBySwiftCodeService;
    private final GetSwiftCodeByCountryISO2CodeService getSwiftCodeByCountryISO2CodeService;

    private final CreateSwiftCodeService createSwiftCodeService;

    private final DeleteSwiftCodeService deleteSwiftCodeService;

    @Cacheable(cacheNames = CACHENAME)
    public GetSwiftCodeBySwiftCodeResponse getSwiftCodeBySwiftCodeResponse(String swiftCode) {
        return getSwiftCodeBySwiftCodeService.getGetSwiftCodeBySwiftCodeResponse(swiftCode);
    }

    @Cacheable(cacheNames = CACHENAME)
    public GetSwiftCodeByCountryISO2CodeResponse getSwiftCodesByCountryISO2code(String countryISO2code) {
        return getSwiftCodeByCountryISO2CodeService.getSwiftCodesByCountryISO2code(countryISO2code);
    }

    public DeleteBySwiftCodeResponse deleteBySwiftCode(String swiftCode) {
        return deleteSwiftCodeService.deleteBySwiftCode(swiftCode);
    }

    public CreateSwiftCodeResponse createSwiftCode(CreateSwiftCodeRequest createSwiftCodeRequest) {
        return createSwiftCodeService.createSwiftCode(createSwiftCodeRequest);
    }
}
