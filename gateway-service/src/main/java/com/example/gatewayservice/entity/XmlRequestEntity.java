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
@Table(name = "xml_request")
public class XmlRequestEntity extends CommonBaseEntity {

    @Column(name = "requestId", unique = true)
    @JsonAlias({"commandId", "requestId"})
    @JsonProperty("commandId")
    private String requestId;

    @Column(name = "producerId")
    @JsonAlias({"player", "producerId", "userId"})
    @JsonProperty("player")
    private String producerId;

    @JsonAlias({"timestamp"})
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @Column(name = "sessionId", unique = true)
    @JsonAlias({"sessionId", "session"})
    @JsonProperty("session")
    private Long sessionId;

    public XmlRequestEntity() {
        super();
    }
}
