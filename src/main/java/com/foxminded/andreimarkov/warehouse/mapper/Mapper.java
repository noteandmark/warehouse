package com.foxminded.andreimarkov.warehouse.mapper;

import com.foxminded.andreimarkov.warehouse.dto.AbstractDTO;
import com.foxminded.andreimarkov.warehouse.model.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDTO> {

    E toEntity(D dto);

    D toDto(E entity);
}
