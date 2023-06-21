package ua.chistikov.OnlineShopServer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.chistikov.OnlineShopServer.dto.OrderDTO;
import ua.chistikov.OnlineShopServer.entity.Order;
import ua.chistikov.OnlineShopServer.entity.User;
import ua.chistikov.OnlineShopServer.exceptions.OrderNotFoundException;
import ua.chistikov.OnlineShopServer.repository.OrderRepository;
import ua.chistikov.OnlineShopServer.repository.UserRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    public static final Logger LOG = LoggerFactory.getLogger(OrderService.class);
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderDTO orderDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        Order order = new Order();
        order.setUserID(user);
        order.setCreatedDate(LocalDate.now().atStartOfDay());
        order.setOrderAddress(orderDTO.getOrderAddress());
        order.setStatus("new order");
        order.setDetails(orderDTO.getDetails());
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderID){
        return orderRepository.findOrderById(orderID)
                .orElseThrow(() -> new OrderNotFoundException("Order cannot be found for id: " + orderID));
    }

    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }
    public List<Order> getAllUserOrder(User user){
        return orderRepository.findOrderByUserID(user);
    }

    public void deleteOrder(Long orderID){
        orderRepository.delete(getOrderById(orderID));
    }

    private User getUserByPrincipal(Principal principal) {
        return userRepository.findUserByUsername(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username " + principal.getName()));
    }

    public Order updateOrder(OrderDTO orderDTO, long id) {
        Order order = orderRepository.findOrderById(id).get();
        order.setOrderAddress(orderDTO.getOrderAddress());
        order.setDetails(orderDTO.getDetails());
        order.setStatus(orderDTO.getStatus());
        return orderRepository.save(order);
    }
}
