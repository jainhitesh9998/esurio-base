package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.ServingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Servings} and its DTO {@link ServingsDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrdersMapper.class, DishesMapper.class, AttendantsMapper.class})
public interface ServingsMapper extends EntityMapper<ServingsDTO, Servings> {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "order.identifier", target = "orderIdentifier")
    @Mapping(source = "dish.id", target = "dishId")
    @Mapping(source = "dish.identifier", target = "dishIdentifier")
    @Mapping(source = "attendant.id", target = "attendantId")
    @Mapping(source = "attendant.identifier", target = "attendantIdentifier")
    ServingsDTO toDto(Servings servings);

    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "dishId", target = "dish")
    @Mapping(source = "attendantId", target = "attendant")
    Servings toEntity(ServingsDTO servingsDTO);

    default Servings fromId(Long id) {
        if (id == null) {
            return null;
        }
        Servings servings = new Servings();
        servings.setId(id);
        return servings;
    }
}
