package com.hmmk.melkite.utility;
import com.hmmk.melkite.entity.SmscConfigItem;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class KannelConf {
    public static void restartKannel(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("sudo", "systemctl", "restart", "kannel.service");
        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void configure(List<SmscConfigItem> smscConfigItemList, String filePath){
        if (filePath == null || filePath.isEmpty()){
            filePath = "/etc/kannel/kannel.conf";
        }
        List<String> lines = new ArrayList<>();
        lines.add("group = core");
        lines.add("admin-port = 13000");
        lines.add("admin-password = test@1234");
        lines.add("admin-deny-ip = \"\"");
        lines.add("admin-allow-ip = \"*.*.*.*\"");
        lines.add("smsbox-port = 13001");
        lines.add("wdp-interface-name = \"*\"");
        lines.add("log-file = \"/var/log/kannel/bearerbox.log\"");
        lines.add("log-level = 0");
        lines.add("box-deny-ip = \"\"");
        lines.add("box-allow-ip = \"*.*.*.*\"");
        lines.add("dlr-storage = spool");
        lines.add("dlr-spool = /var/log/kannel");
        lines.add("store-type = spool");
        lines.add("store-location = /var/spool/kannel/store");
        for (SmscConfigItem smscConfigItem : smscConfigItemList){
            lines.add("");
            lines.add("group=smsc");
            lines.add("smsc=smpp");
            lines.add("smsc-id=" + smscConfigItem.shortCode);
            lines.add("interface-version=34");
            lines.add("host=" + smscConfigItem.ip);
            lines.add("port=" + smscConfigItem.port);
            lines.add("smsc-username=" + smscConfigItem.username);
            lines.add("smsc-password=" + smscConfigItem.password);
            lines.add("system-type=default");
            lines.add("transceiver-mode= true");
            lines.add("allowed-smsc-id=" + smscConfigItem.shortCode);
            lines.add("");
        }
        lines.add("");
        lines.add("group = smsbox");
        lines.add("smsbox-id = mysmsbox");
        lines.add("bearerbox-host = 127.0.0.1");
        lines.add("sendsms-port = 13013");
        lines.add("sendsms-chars = \"+0123456789 \"");
        lines.add("log-file = \"/var/log/kannel/smsbox1.log\"");
        lines.add("log-level = 0");
        lines.add("mo-recode = true");
        lines.add("");
        lines.add("group = sms-service");
        lines.add("keyword = default");
        lines.add("get-url = \"http://localhost/api/receive-sms-custom?message=%a&phone=%p&to=%P\"");
        lines.add("catch-all = yes");
        lines.add("omit-empty = true");
        lines.add("concatenation = true");
        lines.add("max-messages = 0");
        lines.add("");
        for (SmscConfigItem smscConfigItem : smscConfigItemList){
            lines.add("group = sendsms-user");
            lines.add("username = " + smscConfigItem.username);
            lines.add("password = " + smscConfigItem.password);
            lines.add("user-allow-ip = \"*.*.*.*\"");
            lines.add("max-messages = 10");
            lines.add("concatenation = true");
            lines.add("forced-smsc = " + smscConfigItem.shortCode);
            lines.add("");
        }
        Path path = Paths.get(filePath);
        try {
            Files.write(path, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

