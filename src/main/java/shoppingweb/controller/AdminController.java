package shoppingweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shoppingweb.entity.OrderDetail;
import shoppingweb.entity.Product;
import shoppingweb.form.ProductForm;
import shoppingweb.model.OrderDetailInfo;
import shoppingweb.model.OrderInfo;
import shoppingweb.repository.OrderDetailRepository;
import shoppingweb.repository.OrderRepository;
import shoppingweb.service.ProductService;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	@GetMapping("/accountInfo")
    public String accountInfo(HttpServletRequest request,Model model) {
 		
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());
 
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }
	
	@GetMapping("/product")
    public String showProductForm(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductForm productForm = null;
       
        if (code != null && code.length() > 0) {
            Product product = productService.findProductByCode(code);
            if (product != null) {
                productForm = new ProductForm(product);
            }
        }   
        if (productForm == null) {
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }
        
        model.addAttribute("productForm", productForm);
        return "product";
    }
	
	@PostMapping("/product")
	public String saveNewProduct(@ModelAttribute("productForm") @Valid ProductForm productForm,
			Errors errors, Model model) {
		System.out.println(productForm);
		if(errors.hasErrors()) {
			return "product";
		}	
				
		try {
            productService.saveProduct(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "product";
        }
		
        return "redirect:/productList";
	}
	
	@GetMapping("/orderList")
	public String showOrderList(@RequestParam(value = "page", defaultValue = "1") String pageStr,
			Model model) {
		
		int amountOfOrders = orderRepo.getMaxOrderNum(); 
		
        int page = 1;
        int sizeOfPage = 3;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        
        int countPage = (int)Math.ceil((orderRepo.count())/3f);
        int[] pages = new int[countPage];
        for(int i = 0; i<= countPage-1; i++) {
        	pages[i] = i+1;
        }
        
        Pageable pageable = PageRequest.of(page - 1, sizeOfPage);
        
        
        model.addAttribute("pages",pages);
        model.addAttribute("amountOfOrders", amountOfOrders);
        model.addAttribute("orders", 
        		orderRepo.findAllByOrderByOrderNumAsc(pageable));
		
		return "orderList";
	}
	
	@GetMapping("/order")
	public String showOrder(Model model, @RequestParam("orderId") String orderId) {
		
		OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = new OrderInfo(orderRepo.findById(orderId).orElse(null));
        }
        if (orderInfo == null) {
            return "redirect:/admin/orderList";
        }
        List<OrderDetail> details = orderDetailRepo.findByOrderId(orderId);
        
        List<OrderDetailInfo> detailInfos = new ArrayList<OrderDetailInfo>();
        
        for(OrderDetail detail : details) {
        	detailInfos.add(new OrderDetailInfo(detail));
        }
        orderInfo.setDetails(detailInfos);
 
        model.addAttribute("orderInfo", orderInfo);
 
        return "order";
	}
	
	
	
	
}
