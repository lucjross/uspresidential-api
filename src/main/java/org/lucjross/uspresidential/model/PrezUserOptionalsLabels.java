package org.lucjross.uspresidential.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas on 1/14/16.
 */
@Component
public class PrezUserOptionalsLabels {

    /*
     * This map will be functionally a Map<String, List<Map<String, String>>>
     * as long as the JSON retains the correct form.
     */
    private final Map<String, Object> labels;

    private PrezUserOptionalsLabels() throws IOException {

        Resource labelsResource = new ClassPathResource("PrezUser.labels.json");
        Map<String, Object> _labels = new ObjectMapper().readValue(
                labelsResource.getInputStream(), new TypeReference<HashMap>() {});
        labels = Collections.unmodifiableMap(_labels);
    }

    public Map<String, Object> labels() {
        return labels;
    }
}
