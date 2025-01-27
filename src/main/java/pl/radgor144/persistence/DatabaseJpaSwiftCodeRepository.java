package pl.radgor144.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DatabaseJpaSwiftCodeRepository implements SwiftCodeRepository {

    private final JpaSwiftCodeRepository jpaSwiftCodeRepository;

    @Override
    public List<SwiftCodeEntity> findAllBranchesBySwiftCode(String swiftCode) {
        return jpaSwiftCodeRepository.findAllBranchesBySwiftCode(swiftCode);
    }

    @Override
    public Optional<SwiftCodeEntity> findBySwiftCode(String swiftCode) {
        return jpaSwiftCodeRepository.findBySwiftCode(swiftCode);
    }

    @Override
    public List<SwiftCodeEntity> findByCountryISO2(String countryISO2code) {
        return jpaSwiftCodeRepository.findByCountryISO2(countryISO2code);
    }

    @Override
    public void deleteBySwiftCode(String swiftCode) {
        jpaSwiftCodeRepository.deleteBySwiftCode(swiftCode);
    }

    @Override
    public boolean existsBySwiftCode(String swiftCode) {
        return jpaSwiftCodeRepository.existsBySwiftCode(swiftCode);
    }

    @Override
    public void saveAll(List<SwiftCodeEntity> swiftCodeEntities) {
        jpaSwiftCodeRepository.saveAll(swiftCodeEntities);
    }

    @Override
    public SwiftCodeEntity save(SwiftCodeEntity swiftCodeEntity) {
        return jpaSwiftCodeRepository.save(swiftCodeEntity);
    }
}
