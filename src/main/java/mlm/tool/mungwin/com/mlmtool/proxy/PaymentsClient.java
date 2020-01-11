package mlm.tool.mungwin.com.mlmtool.proxy;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "payments")
public interface PaymentsClient {



}
