package com.example.springbootmustache.nakji.api.woori.service;

import com.example.springbootmustache.nakji.api.woori.client.WooriMailClient;
import com.example.springbootmustache.nakji.api.woori.model.WooriMailDto;
import com.example.springbootmustache.nakji.api.woori.model.WooriMailRequestDto;
import com.example.springbootmustache.nakji.common.service.NakjiService;
import com.example.springbootmustache.nakji.common.util.NakjiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.Response;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class WooriMailApiService extends NakjiService {

    private static final String API_URL = "https://woorimail.com";

    public String sendMail(WooriMailDto dto) {
        if(!checkMailStatus()) {
            log.info("WooriMailApiService.sendMail checkMailStatus");
            return "Fail";
        }

        try (Response response = sendMailConnection(dto)) {
            if (response.status() == 200) {
                JsonNode resultJson = readBody(response.body().asInputStream());
                JsonNode jsonMap = resultJson.at("/result");

                return "OK".equals(Optional.ofNullable(jsonMap).map(JsonNode::asText).orElse("Fail")) ? "OK" : "Fail";
            } else {
                log.info("WooriMailApiService.sendMail Request Status: {}, {}", response.status(), response.body());
                throw new BadRequestException("Bad request with status: " + response.status());

            }
        } catch (Exception e) {
            log.error("WooriMailApiService.sendMail Error: ", e);

        }

        return "Fail";
    }

    private boolean checkMailStatus() {
        try (Response response = sendMailConnection(new WooriMailDto())) {
            if (response.status() == 200) {
                JsonNode resultJson = readBody(response.body().asInputStream());
                JsonNode jsonMap = resultJson.at("/result");

                return "OK".equals(Optional.ofNullable(jsonMap).map(JsonNode::asText).orElse(""));
            } else {
                log.info("WooriMailApiService.checkMailStatus Request Status: {}, {}", response.status(), response.body());
                throw new BadRequestException("Bad request with status: " + response.status());

            }
        } catch (Exception e) {
            log.error("WooriMailApiService.checkMailStatus Error: ", e);
            return false;
        }
    }

    private Response sendMailConnection(WooriMailDto dto) {
        Response response = null;

        WooriMailClient client = Feign.builder().target(WooriMailClient.class, API_URL);
        String param = NakjiUtil.toStringForRequestBody(dto);
        response = client.call(param);

        return response;
    }

    public WooriMailDto setDto(WooriMailRequestDto dto) {
        String title = "Subscription title";
        String content = "Send Subscription";
        String wms_domain = "woorimail.com";
        String wms_nick = "testAdmin";
        String sender_email = "";
        String sender_nickname = "woori_test";
        String receiver_nickname = dto.getNickname();
        String receiver_email = dto.getEmail();
        String member_regdate = "20240529162828";
        String ssl = "N";
        String ssl_port = "80";

        return new WooriMailDto(title, content, wms_domain, wms_nick, sender_email, sender_nickname, receiver_nickname, receiver_email, member_regdate, ssl, ssl_port);
    }

}
