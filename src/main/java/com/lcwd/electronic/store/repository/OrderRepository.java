package com.lcwd.electronic.store.repository;

import com.lcwd.electronic.store.entity.Order;
import com.lcwd.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findByUser(User user);
}
