package mlm.tool.mungwin.com.mlmtool.tasks;

import mlm.tool.mungwin.com.mlmtool.services.BonusCalculationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MessageProcessorTask {

    //<editor-fold desc="FIELDS">
    private Logger logger = LoggerFactory.getLogger(MessageProcessorTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    BonusCalculationServiceImpl bonusCalculationService;
    //</editor-fold>

    @Scheduled(fixedRate = 60000)
    public void processMessages(){
        logger.info("BEGINNING PROCESSING MESSAGES AT {}",dateFormat.format(new Date()));
        bonusCalculationService.processMessages();
        logger.info("FINISHED PROCESSING MESSAGES AT {}",dateFormat.format(new Date()));
    }

}