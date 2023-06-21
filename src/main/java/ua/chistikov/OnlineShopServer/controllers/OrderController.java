package ua.chistikov.OnlineShopServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.chistikov.OnlineShopServer.dto.OrderDTO;
import ua.chistikov.OnlineShopServer.dto.OrderItemDTO;
import ua.chistikov.OnlineShopServer.entity.Order;
import ua.chistikov.OnlineShopServer.entity.OrderItem;
import ua.chistikov.OnlineShopServer.entity.User;
import ua.chistikov.OnlineShopServer.facade.OrderFacade;
import ua.chistikov.OnlineShopServer.facade.OrderItemFacade;
import ua.chistikov.OnlineShopServer.payload.response.MessageResponse;
import ua.chistikov.OnlineShopServer.services.OrderItemService;
import ua.chistikov.OnlineShopServer.services.OrderService;
import ua.chistikov.OnlineShopServer.services.UserService;
import ua.chistikov.OnlineShopServer.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/order")
@CrossOrigin()
public class OrderController {
    private final OrderService orderService;
    private final OrderFacade orderFacade;
    private final ResponseErrorValidation responseErrorValidation;
    private final OrderItemFacade orderItemFacade;
    private final OrderItemService orderItemService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, OrderFacade orderFacade, ResponseErrorValidation responseErrorValidation, OrderItemFacade orderItemFacade, OrderItemService orderItemService, UserService userService) {
        this.orderService = orderService;
        this.orderFacade = orderFacade;
        this.responseErrorValidation = responseErrorValidation;
        this.orderItemFacade = orderItemFacade;
        this.orderItemService = orderItemService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDTO>> getAllOrder() {
        List<OrderDTO> orderDTOList = orderService.getAllOrder()
                .stream()
                .map(orderFacade::orderToOrderDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/delete")
    public ResponseEntity<MessageResponse> deleteOrder(@PathVariable("orderId") String orderId) {
        orderService.deleteOrder(Long.parseLong(orderId));
        return new ResponseEntity<>(new MessageResponse("order was deleted"), HttpStatus.OK);
    }

    @PostMapping("/{orderId}/delete/{productId}")
    public ResponseEntity<MessageResponse> deleteOrderItem(@PathVariable("orderId") String orderId, @PathVariable String productId,
                                                           @Valid @RequestBody OrderItemDTO orderItemDTO) {
        orderItemService.deleteOrderItem(orderItemDTO);
        return new ResponseEntity<>(new MessageResponse("order was deleted"), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderDTO orderDTO,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Order order = orderService.createOrder(orderDTO, principal);
        OrderDTO createdOrder = orderFacade.orderToOrderDTO(order);

        return new ResponseEntity<>(createdOrder, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/update")
    public ResponseEntity<Object> updateOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult, Principal principal, @PathVariable String orderId) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        User user = userService.getCurrentUser(principal);
        orderDTO.setUserID(user);
        Order order = orderService.updateOrder(orderDTO, Long.parseLong(orderId));
        OrderDTO updatedOrder  = orderFacade.orderToOrderDTO(order);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItemByOrder(@PathVariable String orderId){
        List<OrderItemDTO> orderItemDTO = orderItemService.getAllOrderItemByOrder(orderService.getOrderById(Long.parseLong(orderId)))
                .stream()
                .map(orderItemFacade::orderItemToOrderItemDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(orderItemDTO, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/updateItem")
    public ResponseEntity<Object> updateOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO, BindingResult bindingResult, @PathVariable String orderId){
        System.out.println("test update");
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        OrderItem orderItem = orderItemService.updateOrderItem(Long.parseLong(orderId), orderItemDTO);

        OrderItemDTO updatedOrderItem  = orderItemFacade.orderItemToOrderItemDTO(orderItem);
        return new ResponseEntity<>(updatedOrderItem, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/deleteItem")
    public ResponseEntity<Object> deleteOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO, BindingResult bindingResult, @PathVariable String orderId){
        orderItemService.deleteOrderItem(orderItemDTO);
        return new ResponseEntity<>(new MessageResponse("order was deleted"), HttpStatus.OK);
    }
}