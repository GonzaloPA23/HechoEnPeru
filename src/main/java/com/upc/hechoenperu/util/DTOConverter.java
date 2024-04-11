package com.upc.hechoenperu.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public <T, U> T convertToDto(U entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <T, U> U convertToEntity(T dto, Class<U> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
