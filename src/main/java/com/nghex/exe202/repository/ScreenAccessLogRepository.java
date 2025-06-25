package com.nghex.exe202.repository;

import com.nghex.exe202.entity.ScreenAccessLog;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ScreenAccessLogRepository extends JpaRepository<ScreenAccessLog, Integer> {

    // ===== BASIC QUERIES =====

    // 1. Lấy tất cả log theo user
    List<ScreenAccessLog> findByUserId(Integer userId);

    // 2. Lấy log của user trong khoảng thời gian
    List<ScreenAccessLog> findByUserIdAndAccessedAtBetween(Integer userId, Date start, Date end);


    // ===== STATISTICS =====

    // 3. Đếm số lượt truy cập theo từng màn hình
    @Query("SELECT s.screenName, COUNT(s) FROM ScreenAccessLog s GROUP BY s.screenName ORDER BY COUNT(s) DESC")
    List<Object[]> countAccessesByScreen();

    // 4. Đếm số lượt truy cập theo từng người dùng
    @Query("SELECT s.userId, COUNT(s) FROM ScreenAccessLog s GROUP BY s.userId ORDER BY COUNT(s) DESC")
    List<Object[]> countAccessesByUser();

    // 5. Thống kê số lượt truy cập theo ngày
    @Query("SELECT FUNCTION('FORMAT', s.accessedAt, 'yyyy-MM-dd'), COUNT(s) " +
            "FROM ScreenAccessLog s " +
            "GROUP BY FUNCTION('FORMAT', s.accessedAt, 'yyyy-MM-dd') " +
            "ORDER BY FUNCTION('FORMAT', s.accessedAt, 'yyyy-MM-dd') ASC")
    List<Object[]> countAccessesPerDay();

    // 6. Lấy top 5 màn hình được truy cập nhiều nhất
    @Query("SELECT s.screenName, COUNT(s) FROM ScreenAccessLog s GROUP BY s.screenName ORDER BY COUNT(s) DESC")
    List<Object[]> findTop5Screens(Pageable pageable);

    // 7. Truy xuất các màn hình được user truy cập gần nhất
    @Query("SELECT s FROM ScreenAccessLog s WHERE s.userId = :userId ORDER BY s.accessedAt DESC")
    List<ScreenAccessLog> findLatestAccessesByUser(Integer userId, Pageable pageable);


    // ===== ADVANCED ADMIN STATISTICS =====

    // 8. Thống kê số lượt truy cập từng màn hình theo từng ngày
    @Query("SELECT s.screenName, FUNCTION('FORMAT', s.accessedAt, 'yyyy-MM-dd'), COUNT(s) " +
            "FROM ScreenAccessLog s " +
            "GROUP BY s.screenName, FUNCTION('FORMAT', s.accessedAt, 'yyyy-MM-dd') " +
            "ORDER BY s.screenName, FUNCTION('FORMAT', s.accessedAt, 'yyyy-MM-dd')")
    List<Object[]> countAccessesByScreenPerDay();

    // 9. Thống kê số người dùng duy nhất mỗi ngày
    @Query("SELECT FUNCTION('FORMAT', s.accessedAt, 'yyyy-MM-dd'), COUNT(DISTINCT s.userId) " +
            "FROM ScreenAccessLog s " +
            "GROUP BY FUNCTION('FORMAT', s.accessedAt, 'yyyy-MM-dd') " +
            "ORDER BY FUNCTION('FORMAT', s.accessedAt, 'yyyy-MM-dd')")
    List<Object[]> countUniqueUsersPerDay();

    // 10. Top người dùng hoạt động nhiều nhất
    @Query("SELECT s.userId, COUNT(s) FROM ScreenAccessLog s " +
            "GROUP BY s.userId ORDER BY COUNT(s) DESC")
    List<Object[]> findTopActiveUsers();

    // 11. Thống kê user không hoạt động từ mốc thời gian nào đó
    @Query("SELECT u.id FROM User u WHERE u.id NOT IN (" +
            "SELECT DISTINCT s.userId FROM ScreenAccessLog s WHERE s.accessedAt >= :cutoff)")
    List<Integer> findInactiveUserIds(Date cutoff);
}
