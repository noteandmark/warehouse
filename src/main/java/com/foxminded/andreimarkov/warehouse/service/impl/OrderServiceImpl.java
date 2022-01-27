package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcOrderDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.OrderDTO;
import com.foxminded.andreimarkov.warehouse.model.Order;
import com.foxminded.andreimarkov.warehouse.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final JdbcOrderDAOImpl orderDAO;
    private final ModelMapper mapper;

    @Autowired
    public OrderServiceImpl(JdbcOrderDAOImpl orderDAO, ModelMapper mapper) {
        this.orderDAO = orderDAO;
        this.mapper = mapper;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order catalog = mapper.map(orderDTO, Order.class);
        return mapper.map(orderDAO.save(catalog), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findAll() {
        List<Order> all = orderDAO.findAll();
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<OrderDTO> getById(long id) {
        Order catalogById = orderDAO.getById(id).get();
        OrderDTO orderDTO = mapper.map(catalogById, OrderDTO.class);
        return Optional.ofNullable(orderDTO);
    }

    @Override
    public OrderDTO update(OrderDTO catalogDTO) {
        Order updated = orderDAO.update(mapper.map(catalogDTO, Order.class));
        return mapper.map(updated, OrderDTO.class);
    }

    @Override
    public int delete(long id) {
        return orderDAO.delete(id);
    }

    private List<OrderDTO> mapListOfEntityToDTO(List<Order> all) {
        return all.stream().map(order -> mapper.map(order,OrderDTO.class))
                .collect(Collectors.toList());
    }
}
