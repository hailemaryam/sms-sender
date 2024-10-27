package com.hmmk.melkite.Dao;

import com.hmmk.melkite.entity.SmscConfigItem;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SmscConfigItemDAO {
    public SmscConfigItem createOrUpdate(SmscConfigItem smscConfigItem) {
        SmscConfigItem existing = findByServiceIdAndProductId(smscConfigItem.serviceId, smscConfigItem.productId);
        if (existing != null){
            existing.serviceId = smscConfigItem.serviceId;
            existing.productId = smscConfigItem.productId;
            existing.username = smscConfigItem.username;
            existing.password = smscConfigItem.password;
            existing.ip = smscConfigItem.ip;
            existing.port = smscConfigItem.port;
            existing.shortCode = smscConfigItem.shortCode;
            existing.persistAndFlush();
            return existing;
        } else {
            smscConfigItem.persistAndFlush();
            return smscConfigItem;
        }
    }
    public SmscConfigItem findByServiceIdAndProductId(String serviceId, String productId) {
        return SmscConfigItem.find("serviceId = ?1 and productId = ?2", serviceId, productId).firstResult();
    }
}
