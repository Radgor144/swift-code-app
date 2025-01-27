package pl.radgor144.inbound;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.radgor144.swiftcode.SwiftCodeProcessingFacade;
import pl.radgor144.swiftcode.createswiftcode.CreateSwiftCodeRequest;
import pl.radgor144.swiftcode.createswiftcode.CreateSwiftCodeResponse;
import pl.radgor144.swiftcode.deleteswiftcode.DeleteBySwiftCodeResponse;
import pl.radgor144.swiftcode.swiftcodebycountryiso2code.GetSwiftCodeByCountryISO2CodeResponse;
import pl.radgor144.swiftcode.swiftcodebyswiftcode.GetSwiftCodeBySwiftCodeResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/v1/swift-codes")
class SwiftCodeController {

    private final SwiftCodeProcessingFacade swiftCodeProcessingFacade;

    @GetMapping("/{swift-code}")
    GetSwiftCodeBySwiftCodeResponse getSwiftCode(@NonNull @PathVariable("swift-code") String swiftCode) {
        return swiftCodeProcessingFacade.getSwiftCodeBySwiftCodeResponse(swiftCode);
    }

    @GetMapping("/country/{countryISO2code}")
    GetSwiftCodeByCountryISO2CodeResponse getSwiftCodesByCountry(@PathVariable String countryISO2code) {
        return swiftCodeProcessingFacade.getSwiftCodesByCountryISO2code(countryISO2code);
    }

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    CreateSwiftCodeResponse createSwiftCode(@Valid @RequestBody CreateSwiftCodeRequest createSwiftCodeRequest) {
        return swiftCodeProcessingFacade.createSwiftCode(createSwiftCodeRequest);
    }

    @DeleteMapping("/{swift-code}")
    DeleteBySwiftCodeResponse deleteSwiftCode(@Valid @NonNull @PathVariable("swift-code")  String swiftCode) {
        return swiftCodeProcessingFacade.deleteBySwiftCode(swiftCode);
    }
}
