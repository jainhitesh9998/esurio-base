package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.TagsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tags} and its DTO {@link TagsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TagsMapper extends EntityMapper<TagsDTO, Tags> {



    default Tags fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tags tags = new Tags();
        tags.setId(id);
        return tags;
    }
}
