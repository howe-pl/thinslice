package com.playlogix.thinslice.db;

import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class MongoDataStoreFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoDataStoreFactory.class);
	private static final String[] MORPHIA_PACKAGES_TO_MAP = new String[] {""};
	private static Morphia morphia;
	protected static MongoClient mongoClient;

	@NotEmpty
	private String host = "localhost";
	@Min(1)
	@Max(65535)
	private Integer port = 27017;
	@NotEmpty
	protected String databaseName;

	public MongoDataStoreFactory() {
		morphia = new Morphia();
		for (String packagesToMap : MORPHIA_PACKAGES_TO_MAP) {
			morphia.mapPackage(packagesToMap, true);
		}
		morphia.getMapper().getConverters().addConverter(DateTimeConverter.class);
		morphia.getMapper().getConverters().addConverter(LocalDateConverter.class);
		morphia.getMapper().getConverters().addConverter(LocalTimeConverter.class);
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	protected MongoClient createOrGetMongoClient() {
		if (mongoClient == null) {
			mongoClient = new MongoClient(getHost(), getPort());
		}
		return mongoClient;
	}

	public Datastore build(final Environment environment) {
		environment.lifecycle().manage(
			new Managed() {
				@Override
				public void start() throws Exception {
					LOGGER.info("Starting MongoClient connection");
				}

				@Override
				public void stop() throws Exception {
					LOGGER.info("Closing MongoClient connection");
					createOrGetMongoClient().close();
					mongoClient = null;
				}
			}
		);
		final Datastore datastore = morphia.createDatastore(createOrGetMongoClient(), getDatabaseName());
		datastore.ensureIndexes(true);
		return datastore;
	}
}