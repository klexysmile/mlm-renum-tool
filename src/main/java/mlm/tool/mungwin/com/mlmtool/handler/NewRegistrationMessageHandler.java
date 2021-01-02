package mlm.tool.mungwin.com.mlmtool.handler;

import mlm.tool.mungwin.com.mlmtool.config.RabbitMessagingService;
import mlm.tool.mungwin.com.mlmtool.entities.Customer;
import mlm.tool.mungwin.com.mlmtool.entities.Messages;
import mlm.tool.mungwin.com.mlmtool.exceptions.ApiException;
import mlm.tool.mungwin.com.mlmtool.exceptions.ErrorCodes;
import mlm.tool.mungwin.com.mlmtool.repositories.MessageRepository;
import mlm.tool.mungwin.com.mlmtool.services.BonusCalculationServiceAssync;
import mlm.tool.mungwin.com.mlmtool.services.contract.BonusCalculationService;
import mlm.tool.mungwin.com.mlmtool.utils.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class NewRegistrationMessageHandler {

    //<editor-fold desc="STATIC FIELDS">
    private static final Logger LOG = LogManager.getLogger();
    //</editor-fold>

    @Autowired
    private BonusCalculationServiceAssync bonusCalculationService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    RabbitMessagingService rabbitMessagingService;

    @RabbitListener(queues = "#{newRegistrationQueue.name}")
    public void newCustomerRegistrationHandler(Message message) throws  InterruptedException
    {
        byte[] body = message.getBody();
        String strMessage = new String(body);
        LOG.info(" >> Registration action message: {}", strMessage);
        handle(strMessage);
    }

    public void handle(String strMessage) {

        JSONObject messageJSON = new JSONObject(strMessage);

        try {
            Map<Customer, Double> customersToBePaid = new HashMap<>();

            boolean operationStatus = bonusCalculationService.processNewRegistration(messageJSON, customersToBePaid);

            if(!operationStatus){
                LOG.error("AN ERROR OCCURRED, COULD NOT FINISH PROCESSING THE TRANSACTIONS");
            }

            if(!customersToBePaid.isEmpty()){

                for(Map.Entry<Customer, Double> paymentEntry : customersToBePaid.entrySet()){

                    bonusCalculationService.transferBonusPaycashToCustomerAccount(paymentEntry.getKey(), paymentEntry.getValue());

                }
            }

            //<editor-fold desc="PUSH RESPONSE TO CUSTOMER">
            Optional<Messages> messagesOptional = messageRepository.findById(messageJSON.optInt("id"));
            if(messagesOptional.isPresent()){
                Messages messages = messagesOptional.get();

                messages.setStatus(Parameters.TRANSACTION_STATUS_COMPLETED);
                messageRepository.save(messages);
            }

//            JSONObject responseJSON = new JSONObject();
//            responseJSON.put("id", messageJSON.optInt("id"));
//            rabbitMessagingService.pushToDlcNewRegistrationQueue(responseJSON);
            //</editor-fold>
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
            throw new ApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.ERROR.name(), "");
        }

        taskExecutor.execute(() -> {
            bonusCalculationService.processBonusMessages();
        });
    }


}
