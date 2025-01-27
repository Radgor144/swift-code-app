package pl.radgor144.swiftcode.swiftcodebycountryiso2code;

public class CountryISO2CodeNotFoundException extends RuntimeException {
    public CountryISO2CodeNotFoundException(String countryISO2Code) {
        super("Not found swiftCodes for countryISO2code: %s".formatted(countryISO2Code));
    }
}