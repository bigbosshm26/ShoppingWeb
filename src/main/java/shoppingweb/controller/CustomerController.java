package shoppingweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import shoppingweb.model.CartInfo;
import shoppingweb.model.CustomerInfo;
import shoppingweb.service.OrderService;

@SessionAttributes("myCart")
@Controller
public class CustomerController {
	
	@Autowired
	private OrderService orderService;
	
    // GET: Nhập thông tin khách hàng.
	@GetMapping("/shoppingCartCustomer")
    public String showShoppingCartCustomerForm(Model model,
    		@ModelAttribute("myCart") CartInfo myCart) {
 
		System.out.println(myCart.getCartLines());
 
        if (myCart.isEmpty()) {
            return "redirect:/shoppingCart";
        }
        CustomerInfo customerInfo = myCart.getCustomerInfo();
 
        System.out.println(customerInfo);
        model.addAttribute("customerInfo", customerInfo);
 
        return "shoppingCartCustomer";
    }
	
	// POST: Save thông tin khách hàng.
	@PostMapping("/shoppingCartCustomer")
	public String saveShoppingCartCustomer(@ModelAttribute("myCart") CartInfo myCart,
			@ModelAttribute("customerInfo") @Valid CustomerInfo customerInfo, Errors errors) {
        if(errors.hasErrors()) {
        	return "shoppingCartCustomer";
        }
        
        System.out.println(customerInfo);
        myCart.setCustomerInfo(customerInfo);
        
		return "redirect:/shoppingCartConfirmation";
	}
	
	// GET: Xem lại thông tin để xác nhận.
    @GetMapping("/shoppingCartConfirmation")
    public String shoppingCartConfirmationReview(Model model,
    		@ModelAttribute("myCart") CartInfo myCart) {
        
 
        if (myCart == null || myCart.isEmpty()) {
 
            return "redirect:/shoppingCart";
        } else if (myCart.getCustomerInfo() == null) {
 
            return "redirect:/shoppingCartCustomer";
        }
 
        return "shoppingCartConfirmation";
    }
    
    // POST: Gửi đơn hàng (Save).
    @PostMapping("/shoppingCartConfirmation")
    public String shoppingCartConfirmationSave( Model model,
    		@ModelAttribute("myCart") CartInfo myCart) {
    	
    	if(myCart.isEmpty()) {
    		
    		return "redirect:/shoppingCart";
    	} else if (myCart.getCustomerInfo() == null) {
 
            return "redirect:/shoppingCartCustomer";
        }
    	orderService.saveOrder(myCart);
    	
  	
        return "redirect:/shoppingCartFinalize";
    }
    @GetMapping("/shoppingCartFinalize")
    public String shoppingCartFinalize(HttpServletRequest request, 
    		@ModelAttribute("myCart") CartInfo myCart, Model model) {
    	
    	if(myCart == null) {
    		return "redirect:/shoppingCart";
    	}
    	
    	model.addAttribute("lastOrderedCart", myCart);

    	//xóa myCart ra khỏi session
    	request.getSession().removeAttribute("myCart");
    	
    	
    	return "shoppingCartFinalize";
    }
        
}
