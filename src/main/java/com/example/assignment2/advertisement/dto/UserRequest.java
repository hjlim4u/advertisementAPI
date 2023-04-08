package com.example.assignment2.advertisement.dto;

import com.example.assignment2.advertisement.domain.Advertisement;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserRequest {
    List<Advertisement> advertisements;
    long userId;
    int total;
}
