package shoppingweb.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import shoppingweb.entity.Product;

@Data
public class ProductForm {

	@NotBlank(message = "Code must not be blank")
	@Size(max = 20,
		message = "Code must not be more than 20 characters long")
	private String code;
	
	@NotBlank(message = "Name must not be blank")
	@Size(max = 255,
		message = "Name must not be more than 255 characters long")
    private String name;

    private double price;
	
    private MultipartFile fileData;
    
    private boolean newProduct = false;
    
    public ProductForm() {
        this.newProduct= true;

    }
    
    public ProductForm(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }
    
    
}
