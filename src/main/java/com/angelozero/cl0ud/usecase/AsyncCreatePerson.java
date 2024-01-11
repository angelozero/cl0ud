package com.angelozero.cl0ud.usecase;

import com.angelozero.cl0ud.async.service.SendWhatsAppMessage;
import com.angelozero.cl0ud.async.service.records.WhatsAppMessage;
import com.angelozero.cl0ud.exception.person.CreatePersonException;
import com.angelozero.cl0ud.usecase.model.AsyncMessage;
import com.angelozero.cl0ud.usecase.model.Person;
import com.angelozero.cl0ud.usecase.utils.ToJson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AsyncCreatePerson {

    private final CreatePerson createPerson;
    private final SendWhatsAppMessage sendWhatsAppMessage;

    @Async("asyncTaskExecutor")
    public void createPersonService(Person person) {
        try {
            log.info("\n[ASYNC_PERSON] - creating person: {} - Thread: {}\n", ToJson.execute(person), Thread.currentThread().getName());
            createPerson.execute(person);
        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to create an async person\n");
            throw new CreatePersonException("Error to create an async person: " + ex.getMessage());
        }
    }

    @Async("asyncTaskExecutor")
    public void sendWhatsAppMessage(AsyncMessage asyncMessage) {
        try {
            log.info("\n[ASYNC_MESSAGE] - send message: {} - Thread: {}\n", ToJson.execute(asyncMessage), Thread.currentThread().getName());
            Thread.sleep(2000);
            sendWhatsAppMessage.execute(new WhatsAppMessage(asyncMessage.getTo(), asyncMessage.getBody()));
        } catch (Exception ex) {
            log.error("\n[ERROR] - Error to  send an async whatsapp message\n");
            throw new CreatePersonException("Error to  send an async whatsapp message: " + ex.getMessage());
        }
    }
}
