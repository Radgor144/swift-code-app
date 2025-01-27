package pl.radgor144.swiftcode.createswiftcode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.persistence.SwiftCodeRepository;

@Service
@RequiredArgsConstructor
public class CreateSwiftCodeService {

    private final SwiftCodeRepository swiftCodeRepository;
    private final UuidGenerator uuidGenerator;
    @Transactional
    public CreateSwiftCodeResponse createSwiftCode(CreateSwiftCodeRequest createSwiftCodeRequest) {
        String requestedSwiftCode = createSwiftCodeRequest.swiftCode();
        if (swiftCodeRepository.existsBySwiftCode(requestedSwiftCode)) {
            throw new SwiftCodeExistException(requestedSwiftCode);
        }
        SwiftCodeEntity swiftCodeEntity = mapToSwiftCodeEntity(createSwiftCodeRequest);
        SwiftCodeEntity savedSwiftCodeEntity = swiftCodeRepository.save(swiftCodeEntity);
        return new CreateSwiftCodeResponse("Created swiftCode %s".formatted(savedSwiftCodeEntity.toString()));
    }

    private SwiftCodeEntity mapToSwiftCodeEntity(CreateSwiftCodeRequest createSwiftCodeRequest) {
        return SwiftCodeEntity.builder()
                .id(uuidGenerator.generate())
                .address(createSwiftCodeRequest.address())
                .bankName(createSwiftCodeRequest.bankName())
                .isHeadquarter(createSwiftCodeRequest.isHeadquarter())
                .countryISO2(createSwiftCodeRequest.countryISO2())
                .countryName(createSwiftCodeRequest.countryName())
                .swiftCode(createSwiftCodeRequest.swiftCode())
                .build();
    }
}
