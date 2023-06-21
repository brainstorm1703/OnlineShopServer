package ua.chistikov.OnlineShopServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.chistikov.OnlineShopServer.dto.OrderItemDTO;
import ua.chistikov.OnlineShopServer.dto.ProductDTO;
import ua.chistikov.OnlineShopServer.entity.Product;
import ua.chistikov.OnlineShopServer.facade.OrderItemFacade;
import ua.chistikov.OnlineShopServer.facade.ProductFacade;
import ua.chistikov.OnlineShopServer.payload.response.MessageResponse;
import ua.chistikov.OnlineShopServer.services.OrderItemService;
import ua.chistikov.OnlineShopServer.services.OrderService;
import ua.chistikov.OnlineShopServer.services.ProductService;
import ua.chistikov.OnlineShopServer.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/product")
@CrossOrigin()
public class ProductController {
    private final ProductService productService;
    private final ProductFacade productFacade;
    private final ResponseErrorValidation responseErrorValidation;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Autowired
    public ProductController(ProductService productService, ProductFacade productFacade, ResponseErrorValidation responseErrorValidation, OrderService orderService, OrderItemService orderItemService) {
        this.productService = productService;
        this.productFacade = productFacade;
        this.responseErrorValidation = responseErrorValidation;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
        List<ProductDTO> productDTOList = productService.getAllProduct()
                .stream()
                .map(productFacade::productToProductDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }
    @PostMapping("/{productId}/delete")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable("productId") String productId, Principal principal) {
        productService.deleteProduct(Long.parseLong(productId), principal);
        return new ResponseEntity<>(new MessageResponse("product was deleted"), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductDTO productDTO,
                                             BindingResult bindingResult,
                                             Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Product product = productService.createProduct(productDTO, principal);
        ProductDTO createdProduct = productFacade.productToProductDTO(product);

        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }

    @PostMapping("/{productId}/update")
    public ResponseEntity<Object> updateProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult, Principal principal, @PathVariable String productId) {
            ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Product product = productService.updateProduct(productDTO, principal, Long.parseLong(productId));

        ProductDTO updatedProduct  = productFacade.productToProductDTO(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PostMapping("/{productId}/add/{orderId}")
    public ResponseEntity<Object> addItemToOrder(@PathVariable String productId,
                                                 @PathVariable String orderId,
                                                 @Valid @RequestBody OrderItemDTO orderItemDTO,
                                                 BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        orderItemDTO.setOrderID(orderService.getOrderById(Long.parseLong(orderId)));
        orderItemDTO.setProductID(productService.getProductById(Long.parseLong(productId)));
        System.out.println(orderItemDTO.getQuantity());
        orderItemService.createOrderItem(orderItemDTO);

        return new ResponseEntity<>(new MessageResponse("product added to order"), HttpStatus.OK);
    }
}
