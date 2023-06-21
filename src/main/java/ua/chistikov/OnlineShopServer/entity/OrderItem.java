package ua.chistikov.OnlineShopServer.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "productID", referencedColumnName = "id")
    private Product productID;

    @ManyToOne()
    @JoinColumn(name = "orderID", referencedColumnName = "id", nullable = false)
    private Order orderID;

    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "amount", nullable = false)
    private Double amount;
}
