package com.KalaroApplication.KALARO_ORDERS.repository;

import com.KalaroApplication.KALARO_ORDERS.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    OrderDetails findByOrderId(int orderId);

    List<OrderDetails> findAllByOrderCategory(String category);
}
