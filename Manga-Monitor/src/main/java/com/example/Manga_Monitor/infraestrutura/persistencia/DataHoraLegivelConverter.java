package com.example.Manga_Monitor.infraestrutura.persistencia;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/** Mantém a data como LocalDateTime no domínio e texto legível no SQLite. */
@Converter
public class DataHoraLegivelConverter implements AttributeConverter<LocalDateTime, String> {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HH:mm:ss.SSS")
            .withResolverStyle(ResolverStyle.STRICT);

    @Override
    public String convertToDatabaseColumn(LocalDateTime data) {
        return data == null ? null : data.format(FORMATO);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String data) {
        return data == null ? null : LocalDateTime.parse(data, FORMATO);
    }
}
