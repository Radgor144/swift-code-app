package pl.radgor144.initalizedatabase;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.persistence.SwiftCodeRepository;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class SwiftCodeCsvImporter {

    private final SwiftCodeRepository swiftCodeRepository;

    private final String swiftCodePath;
    private final int batchSize;

    public SwiftCodeCsvImporter(SwiftCodeRepository swiftCodeRepository,
                                @Value("${swiftCodePath}") String swiftCodePath,
                                @Value("${batchSize}") int batchSize) {
        this.swiftCodeRepository = swiftCodeRepository;
        this.swiftCodePath = swiftCodePath;
        this.batchSize = batchSize;
    }

    void importSwiftCodes() {
        try (Reader reader = getPathToSwiftCodesFile()) {
            CsvToBean<SwiftCodeLine> swiftCodeLines = new CsvToBeanBuilder<SwiftCodeLine>(reader)
                    .withType(SwiftCodeLine.class)
                    .build();
            List<SwiftCodeEntity> swiftCodeEntities = new ArrayList<>();
            for (SwiftCodeLine swiftCodeLine : swiftCodeLines) {
                SwiftCodeEntity swiftCodeEntity = mapToSwiftCodeEntity(swiftCodeLine);
                swiftCodeEntities.add(swiftCodeEntity);
                if (swiftCodeEntities.size() == batchSize) {
                    swiftCodeRepository.saveAll(swiftCodeEntities);
                    swiftCodeEntities.clear();
                }
            }
            if (!swiftCodeEntities.isEmpty()) {
                swiftCodeRepository.saveAll(swiftCodeEntities);
            }
        } catch (Exception exception) {
            log.error("Error while saving swift-codes from file: {}", exception.getMessage(), exception);
        }
    }

    private Reader getPathToSwiftCodesFile() {
        InputStreamReader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(swiftCodePath));
        if (reader == null) {
            log.error("CSV file not found in the classpath: {}", swiftCodePath);
        }
        return reader;
    }

    private static SwiftCodeEntity mapToSwiftCodeEntity(SwiftCodeLine swiftCodeLine) {
        return SwiftCodeEntity.builder()
                .id(UUID.randomUUID())
                .address(swiftCodeLine.getAddress())
                .bankName(swiftCodeLine.getName())
                .isHeadquarter(swiftCodeLine.getSwiftCode().endsWith("XXX"))
                .countryISO2(swiftCodeLine.getCountryISO2())
                .countryName(swiftCodeLine.getCountryName())
                .swiftCode(swiftCodeLine.getSwiftCode())
                .build();
    }
}