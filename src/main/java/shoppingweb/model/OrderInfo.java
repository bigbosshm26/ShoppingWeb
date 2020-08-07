package shoppingweb.model;

import java.util.Date;
import java.util.List;


import lombok.Data;
import shoppingweb.entity.Order;

@Data
public class OrderInfo {

	private String id;
    private Date orderDate;
    private int orderNum;
    private double amount;
 
    private String customerName;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;
    
    private List<OrderDetailInfo> details;
    
    public OrderInfo() {
    	 
    }
    
    public OrderInfo(Order order) {
    	
    	this.id = order.getId();
    	this.orderDate = order.getOrderDate();
    	this.orderNum = order.getOrderNum();
    	this.amount = order.getAmount();
    	
    	this.customerName = order.getCustomerName();
    	this.customerAddress = order.getCustomerAddress();
    	this.customerEmail = order.getCustomerEmail();
    	this.customerPhone = order.getCustomerPhone();

    }
}
