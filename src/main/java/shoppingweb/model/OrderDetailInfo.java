package shoppingweb.model;

import lombok.Data;
import shoppingweb.entity.OrderDetail;

@Data
public class OrderDetailInfo {

	private String id;
	 
    private String productCode;
    private String productName;
 
    private int quanity;
    private double price;
    private double amount;
    
    public OrderDetailInfo() {
    	
    }
    
    public OrderDetailInfo(OrderDetail orderDetail) {
    	
    	this.id = orderDetail.getId();
    	this.productCode = orderDetail.getProduct().getCode();
    	this.productName = orderDetail.getProduct().getName();
    	this.quanity = orderDetail.getQuanity();
    	this.price = orderDetail.getPrice();
    	this.amount = orderDetail.getAmount();
    }
}
