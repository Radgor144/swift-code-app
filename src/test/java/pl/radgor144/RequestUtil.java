package pl.radgor144;

import lombok.experimental.UtilityClass;
import org.springframework.test.web.reactive.server.WebTestClient;

@UtilityClass
public class RequestUtil {
    private final static WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080/v1/swift-codes").build();

    public static WebTestClient.ResponseSpec get(String uri,  Object... uriVariables) {
        return webTestClient.get()
                .uri(uri, uriVariables)
                .exchange();
    }

    public static WebTestClient.ResponseSpec post(String uri, Object bodyValue) {
        return webTestClient.post()
                .uri(uri)
                .bodyValue(bodyValue)
                .exchange();
    }

    public static WebTestClient.ResponseSpec delete(String uri, Object... uriVariables) {
        return webTestClient.delete()
                .uri(uri, uriVariables)
                .exchange();
    }
}
