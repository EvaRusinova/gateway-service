package com.example.gatewayservice.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "json_request")
public class JsonRequestEntity extends CommonBaseEntity {

    @Column(name = "requestId", unique = true)
    @JsonAlias({"requestId", "commandId"})
    @JsonProperty("requestId")
    private String requestId;

    @Column(name = "producerId")
    @JsonAlias({"player", "producerId", "userId"})
    @JsonProperty("producerId")
    private String producerId;

    @JsonAlias({"timestamp"})
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @Column(name = "sessionId", unique = true)
    @JsonAlias({"sessionId", "session"})
    @JsonProperty("sessionId")
    private Long sessionId;

    public JsonRequestEntity() {
        super();
    }
}
