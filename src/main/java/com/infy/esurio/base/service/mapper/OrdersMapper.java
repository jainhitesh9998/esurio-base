package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring", uses = {EsuriitsMapper.class, OutletsMapper.class, AttendantsMapper.class})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {

    @Mapping(source = "esuriit.id", target = "esuriitId")
    @Mapping(source = "esuriit.identifier", target = "esuriitIdentifier")
    @Mapping(source = "outlet.id", target = "outletId")
    @Mapping(source = "outlet.identifier", target = "outletIdentifier")
    @Mapping(source = "attendant.id", target = "attendantId")
    @Mapping(source = "attendant.identifier", target = "attendantIdentifier")
    OrdersDTO toDto(Orders orders);

    @Mapping(source = "esuriitId", target = "esuriit")
    @Mapping(source = "outletId", target = "outlet")
    @Mapping(source = "attendantId", target = "attendant")
    Orders toEntity(OrdersDTO ordersDTO);

    default Orders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orders orders = new Orders();
        orders.setId(id);
        return orders;
    }
}
