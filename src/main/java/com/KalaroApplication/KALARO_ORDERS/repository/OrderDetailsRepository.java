package com.KalaroApplication.KALARO_ORDERS.repository;

import com.KalaroApplication.KALARO_ORDERS.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    OrderDetails findByOrderId(int orderId);

    List<OrderDetails> findAllByOrderCategory(String category);

    OrderDetails findByModelNo(String modelNo);

    List<OrderDetails> findAllByOrderIdNot(int orderId);

}
