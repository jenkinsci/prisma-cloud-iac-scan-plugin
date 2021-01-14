package io.prismacloud.iac.commons.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigYmlTagsUtil {

  private static final String EMPTY = "";

  private static final ObjectMapper YAML = new ObjectMapper(new YAMLFactory());

  public static Map<String, String> readTags(PrintStream logger, File configFile) {
    try {
      ObjectNode config = YAML.readValue(configFile, ObjectNode.class);
      if (config.hasNonNull("tags")) {
        JsonNode tagsNode = config.get("tags");
        if (!tagsNode.isEmpty()) {
          Map<String, String> tags = new LinkedHashMap<>();
          if (tagsNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> itr = tagsNode.fields();
            while (itr.hasNext()) {
              Map.Entry<String, JsonNode> e = itr.next();
              String name = e.getKey();
              if (name != null && !(name = name.trim()).isEmpty()) {
                tags.put(e.getKey(), e.getValue().asText(EMPTY));
              }
            }
          } else if (tagsNode.isArray()) {
            Iterator<JsonNode> itr = tagsNode.elements();
            while (itr.hasNext()) {
              JsonNode e = itr.next();
              parseAndSetTag(e.asText(EMPTY), tags);
            }
          } else {
            parseAndSetTag(tagsNode.asText(EMPTY), tags);
          }
          return tags;
        }
      }
    } catch (Exception e) {
      logger.println("Failed to read tags from config file '" + configFile.getAbsolutePath() + "': " + e.getMessage());
    }
    return Collections.emptyMap();
  }

  public static void parseAndSetTag(String tagPairString, Map<String, String> tags) {
    if (tagPairString != null && !(tagPairString = tagPairString.trim()).isEmpty()) {
      String[] nameValuePair = tagPairString.split(":");
      if (nameValuePair.length == 1) {
        tags.put(tagPairString, EMPTY);
      } else if (nameValuePair.length > 1) {
        String name = nameValuePair[0];
        if (name != null && !(name = name.trim()).isEmpty()) {
          StringBuilder value = new StringBuilder();
          for (int i = 1; i < nameValuePair.length; i++) {
            String valuePart = nameValuePair[i];
            if (valuePart == null) {
              valuePart = EMPTY;
            } else {
              valuePart = valuePart.trim();
            }
            if (i > 1) {
              value.append(":");
            }
            value.append(valuePart);
          }
          tags.put(name, value.toString());
        }
      }
    }
  }

}
