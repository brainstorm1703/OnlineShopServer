package ua.chistikov.OnlineShopServer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User userID;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "orderAddress", nullable = false, length = 3000)
    private String orderAddress;


    @Column(name = "status", nullable = false, length = 3000)
    private String status;


    @Column(name = "details", length = 3000)
    private String details;

    @OneToMany(mappedBy = "orderID", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<OrderItem> orderItems;


    public Order() {}

    public Order(Long id, User userID, String orderAddress, String status, String details, List<OrderItem> orderItems) {
        this.id = id;
        this.userID = userID;
        this.orderAddress = orderAddress;
        this.status = status;
        this.details = details;
        this.orderItems = orderItems;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

}
