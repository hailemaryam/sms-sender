package com.hmmk.melkite.service;

import com.hmmk.melkite.DTO.SmscSendItem;
import com.hmmk.melkite.DTO.WebServiceQueueItem;
import com.hmmk.melkite.utility.SendSMS;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class SmscSendItemReceiver {

    @Inject
    SendSMS sendSMS;

    @Inject
    QueueItemToSmscSendItemConvertor queueItemToSmscSendItemConvertor;

    @Incoming("subscriber")
    @Blocking
    public void sendSms(WebServiceQueueItem webServiceQueueItem) {
        SmscSendItem smscSendItem = queueItemToSmscSendItemConvertor.convert(webServiceQueueItem);
        try {
            sendSMS.sendSMS(smscSendItem, "localhost", "8080");
        } catch (Exception e) {
            // todo handle this exception using logger and tenant problem notifier
            throw new RuntimeException(e);
        }

    }

}
