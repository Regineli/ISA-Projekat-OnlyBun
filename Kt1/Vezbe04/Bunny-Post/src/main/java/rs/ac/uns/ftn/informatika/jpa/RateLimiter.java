package rs.ac.uns.ftn.informatika.jpa;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class RateLimiter {

    private final Map<String, UserRequestInfo> requestMap = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private static final int BLOCK_TIME_MINUTES = 15; // Blokira na 15 minuta
    
    private final Map<String, List<Long>> requests = new ConcurrentHashMap<>();


    public boolean isBlocked(String ipAddress) {
        UserRequestInfo info = requestMap.get(ipAddress);
        
        System.out.println("ip address: " + ipAddress);

        if (info == null) {
        	recordAttempt(ipAddress);
            return false;
        }
        	
        recordAttempt(ipAddress);
        System.out.println("number of attempts: " + info.getAttempts());
        
        if (info.getAttempts() < MAX_ATTEMPTS) {
            return false;
        }

        if (info.getBlockedUntil() != null && info.getBlockedUntil().isAfter(LocalDateTime.now())) {
            return true;
        }

        // Resetuj ako je blokada istekla
        requestMap.remove(ipAddress);
        return false;
    }
    
    public boolean isTooManuRequests(String userId) {
    	long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC); 
        requests.putIfAbsent(userId, new ArrayList<>());

        List<Long> recentRequests = requests.get(userId).stream()
                .filter(timestamp -> timestamp > now - 60)
                .collect(Collectors.toList());
        if (recentRequests.size() >= 	MAX_ATTEMPTS) {
            return false;
        }
        
        recentRequests.add(now);
        requests.put(userId, recentRequests);
    	
        return true;
    }

    public void recordAttempt(String ipAddress) {
        UserRequestInfo info = requestMap.computeIfAbsent(ipAddress, k -> new UserRequestInfo());
        info.incrementAttempts();

        if (info.getAttempts() >= MAX_ATTEMPTS) {
            info.setBlockedUntil(LocalDateTime.now().plusMinutes(BLOCK_TIME_MINUTES));
        }
    }

    private static class UserRequestInfo {
        private int attempts;
        private LocalDateTime blockedUntil;

        public int getAttempts() {
            return attempts;
        }

        public void incrementAttempts() {
            attempts++;
        }

        public LocalDateTime getBlockedUntil() {
            return blockedUntil;
        }

        public void setBlockedUntil(LocalDateTime blockedUntil) {
            this.blockedUntil = blockedUntil;
        }
    }
}
