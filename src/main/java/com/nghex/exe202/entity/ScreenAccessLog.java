package com.nghex.exe202.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "screen_access_log")
public class ScreenAccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "screen_name", nullable = false, length = 100)
    private String screenName;

    @Column(name = "accessed_at", columnDefinition = "DATETIME", nullable = false)
    private Date accessedAt;


    @Column(name = "left_at", columnDefinition = "DATETIME")
    private Date leftAt;

    @Override
    public String toString() {
        return "ScreenAccessLog{" +
                "logId=" + logId +
                ", userId=" + userId +
                ", screenName='" + screenName + '\'' +
                ", accessedAt=" + accessedAt +
                '}';
    }
}