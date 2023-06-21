package ua.chistikov.OnlineShopServer.dto;

import lombok.Data;
import ua.chistikov.OnlineShopServer.entity.Order;
import ua.chistikov.OnlineShopServer.entity.Product;

@Data
public class OrderItemDTO {
    private Product productID;
    private Order orderID;
    private Integer quantity;
    private Double amount;
}
