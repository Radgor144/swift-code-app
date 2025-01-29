package pl.radgor144.swiftcode.swiftcodebyswiftcode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.persistence.SwiftCodeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetGetSwiftCodeBySwiftCodeResponseTest {

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @InjectMocks
    private GetSwiftCodeBySwiftCodeService getSwiftCodeBySwiftCodeService;

    @Test
    public void shouldReturnSwiftCodeWithoutHqResponse() {
        // GIVEN
        String swiftCode = "KDPWPLPAASD";
        GetSwiftCodeBySwiftCodeResponse expectedResponse = createExpectedResponse(branchWithoutHq, null);

        when(swiftCodeRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.of(branchWithoutHq));

        // WHEN
        var response = getSwiftCodeBySwiftCodeService.getGetSwiftCodeBySwiftCodeResponse(swiftCode);

        // THEN
        verify(swiftCodeRepository).findBySwiftCode(swiftCode);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void shouldReturnSwiftCodeWithHqResponse() {
        // GIVEN
        String swiftCode = "AIZKLV22XXX";
        String swiftCodeWithoutSuffix = "AIZKLV22";
        List<BranchResponse> branchResponses = branches.stream().map(this::mapToBranch).toList();
        GetSwiftCodeBySwiftCodeResponse expectedResponse = createExpectedResponse(branchWithHq, branchResponses);

        when(swiftCodeRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.of(branchWithHq));
        when(swiftCodeRepository.findAllBranchesBySwiftCode(swiftCodeWithoutSuffix)).thenReturn(branches);

        // WHEN
        var response = getSwiftCodeBySwiftCodeService.getGetSwiftCodeBySwiftCodeResponse(swiftCode);

        // THEN
        verify(swiftCodeRepository).findBySwiftCode(swiftCode);
        verify(swiftCodeRepository).findAllBranchesBySwiftCode(swiftCodeWithoutSuffix);
        assertEquals(expectedResponse, response);
    }

    @Test
    public void shouldThrowSwiftCodeNotFoundException() {
        // GIVEN
        String swiftCode = "KDPWPLPAASD";
        String expectedMessage = "Not found swiftCode " + swiftCode;

        when(swiftCodeRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.empty());

        // WHEN & THEN
        SwiftCodeNotFoundException exception = assertThrows(SwiftCodeNotFoundException.class,
                () -> getSwiftCodeBySwiftCodeService.getGetSwiftCodeBySwiftCodeResponse(swiftCode)
        );

        verify(swiftCodeRepository).findBySwiftCode(swiftCode);
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static final SwiftCodeEntity branchWithoutHq = new SwiftCodeEntity(
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
}
