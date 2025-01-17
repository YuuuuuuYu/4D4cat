package com.nakji.myapp.labs.woori.service;

import com.nakji.myapp.labs.woori.client.WooriMailClient;
import com.nakji.myapp.labs.woori.model.WooriMailDto;
import com.nakji.myapp.labs.woori.model.WooriMailRequestDto;
import com.nakji.myapp.common.property.ThirdPartyProperties;
import com.nakji.myapp.common.util.NakjiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Feign;
import feign.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WooriMailApiService {
    private static final String API_URL = "https://woorimail.com";

    private final ThirdPartyProperties secrets;

    public String sendMail(WooriMailDto dto) {
        if(!checkMailStatus()) {
            log.info("WooriMailApiService.sendMail checkMailStatus");
            return "Fail";
        }

        try (Response response = sendMailConnection(dto)) {
            if (response.status() == 200) {
                JsonNode resultJson = NakjiUtil.readBody(response.body().asInputStream());
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
        try (Response response = sendMailConnection(new WooriMailDto(
                                                            secrets.woorimail().woorimailKey(),
                                                            secrets.woorimail().domain()
        ))) {
            if (response.status() == 200) {
                JsonNode resultJson = NakjiUtil.readBody(response.body().asInputStream());
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
        String type = "api";
        String mid = "auth_woorimail";
        String actMail = "'dispWwapimanagerMailApi'";
        String title = "Subscription title";
        String content = "Send Subscription";
        String wmsDomain = "woorimail.com";
        String wmsNick = "testAdmin";
        String senderEmail = "";
        String senderNickname = "woori_test";
        String receiverNickname = dto.getNickname();
        String receiverEmail = dto.getEmail();
        String memberRegdate = "20240529162828";
        String ssl = "N";
        String ssl_port = "80";

        return new WooriMailDto(secrets.woorimail().woorimailKey(),
                type, mid, actMail, title, content, wmsDomain, wmsNick, senderEmail, senderNickname, receiverNickname, receiverEmail, memberRegdate, ssl, ssl_port,
                secrets.woorimail().domain(), "");
    }

}
