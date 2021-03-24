package pl.edu.wszib.warehouse.services;

import pl.edu.wszib.warehouse.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(int id);
    Product getProductById(int id);
    boolean addNewProduct(Product product);
    boolean updateProductQuantity(int id, int quantity);
}
