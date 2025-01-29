package pl.radgor144.swiftcode.createswiftcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.persistence.SwiftCodeRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateSwiftCodeServiceTest {

    private final String SWIFT_CODE = "AIZKLV22XXX";
    CreateSwiftCodeRequest createSwiftCodeRequest;

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @Mock
    private UuidGenerator uuidGenerator;

    @InjectMocks
    private CreateSwiftCodeService createSwiftCodeService;

    @BeforeEach
    void setUp() {
        createSwiftCodeRequest = new CreateSwiftCodeRequest(
                "MIHAILA TALA STREET 1  RIGA, RIGA, LV-1045",
                "ABLV BANK, AS IN LIQUIDATION",
                "LV",
                "LATVIA",
                true,
                "AIZKLV22XXX"
        );
    }

    @Test
    void shouldCreateSwiftCodeSuccessfully() {
        //GIVEN
        UUID generatedId = UUID.randomUUID();

        when(swiftCodeRepository.existsBySwiftCode(SWIFT_CODE)).thenReturn(false);
        when(uuidGenerator.generate()).thenReturn(generatedId);
        when(swiftCodeRepository.save(any(SwiftCodeEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //WHEN
        CreateSwiftCodeResponse result = createSwiftCodeService.createSwiftCode(createSwiftCodeRequest);

        //THEN
        assertNotNull(result);
        assertEquals(
                "Created swiftCode SwiftCodeEntity(id=" + generatedId + ", address=MIHAILA TALA STREET 1  RIGA, RIGA, LV-1045, bankName=ABLV BANK, AS IN LIQUIDATION, countryISO2=LV, countryName=LATVIA, isHeadquarter=true, swiftCode=AIZKLV22XXX)",
                result.message()
        );

        verify(swiftCodeRepository, times(1)).existsBySwiftCode(SWIFT_CODE);
        verify(uuidGenerator, times(1)).generate();
        verify(swiftCodeRepository, times(1)).save(any(SwiftCodeEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenSwiftCodeAlreadyExists() {
        //GIVEN
        when(swiftCodeRepository.existsBySwiftCode(SWIFT_CODE)).thenReturn(true);

        //GIVEN & THEN
        SwiftCodeExistException exception = assertThrows(SwiftCodeExistException.class, () ->
                createSwiftCodeService.createSwiftCode(createSwiftCodeRequest)
        );

        assertEquals("SwiftCode " + SWIFT_CODE + " already exist!", exception.getMessage());
        verify(swiftCodeRepository, times(1)).existsBySwiftCode(SWIFT_CODE);
        verifyNoMoreInteractions(swiftCodeRepository);
    }
}
