package ua.chistikov.OnlineShopServer.exceptions;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String msg) {
        super(msg);
    }
}
