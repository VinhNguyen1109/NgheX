package com.nghex.exe202.controller;

import com.nghex.exe202.entity.*;
import com.nghex.exe202.exception.OrderException;
import com.nghex.exe202.exception.SellerException;
import com.nghex.exe202.exception.UserException;
import com.nghex.exe202.repository.PaymentOrderRepository;
import com.nghex.exe202.response.PaymentLinkResponse;
import com.nghex.exe202.service.*;
import com.nghex.exe202.util.enums.PaymentMethod;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {
	
	private final OrderService orderService;
	private final UserService userService;
	private final OrderItemService orderItemService;
	private final CartService cartService;
	private final PaymentService paymentService;
	private final PaymentOrderRepository paymentOrderRepository;
	private final SellerReportService sellerReportService;
	private final SellerService sellerService;

	private final PayOS payOS;



	@PostMapping("/create")
	public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> body) {
		try {
			String productName = (String) body.get("productName");
			String description = (String) body.get("description");
			int price = (int) body.get("price");
			String returnUrl = (String) body.get("returnUrl");
			String cancelUrl = (String) body.get("cancelUrl");

			long orderCode = System.currentTimeMillis() % 1_000_000;
			ItemData item = ItemData.builder().name(productName).quantity(1).price(price).build();

			PaymentData paymentData = PaymentData.builder()
					.orderCode(orderCode)
					.amount(price)
					.description(description)
					.returnUrl(returnUrl)
					.cancelUrl(cancelUrl)
					.item(item)
					.build();

			CheckoutResponseData data = payOS.createPaymentLink(paymentData);
			return ResponseEntity.ok(Map.of("checkoutUrl", data.getCheckoutUrl(), "orderCode", orderCode));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrder(@PathVariable long orderId) {
		try {
			var info = payOS.getPaymentLinkInformation(orderId);
			return ResponseEntity.ok(info);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", e.getMessage()));
		}
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<?> cancelOrder(@PathVariable long orderId) {
		try {
			var result = payOS.cancelPaymentLink(orderId, null);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping
	public ResponseEntity<List<Map<String, Object>>> listMockOrders() {
		List<Map<String, Object>> orders = List.of(
				Map.of("orderCode", 111111, "description", "Gói tháng 6", "amount", 100000, "status", "Đã thanh toán"),
				Map.of("orderCode", 222222, "description", "Gói tháng 7", "amount", 200000, "status", "Đang xử lý")
		);
		return ResponseEntity.ok(orders);
	}



	@PostMapping()
	public ResponseEntity<PaymentLinkResponse> createOrderHandler(
			@RequestBody Address spippingAddress,
			@RequestParam PaymentMethod paymentMethod,
			@RequestHeader("Authorization")String jwt)
            throws UserException, RazorpayException, StripeException {
		
		User user=userService.findUserProfileByJwt(jwt);
		Cart cart=cartService.findUserCart(user);
		Set<Order> orders =orderService.createOrder(user, spippingAddress,cart);

		PaymentOrder paymentOrder=paymentService.createOrder(user,orders);

		PaymentLinkResponse res = new PaymentLinkResponse();

		if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
			PaymentLink payment=paymentService.createRazorpayPaymentLink(user,
					paymentOrder.getAmount(),
					paymentOrder.getId());
			String paymentUrl=payment.get("short_url");
			String paymentUrlId=payment.get("id");


			res.setPayment_link_url(paymentUrl);
//			res.setPayment_link_id(paymentUrlId);
			paymentOrder.setPaymentLinkId(paymentUrlId);
			paymentOrderRepository.save(paymentOrder);
		}
		else{
			String paymentUrl=paymentService.createStripePaymentLink(user,
					paymentOrder.getAmount(),
					paymentOrder.getId());
			res.setPayment_link_url(paymentUrl);
		}
		return new ResponseEntity<>(res,HttpStatus.OK);

	}
	
	@GetMapping("/user")
	public ResponseEntity< List<Order>> usersOrderHistoryHandler(
			@RequestHeader("Authorization")
	String jwt) throws UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		List<Order> orders=orderService.usersOrderHistory(user.getId());
		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
	}
	
//	@GetMapping("/{orderId}")
//	public ResponseEntity< Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization")
//	String jwt) throws OrderException, UserException{
//
//		User user = userService.findUserProfileByJwt(jwt);
//		Order orders=orderService.findOrderById(orderId);
//		return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
//	}

	@GetMapping("/item/{orderItemId}")
	public ResponseEntity<OrderItem> getOrderItemById(
			@PathVariable Long orderItemId, @RequestHeader("Authorization")
	String jwt) throws Exception {
		System.out.println("------- controller ");
		User user = userService.findUserProfileByJwt(jwt);
		OrderItem orderItem=orderItemService.getOrderItemById(orderItemId);
		return new ResponseEntity<>(orderItem,HttpStatus.ACCEPTED);
	}

	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancelOrder(
			@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt
	) throws UserException, OrderException, SellerException {
		User user=userService.findUserProfileByJwt(jwt);
		Order order=orderService.cancelOrder(orderId,user);

		Seller seller= sellerService.getSellerById(order.getSellerId());
		SellerReport report=sellerReportService.getSellerReport(seller);

		report.setCanceledOrders(report.getCanceledOrders()+1);
		report.setTotalRefunds(report.getTotalRefunds()+order.getTotalSellingPrice());
		sellerReportService.updateSellerReport(report);

		return ResponseEntity.ok(order);
	}

}
