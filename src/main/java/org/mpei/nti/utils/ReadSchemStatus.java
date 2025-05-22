package org.mpei.nti.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mpei.nti.substation.substationStructures.SchemaStatus;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReadSchemStatus {

    public static List<SchemaStatus> readSchem() throws IOException {
        File file = new File("src/main/resources/schemaStatus.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<SchemaStatus> schemaStatus = objectMapper.readValue(file, new TypeReference<List<SchemaStatus>>() {
        });
        return schemaStatus;
    }

}
