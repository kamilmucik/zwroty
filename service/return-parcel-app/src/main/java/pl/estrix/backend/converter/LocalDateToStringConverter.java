package pl.estrix.backend.converter;


import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter
public class LocalDateToStringConverter implements AttributeConverter<LocalDate, String> {

    private static final DateTimeFormatter sequenceStartDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final DateTimeFormatter YYMMDD_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        if(localDate == null) return null;
        return localDate.format(sequenceStartDateFormat);
    }

    @Override
    public LocalDate convertToEntityAttribute(String s) {
        if (StringUtils.isBlank(s))
            return null;
        if (StringUtils.length(s) == 6) {
            return LocalDate.parse(s, YYMMDD_FORMATTER);
        }
        return LocalDate.parse(s, sequenceStartDateFormat);
    }
}