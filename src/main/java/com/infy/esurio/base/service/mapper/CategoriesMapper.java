package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.CategoriesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categories} and its DTO {@link CategoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ItemsMapper.class, TagsMapper.class})
public interface CategoriesMapper extends EntityMapper<CategoriesDTO, Categories> {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.identifier", target = "itemIdentifier")
    @Mapping(source = "tag.id", target = "tagId")
    @Mapping(source = "tag.identifier", target = "tagIdentifier")
    CategoriesDTO toDto(Categories categories);

    @Mapping(source = "itemId", target = "item")
    @Mapping(source = "tagId", target = "tag")
    Categories toEntity(CategoriesDTO categoriesDTO);

    default Categories fromId(Long id) {
        if (id == null) {
            return null;
        }
        Categories categories = new Categories();
        categories.setId(id);
        return categories;
    }
}
