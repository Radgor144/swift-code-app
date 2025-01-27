package pl.radgor144.swiftcode.deleteswiftcode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.radgor144.persistence.SwiftCodeRepository;
import pl.radgor144.swiftcode.swiftcodebyswiftcode.SwiftCodeNotFoundException;

@Service
@RequiredArgsConstructor
public class DeleteSwiftCodeService {

    private final SwiftCodeRepository swiftCodeRepository;

    @Transactional
    public DeleteBySwiftCodeResponse deleteBySwiftCode(String swiftCode) {
        if (!swiftCodeRepository.existsBySwiftCode(swiftCode)) {
            throw new SwiftCodeNotFoundException(swiftCode);
        }
        swiftCodeRepository.deleteBySwiftCode(swiftCode);
        return new DeleteBySwiftCodeResponse("Deleted swiftCode %s".formatted(swiftCode));
    }
}
