//package co.com.ml.challenge.favorite.items.coupon.busicapa;
//
//import co.com.ml.challenge.favorite.items.coupon.model.consumer.response.ItemResponse;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockserver.integration.ClientAndServer;
//import org.mockserver.model.HttpRequest;
//import org.mockserver.model.HttpResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//
//import java.util.concurrent.TimeUnit;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(MockitoExtension.class)
//class ItemRestClientTest {
//
//    private ClientAndServer mockServer;
//
//    @Autowired
//    ItemRestClient itemRestClient;
//
//    @BeforeEach
//    public void startServer() {
//        mockServer = ClientAndServer.startClientAndServer(80);
//    }
//
//    @AfterEach
//    public void stopServer() {
//        mockServer.stop();
//    }
//
//    @Test
//    void getItemDetail() {
//
//        // set up mock server with a delay of 1 seconds
//        mockServer.when(HttpRequest.request().withMethod("GET")
//                .withPath("/items/MLA651394869"))
//                .respond(HttpResponse.response()
//                        .withBody("{\n" +
//                                "    \"id\": \"MLA651394869\",\n" +
//                                "    \"site_id\": \"MLA\",\n" +
//                                "    \"title\": \"Cubierta 140 60 17 Trasera Yamaha Fz 16 Fi 2.0 Gixxer  Fas\",\n" +
//                                "    \"subtitle\": null,\n" +
//                                "    \"seller_id\": 89575112,\n" +
//                                "    \"category_id\": \"MLA45529\",\n" +
//                                "    \"official_store_id\": null,\n" +
//                                "    \"price\": 8599\n" +
//                                "}")
//                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .withDelay(TimeUnit.MILLISECONDS, 1000));
//
//        // given: a new item
//        //ItemRestClient itemRestClient = new ItemRestClient();
//        ItemResponse itemResponse = itemRestClient.getItemDetail("MLA651394869");
//
//        // then: response should be ok
//        assertThat(itemResponse).isNotNull();
//        assertThat(itemResponse.getId()).isEqualTo("MLA651394869");
//
//    }
//
//    /*@Test
//    void getItemError500(){
//
//        RestException restException = new RestException(HttpStatus.INTERNAL_SERVER_ERROR,"Error endpoint with status code");
//        // set up mock server with a http status 500
//        mockServer.when(HttpRequest.request().withMethod("GET")
//                .withPath("/items/MLA1"))
//                .respond((restException));
//
//        // when: send a request and a 500 error occurs
//        // then: throw a ApiWebClientException
//        assertThrows(RestException.class, () -> itemRestClient.getItemDetail("MLA1"));
//        assertTrue(restException.getMessage().contains("Error endpoint with status code"));
//
//    }*/
//}