package com.playlogix.thinslice;

import com.playlogix.thinslice.api.User;
import com.playlogix.thinslice.auth.LoginAuthenticator;
import com.playlogix.thinslice.auth.LoginAuthorizer;
import com.playlogix.thinslice.db.dao.ActivityDAO;
import com.playlogix.thinslice.db.dao.SessionDAO;
import com.playlogix.thinslice.db.dao.TestDAO;
import com.playlogix.thinslice.db.dao.UserDAO;
import com.playlogix.thinslice.resources.ActivityResource;
import com.playlogix.thinslice.resources.SessionResource;
import com.playlogix.thinslice.resources.TestResource;
import com.playlogix.thinslice.resources.UserResource;
import com.playlogix.thinslice.services.ActivityService;
import com.playlogix.thinslice.services.SessionService;
import com.playlogix.thinslice.services.TestService;
import com.playlogix.thinslice.services.UserService;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.mongodb.morphia.Datastore;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class LoginApplication extends Application<LoginConfiguration> {

    public static void main(final String[] args) throws Exception {
        new LoginApplication().run(args);
    }

    @Override
    public String getName() {
        return "Login";
    }

    @Override
    public void initialize(final Bootstrap<LoginConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final LoginConfiguration configuration,
                    final Environment environment) {

        final Datastore mongoDataStore = configuration.getMongoDataStoreFactory().build(environment);

        UserDAO userDAO = new UserDAO(mongoDataStore);
        UserService userService = new UserService(userDAO);
        SessionDAO sessionDAO = new SessionDAO(mongoDataStore);
        SessionService sessionService = new SessionService(configuration, sessionDAO, userService);
        ActivityDAO activityDAO = new ActivityDAO(mongoDataStore);
        ActivityService activityService = new ActivityService(activityDAO);
        TestDAO testDAO = new TestDAO(mongoDataStore);
        TestService testService = new TestService(testDAO);

        setupAuth(environment, sessionService, userService);
        registerResources(environment, sessionService, userService, activityService, testService);
        configureCors(environment);
    }

    private void registerResources(Environment environment,
                                   SessionService sessionService,
                                   UserService userService,
                                   ActivityService activityService,
                                   TestService testService) {
        environment.jersey().register(new SessionResource(sessionService));
        environment.jersey().register(new UserResource(userService));
        environment.jersey().register(new ActivityResource(activityService));
        environment.jersey().register(new TestResource(testService));
    }

    private void setupAuth(Environment environment, SessionService sessionService, UserService userService) {
        environment.jersey().register(
                new AuthDynamicFeature(
                        new OAuthCredentialAuthFilter
                                .Builder()
                                .setAuthenticator(new LoginAuthenticator(sessionService, userService))
                                .setAuthorizer(new LoginAuthorizer())
                                .setPrefix("Bearer")
                                .buildAuthFilter()
                )
        );
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<User>(User.class));
    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }
}
