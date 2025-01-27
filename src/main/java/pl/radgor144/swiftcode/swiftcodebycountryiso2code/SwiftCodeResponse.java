package pl.radgor144.swiftcode.swiftcodebycountryiso2code;

public record SwiftCodeResponse(String address,
                                String bankName,
                                String countryISO2,
                                boolean isHeadquarter,
                                String swiftCode) {

}
