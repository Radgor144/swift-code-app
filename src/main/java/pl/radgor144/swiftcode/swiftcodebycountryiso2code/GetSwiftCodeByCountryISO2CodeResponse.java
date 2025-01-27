package pl.radgor144.swiftcode.swiftcodebycountryiso2code;

import java.util.List;

public record GetSwiftCodeByCountryISO2CodeResponse(String countryISO2, String countryName, List<SwiftCodeResponse> swiftCodes) {
}
