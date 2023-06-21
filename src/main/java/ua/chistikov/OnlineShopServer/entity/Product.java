package ua.chistikov.OnlineShopServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",unique = true, nullable = false)
    private String name;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "description", length = 3000)
    private String description;
    @OneToMany(mappedBy = "productID", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<OrderItem> orderItems;


    public Product() {}

    public Product(Long id, String name, Double price, Integer quantity, String description, List<OrderItem> orderItems) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.orderItems = orderItems;
    }
}
