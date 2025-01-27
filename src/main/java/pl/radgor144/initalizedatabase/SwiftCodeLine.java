package pl.radgor144.initalizedatabase;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
@Data
public class SwiftCodeLine {
    @CsvBindByName(column = "COUNTRY ISO2 CODE")
    private String countryISO2;
    @CsvBindByName(column = "SWIFT CODE")
    private String swiftCode;
    @CsvBindByName(column = "NAME")
    private String name;
    @CsvBindByName(column = "ADDRESS")
    private String address;
    @CsvBindByName(column = "COUNTRY NAME")
    private String countryName;
}

