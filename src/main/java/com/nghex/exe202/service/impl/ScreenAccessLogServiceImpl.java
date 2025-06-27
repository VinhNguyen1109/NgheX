package com.nghex.exe202.service.impl;

import com.nghex.exe202.entity.ScreenAccessLog;
import com.nghex.exe202.repository.ScreenAccessLogRepository;
import com.nghex.exe202.service.ScreenAccessLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ScreenAccessLogServiceImpl implements ScreenAccessLogService {

    private final ScreenAccessLogRepository repository;

    // ===== Basic Methods =====

    @Override
    public List<ScreenAccessLog> getAllLogsByUser(Integer userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<ScreenAccessLog> getLogsByUserInPeriod(Integer userId, Date start, Date end) {
        return repository.findByUserIdAndAccessedAtBetween(userId, start, end);
    }

    @Override
    public List<ScreenAccessLog> getLatestAccessByUser(Integer userId, int limit) {
        return repository.findLatestAccessesByUser(userId, PageRequest.of(0, limit));
    }

    // ===== Statistics Methods =====

    @Override
    public List<Object[]> getAccessCountByScreen() {
        return repository.countAccessesByScreen();
    }

    @Override
    public List<Object[]> getAccessCountByUser() {
        return repository.countAccessesByUser();
    }

    @Override
    public List<Object[]> getAccessCountPerDay() {
        return repository.countAccessesPerDay();
    }

    @Override
    public List<Object[]> getTop5MostVisitedScreens() {
        return repository.findTop5Screens(PageRequest.of(0, 5));
    }

    @Override
    public List<Object[]> getAccessCountByScreenPerDay() {
        return repository.countAccessesByScreenPerDay();
    }

    @Override
    public List<Object[]> getUniqueUserCountPerDay() {
        return repository.countUniqueUsersPerDay();
    }

    @Override
    public List<Object[]> getTopActiveUsers(int limit) {
        return repository.findTopActiveUsers().stream().limit(limit).toList();
    }

    @Override
    public List<Integer> getInactiveUserIdsSince(Date cutoff) {
        return repository.findInactiveUserIds(cutoff);
    }

    @Override
    public void save(ScreenAccessLog log) {
        List<ScreenAccessLog> data = new ArrayList<>();

        // Lấy bản ghi cuối của user mà chưa có left_at (tức là chưa kết thúc)
        ScreenAccessLog preScreen = repository.findTopByUserIdAndLeftAtIsNullOrderByAccessedAtDesc(log.getUserId());

        if (preScreen != null) {
            System.out.println(preScreen.getLogId());
            preScreen.setLeftAt(log.getAccessedAt());
            data.add(preScreen);
        }

        data.add(log); // log hiện tại
        repository.saveAll(data);
    }

    @Override
    public List<Map<String, Object>> getAverageTimePerScreen() {
        List<Object[]> raw = repository.getAverageTimePerScreen();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : raw) {
            Map<String, Object> map = new HashMap<>();
            map.put("screenName", row[0]);
            map.put("avgDurationSec", row[1]);
            result.add(map);
        }
        return result;
    }


}
