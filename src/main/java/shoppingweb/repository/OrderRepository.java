package shoppingweb.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import shoppingweb.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{

	//Lay gia tri order max trong bang orders
	@Query("Select max(o.orderNum) from orders o")
	Integer getMaxOrderNum();
	
	
	//lay ra list order de chia trang
	List<Order> findAllByOrderByOrderNumAsc(Pageable pageable);
	
}
