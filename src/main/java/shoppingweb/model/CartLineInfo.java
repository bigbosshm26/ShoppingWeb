package shoppingweb.model;

import lombok.Data;

@Data
public class CartLineInfo {

	private ProductInfo productInfo;
	private int quantity;
	
	public double getAmount() {
		return this.productInfo.getPrice() * this.quantity;
	}
}
