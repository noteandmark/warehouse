package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcProductDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.ProductDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.model.Product;
import com.foxminded.andreimarkov.warehouse.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final JdbcProductDAOImpl productDAO;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(JdbcProductDAOImpl productDAO, ModelMapper mapper) {
        this.productDAO = productDAO;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("start method save productDTO");
        Product product = mapper.map(productDTO, Product.class);
        log.debug("saved product id {}", product.getId());
        return mapper.map(productDAO.save(product), ProductDTO.class);
    }

    @Override
    public List<ProductDTO> findAll() {
        log.debug("getting findAll in productService");
        List<Product> all = productDAO.findAll();
        if (all.isEmpty()) {
            log.warn("there are no any product");
            throw new ServiceException("Product is empty");
        }
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<ProductDTO> getById(long id) {
        log.debug("getting product by id {}", id);
        Optional<Product> optionalProduct = productDAO.getById(id);
        log.debug("get optional value");
        Product productById;
        if (!optionalProduct.isPresent()) {
            log.warn("no product with id {}",id);
            throw new ServiceException("No product with this id");
        }
        productById = optionalProduct.get();
        ProductDTO productDTO = mapper.map(productById, ProductDTO.class);
        log.debug("get productDTO");
        return Optional.ofNullable(productDTO);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {
        log.debug("starting update productDTO");
        Product updated = productDAO.update(mapper.map(productDTO, Product.class));
        log.debug("updated productDTO");
        return mapper.map(updated, ProductDTO.class);
    }

    @Override
    public int delete(long id) {
        log.debug("starting delete product with id {}",id);
        return productDAO.delete(id);
    }

    private List<ProductDTO> mapListOfEntityToDTO(List<Product> all) {
        log.debug("start mapping List<Product> in List<ProductDTO>");
        return all.stream().map(product -> mapper.map(product,ProductDTO.class))
                .collect(Collectors.toList());
    }
}
