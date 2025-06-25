package com.nghex.exe202.controller;

import com.nghex.exe202.dto.CartDTO;
import com.nghex.exe202.entity.Cart;
import com.nghex.exe202.entity.CartItem;
import com.nghex.exe202.entity.Product;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.exception.CartItemException;
import com.nghex.exe202.exception.ProductException;
import com.nghex.exe202.exception.UserException;
import com.nghex.exe202.request.AddItemRequest;
import com.nghex.exe202.response.ApiResponse;
import com.nghex.exe202.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;
	private final UserService userService;
	private final ProductService productService;
	private final CartItemService cartItemService;


	@GetMapping("/fetchUserCart")
	public ResponseEntity<CartDTO> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user);
		CartDTO dto = CartMapper.toDTO(cart);
		return ResponseEntity.ok(dto);
	}


	@PutMapping("/add")
	public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req,
												  @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user=userService.findUserProfileByJwt(jwt);
		System.out.println(user.getEmail());
		Product product=productService.findProductById(req.getProductId());
		System.out.println(product.getId());
		CartItem item = cartService.addCartItem(user,
				product,
				req.getSize(),
				req.getQuantity());
		
		return new ResponseEntity<>(item,HttpStatus.ACCEPTED);
		
	}

	@DeleteMapping("/item/{cartItemId}")
	public ResponseEntity<ApiResponse>deleteCartItemHandler(
			@PathVariable Long cartItemId,
			@RequestHeader("Authorization")String jwt)
			throws CartItemException, UserException{

		User user=userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);

		ApiResponse res=new ApiResponse("Item Remove From Cart",true);

		return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
	}

	@PutMapping("/item/{cartItemId}")
	public ResponseEntity<CartItem>updateCartItemHandler(
			@PathVariable Long cartItemId,
			@RequestBody CartItem cartItem,
			@RequestHeader("Authorization")String jwt)
			throws CartItemException, UserException{

		User user=userService.findUserProfileByJwt(jwt);

		CartItem updatedCartItem = null;
		if(cartItem.getQuantity()>0){
			updatedCartItem=cartItemService.updateCartItem(user.getId(),
					cartItemId, cartItem);
		}
	
		return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
	}
	

}
