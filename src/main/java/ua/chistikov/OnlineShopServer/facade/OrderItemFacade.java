package ua.chistikov.OnlineShopServer.facade;

import org.springframework.stereotype.Component;
import ua.chistikov.OnlineShopServer.dto.OrderItemDTO;
import ua.chistikov.OnlineShopServer.entity.OrderItem;

@Component
public class OrderItemFacade {
    public OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem){
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductID(orderItem.getProductID());
        orderItemDTO.setOrderID(orderItem.getOrderID());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setAmount(orderItem.getAmount());
        return orderItemDTO;
    }
}
