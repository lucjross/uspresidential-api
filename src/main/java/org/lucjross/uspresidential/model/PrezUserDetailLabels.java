package org.lucjross.uspresidential.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by lucas on 1/14/16.
 */
@Component
public class PrezUserDetailLabels {

    private final Map<String, Object> labels; // Map<String, List<Map.Entry<String, String>>>

    private PrezUserDetailLabels() throws IOException {

        Resource labelsResource = new ClassPathResource("PrezUser.labels.json");
        Map<String, Object> _labels = new HashMap<>();
        JsonNode rootNode = new ObjectMapper().readTree(labelsResource.getInputStream());
        mapTree(rootNode, _labels);
        labels = Collections.unmodifiableMap(_labels);
    }

    private void mapTree0(JsonNode valNode, Object treeObj, String nodeKeyIfMap) {

        Object nestObj = null;
        boolean isLeaf = false;
        if (valNode.isObject()) {
            if (valNode.size() == 1) {
                Map.Entry<String, JsonNode> childNodeEntry = valNode.fields().next();
                nestObj = new AbstractMap.SimpleEntry<>(childNodeEntry.getKey(), null);
            }
            else {
                nestObj = new HashMap<>();
            }
        }
        else if (valNode.isArray()) {
            nestObj = new LinkedList<>();
        }
        else if (valNode.isValueNode()) {
            isLeaf = true;
            switch (valNode.getNodeType()) {
                case BINARY:
                    try {
                        nestObj = valNode.binaryValue();
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case BOOLEAN:
                    nestObj = valNode.booleanValue();
                    break;
                case NULL:
                    nestObj = null;
                    break;
                case NUMBER:
                    nestObj = valNode.numberValue();
                    break;
                case STRING:
                    nestObj = valNode.asText();
                    break;
                default:
                    nestObj = valNode.asText();
            }
        }

        if (nestObj != null) {
            if (treeObj instanceof Map.Entry) {
                ((Map.Entry<String, Object>) treeObj).setValue(nestObj);
            }
            else if (treeObj instanceof Map && nodeKeyIfMap != null) {
                ((Map<String, Object>) treeObj).put(nodeKeyIfMap, nestObj);
            }
            else if (treeObj instanceof List) {
                ((List<Object>) treeObj).add(nestObj);
            }
        }

        if (! isLeaf) {
            mapTree(valNode, nestObj);
        }
    }

    private void mapTree(JsonNode node, Object treeObj) {

        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> iter = node.fields();
            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> childNodeEntry = iter.next();
                mapTree0(childNodeEntry.getValue(), treeObj, childNodeEntry.getKey());
            }
        }
        else if (node.isArray()) {
            Iterator<JsonNode> iter = node.elements();
            while (iter.hasNext()) {
                JsonNode childNode = iter.next();
                mapTree0(childNode, treeObj, null);
            }
        }
        else {
            mapTree0(node, treeObj, null);
        }
    }

    public Map<String, Object> labels() {
        return labels;
    }

    public Object getValue(String field, String key) {
        Object fieldMap = labels.get(field);
        if (fieldMap != null && fieldMap instanceof Map)
            return ((Map) fieldMap).get(key);
        return null;
    }
}
