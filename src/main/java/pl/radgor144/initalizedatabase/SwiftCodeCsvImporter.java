package pl.radgor144.initalizedatabase;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.persistence.SwiftCodeRepository;

import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    void importSwiftCodes() throws Exception {
        Path pathToSwiftCodesFile = getPathToSwiftCodesFile();
        try (Reader reader = Files.newBufferedReader(pathToSwiftCodesFile)) {
            CsvToBean<SwiftCodeLine> swiftCodeLines = new CsvToBeanBuilder<SwiftCodeLine>(reader)
                    .withType(SwiftCodeLine.class)
                    .build();
            List<SwiftCodeEntity> swiftCodeEntities = new ArrayList<>();
            for (SwiftCodeLine swiftCodeLine : swiftCodeLines) {
                SwiftCodeEntity swiftCodeEntity = mapToSwiftCodeEntity(swiftCodeLine);
                swiftCodeEntities.add(swiftCodeEntity);
                if (swiftCodeEntities.size() == batchSize) {
                    swiftCodeRepository.saveAll(swiftCodeEntities);
                }
            }
            if (!swiftCodeEntities.isEmpty()) {
                swiftCodeRepository.saveAll(swiftCodeEntities);
            }
        } catch (Exception exception) {
            log.error("Error while saving swift-codes from file: {}", exception.getMessage(), exception);
        }
    }

    private Path getPathToSwiftCodesFile() throws URISyntaxException {
        URL urlToSwiftCodesFile = getClass().getClassLoader().getResource(swiftCodePath);
        return Paths.get(Objects.requireNonNull(urlToSwiftCodesFile).toURI());
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