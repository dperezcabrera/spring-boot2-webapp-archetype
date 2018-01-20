#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ToStringUtil {
    ; // without instances

    private static final ObjectMapper MAPPER = new UtilFactory().objectMapper();

    public static String json(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            log.error("Error en toString", ex);
            return "(toString() [ERROR])";
        }
    }
}
