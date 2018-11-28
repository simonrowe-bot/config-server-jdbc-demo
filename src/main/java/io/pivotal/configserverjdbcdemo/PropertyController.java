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
    private static final String DEFAULT_LABEL = "master";


    @Autowired
    private PropertyRepository propertyRepository;


    @PostMapping(value = "/{filename}")
    public ResponseEntity uploadPropertes(@RequestBody String input,
                                          @PathVariable("filename") String filename) throws IOException {
        final String application = applicationName(filename);
        final String profile = profileName(filename);

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
            prop.setLabel(DEFAULT_LABEL);
            propertyRepository.save(prop);
        });

        return new ResponseEntity(HttpStatus.OK);
    }

    protected String profileName(String filename) {
        String prefix = filename.substring(0, filename.indexOf("."));
        String[] tokens = prefix.split("-");
        if (tokens.length == 1) {
            return "default";
        }
        return tokens[tokens.length - 1];

    }

    protected String applicationName(String filename) {
        String prefix = filename.substring(0, filename.indexOf("."));
        String[] tokens = prefix.split("-");
        if (tokens.length == 1) {
            return prefix;
        } else {
            return prefix.substring(0, prefix.lastIndexOf("-"));
        }
    }


}
