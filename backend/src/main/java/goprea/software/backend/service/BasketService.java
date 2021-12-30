package goprea.software.backend.service;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Transactional
public class BasketService {

    public void getBasketGames() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api-basketball.p.rapidapi.com/timezone"))
                .header("x-rapidapi-host", "api-basketball.p.rapidapi.com")
                .header("x-rapidapi-key", "69088e4f41msh2a9b19cd5960917p1c4107jsnf1d9eb4a3944")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
