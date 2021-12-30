package goprea.software.backend.controller;


import goprea.software.backend.dto.UserLoginDTO;
import goprea.software.backend.dto.UserRegisterDTO;
import goprea.software.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class PublicRestController {

    private final UserService userService;

    @Autowired
    public PublicRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login with an username and password.",
            description = "Will generate a token, which will be attached to the next requests for authorization.")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(userService.login(userLoginDTO),HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register an user in the system")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        return new ResponseEntity<>(userService.register(userRegisterDTO), HttpStatus.OK);
    }
}
