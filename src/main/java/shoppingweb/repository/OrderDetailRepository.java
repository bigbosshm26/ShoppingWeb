package shoppingweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import shoppingweb.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String>{

	List<OrderDetail> findByOrderId(String orderId);
}
