package goprea.software.backend.dto;

import goprea.software.backend.annotation.ValidPassword;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRegisterDTO {

    @Column(name = "NAME")
    @NotNull(message = "Name cannot be null!")
    @Size(min = 2, max = 50, message = "Invalid first name length!")
    private String name;

    @NotNull(message = "Username cannot be null!")
    @Size(min = 2, max = 50, message = "Invalid username length!")
    private String username;

    @NotNull(message = "Password cannot be null!")
    @ValidPassword
    private String password;
}
