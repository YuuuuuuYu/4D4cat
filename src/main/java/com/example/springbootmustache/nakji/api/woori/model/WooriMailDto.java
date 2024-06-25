package com.example.springbootmustache.nakji.api.woori.model;

import com.example.springbootmustache.nakji.common.config.NakjiProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

public class WooriMailDto {
    private static final String AUTHKEY = NakjiProperty.getWoorimailKey();
    private static final String TYPE = "api";
    private static final String MID = "auth_woorimail";
    private static final String DOMAIN = NakjiProperty.getDomain();
    private static final String ACT_STATUS = "dispWwapimanagerStatusApi";
    private static final String ACT_MAIL = "dispWwapimanagerMailApi";

    private String authkey;
    private String type;
    private String mid;
    private String act;

    private String title;               // 메일 제목
    private String content;             // 메일 내용
    private String wms_domain;          // 전용채널 사용할 경우, 보낸 사람 이메일 도메인
    private String wms_nick;            // 전용채널 사용할 경우, 보낸 사람 이메일 계정
    private String sender_email;        // 보낸 사람 이메일
    private String sender_nickname;     // 보낸 사람 닉네임
    private String receiver_nickname;   // 받는 사람 닉네임
    private String receiver_email;      // 받는 사람 이메일
    private String member_regdate;
    private String ssl = "N";
    private String ssl_port = "80";

    @Setter
    @Getter
    private String domain;

    @Setter
    @Getter
    private String callback;

    // 메일 상태 체크(Default)
    public WooriMailDto() {
        authkey = AUTHKEY;
        type = TYPE;
        mid = MID;
        act = ACT_STATUS;
        domain = DOMAIN;
        callback = "";
    }

    // 메일 발송
    public WooriMailDto(String title, String content, String wms_domain, String wms_nick, String sender_email, String sender_nickname, String receiver_nickname, String receiver_email, String member_regdate, String ssl, String ssl_port) {
        this();
        this.title = title;
        this.content = content;
        this.wms_domain = wms_domain;
        this.wms_nick = wms_nick;
        this.sender_email = sender_email;
        this.sender_nickname = sender_nickname;
        this.receiver_nickname = receiver_nickname;
        this.receiver_email = receiver_email;
        this.member_regdate = member_regdate;
        this.ssl = ssl;
        this.ssl_port = ssl_port;
        act = ACT_MAIL;
    }
}