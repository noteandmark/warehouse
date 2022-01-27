package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcProductDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.ProductDTO;
import com.foxminded.andreimarkov.warehouse.model.Product;
import com.foxminded.andreimarkov.warehouse.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
        Product product = mapper.map(productDTO, Product.class);
        return mapper.map(productDAO.save(product), ProductDTO.class);
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> all = productDAO.findAll();
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<ProductDTO> getById(long id) {
        Product productById = productDAO.getById(id).get();
        ProductDTO locationDTO = mapper.map(productById, ProductDTO.class);
        return Optional.ofNullable(locationDTO);
    }

    @Override
    public ProductDTO update(ProductDTO locationDTO) {
        Product updated = productDAO.update(mapper.map(locationDTO, Product.class));
        return mapper.map(updated, ProductDTO.class);
    }

    @Override
    public int delete(long id) {
        return productDAO.delete(id);
    }

    private List<ProductDTO> mapListOfEntityToDTO(List<Product> all) {
        return all.stream().map(location -> mapper.map(location,ProductDTO.class))
                .collect(Collectors.toList());
    }
}
