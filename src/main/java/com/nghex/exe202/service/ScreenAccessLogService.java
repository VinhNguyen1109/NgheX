package com.nghex.exe202.service;

import com.nghex.exe202.entity.ScreenAccessLog;

import java.util.Date;
import java.util.List;

public interface ScreenAccessLogService {

    // Basic
    List<ScreenAccessLog> getAllLogsByUser(Integer userId);

    List<ScreenAccessLog> getLogsByUserInPeriod(Integer userId, Date start, Date end);

    List<ScreenAccessLog> getLatestAccessByUser(Integer userId, int limit);

    // Statistics
    List<Object[]> getAccessCountByScreen();

    List<Object[]> getAccessCountByUser();

    List<Object[]> getAccessCountPerDay();

    List<Object[]> getTop5MostVisitedScreens();

    List<Object[]> getAccessCountByScreenPerDay();

    List<Object[]> getUniqueUserCountPerDay();

    List<Object[]> getTopActiveUsers(int limit);

    List<Integer> getInactiveUserIdsSince(Date cutoff);

    void save(ScreenAccessLog log);
}