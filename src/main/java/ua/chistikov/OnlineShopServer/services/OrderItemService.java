package ua.chistikov.OnlineShopServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.chistikov.OnlineShopServer.dto.OrderItemDTO;
import ua.chistikov.OnlineShopServer.entity.Order;
import ua.chistikov.OnlineShopServer.entity.OrderItem;
import ua.chistikov.OnlineShopServer.exceptions.OrderItemNotFoundException;
import ua.chistikov.OnlineShopServer.repository.OrderItemRepository;

import java.util.List;


@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem createOrderItem(OrderItemDTO orderItemDTO){
        OrderItem orderItem = new OrderItem();
        orderItem.setProductID(orderItemDTO.getProductID());
        orderItem.setOrderID(orderItemDTO.getOrderID());
        orderItem.setPrice(orderItem.getProductID().getPrice());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setAmount(orderItem.getProductID().getPrice() * orderItem.getQuantity());
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllOrderItemByOrder(Order id)
    {
        return orderItemRepository.findOrderItemByOrderID(id);
    }

    public void deleteOrderItem(OrderItemDTO orderItemDTO){
        orderItemRepository.delete(getOrderItemByProductId(orderItemDTO.getProductID().getId()));
    }

    private OrderItem getOrderItemByProductId(Long id) {
        return orderItemRepository.findOrderItemByProductIDId(id).get(0);
    }

    public OrderItem updateOrderItem(long l, OrderItemDTO orderItemDTO) {

        OrderItem orderItem = orderItemRepository.findOrderItemByProductIDName(orderItemDTO.getProductID().getName()).get();

        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setAmount(orderItem.getProductID().getPrice() * orderItem.getQuantity());
        return orderItemRepository.save(orderItem);
    }
}
