package io.pivotal.configserverjdbcdemo;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PropertyControllerTest {

    private PropertyController propertyController = new PropertyController();

    @Test
    public void profileName() {
        assertThat("default", is(propertyController.profileName("application.properties")));
        assertThat("local", is(propertyController.profileName("application-local.properties")));
        assertThat("local", is(propertyController.profileName("application-this-local.properties")));
    }

    @Test
    public void applicationName() {
        assertThat("application", is(propertyController.applicationName("application.properties")));
        assertThat("application", is(propertyController.applicationName("application-local.properties")));
        assertThat("application-this", is(propertyController.applicationName("application-this-local.properties")));
        assertThat("web", is(propertyController.applicationName("web-local.properties")));
    }
}