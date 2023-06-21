package ua.chistikov.OnlineShopServer.facade;


import org.springframework.stereotype.Component;
import ua.chistikov.OnlineShopServer.dto.ProductDTO;
import ua.chistikov.OnlineShopServer.entity.Product;

@Component
public class ProductFacade {

    public ProductDTO productToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setDescription(product.getDescription());

        return productDTO;
    }

}
