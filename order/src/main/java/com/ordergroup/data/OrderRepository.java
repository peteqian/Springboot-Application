package com.ordergroup.data;

import com.ordergroup.application.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
        extends JpaRepository<Orders, Long> {
}
