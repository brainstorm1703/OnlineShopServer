package ua.chistikov.OnlineShopServer.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.chistikov.OnlineShopServer.entity.Order;
import ua.chistikov.OnlineShopServer.entity.OrderItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findOrderItemById(Long id);
    Optional<OrderItem> findOrderItemByProductIDName(String name);
    List<OrderItem> findOrderItemByProductIDId(Long id);
    List<OrderItem> findOrderItemByOrderID(Order orderID);

}