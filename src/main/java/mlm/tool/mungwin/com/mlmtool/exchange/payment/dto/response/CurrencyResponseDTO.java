package mlm.tool.mungwin.com.mlmtool.exchange.payment.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CurrencyResponseDTO {
    private CurrencyDTO base;
    private List<CurrencyDTO> currencies;
}
