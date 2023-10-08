package com.byt3social.acompanhamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenDTO(
        @JsonProperty("access_token")
        String accessToken
) {
}
