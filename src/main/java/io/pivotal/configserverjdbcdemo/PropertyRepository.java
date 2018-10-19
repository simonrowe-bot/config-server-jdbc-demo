package io.pivotal.configserverjdbcdemo;

import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, Long> {
}
