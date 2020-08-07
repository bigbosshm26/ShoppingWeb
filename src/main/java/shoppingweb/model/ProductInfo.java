package shoppingweb.model;


import lombok.Data;
import shoppingweb.entity.Product;

@Data
public class ProductInfo {

	private String code;
	
	private String name;
	
	private double price;
	
	public ProductInfo() {
		
	}
	
	public ProductInfo(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
	
	public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
