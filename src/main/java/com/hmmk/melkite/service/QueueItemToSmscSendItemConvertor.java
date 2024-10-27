package com.hmmk.melkite.service;

import com.hmmk.melkite.DTO.SmscSendItem;
import com.hmmk.melkite.DTO.WebServiceQueueItem;
import com.hmmk.melkite.Dao.SmscConfigItemDAO;
import com.hmmk.melkite.entity.SmscConfigItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
public class QueueItemToSmscSendItemConvertor {
    @Inject
    SmscConfigItemDAO smscConfigItemDAO;

    public SmscSendItem convert(WebServiceQueueItem webServiceQueueItem){
        SmscConfigItem smscConfigItem = smscConfigItemDAO.findByServiceIdAndProductId(webServiceQueueItem.getServiceId(), webServiceQueueItem.getProductId());
        if (smscConfigItem != null) {
            return new SmscSendItem(
                    webServiceQueueItem.getId(),
                    webServiceQueueItem.getPhone(),
                    webServiceQueueItem.getWebsiteRegisteredId(),
                    webServiceQueueItem.getGeneratedPassword(),
                    webServiceQueueItem.getServiceId(),
                    webServiceQueueItem.getProductId(),
                    modifyMessage(smscConfigItem.okReplyTemplate, webServiceQueueItem.getGeneratedPassword()),
                    smscConfigItem.language,
                    smscConfigItem.shortCode,
                    smscConfigItem.username,
                    smscConfigItem.password
            );
        } else {
            return null;
        }
    }
    public String modifyMessage(String template, String generatedPassword){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = formatter.format(new Date());
        template.replace("{PASSWORD}", generatedPassword);
        template.replace("{DATE}", s);
        return template;
    }
}
