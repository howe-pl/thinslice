package com.playlogix.thinslice.db;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

import java.util.Date;

public class LocalTimeConverter extends TypeConverter implements SimpleValueConverter {
	public static final LocalTimeConverter INSTANCE = new LocalTimeConverter();
	public static final DateTimeFormatter ISO_DATE_HOURS = ISODateTimeFormat.hour();

	public LocalTimeConverter() {
		super(LocalTime.class);
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
			return new LocalTime(fromDBObject);
        }
		return ISO_DATE_HOURS.parseLocalTime(fromDBObject.toString());
	}

	@Override
	public Object encode(final Object value, final MappedField optionalExtraInfo) {
		if (value == null) {
			return null;
		}
		if (!(value instanceof LocalTime)) {
			return value;
		}
		final LocalTime dateTime = (LocalTime) value;
		return ISO_DATE_HOURS.print(dateTime);
	}
}
