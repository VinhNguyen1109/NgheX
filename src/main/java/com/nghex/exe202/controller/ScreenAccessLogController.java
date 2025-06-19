package com.nghex.exe202.controller;

import com.nghex.exe202.configuration.JwtProvider;
import com.nghex.exe202.entity.ScreenAccessLog;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.exception.UserException;
import com.nghex.exe202.service.ScreenAccessLogService;
import com.nghex.exe202.service.UserService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScreenAccessLogController {

    private final ScreenAccessLogService logService;
    private final JwtProvider jwtProvider;

    private final UserService userService;
    // ===== Basic Logs =====

    @GetMapping("/user/{userId}")
    public List<ScreenAccessLog> getLogsByUser(@PathVariable Integer userId) {
        return logService.getAllLogsByUser(userId);
    }

    @GetMapping("/user/{userId}/range")
    public List<ScreenAccessLog> getLogsByUserInRange(
            @PathVariable Integer userId,
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end
    ) {
        return logService.getLogsByUserInPeriod(userId, start, end);
    }

    @GetMapping("/user/{userId}/latest")
    public List<ScreenAccessLog> getLatestLogsByUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return logService.getLatestAccessByUser(userId, limit);
    }

    // ===== Statistics =====

    @GetMapping("/stats/screen")
    public List<Object[]> getAccessCountByScreen() {
        return logService.getAccessCountByScreen();
    }

    @GetMapping("/stats/user")
    public List<Object[]> getAccessCountByUser() {
        return logService.getAccessCountByUser();
    }

    @GetMapping("/stats/daily")
    public List<Object[]> getAccessCountPerDay() {
        return logService.getAccessCountPerDay();
    }

    @GetMapping("/stats/screen/daily")
    public List<Object[]> getAccessCountByScreenPerDay() {
        return logService.getAccessCountByScreenPerDay();
    }

    @GetMapping("/stats/users/unique")
    public List<Object[]> getUniqueUserCountPerDay() {
        return logService.getUniqueUserCountPerDay();
    }

    @GetMapping("/stats/screens/top")
    public List<Object[]> getTop5Screens() {
        return logService.getTop5MostVisitedScreens();
    }

    @GetMapping("/stats/users/top")
    public List<Object[]> getTopActiveUsers(@RequestParam(defaultValue = "10") int limit) {
        return logService.getTopActiveUsers(limit);
    }

    @GetMapping("/stats/users/inactive")
    public List<Integer> getInactiveUsersSince(
            @RequestParam("since") @DateTimeFormat(pattern = "yyyy-MM-dd") Date since
    ) {
        return logService.getInactiveUserIdsSince(since);
    }
    @PostMapping("/track")
    public ResponseEntity<Void> trackScreen(@RequestHeader("Authorization") String jwt,
                                            @RequestBody Map<String, String> body) throws UserException {

        String screenName = body.get("screenName");

        if (jwt == null || !jwt.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        User user = userService.findUserProfileByJwt(jwt);

        System.out.println("user " + user.getEmail());
        ScreenAccessLog log = ScreenAccessLog.builder()
                .userId(user.getId().intValue())
                .screenName(screenName)
                .accessedAt(new Date())
                .build();
        System.out.println("checkkkk");
        System.out.println(log.toString());
        logService.save(log);
        return ResponseEntity.ok().build();
    }


}