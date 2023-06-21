package ua.chistikov.OnlineShopServer.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.chistikov.OnlineShopServer.entity.Order;
import ua.chistikov.OnlineShopServer.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderById(Long id);

    List<Order> findOrderByUserID(User user);
    Optional<Order> findOrderByOrderAddress(String address);
}