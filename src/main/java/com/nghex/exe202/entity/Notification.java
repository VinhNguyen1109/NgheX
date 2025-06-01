package com.nghex.exe202.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User customer;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;
    private boolean sendStatus;
}
