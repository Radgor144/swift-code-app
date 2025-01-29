package pl.radgor144.fixtures;

import org.junit.jupiter.params.provider.Arguments;
import pl.radgor144.persistence.SwiftCodeEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class SwiftCodeEntityFixture {

    private static SwiftCodeEntity createSwiftCodeEntity(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
        return SwiftCodeEntity.builder()
                .id(UUID.randomUUID())
                .address(address)
                .bankName(bankName)
                .countryISO2(countryISO2)
                .countryName(countryName)
                .isHeadquarter(isHeadquarter)
                .swiftCode(swiftCode)
                .build();
    }

    public static Stream<Arguments> provideSwiftCodeBy() {
        return Stream.of(
                // POLAND
                Arguments.of(
                        "PL",
                        "POLAND",
                        List.of(
                        createSwiftCodeEntity("STRZEGOMSKA 42C WROCLAW, DOLNOSLASKIE, 53-611",
                                "SANTANDER CONSUMER BANK SPOLKA AKCYJNA", "PL", "POLAND", true, "AIPOPLP1XXX"),
                        createSwiftCodeEntity("WARSZAWA, MAZOWIECKIE",
                                "ALIOR BANK SPOLKA AKCYJNA", "PL", "POLAND", false, "ALBPPLP1BMW"),
                        createSwiftCodeEntity("LOPUSZANSKA BUSINESS PARK LOPUSZANSKA 38 D WARSZAWA, MAZOWIECKIE, 02-232",
                                "ALIOR BANK SPOLKA AKCYJNA", "PL", "POLAND", true, "ALBPPLPWXXX")
                )),

                // LATVIA
                Arguments.of("LV", "LATVIA", List.of(
                        createSwiftCodeEntity("MIHAILA TALA STREET 1  RIGA, RIGA, LV-1045",
                                "ABLV BANK, AS IN LIQUIDATION", "LV", "LATVIA", true, "AIZKLV22XXX"),
                        createSwiftCodeEntity("SIA MONO KATLAKALNA ST. 1 RIGA, RIGA, LV-1017",
                                "AS BALTIJAS PRIVATBANKA", "LV", "LATVIA", true, "BLPBLV21XXX"),
                        createSwiftCodeEntity("SMILSU IELA 6  RIGA, RIGA, LV-1050",
                                "BLUOR BANK AS", "LV", "LATVIA", true, "CBBRLV22XXX"),
                        createSwiftCodeEntity("BALASTA DAMBIS 1A  RIGA, RIGA, LV-1048",
                                "SWEDBANK AS", "LV", "LATVIA", true, "HABALV22XXX"),
                        createSwiftCodeEntity("LACPLESA 20A-1  RIGA, RIGA, LV-1011",
                                "DUKASCOPY EUROPE IBS AS", "LV", "LATVIA", true, "JSSILV21XXX")
                )),

                // ALBANIA
                Arguments.of("AL", "ALBANIA", List.of(
                        createSwiftCodeEntity("HYRJA 3 RR. DRITAN HOXHA ND. 11 TIRANA, TIRANA, 1023",
                                "UNITED BANK OF ALBANIA SH.A", "AL", "ALBANIA", true, "AAISALTRXXX"),
                        createSwiftCodeEntity("VASO PASHA 8  TIRANA, TIRANA, 1019",
                                "CREDINS BANK S.A.", "AL", "ALBANIA", true, "CDISALTRXXX"),
                        createSwiftCodeEntity("TIRANA, TIRANA", "BANKA OTP ALBANIA SH.A", "AL", "ALBANIA", true, "CRBAALTRXXX"),
                        createSwiftCodeEntity("HYRJA 1 RRUGA E KAVAJES NDERTESA 27 TIRANA, TIRANA, 1001",
                                "AMERICAN BANK OF INVESTMENTS S.A.", "AL", "ALBANIA", true, "EMPOALTRXXX"),
                        createSwiftCodeEntity("H. 15 RRUGA DRITAN HOXHA, NJESIA BASHKIAKE 11 TIRANA, TIRANA, 1023",
                                "PROCREDIT BANK SH. A. ALBANIA (FORMERLY FEFAD BANK)", "AL", "ALBANIA", true, "FEFAALTRXXX"),
                        createSwiftCodeEntity("TWIN TOWERS NO.2 BLVD.DESHMORET E KOMBIT TIRANA, TIRANA, 1010",
                                "FIRST INVESTMENT BANK-ALBANIA SH.A", "AL", "ALBANIA", true, "FINVALTRXXX"),
                        createSwiftCodeEntity("BLVD DESHMORET E KOMBIT 3  TIRANA, TIRANA, 1001",
                                "MINISTRY OF FINANCE OF ALBANIA", "AL", "ALBANIA", true, "MIFBALTRXXX")
                ))
        );
    }
}