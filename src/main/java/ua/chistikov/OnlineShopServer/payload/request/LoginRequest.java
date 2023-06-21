package ua.chistikov.OnlineShopServer.payload.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LoginRequest {

    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    public LoginRequest() {
        this.username = "invalid username";
        this.password = "invalid password";
    }
}
