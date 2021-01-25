package io.prismacloud.iac.commons.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.prismacloud.iac.commons.model.IacTemplateParameters;


import java.io.File;
import java.io.PrintStream;
import java.util.*;

public class ConfigYmlTagsUtil {

    private static final String EMPTY = "";

    private static final ObjectMapper YAML = new ObjectMapper(new YAMLFactory());

    public static Map<String, String> readTags(PrintStream logger, File configFile) {
        logger.println("Prisma Cloud IaC Scan: Inside readTags method of ConfigYmlTagsUtil");
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
            logger.println("Prisma Cloud IaC Scan: Failed to read tags from config file '" + configFile.getAbsolutePath() + "': " + e.getMessage());
        }
        return Collections.emptyMap();
    }


    public static IacTemplateParameters readTemplateParams(PrintStream logger, File configFile) {
        logger.println("Prisma Cloud IaC Scan: Inside readTemplateParams method");
        IacTemplateParameters iacTemplateParameters = new IacTemplateParameters();
        try {
            ObjectNode config = YAML.readValue(configFile, ObjectNode.class);
            if (config.hasNonNull("template_parameters")) {
                JsonNode templateParams = config.get("template_parameters");
                if (templateParams.hasNonNull("variables")) {
                    JsonNode variablesNode = templateParams.get("variables");
                    if (!variablesNode.isEmpty()) {
                        Map<String, String> variablesMap = new LinkedHashMap<>();
                        if (variablesNode.isObject()) {
                            Iterator<Map.Entry<String, JsonNode>> itr = variablesNode.fields();
                            while (itr.hasNext()) {
                                Map.Entry<String, JsonNode> e = itr.next();
                                String name = e.getKey();
                                if (name != null && !(name = name.trim()).isEmpty()) {
                                    variablesMap.put(e.getKey(), e.getValue().asText(EMPTY));
                                }
                            }
                        } else if (variablesNode.isArray()) {
                            Iterator<JsonNode> itr = variablesNode.elements();
                            while (itr.hasNext()) {
                                JsonNode e = itr.next();
                                parseAndSetTag(e.asText(EMPTY), variablesMap);
                            }
                        } else {
                            parseAndSetTag(variablesNode.asText(EMPTY), variablesMap);
                        }
                        logger.println("Prisma Cloud IaC Scan: Printing template parameters variables");
                        if (variablesMap.size() > 0) {
                            variablesMap.forEach((k, v) -> logger.println("Key : " + k + ", Value : " + v));
                        }

                        iacTemplateParameters.setVariables(variablesMap);
                    }
                }

                if (templateParams.hasNonNull("variable_files")) {
                    JsonNode variableFilesNode = templateParams.get("variable_files");
                    if (!variableFilesNode.isEmpty()) {
                        List<String> filesList = new ArrayList<>();
                        if (variableFilesNode.isArray()) {
                            Iterator<JsonNode> itr = variableFilesNode.elements();
                            while (itr.hasNext()) {
                                JsonNode e = itr.next();
                                filesList.add(e.toString());
                            }
                        }
                        logger.println("Prisma Cloud IaC Scan: Printing template parameters variable files");
                        if (filesList.size() > 0) {
                            filesList.forEach(v -> logger.println(v));
                        }
                        iacTemplateParameters.setFiles(filesList);
                    }
                }
            }
        } catch (RuntimeException e) {
            logger.println("Prisma Cloud IaC Scan: Failed to read template parameters variables from config file '" + configFile.getAbsolutePath() + "': " + e.getMessage());
        } catch (Exception e) {
            logger.println("Prisma Cloud IaC Scan: Failed to read template parameters variables from config file '" + configFile.getAbsolutePath() + "': " + e.getMessage());

        }
        return iacTemplateParameters;
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
