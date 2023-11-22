package com.example.gatewayservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@Table(name = "common_base_entity")
public class CommonBaseEntity implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "requestId")
    private String requestId; // Same as commandId in XML

    @Column(name = "userId")
    private String userId;

    @CreationTimestamp
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "producerId")
    private String producerId; // Same as player in XML


    @Column(name = "sessionId")
    private Long sessionId;

    @JsonIgnore
    @Column(name = "response")
    private String response;

}
