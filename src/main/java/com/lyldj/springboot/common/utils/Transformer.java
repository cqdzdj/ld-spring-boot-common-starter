package com.lyldj.springboot.common.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

/**
 * Object transformer
 */
@Component
public class Transformer {
    private MapperFacade createMapper(Class<?> sourceClazz, Class<?> descClazz, Map<String, String> mapper) {
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        factory.getConverterFactory().registerConverter(
                new PassThroughConverter(DateTime.class, LocalDate.class, LocalTime.class));
        ClassMapBuilder cmb = factory.classMap(sourceClazz, descClazz)
                .mapNulls(false)
                .mapNullsInReverse(false);
        if (mapper != null) {
            for (Map.Entry<String, String> entry : mapper.entrySet()) {
                cmb.field(entry.getKey(), entry.getValue());
            }
        }
        cmb.byDefault().register();
        factory.registerClassMap(cmb);
        return factory.getMapperFacade();
    }

    public <T> T toMapper(Object source, Class<T> descClazz) {
        MapperFacade mapperFacade = createMapper(source.getClass(), descClazz, null);
        return mapperFacade.map(source, descClazz);
    }
}
