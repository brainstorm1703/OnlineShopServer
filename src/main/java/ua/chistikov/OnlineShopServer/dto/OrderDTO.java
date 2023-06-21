    package ua.chistikov.OnlineShopServer.dto;

    import lombok.Data;
    import ua.chistikov.OnlineShopServer.entity.User;

    import javax.validation.constraints.NotEmpty;

    @Data
    public class OrderDTO {
        private Long id;
        private User userID;
        @NotEmpty
        private String orderAddress;
        private String details;
        private String status;
    }
