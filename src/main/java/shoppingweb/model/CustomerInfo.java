package shoppingweb.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CustomerInfo {
	
	@NotNull(message="Name is required")
	private String name;
	
	@NotNull(message="Address is required")
    private String address;
	
	@NotNull(message="Email is required")
	@Email(message="Invalid email")
    private String email;
	
	@NotNull(message="Phone is required")
	@Digits(integer=10, fraction=0, message="Invalid phone number")
    private String phone;
    
	public CustomerInfo() {
		
	}
	
	
}
