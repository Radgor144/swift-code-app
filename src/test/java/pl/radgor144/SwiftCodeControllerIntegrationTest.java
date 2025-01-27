package pl.radgor144;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import pl.radgor144.inbound.ExceptionResponse;
import pl.radgor144.persistence.SwiftCodeEntity;
import pl.radgor144.swiftcode.createswiftcode.CreateSwiftCodeRequest;
import pl.radgor144.swiftcode.createswiftcode.CreateSwiftCodeResponse;
import pl.radgor144.swiftcode.createswiftcode.UuidGenerator;
import pl.radgor144.swiftcode.deleteswiftcode.DeleteBySwiftCodeResponse;

import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwiftCodeControllerIntegrationTest {

    private static final String DEFAULT_ADDRESS = "address";
    private static final String DEFAULT_BANK_NAME = "bankName";
    private static final String DEFAULT_COUNTRY_ISO_2 = "countryISO2";
    private static final String DEFAULT_COUNTRY_NAME = "countryName";

    @ServiceConnection
    private static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15-alpine").withUsername("root")
            .withPassword("root")
            .withNetworkAliases("mysql")
            .withDatabaseName("test");

    @MockBean
    private UuidGenerator uuidGenerator;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
    }

    @BeforeAll
    static void setUp() {
        POSTGRES_SQL_CONTAINER.start();
    }

    @AfterAll
    static void tearDown() {
        POSTGRES_SQL_CONTAINER.close();
    }

    @Test
    void testGetSwiftCode() {
//      GIVEN
        String swiftCode = "ABCDEF12";

//      WHEN
        WebTestClient.ResponseSpec response = RequestUtil.get("/{swift-code}", swiftCode);

//      THEN
        response.expectStatus().isNotFound()
                .expectBody(ExceptionResponse.class)
                .isEqualTo(anExceptionResponse("Not found swiftCode %s".formatted(swiftCode)));
    }

    @Test
    void shouldReturnErrorIfSwiftCodeNotExistWhenDeleteCalled() {
//      GIVEN
        String swiftCode = "ABCDEF12";

//      WHEN
        WebTestClient.ResponseSpec response = RequestUtil.delete("/{swift-code}", swiftCode);

//      THEN
        response.expectStatus().isNotFound()
                .expectBody(ExceptionResponse.class)
                .isEqualTo(anExceptionResponse("Not found swiftCode %s".formatted(swiftCode)));
    }

    @Test
    void shouldDeleteSwiftCodeWhenShiftCodeExist() {
//      GIVEN
        String swiftCode = "AAISALTRXXX";

//      WHEN
        WebTestClient.ResponseSpec response = RequestUtil.delete("/{swift-code}", swiftCode);

//      THEN
        response.expectStatus().isOk()
                .expectBody(DeleteBySwiftCodeResponse.class)
                .isEqualTo(new DeleteBySwiftCodeResponse("Deleted swiftCode %s".formatted(swiftCode)));
    }

    @Test
    void shouldNotCreateSwiftCodeWhenShiftCodeExist() {
//      GIVEN
        String swiftCode = "ABIEBGS1XXX";

//      WHEN
        WebTestClient.ResponseSpec response = RequestUtil.post("/", aCreateSwiftCodeRequest(swiftCode));

//      THEN
        response.expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(ExceptionResponse.class)
                .isEqualTo(anExceptionResponse("SwiftCode %s already exist!".formatted(swiftCode)));
    }

    @Test
    void shouldCreateSwiftCodeWhenShiftCodeExist() {
//      GIVEN
        String swiftCode = "NEW_SWIFT_CODE";
        UUID swiftCodeEntityId = UUID.fromString("ea2269e2-0329-41b9-afb3-e14d892e071a");
        when(uuidGenerator.generate()).thenReturn(swiftCodeEntityId);

//      WHEN
        WebTestClient.ResponseSpec response = RequestUtil.post("/", aCreateSwiftCodeRequest(swiftCode));

//      THEN
        response.expectStatus().isOk()
                .expectBody(CreateSwiftCodeResponse.class)
                .isEqualTo(new CreateSwiftCodeResponse("Created swiftCode %s".formatted(aSwiftCodeEntity(swiftCodeEntityId, swiftCode).toString())));
    }

    @Test
    void shouldNotCreateSwiftCodeWhenCreateSwiftCodeRequestIsNotValid() {
//      GIVEN
        String swiftCode = null;

//      WHEN
        WebTestClient.ResponseSpec response = RequestUtil.post("/", aCreateSwiftCodeRequest(swiftCode));

//      THEN
        response.expectStatus().isBadRequest()
                .expectBody(ExceptionResponse.class)
                .isEqualTo(anExceptionResponse("Validation error:\nswiftCode: nie może mieć wartości null"));
    }

    @Test
    void shouldReturnErrorWhenCountryISO2CodeNotExist() {
//      GIVEN
        String countryISO2code = "NOT_EXIST";

//      WHEN
        WebTestClient.ResponseSpec response = RequestUtil.get("/country/{countryISO2code}", countryISO2code);

//      THEN
        response.expectStatus().isNotFound()
                .expectBody(ExceptionResponse.class)
                .isEqualTo(anExceptionResponse("Not found swiftCodes for countryISO2code: %s".formatted(countryISO2code)));
    }

    @NotNull
    private static CreateSwiftCodeRequest aCreateSwiftCodeRequest(String swiftCode) {
        return new CreateSwiftCodeRequest(DEFAULT_ADDRESS,
                DEFAULT_BANK_NAME,
                DEFAULT_COUNTRY_ISO_2,
                DEFAULT_COUNTRY_NAME,
                true,
                swiftCode
        );
    }

    private SwiftCodeEntity aSwiftCodeEntity(UUID id, String swiftCode) {
        return SwiftCodeEntity.builder()
                .id(id)
                .address(DEFAULT_ADDRESS)
                .bankName(DEFAULT_BANK_NAME)
                .isHeadquarter(true)
                .countryISO2(DEFAULT_COUNTRY_ISO_2)
                .countryName(DEFAULT_COUNTRY_NAME)
                .swiftCode(swiftCode)
                .build();
    }

    private static ExceptionResponse anExceptionResponse(String message) {
        return new ExceptionResponse(message);
    }
}