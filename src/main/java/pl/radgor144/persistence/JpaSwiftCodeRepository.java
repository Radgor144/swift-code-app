package pl.radgor144.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface JpaSwiftCodeRepository extends JpaRepository<SwiftCodeEntity, UUID> {

    @Query(value = "SELECT * FROM swift_code_entity WHERE swift_code LIKE CONCAT(:swiftCode, '%') AND is_headquarter = false", nativeQuery = true)
    List<SwiftCodeEntity> findAllBranchesBySwiftCode(String swiftCode);

    Optional<SwiftCodeEntity> findBySwiftCode(String swiftCode);

    List<SwiftCodeEntity> findByCountryISO2(String countryISO2code);

    void deleteBySwiftCode(String swiftCode);

    boolean existsBySwiftCode(String swiftCode);
}
