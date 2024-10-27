package com.hmmk.melkite.utility;

import com.hmmk.melkite.DTO.SmscSendItem;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.java.Log;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.net.URLEncoder;

@ApplicationScoped
@Log
public class SendSMS {

    public void sendSMS(SmscSendItem smscSendItem, String dlrHostAddress, String dlrServerPort) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            String  url = "http://localhost:13013/cgi-bin/sendsms?" +
                    "username=" +
                    smscSendItem.getUsername() +
                    "&password=" +
                    smscSendItem.getPassword() +
                    "&from="
                    + smscSendItem.getShortCode()
                    + "&&to=" + smscSendItem.getPhone()
                    + "&text=" + URLEncoder.encode(smscSendItem.getMessage())
                    + "&dlr-mask=31"
                    + "&meta-data=" + smscSendItem.getId()
                    + "&dlr-url=" + URLEncoder.encode("http://" + dlrHostAddress + ":" + dlrServerPort + "/api/receive-dlr?sentId=" + smscSendItem.getId() + "&dlrStatus=%d&answer=%A");

            if (!smscSendItem.getLanguage().equalsIgnoreCase("ENGLISH")) {
                url = url + "&coding=2" + "&charset=UTF-8";
            }
            HttpGet request = new HttpGet(url);
            httpclient.execute(request);
            log.info("sms delivered to gateway");
        } catch (Exception e) {
            log.info("FAILED TO SEND SMS");
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.info("FAILED TO SEND SMS");
            }
        }
    }
}
