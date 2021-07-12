package co.com.ml.challenge.favorite.items.coupon.busicapa;

import co.com.ml.challenge.favorite.items.coupon.model.consumer.response.ItemResponse;
import co.com.ml.challenge.favorite.items.coupon.web.controller.handler.error.RestException;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Component
public class ItemRestClient {

    private static final Logger logger = LoggerFactory.getLogger(ItemRestClient.class);

    @Value("${items.endpoint}")
    private String endpoint;

    private SslContext sslContext;

    @SneakyThrows
    public ItemResponse getItemDetail (String itemId) {

        sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        WebClient webClient = WebClient.builder().clientConnector(
                new ReactorClientHttpConnector(httpClient))
                .baseUrl(endpoint)
                .build();

        ItemResponse itemResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/items/{itemId}")
                        .build(itemId))
                .retrieve()
                // handle status
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                  logger.error("Error endpoint with status code {}", clientResponse.statusCode());
                    throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR,"Error endpoint with status code {} " + clientResponse.statusCode());
                })
                .bodyToMono(ItemResponse.class)
                .block();

        logger.info(itemResponse.toString());
        return itemResponse;

    }
}
