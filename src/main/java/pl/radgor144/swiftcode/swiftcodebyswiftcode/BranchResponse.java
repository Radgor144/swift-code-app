package pl.radgor144.swiftcode.swiftcodebyswiftcode;

public record BranchResponse(String address,
                             String bankName,
                             String countryISO2,
                             String countryName,
                             boolean isHeadquarter,
                             String swiftCode) {
}
