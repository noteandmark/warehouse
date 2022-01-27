package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcOrderPositionDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.OrderPositionDTO;
import com.foxminded.andreimarkov.warehouse.model.OrderPosition;
import com.foxminded.andreimarkov.warehouse.service.OrderPositionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderPositionServiceImpl implements OrderPositionService {
    private final JdbcOrderPositionDAOImpl orderPositionDAO;
    private final ModelMapper mapper;

    @Autowired
    public OrderPositionServiceImpl(JdbcOrderPositionDAOImpl orderPositionDAO, ModelMapper modelMapper) {
        this.orderPositionDAO = orderPositionDAO;
        this.mapper = modelMapper;
    }

    @Override
    public OrderPositionDTO save(OrderPositionDTO orderPositionDTO) {
        OrderPosition orderPosition = mapper.map(orderPositionDTO, OrderPosition.class);
        return mapper.map(orderPositionDAO.save(orderPosition), OrderPositionDTO.class);
    }

    @Override
    public List<OrderPositionDTO> findAll() {
        List<OrderPosition> all = orderPositionDAO.findAll();
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<OrderPositionDTO> getById(long id) {
        OrderPosition orderPositionById = orderPositionDAO.getById(id).get();
        OrderPositionDTO orderPositionDTO = mapper.map(orderPositionById, OrderPositionDTO.class);
        return Optional.ofNullable(orderPositionDTO);
    }

    @Override
    public OrderPositionDTO update(OrderPositionDTO orderPositionDTO) {
        OrderPosition updated = orderPositionDAO.update(mapper.map(orderPositionDTO, OrderPosition.class));
        return mapper.map(updated, OrderPositionDTO.class);
    }

    @Override
    public int delete(long id) {
        return orderPositionDAO.delete(id);
    }

    private List<OrderPositionDTO> mapListOfEntityToDTO(List<OrderPosition> all) {
        return all.stream().map(orderPosition -> mapper.map(orderPosition,OrderPositionDTO.class))
                .collect(Collectors.toList());
    }
}
