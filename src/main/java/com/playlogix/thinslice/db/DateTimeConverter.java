package com.playlogix.thinslice.db;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

import java.util.Date;

public class DateTimeConverter extends TypeConverter implements SimpleValueConverter {
	public static final DateTimeConverter INSTANCE = new DateTimeConverter();
	public static final DateTimeFormatter ISO_DATE_TIME_FORMAT_UTC = ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC);

	public DateTimeConverter() {
		super(DateTime.class);
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
			return new DateTime(fromDBObject);
        }
		return ISO_DATE_TIME_FORMAT_UTC.parseDateTime(fromDBObject.toString());
	}

	@Override
	public Object encode(final Object value, final MappedField optionalExtraInfo) {
		if (value == null) {
			return null;
		}
		if (!(value instanceof DateTime)) {
			return value;
		}
		final DateTime dateTime = (DateTime) value;
		return dateTime.toDate();
	}
}
