package com.shrikane.makespace;

import com.shrikane.makespace.converter.BookRequestConverter;
import com.shrikane.makespace.converter.VacancyRequestConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class MakeSpaceConfiguration {
    @Bean
    public ConversionService conversionService() {
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        Set<Converter<?, ?>> convSet = new HashSet<>();
        convSet.add(bookRequestConverter());
        convSet.add(vacancyRequestConverter());
        factory.setConverters(convSet);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public BookRequestConverter bookRequestConverter() {
        return new BookRequestConverter();
    }

    @Bean
    public VacancyRequestConverter vacancyRequestConverter() {
        return new VacancyRequestConverter();
    }
}