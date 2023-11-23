package com.example.gatewayservice.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class StatisticsEntity extends CommonBaseEntity {

    @Column(name = "userId", unique = true)
    @JsonAlias({"player", "producerId", "userId"})
    @JsonProperty("userId")
    private String producerId; // userId is the same as producerId in Json and player in Xml

    @CreationTimestamp
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "sessionIds")
    private List<Long> sessionIds;

    @Builder
    public StatisticsEntity(String producerId, List<Long> sessionIds, LocalDateTime timestamp) {
        this.producerId = producerId;
        this.sessionIds = sessionIds;
        this.timestamp = timestamp;
    }
}
