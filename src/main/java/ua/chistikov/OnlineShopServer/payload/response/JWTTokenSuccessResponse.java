package ua.chistikov.OnlineShopServer.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTTokenSuccessResponse {
    private Boolean success;
    private String token;
}
