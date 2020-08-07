package shoppingweb.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import shoppingweb.entity.Product;
import shoppingweb.model.CartInfo;
import shoppingweb.model.ProductInfo;
import shoppingweb.service.ProductService;

@SessionAttributes("myCart")
@Controller
public class ProductController {

	@Autowired
	ProductService productService;
	
	@ModelAttribute(name = "myCart")
	public CartInfo cartInfo() {
		return new CartInfo();
	}
	@GetMapping("/")
    public String home() {
        return "index";
    }
	
	@GetMapping("/403")
    public String accessDenied() {
        return "/403";
    }
	
	@GetMapping("/productList")
	public String showListOfProducts(HttpServletRequest request, Model model) {
		
		request.getSession().setAttribute("products", null);
		List<Product> listOfProducts = productService.findAllProducts();
		model.addAttribute("products",listOfProducts);
		
		return "redirect:/productList/page/1";
	}
	
	@GetMapping("/productList/page/{pageNumber}")
	public String showListOfProductsPage(HttpServletRequest request,
			Model model, @PathVariable int pageNumber) {
		
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("products");
		int pageSize = 3;
		List<Product> list = productService.findAllProducts();
		if(pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pageSize);
		}else {
			final int goToPage = pageNumber - 1;
			if(goToPage<= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("products",pages);
		int current = pages.getPage() + 1;	
		int begin = Math.max(1, current - list.size());	
		int end = Math.min(begin + 5, pages.getPageCount());	
		int totalPageCount = pages.getPageCount();	
		String baseUrl = "/productList/page/";	
		
		model.addAttribute("beginIndex", begin);	
		model.addAttribute("endIndex", end);	
		model.addAttribute("currentIndex", current);	
		model.addAttribute("totalPageCount", totalPageCount);	
		model.addAttribute("baseUrl", baseUrl);	
		model.addAttribute("products", pages);
		
		return "productList";
	}
	
	@GetMapping("/productImage")
    public void productImage(HttpServletResponse response,
    		@RequestParam("code") String code) throws IOException {
        Product product = null;
        if (code != null) {
            product = this.productService.findProductByCode(code);
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
	
	// POST: Thêm 1 sản phẩm (số lượng của sản phẩm đó tăng 1) vào giỏ hàng
	@GetMapping("/buyProduct")
	public String buyProduct(@RequestParam String code,
			@ModelAttribute("myCart") CartInfo myCart) {
		
		Product product = null;
		if(code != null && code.length() > 0) {
			product = productService.findProductByCode(code);
		}
		if(product!= null) {
			
			ProductInfo productInfo = new ProductInfo(product);
		
			myCart.addProduct(productInfo, 1);
		}
		
		return "redirect:/shoppingCart";
	}
	
	// GET: Hiển thị giỏ hàng.
	@GetMapping("/shoppingCart")
	public String showShoppingCart(Model model,
			@ModelAttribute("myCart") CartInfo myCart) {
		
		model.addAttribute("cartInfo", myCart);
		return "shoppingCart";
	}
	
    // POST: Cập nhập số lượng cho các sản phẩm đã mua.
	@PostMapping("/shoppingCart")
	public String updateShoppingCart(
			@ModelAttribute("myCart") CartInfo myCart, 
			@ModelAttribute("cartInfo") CartInfo newCart) {
		
		System.out.println(myCart.getCartLines());
		System.out.println(newCart.getCartLines());
        myCart.updateQuantity(newCart);
 
        return "redirect:/shoppingCart";
    }
	// DELETE: Xóa 1 sản phẩm
	@GetMapping("/shoppingCartRemoveProduct")
    public String removeProduct(@ModelAttribute("myCart") CartInfo myCart,
            @RequestParam("code") String code) {
        Product product = null;
        if (code != null && code.length() > 0) {
            product = productService.findProductByCode(code);
        }
        if (product != null) {
 
            ProductInfo productInfo = new ProductInfo(product);
            myCart.removeProduct(productInfo);
        }
 
        return "redirect:/shoppingCart";
    }
	
	
}
