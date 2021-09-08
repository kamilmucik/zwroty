package pl.estrix.backend.converter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter
public class LocalDateTimeToStringConverter implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter sequenceStartDateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    private static final DateTimeFormatter YYMMDD_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmm");

    @Override
    public String convertToDatabaseColumn(LocalDateTime localDate) {
        if(localDate == null) return null;
        return localDate.format(sequenceStartDateFormat);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String s) {
        if (StringUtils.isBlank(s))
            return null;
        if (StringUtils.length(s) == 6) {
            return LocalDateTime.parse(s, YYMMDD_FORMATTER);
        }
        return LocalDateTime.parse(s, sequenceStartDateFormat);
    }
}
