package io.pivotal.configserverjdbcdemo;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PropertyRepository extends CrudRepository<Property, Long> {

    Optional<Property> findByPropertyKeyAndApplicationAndProfile(String propertyKey, String application, String profile);
}
