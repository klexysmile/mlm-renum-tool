package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OwnAccountRequestDTO {
    @NotBlank(message = "password is required")
    private String password;
}
