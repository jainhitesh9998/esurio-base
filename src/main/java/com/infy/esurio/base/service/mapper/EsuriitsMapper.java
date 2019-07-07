package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.EsuriitsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Esuriits} and its DTO {@link EsuriitsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CentersMapper.class})
public interface EsuriitsMapper extends EntityMapper<EsuriitsDTO, Esuriits> {

    @Mapping(source = "center.id", target = "centerId")
    @Mapping(source = "center.identifier", target = "centerIdentifier")
    EsuriitsDTO toDto(Esuriits esuriits);

    @Mapping(source = "centerId", target = "center")
    Esuriits toEntity(EsuriitsDTO esuriitsDTO);

    default Esuriits fromId(Long id) {
        if (id == null) {
            return null;
        }
        Esuriits esuriits = new Esuriits();
        esuriits.setId(id);
        return esuriits;
    }
}
