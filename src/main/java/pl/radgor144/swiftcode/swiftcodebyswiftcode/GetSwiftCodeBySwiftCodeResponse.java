package pl.radgor144.swiftcode.swiftcodebyswiftcode;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetSwiftCodeBySwiftCodeResponse(String address,
                                              String bankName,
                                              String countryISO2,
                                              String countryName,
                                              boolean isHeadquarter,
                                              String swiftCode,
                                              List<BranchResponse> branchResponses) {
}
