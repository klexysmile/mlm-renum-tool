package mlm.tool.mungwin.com.mlmtool.config;

import mlm.tool.mungwin.com.mlmtool.utils.Parameters;
import mlm.tool.mungwin.com.mlmtool.utils.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Clayton on 10/18/17.
 */
@Component
public class RabbitMessagingService {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMessagingService.class);

    String exchange = Parameters.DLC_EXCHANGE;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private String dlcRegistrationResponseRoutingKey = Parameters.DLC_MEMBER_REGISTRATION_RESPONSE_EXCHANGE_KEY;

    public void pushToDlcNewRegistrationQueue(JSONObject messageContainerJSON) {
        if (!StringUtils.isNullOrEmpty(dlcRegistrationResponseRoutingKey)) {
            pushMessageToExchange(messageContainerJSON, dlcRegistrationResponseRoutingKey);
        }
    }

    private void pushMessageToExchange(JSONObject messageContainerJSON, String availabilityKey) {
        UUID uuid = UUID.randomUUID();
        messageContainerJSON.put("uuid", uuid.toString());
        taskExecutor.execute(() -> {
            Message message = new Message(messageContainerJSON.toString().getBytes(), new MessageProperties());
            LOG.info(" ===> PUSHING MESSAGE: {}", messageContainerJSON);
            amqpTemplate.send(exchange, availabilityKey, message);
            LOG.info("MESSAGE PUSHED");
        });
    }
}