package ua.chistikov.OnlineShopServer.payload.request;

import lombok.Data;
import ua.chistikov.OnlineShopServer.annotations.PasswordMatches;

import javax.validation.constraints.*;

@Data
@PasswordMatches
public class SignupRequest {
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Please enter your password")
    @Size(min = 4)
    private String password;
    private String confirmPassword;
}
