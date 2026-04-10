package com.app.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_tracking_number")
    private String orderTrackingNumber;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    private String status;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreate;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItem> items = new HashSet<>();

    public void addItem(OrderItem item) {
        if (item != null) {
            if (items == null){
                items = new HashSet<>();
            }
            items.add(item);
            item.setOrder(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private Address shippingAddres;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    private Address billingAddress;

}
