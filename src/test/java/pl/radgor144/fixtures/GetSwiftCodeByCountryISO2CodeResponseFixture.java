package pl.radgor144.fixtures;

import org.junit.jupiter.params.provider.Arguments;
import pl.radgor144.swiftcode.swiftcodebycountryiso2code.GetSwiftCodeByCountryISO2CodeResponse;
import pl.radgor144.swiftcode.swiftcodebycountryiso2code.SwiftCodeResponse;

import java.util.List;
import java.util.stream.Stream;

public class GetSwiftCodeByCountryISO2CodeResponseFixture {

    public static GetSwiftCodeByCountryISO2CodeResponse createGetSwiftCodeByCountryISO2CodeResponse(
            String countryISO2, String countryName, List<SwiftCodeResponse> swiftCodes) {
        return new GetSwiftCodeByCountryISO2CodeResponse(countryISO2, countryName, swiftCodes);
    }

    private static SwiftCodeResponse createSwiftCodeResponse(String address, String bankName, String countryISO2, String swiftCode, boolean isHeadquarter) {
        return new SwiftCodeResponse(address, bankName, countryISO2, isHeadquarter, swiftCode);
    }

    public static Stream<Arguments> provideSwiftCodeByCountryISO2TestData() {
        return Stream.of(
                // POLAND
                Arguments.of("PL", "POLAND", List.of(
                        createSwiftCodeResponse("STRZEGOMSKA 42C WROCLAW, DOLNOSLASKIE, 53-611",
                                "SANTANDER CONSUMER BANK SPOLKA AKCYJNA", "PL", "AIPOPLP1XXX", true),
                        createSwiftCodeResponse("WARSZAWA, MAZOWIECKIE",
                                "ALIOR BANK SPOLKA AKCYJNA", "PL", "ALBPPLP1BMW", false),
                        createSwiftCodeResponse("LOPUSZANSKA BUSINESS PARK LOPUSZANSKA 38 D WARSZAWA, MAZOWIECKIE, 02-232",
                                "ALIOR BANK SPOLKA AKCYJNA", "PL", "ALBPPLPWXXX", true)
                )),

                // LATVIA
                Arguments.of("LV", "LATVIA", List.of(
                        createSwiftCodeResponse("MIHAILA TALA STREET 1  RIGA, RIGA, LV-1045",
                                "ABLV BANK, AS IN LIQUIDATION", "LV", "AIZKLV22XXX", true),
                        createSwiftCodeResponse("SIA MONO KATLAKALNA ST. 1 RIGA, RIGA, LV-1017",
                                "AS BALTIJAS PRIVATBANKA", "LV", "BLPBLV21XXX", true),
                        createSwiftCodeResponse("SMILSU IELA 6  RIGA, RIGA, LV-1050",
                                "BLUOR BANK AS", "LV", "CBBRLV22XXX", true),
                        createSwiftCodeResponse("BALASTA DAMBIS 1A  RIGA, RIGA, LV-1048",
                                "SWEDBANK AS", "LV", "HABALV22XXX", true),
                        createSwiftCodeResponse("LACPLESA 20A-1  RIGA, RIGA, LV-1011",
                                "DUKASCOPY EUROPE IBS AS", "LV", "JSSILV21XXX", true)
                )),

                // ALBANIA
                Arguments.of("AL", "ALBANIA", List.of(
                        createSwiftCodeResponse("HYRJA 3 RR. DRITAN HOXHA ND. 11 TIRANA, TIRANA, 1023",
                                "UNITED BANK OF ALBANIA SH.A", "AL", "AAISALTRXXX", true),
                        createSwiftCodeResponse("VASO PASHA 8  TIRANA, TIRANA, 1019",
                                "CREDINS BANK S.A.", "AL", "CDISALTRXXX", true),
                        createSwiftCodeResponse("TIRANA, TIRANA", "BANKA OTP ALBANIA SH.A", "AL", "CRBAALTRXXX", true),
                        createSwiftCodeResponse("HYRJA 1 RRUGA E KAVAJES NDERTESA 27 TIRANA, TIRANA, 1001",
                                "AMERICAN BANK OF INVESTMENTS S.A.", "AL", "EMPOALTRXXX", true),
                        createSwiftCodeResponse("H. 15 RRUGA DRITAN HOXHA, NJESIA BASHKIAKE 11 TIRANA, TIRANA, 1023",
                                "PROCREDIT BANK SH. A. ALBANIA (FORMERLY FEFAD BANK)", "AL", "FEFAALTRXXX", true),
                        createSwiftCodeResponse("TWIN TOWERS NO.2 BLVD.DESHMORET E KOMBIT TIRANA, TIRANA, 1010",
                                "FIRST INVESTMENT BANK-ALBANIA SH.A", "AL", "FINVALTRXXX", true),
                        createSwiftCodeResponse("BLVD DESHMORET E KOMBIT 3  TIRANA, TIRANA, 1001",
                                "MINISTRY OF FINANCE OF ALBANIA", "AL", "MIFBALTRXXX", true)
                ))
        );
    }
}
