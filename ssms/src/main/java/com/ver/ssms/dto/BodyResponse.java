package com.ver.ssms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class BodyResponse<T> {
    private boolean status;
    private Map<String, T> response;

    public static <T>BodyResponse<T> success(Map<String,T> result){
        return new BodyResponse<T>(true, result);
    }
    public static <T>BodyResponse<T> fail(Map<String,T> result){
        return new BodyResponse<T>(false, result);
    }

    public static <T>BodyResponse<T> debug(T t){
        return new BodyResponse<T>(false, Map.of("debug_mode", t));
    }
}
