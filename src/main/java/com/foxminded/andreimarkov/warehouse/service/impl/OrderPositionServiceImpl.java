package com.foxminded.andreimarkov.warehouse.service.impl;

import com.foxminded.andreimarkov.warehouse.dao.impl.JdbcOrderPositionDAOImpl;
import com.foxminded.andreimarkov.warehouse.dto.OrderPositionDTO;
import com.foxminded.andreimarkov.warehouse.exceptions.ServiceException;
import com.foxminded.andreimarkov.warehouse.model.OrderPosition;
import com.foxminded.andreimarkov.warehouse.service.OrderPositionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.debug("start method save OrderPositionDTO");
        OrderPosition orderPosition = mapper.map(orderPositionDTO, OrderPosition.class);
        log.debug("saved orderPosition id {}", orderPosition.getId());
        return mapper.map(orderPositionDAO.save(orderPosition), OrderPositionDTO.class);
    }

    @Override
    public List<OrderPositionDTO> findAll() {
        log.debug("getting findAll in orderPositionService");
        List<OrderPosition> all = orderPositionDAO.findAll();
        if (all.isEmpty()) {
            log.warn("there are no any orderPosition");
            throw new ServiceException("OrderPosition is empty");
        }
        return mapListOfEntityToDTO(all);
    }

    @Override
    public Optional<OrderPositionDTO> getById(long id) {
        log.debug("getting orderPosition by id {}", id);
        Optional<OrderPosition> optionalOrderPosition = orderPositionDAO.getById(id);
        log.debug("get optional value");
        OrderPosition orderPositionById;
        if (!optionalOrderPosition.isPresent()) {
            log.warn("no orderPosition with id {}",id);
            throw new ServiceException("No orderPosition with this id");
        }
        orderPositionById = optionalOrderPosition.get();
        OrderPositionDTO orderPositionDTO = mapper.map(orderPositionById, OrderPositionDTO.class);
        log.debug("get orderPositionDTO");
        return Optional.ofNullable(orderPositionDTO);
    }

    @Override
    public OrderPositionDTO update(OrderPositionDTO orderPositionDTO) {
        log.debug("starting update orderPositionDTO");
        OrderPosition updated = orderPositionDAO.update(mapper.map(orderPositionDTO, OrderPosition.class));
        log.debug("updated orderPositionDTO");
        return mapper.map(updated, OrderPositionDTO.class);
    }

    @Override
    public int delete(long id) {
        log.debug("starting delete orderPosition with id {}",id);
        return orderPositionDAO.delete(id);
    }

    private List<OrderPositionDTO> mapListOfEntityToDTO(List<OrderPosition> all) {
        log.debug("start mapping List<OrderPosition> in List<OrderPositionDTO>");
        return all.stream().map(orderPosition -> mapper.map(orderPosition,OrderPositionDTO.class))
                .collect(Collectors.toList());
    }
}
