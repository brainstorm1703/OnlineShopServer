package ua.chistikov.OnlineShopServer.facade;

import org.springframework.stereotype.Component;
import ua.chistikov.OnlineShopServer.dto.OrderDTO;
import ua.chistikov.OnlineShopServer.entity.Order;

@Component
public class OrderFacade {
    public OrderDTO orderToOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserID(order.getUserID());
        orderDTO.setOrderAddress(order.getOrderAddress());
        orderDTO.setDetails(order.getDetails());
        orderDTO.setStatus(order.getStatus());

        return orderDTO;
    }
}
