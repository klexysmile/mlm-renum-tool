package mlm.tool.mungwin.com.mlmtool.services.utilities.autopay.jwtengine;

import lombok.Data;

import java.util.Map;
@Data
public class JwtToken {
    private Map<String, Object> payload;
    private Map<String, Object> header;
}
