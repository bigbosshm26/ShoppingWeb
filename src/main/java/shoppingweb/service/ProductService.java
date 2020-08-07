package shoppingweb.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shoppingweb.entity.Product;
import shoppingweb.form.ProductForm;
import shoppingweb.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepo;
	
	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}
	
	public Product findProductByCode(String code) {
		return productRepo.findByCode(code).orElse(null);
	}
	
	public void saveProduct(ProductForm productForm) {

		String code = productForm.getCode();
		Product product = null;
		
		if(code !=null) {
			product = productRepo.findByCode(code).orElse(null);
		}
		
		if(product == null) {
			product = new Product();
	        product.setDateCreated(new Date());	
	
		}	        
		product.setCode(code);
		product.setName(productForm.getName());
		product.setPrice(productForm.getPrice());	
		
		if(productForm.getFileData()!=null) {
			byte[] image = null;
			try {
				image = productForm.getFileData().getBytes();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			if (image != null && image.length > 0) {
                product.setImage(image);
            }
		}

		productRepo.save(product);
		
	}
	
	public Product updateProduct(Product product) {
		return productRepo.save(product);
	} 
	
	public void deleteProduct(Product product) {
		productRepo.delete(product);
	} 
}
