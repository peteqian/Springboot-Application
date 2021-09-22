package com.productgroup.application.service;

import com.productgroup.application.domain.Product;
import com.productgroup.application.domain.ProductDetail;
import com.productgroup.data.ProductDetailRepository;
import com.productgroup.data.ProductRepository;
import com.productgroup.exception.ProductFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDetailRepository productDetailRepository;
    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductDetailRepository productDetailRepository){
        this.productRepository = productRepository;
        this.productDetailRepository = productDetailRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product findProductById(long id){
        return productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cannot find a product with " +
                        "the id: " + id));
    }

    public Product findProductByName(String productName){
        return productRepository.findProductByName(productName)
                .orElseThrow(()-> new RuntimeException("Cannot find a product with " +
                        "the name: " + productName));
    }

    public void addProduct(Product product){

        Boolean productExist =
                productRepository.findExistingProduct(product.getProductName());
        if(productExist){
            throw new ProductFailedException("A product with this name already exists!");
        }
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(String productName,
                              String productCategory,
                              double price,
                              long stockQuantity){
        Product product = productRepository.findProductByName(productName)
                .orElseThrow( ()-> new ProductFailedException("Product " + productName + " cannot be found."));

        if (productCategory != null && productCategory.length() > 0){
            product.setProductCategory(productCategory);
        }

        if(price >= 0.0){
            product.setPrice(price);
        } else {
            throw new ProductFailedException("Price cannot be below 0.0");
        }

        if(stockQuantity >= 0 && (Long)stockQuantity != null){
            product.setStockQuantity(stockQuantity);
        } else {
            throw new ProductFailedException("Stock Quantity cannot be below 0");
        }
    }

    @Transactional
    public void insertProductDetail(long productId, long productDetailId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Cannot find a product with the id: " + productId));

        ProductDetail productDetail = productDetailRepository.findById(productDetailId)
                .orElseThrow( ()->
                        new ProductFailedException("Cannot find the product description of id: "
                                + productDetailId));

        // If the productDetail has already been assigned to a product.
        if(productDetail.getAssigned() != -1){
            // Throw an error
            throw new ProductFailedException("Already assigned");
        } else {

            // Otherwise, get the productDetail from the current product.
            if(product.getProductDetail() != null){
                System.out.println("The current product's details is not empty");
                // Clear the product detail assignment and set the product to null.
                ProductDetail currentProductDetail = product.getProductDetail();
                currentProductDetail.setAssigned(-1);
                currentProductDetail.setProduct(null);
            }
            // set the Product to new details and assign the productdetail to new product.
            product.setProductDetail(productDetail);
            productDetail.setAssigned(productId);
            System.out.println("Assigned new product details successfully.");
        }


    }

}
