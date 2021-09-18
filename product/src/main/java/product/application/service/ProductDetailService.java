package product.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.application.domain.ProductDetail;
import product.exception.ProductFailedException;
import product.data.ProductDetailRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;

    @Autowired
    public ProductDetailService(ProductDetailRepository productDetailRepository){
        this.productDetailRepository = productDetailRepository;
    }

    public List<ProductDetail> getAllProductDetails(){
        return productDetailRepository.findAll();
    }

    public ProductDetail findProductDetailById(long id){
        return productDetailRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cannot find the product detail" +
                        "with the id " + id));
    }

    public void addProductDetail(ProductDetail productDetail){
        productDetailRepository.save(productDetail);
    }

    public void updateProductDetail(long id, String description, String comment){
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow( ()-> new ProductFailedException("Cannot find the product description of id: " + id));

        if (description != null && description.length() > 0){
            productDetail.setDescription(description);
        }

        if (comment != null && comment.length() > 0){
            productDetail.setComment(comment);
        }
    }

}
