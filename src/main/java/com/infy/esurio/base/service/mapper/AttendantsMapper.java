package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.AttendantsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendants} and its DTO {@link AttendantsDTO}.
 */
@Mapper(componentModel = "spring", uses = {OutletsMapper.class})
public interface AttendantsMapper extends EntityMapper<AttendantsDTO, Attendants> {

    @Mapping(source = "outlet.id", target = "outletId")
    @Mapping(source = "outlet.identifier", target = "outletIdentifier")
    AttendantsDTO toDto(Attendants attendants);

    @Mapping(source = "outletId", target = "outlet")
    Attendants toEntity(AttendantsDTO attendantsDTO);

    default Attendants fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attendants attendants = new Attendants();
        attendants.setId(id);
        return attendants;
    }
}
