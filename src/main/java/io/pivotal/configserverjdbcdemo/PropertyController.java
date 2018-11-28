package io.pivotal.configserverjdbcdemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@RestController
@RequestMapping("/properties/upload")
public class PropertyController {

    private static final String DEFAULT_PROFILE = "default";


    @Autowired
    private PropertyRepository propertyRepository;


    @PostMapping(value = "/{application}/{profile}")
    public ResponseEntity uploadPropertes(@RequestBody String input,
                                          @PathVariable("application") String application,
                                          @PathVariable("profile") final String profile) throws IOException {
        Properties properties = PropertiesLoaderUtils.loadProperties(new ByteArrayResource(input.getBytes()));
        properties.entrySet().forEach(entry -> {
            final Property prop;
            Optional<Property> optionalProperty = propertyRepository.findByPropertyKeyAndApplicationAndProfile((String) entry.getKey(), application, profile);
            if (optionalProperty.isPresent()) {
                prop = optionalProperty.get();
            } else {
                prop = new Property();
                prop.setApplication(application);
                prop.setProfile(profile);
                prop.setPropertyKey((String) entry.getKey());
            }
            prop.setPropertyValue((String) entry.getValue());
            propertyRepository.save(prop);
        });

        return new ResponseEntity(HttpStatus.OK);
    }


}
