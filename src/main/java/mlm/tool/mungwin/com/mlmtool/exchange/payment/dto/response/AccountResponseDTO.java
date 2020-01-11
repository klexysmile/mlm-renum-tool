package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AccountResponseDTO {
    private Long id;
    private String name;
    private Double currentBalance;
    private Double availableBalance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}
