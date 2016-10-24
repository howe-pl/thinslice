package com.playlogix.thinslice.db;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

import java.util.Date;

public class LocalDateConverter extends TypeConverter implements SimpleValueConverter {
	public static final LocalDateConverter INSTANCE = new LocalDateConverter();
	public static final DateTimeFormatter ISO_DATE_TIME_FORMAT_UTC = ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC);

	public LocalDateConverter() {
		super(LocalDate.class);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object decode(
			final Class targetClass,
			final Object fromDBObject,
			final MappedField optionalExtraInfo) throws MappingException {
		if (fromDBObject == null) {
			return null;
		}
		if ((fromDBObject instanceof Date) || (fromDBObject instanceof Number)) {
			return new LocalDate(fromDBObject);
        }
		return ISO_DATE_TIME_FORMAT_UTC.parseLocalDate(fromDBObject.toString());
	}

	@Override
	public Object encode(final Object value, final MappedField optionalExtraInfo) {
		if (value == null) {
			return null;
		}
		if (!(value instanceof LocalDate)) {
			return value;
		}
		final LocalDate dateTime = (LocalDate) value;
		return dateTime.toDate();
	}
}
