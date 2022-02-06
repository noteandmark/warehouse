package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcOrderDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.OrderDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.model.Order;
import com.foxminded.andreimarkov.warehouse.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.debug("start method save orderDTO");
        Order order = mapper.map(orderDTO, Order.class);
        log.debug("saved order id {}", order.getId());
        return mapper.map(orderDAO.save(order), OrderDTO.class);
    }

    @Override
    public List<OrderDTO> findAll() {
        log.debug("getting findAll in orderService");
        List<Order> all = orderDAO.findAll();
        if (all.isEmpty()) {
            log.warn("there are no any order");
            throw new ServiceException("Order is empty");
        }
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<OrderDTO> getById(long id) {
        log.debug("getting order by id {}", id);
        Optional<Order> optionalOrder = orderDAO.getById(id);
        log.debug("get optional value");
        Order orderById;
        if (!optionalOrder.isPresent()) {
            log.warn("no order with id {}",id);
            throw new ServiceException("No order with this id");
        }
        orderById = optionalOrder.get();
        OrderDTO orderDTO = mapper.map(orderById, OrderDTO.class);
        log.debug("get orderDTO");
        return Optional.ofNullable(orderDTO);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        log.debug("starting update orderDTO");
        Order updated = orderDAO.update(mapper.map(orderDTO, Order.class));
        log.debug("updated orderDTO");
        return mapper.map(updated, OrderDTO.class);
    }

    @Override
    public int delete(long id) {
        log.debug("starting delete order with id {}",id);
        return orderDAO.delete(id);
    }

    private List<OrderDTO> mapListOfEntityToDTO(List<Order> all) {
        log.debug("start mapping List<Order> in List<OrderDTO>");
        return all.stream().map(order -> mapper.map(order,OrderDTO.class))
                .collect(Collectors.toList());
    }
}
