package com.angelozero.cl0ud.async.service;

import com.angelozero.cl0ud.async.configuration.MessageConfiguration;

import com.angelozero.cl0ud.async.service.records.WhatsAppMessage;
import com.angelozero.cl0ud.async.service.records.WhatsAppResponse;
import com.angelozero.cl0ud.async.utils.MessageConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendWhatsAppMessage {

    private final MessageConfiguration messageConfiguration;

    public WhatsAppResponse execute(WhatsAppMessage message) {
        Unirest.setTimeouts(0, 0);

        try {
            HttpResponse<String> response = Unirest.post(messageConfiguration.getUrl() +
                            messageConfiguration.getInstanceId() +
                            messageConfiguration.getPath())
                    .header(MessageConstants.CONTENT_TYPE, MessageConstants.X_WWW_FORM_URLENCODED)
                    .field(MessageConstants.TOKEN, messageConfiguration.getToken())
                    .field(MessageConstants.TO, message.to())
                    .field(MessageConstants.BODY, message.body())
                    .asString();

            return new ObjectMapper().readValue(response.getBody(), WhatsAppResponse.class);

        } catch (UnirestException | JsonProcessingException e) {
            log.error("Error to send WhatsApp message: " + e.getMessage());
            return null;
        }
    }
}
