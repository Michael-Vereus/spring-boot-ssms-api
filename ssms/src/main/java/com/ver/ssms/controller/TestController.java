package com.ver.ssms.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ssms/api")
public class TestController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> checkHealth(){
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long usedMemory = totalMemory - freeMemory;

        String dbStatus;
        try {
            Object result = entityManager.createNativeQuery("SELECT 1").getSingleResult();
            dbStatus = (result != null) ? "Connected" : "Disconnected";
        } catch (Exception e) {
            dbStatus = "Disconnected: " + e.getMessage();
        }

        return ResponseEntity.ok(Map.of(
                "status     : ", "Up",
                "message    : ", "API is running ",
                "db_conn    :", dbStatus,
                "used       : ", usedMemory + " MB",
                "total      : ", totalMemory + " MB",
                "free       : ", freeMemory + " MB"
        ));
    }
}
