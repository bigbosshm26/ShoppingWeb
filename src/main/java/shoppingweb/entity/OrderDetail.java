package shoppingweb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity(name = "order_details")
@Table
public class OrderDetail implements Serializable{

	private static final long serialVersionUID = -4484722700137365975L;

	@Id
	@Size(max = 50)
    @Column(name = "ID")
    private String id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false, 
    		referencedColumnName = "ID")
    private Order order;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false, 
    		referencedColumnName = "CODE")
    private Product product;
 
    @NotNull
    @Column(name = "QUANITY")
    private int quanity;
    
    @NotNull
    @Column(name = "PRICE")
    private double price;
 
    @NotNull
    @Column(name = "AMOUNT")
    private double amount;
}
