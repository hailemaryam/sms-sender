package com.hmmk.melkite.web.rest;

import com.hmmk.melkite.Dao.SmscConfigItemDAO;
import com.hmmk.melkite.entity.SmscConfigItem;
import com.hmmk.melkite.utility.KannelConf;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.AllArgsConstructor;

import java.util.List;

@Path("smsc-config")
@AllArgsConstructor
public class SmscConfg {
    @Inject
    SmscConfigItemDAO smscConfigItemDAO;

    @GET
    @Path("get-smsc-config")
    @Produces("application/json")
    @Consumes("application/json")
    SmscConfigItem getSmscConfigItem(
            @QueryParam("service-id") String serviceId,
            @QueryParam("product-id") String productId
    ) {
        return smscConfigItemDAO.findByServiceIdAndProductId(serviceId, productId);
    }

    @POST
    @Path("get-smsc-config")
    @Consumes("application/json")
    @Produces("application/json")
    SmscConfigItem createOrUpdate(SmscConfigItem smscConfigItem) {
        SmscConfigItem orUpdate = smscConfigItemDAO.createOrUpdate(smscConfigItem);
        List<SmscConfigItem> smscConfigItemList = SmscConfigItem.listAll();
        KannelConf.configure(smscConfigItemList,"");
        KannelConf.restartKannel();
        return orUpdate;
    }
}
