package ua.chistikov.OnlineShopServer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.chistikov.OnlineShopServer.dto.ProductDTO;
import ua.chistikov.OnlineShopServer.entity.Product;
import ua.chistikov.OnlineShopServer.entity.User;
import ua.chistikov.OnlineShopServer.entity.enums.ERole;
import ua.chistikov.OnlineShopServer.exceptions.NotAllowedException;
import ua.chistikov.OnlineShopServer.exceptions.ProductNotFoundException;
import ua.chistikov.OnlineShopServer.repository.ProductRepository;
import ua.chistikov.OnlineShopServer.repository.UserRepository;

import java.security.Principal;
import java.util.List;

@Service
public class ProductService {
    public static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Product createProduct(ProductDTO productDTO, Principal principal) {
        isUserAllowed(principal);
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());

        LOG.info("Saving Product : " + product.getName());
        return productRepository.save(product);
    }

    public Product updateProduct(ProductDTO productDTO, Principal principal, Long id) {
        isUserAllowed(principal);
        Product product = productRepository.findProductById(id).get();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productID) {
        return productRepository.findProductById(productID)
                .orElseThrow(() -> new ProductNotFoundException("Product cannot be found for id: " + productID));
    }


    public void deleteProduct(Long postId, Principal principal) {
        isUserAllowed(principal);
        productRepository.delete(getProductById(postId));
    }

    private User getUserByPrincipal(Principal principal) {
        return userRepository.findUserByUsername(principal.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username " + principal.getName()));
    }

    public void isUserAllowed(Principal principal){
        User user = getUserByPrincipal(principal);
        if (!user.getRoles().contains(ERole.ROLE_ADMIN)) {
            throw new NotAllowedException("You dont have permission to do that.");
        }
    }
}
