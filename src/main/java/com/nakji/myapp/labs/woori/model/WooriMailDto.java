package com.nakji.myapp.labs.woori.model;

public record WooriMailDto(
        String authkey,
        String type,
        String mid,
        String act,
        String title,
        String content,
        String wmsDomain,
        String wmsNick,
        String senderEmail,
        String senderNickname,
        String receiverNickname,
        String receiverEmail,
        String memberRegdate,
        String ssl,
        String sslPort,
        String domain,
        String callback
) {
    public WooriMailDto(String authkey, String domain) {
        this(authkey, "api", "auth_woorimail", "dispWwapimanagerStatusApi",
                null, null, null, null, null, null, null, null, null, null, null,
                domain, ""
        );
    }
}