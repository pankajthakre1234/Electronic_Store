package com.lcwd.electronic.store.repository;

import com.lcwd.electronic.store.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
}
