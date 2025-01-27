package pl.radgor144.swiftcode.swiftcodebyswiftcode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.persistence.SwiftCodeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetSwiftCodeBySwiftCodeService {
    private final SwiftCodeRepository swiftCodeRepository;

    public GetSwiftCodeBySwiftCodeResponse getGetSwiftCodeBySwiftCodeResponse(String swiftCode) {
        Optional<SwiftCodeEntity> swiftCodeEntityOptional = swiftCodeRepository.findBySwiftCode(swiftCode);
        if (swiftCodeEntityOptional.isEmpty()) {
            throw new SwiftCodeNotFoundException(swiftCode);
        } else {
            return calculateResponse(swiftCode, swiftCodeEntityOptional.get());
        }
    }

    private GetSwiftCodeBySwiftCodeResponse calculateResponse(String swiftCode, SwiftCodeEntity swiftCodeEntity) {
        if (swiftCodeEntity.isHeadquarter()) {
            swiftCode = removeSuffixIfNecessary(swiftCode);
            List<SwiftCodeEntity> swiftCodeEntities = swiftCodeRepository.findAllBranchesBySwiftCode(swiftCode);
            List<BranchResponse> branchResponses = swiftCodeEntities.stream()
                    .filter(branch -> !branch.isHeadquarter())
                    .map(this::mapToBranch)
                    .toList();
            return mapToHeadquarter(swiftCodeEntity, branchResponses);
        } else {
            return mapToHeadquarter(swiftCodeEntity, null);
        }
    }

    static String removeSuffixIfNecessary(String swiftCode) {
        if (swiftCode.endsWith("XXX")) {
            return swiftCode.substring(0, swiftCode.length() - 3).trim();
        }
        return swiftCode;
    }

    private static GetSwiftCodeBySwiftCodeResponse mapToHeadquarter(SwiftCodeEntity swiftCodeEntity, List<BranchResponse> branchResponses) {
        return new GetSwiftCodeBySwiftCodeResponse(swiftCodeEntity.getAddress(), swiftCodeEntity.getBankName(), swiftCodeEntity.getCountryISO2(), swiftCodeEntity.getCountryName(), swiftCodeEntity.isHeadquarter(), swiftCodeEntity.getSwiftCode(), branchResponses);
    }

    private BranchResponse mapToBranch(SwiftCodeEntity swiftCodeEntity) {
        return new BranchResponse(swiftCodeEntity.getAddress(), swiftCodeEntity.getBankName(), swiftCodeEntity.getCountryISO2(), swiftCodeEntity.getCountryName(), swiftCodeEntity.isHeadquarter(), swiftCodeEntity.getSwiftCode());
    }
}
