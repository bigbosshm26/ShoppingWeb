package shoppingweb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity(name = "orders")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "ORDER_NUM") })
public class Order implements Serializable{

	private static final long serialVersionUID = 8507100153420989120L;

	@Id
	private String id;
	
	@NotNull()
	@Column(name = "ORDER_DATE")
	private Date orderDate;
	
	@NotNull
	@Column(name = "ORDER_NUM")
	private int orderNum;
	
	@NotNull
	@Column(name = "AMOUNT")
	private double amount;

	@Size(max = 255)
	@NotNull(message = "Name of customer is required")
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "CUSTOMER_ADDRESS")
	private String customerAddress;
	
	@Size(max = 128)
	@NotNull
	@Column(name = "CUSTOMER_EMAIL")
    private String customerEmail;
	
	@Size(max = 128)
	@NotNull
	@Column(name = "CUSTOMER_PHONE")
    private String customerPhone;

}
