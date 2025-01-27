package pl.radgor144.swiftcode.createswiftcode;

import jakarta.validation.constraints.NotNull;

public record CreateSwiftCodeRequest(@NotNull String address,
                                     @NotNull String bankName,
                                     @NotNull String countryISO2,
                                     @NotNull String countryName,
                                     @NotNull boolean isHeadquarter,
                                     @NotNull String swiftCode
) {
}
