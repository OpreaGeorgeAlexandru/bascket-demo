package goprea.software.backend.controller;

import goprea.software.backend.dto.UserLoginDTO;
import goprea.software.backend.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/basket")
public class BasketRestController {

    private final BasketService basketService;

    @Autowired
    public BasketRestController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("")
    @Operation(
            summary = "Get basket games")
    public ResponseEntity<?> getGames(@RequestHeader final String token) throws IOException, InterruptedException {
        basketService.getBasketGames();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
