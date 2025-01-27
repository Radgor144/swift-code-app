package pl.radgor144.initalizedatabase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@RequiredArgsConstructor
@Slf4j
public class SwiftCodeDatabaseInitializer implements CommandLineRunner {

    private final SwiftCodeCsvImporter swiftCodeCsvImporter;

    @Override
    public void run(String... args) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Saving swiftCode to database");
        swiftCodeCsvImporter.importSwiftCodes();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }
}