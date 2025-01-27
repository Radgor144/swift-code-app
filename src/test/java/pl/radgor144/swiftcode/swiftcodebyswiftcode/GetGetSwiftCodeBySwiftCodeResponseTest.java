package pl.radgor144.swiftcode.swiftcodebyswiftcode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.persistence.SwiftCodeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.radgor144.swiftcode.swiftcodebyswiftcode.GetSwiftCodeBySwiftCodeService.removeSuffixIfNecessary;

@SpringBootTest
public class GetGetSwiftCodeBySwiftCodeResponseTest {

    @MockBean
    private SwiftCodeRepository swiftCodeRepository;

    @Autowired
    private GetSwiftCodeBySwiftCodeService getSwiftCodeBySwiftCodeService;

    private static final SwiftCodeEntity branchWithOutHq = new SwiftCodeEntity(
            UUID.randomUUID(),
            "Branch Street 2",
            "Test Bank",
            "PL",
            "Poland",
            false,
            "KDPWPLPAASD"
    );

    private static final SwiftCodeEntity branchWithHq = new SwiftCodeEntity(
            UUID.randomUUID(),
            "Branch Street 2",
            "Test Bank",
            "PL",
            "Poland",
            true,
            "AIZKLV22XXX"
    );

    private static final SwiftCodeEntity branchFromHq = new SwiftCodeEntity(
            UUID.randomUUID(),
            "Branch Street 2",
            "Test Bank",
            "PL",
            "Poland",
            false,
            "AIZKLV22CLN"
    );

    private static final List<SwiftCodeEntity> branches = List.of(branchFromHq);

    private GetSwiftCodeBySwiftCodeResponse createExpectedResponse(SwiftCodeEntity branch, List<BranchResponse> branchResponses) {
        return new GetSwiftCodeBySwiftCodeResponse(
                branch.getAddress(),
                branch.getBankName(),
                branch.getCountryISO2(),
                branch.getCountryName(),
                branch.isHeadquarter(),
                branch.getSwiftCode(),
                branchResponses
        );
    }

    private BranchResponse mapToBranch(SwiftCodeEntity swiftCodeEntity) {
        return new BranchResponse(
                swiftCodeEntity.getAddress(),
                swiftCodeEntity.getBankName(),
                swiftCodeEntity.getCountryISO2(),
                swiftCodeEntity.getCountryName(),
                swiftCodeEntity.isHeadquarter(),
                swiftCodeEntity.getSwiftCode()
        );
    }

    @Test
    public void shouldReturnSwiftCodeWithOutHqResponse() {
        // GIVEN
        String swiftCode = "KDPWPLPAASD";
        GetSwiftCodeBySwiftCodeResponse expectedResponse = createExpectedResponse(branchWithOutHq, null);

        // WHEN
        when(swiftCodeRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.of(branchWithOutHq));

        // THEN
        var response = getSwiftCodeBySwiftCodeService.getGetSwiftCodeBySwiftCodeResponse(swiftCode);
        verify(swiftCodeRepository).findBySwiftCode(swiftCode);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void shouldReturnSwiftCodeWithHqResponse() {
        // GIVEN
        String swiftCode = "KDPWPLPAASD";
        List<BranchResponse> branchResponses = branches.stream().map(this::mapToBranch).toList();
        GetSwiftCodeBySwiftCodeResponse expectedResponse = createExpectedResponse(branchWithHq, branchResponses);

        // WHEN
        when(swiftCodeRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.of(branchWithHq));
        String swiftCodeWithoutSuffix = removeSuffixIfNecessary(swiftCode);
        when(swiftCodeRepository.findAllBranchesBySwiftCode(swiftCodeWithoutSuffix)).thenReturn(branches);

        // THEN
        var response = getSwiftCodeBySwiftCodeService.getGetSwiftCodeBySwiftCodeResponse(swiftCode);

        verify(swiftCodeRepository).findBySwiftCode(swiftCode);
        verify(swiftCodeRepository).findAllBranchesBySwiftCode(swiftCodeWithoutSuffix);
        assertEquals(expectedResponse, response);
    }
}
