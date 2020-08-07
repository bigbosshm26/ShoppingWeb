package shoppingweb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product implements Serializable{
	
	private static final long serialVersionUID = -4149241624297471857L;

	@Id
	@NotNull
	@Size(max = 20)
	@Column(name = "CODE")
	private String code;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "NAME")
	private String name;
	
	@NotNull
	@Column(name = "PRICE")
	private double price;
	
	
	@Lob
	@Column(name = "IMAGE")
	private byte[] image;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(name="CREATE_DATE")
	private Date dateCreated;
}
