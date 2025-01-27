package pl.radgor144.persistence;

import java.util.List;
import java.util.Optional;

public interface SwiftCodeRepository {

    List<SwiftCodeEntity> findAllBranchesBySwiftCode(String swiftCode);

    Optional<SwiftCodeEntity> findBySwiftCode(String swiftCode);

    List<SwiftCodeEntity> findByCountryISO2(String countryISO2code);


    void saveAll(List<SwiftCodeEntity> swiftCodeEntities);

    SwiftCodeEntity save(SwiftCodeEntity swiftCodeEntity);

    void deleteBySwiftCode(String swiftCode);

    boolean existsBySwiftCode(String swiftCode);
}
