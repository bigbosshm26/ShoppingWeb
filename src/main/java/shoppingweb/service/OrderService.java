package shoppingweb.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shoppingweb.entity.Order;
import shoppingweb.entity.OrderDetail;
import shoppingweb.entity.Product;
import shoppingweb.model.CartInfo;
import shoppingweb.model.CartLineInfo;
import shoppingweb.model.CustomerInfo;
import shoppingweb.repository.OrderDetailRepository;
import shoppingweb.repository.OrderRepository;
import shoppingweb.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	OrderDetailRepository orderDetailRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	public void saveOrder(CartInfo cartInfo) {
		
		int orderNum;
		
		Integer maxOrderNum = orderRepo.getMaxOrderNum();
		
		if(maxOrderNum == null) {
			orderNum = 1;
		}else {
			orderNum = maxOrderNum + 1;
		}
		
		Order order = new Order();
		
		order.setId(UUID.randomUUID().toString());
		order.setOrderDate(new Date());
		order.setOrderNum(orderNum);
		order.setAmount(cartInfo.getAmountTotal());
		
		CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		order.setCustomerName(customerInfo.getName());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhone());
        order.setCustomerAddress(customerInfo.getAddress());
        
        orderRepo.save(order);
        
        List<CartLineInfo> lines = cartInfo.getCartLines();
        for(CartLineInfo line : lines) {
        	OrderDetail detail = new OrderDetail();
        	detail.setId(UUID.randomUUID().toString());
            detail.setOrder(order);
            detail.setAmount(line.getAmount());
            detail.setPrice(line.getProductInfo().getPrice());
            detail.setQuanity(line.getQuantity());
            
            String code = line.getProductInfo().getCode();
            Product product = productRepo.findByCode(code).orElse(null);
            detail.setProduct(product);
            
            orderDetailRepo.save(detail);
        }
        
        cartInfo.setOrderNum(orderNum);
        
        orderRepo.flush();
        orderDetailRepo.flush();
	}
	

}
