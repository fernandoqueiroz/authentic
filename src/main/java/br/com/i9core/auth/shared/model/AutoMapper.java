package br.com.i9core.auth.shared.model;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;

/**
 * Generic Auto-Mapper class for convert entity to DTO and vice-versa
 * @author Fernando Queiroz Fonseca <fernando.fonseca@socialbank.com.br>
 * @since 18/05/2020
 * @version 1.0.0
 */
public abstract class AutoMapper<SOURCE, TARGET> extends ModelMapper {

    /**
     * Convert a DTO to Entity object
     *
     * @param dto DTO object
     * @return converted Entity
     */
    public TARGET to(SOURCE dto) {
        return map(dto, getEntityTypeClass());
    }

    /**
     * Convert a entity object to DTO
     *
     * @param entity Entity E
     * @return object DTO
     */
    public SOURCE from(TARGET entity) {
        return map(entity, getDTOTypeClass());
    }

    /**
     * Get type class for instance DTO
     *
     * @return Class<DTO>
     */
    @SuppressWarnings("unchecked")
    private Class<TARGET> getEntityTypeClass() {
        return (Class<TARGET>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * Get type class for instance DTO
     *
     * @return Class<DTO>
     */
    @SuppressWarnings("unchecked")
    private Class<SOURCE> getDTOTypeClass() {
        return (Class<SOURCE>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
