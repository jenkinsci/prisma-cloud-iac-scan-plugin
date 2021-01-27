package io.prismacloud.iac.commons.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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


    /**
     * This method reads config.yml file and extracts Template Parameters Information
     * Set extracted values to IaCTemplateParameters object
     * @param logger
     * @param configFile
     * @return IacTemplateParameters
     */

    public static IacTemplateParameters readTemplateParams(PrintStream logger, File configFile) {
        logger.println("Prisma Cloud IaC Scan: Inside readTemplateParams method");
        IacTemplateParameters iacTemplateParameters = new IacTemplateParameters();
        try {
            ObjectNode config = YAML.readValue(configFile, ObjectNode.class);
            if (config.hasNonNull("template_parameters")) {
                logger.println("Prisma Cloud IaC Scan: Processing Template Parameters .....");
                ObjectMapper mapper = new ObjectMapper(new JsonFactory());

                TemplateParametersModel templateParametersModel = mapper.readValue(config.get("template_parameters").toString(), TemplateParametersModel.class);

                if (templateParametersModel != null) {
                    if (templateParametersModel.getVariables() != null && templateParametersModel.getVariables().size() > 0) {
                        iacTemplateParameters.setVariables(templateParametersModel.getVariables());
                    }
                    if (templateParametersModel.getVariableFiles() != null && templateParametersModel.getVariableFiles().size() > 0) {
                        iacTemplateParameters.setVariableFiles(templateParametersModel.getVariableFiles());
                    }
                    if (templateParametersModel.getFiles() != null && templateParametersModel.getFiles().size() > 0) {
                        iacTemplateParameters.setFiles(templateParametersModel.getFiles());
                    }
                    if (templateParametersModel.getPolicyIdFilters() != null && templateParametersModel.getPolicyIdFilters().size() > 0) {
                        iacTemplateParameters.setPolicyIdFilters(templateParametersModel.getPolicyIdFilters());
                    }
                    if (templateParametersModel.getFolders() != null && templateParametersModel.getFolders().size() > 0) {
                        iacTemplateParameters.setFolders(templateParametersModel.getFolders());
                    }
                    if (templateParametersModel.getScanPlanFilesOnly() != null) {
                        iacTemplateParameters.setScanPlanFilesOnly(templateParametersModel.getScanPlanFilesOnly());
                    }

                } else {
                    logger.println("Prisma Cloud IaC Scan: Unable to parse config.yml for template_parameters object");
                }
            }
        } catch (JsonMappingException e) {
            logger.println("Prisma Cloud IaC Scan: Failed to read template parameters variables from config file '" + configFile.getAbsolutePath() + "': " + e.getMessage());
        } catch (JsonProcessingException e) {
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
