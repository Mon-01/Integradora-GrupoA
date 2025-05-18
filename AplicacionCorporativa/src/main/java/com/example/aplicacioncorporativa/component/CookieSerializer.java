package com.example.aplicacioncorporativa.component;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CookieSerializer {

    public static String serialize(List<String> emails) {
        // JSON manual simple: ["email1","email2"]
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < emails.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(emails.get(i)).append("\"");
        }
        sb.append("]");

        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }

    public static List<String> deserialize(String cookieValue) {
        List<String> emails = new ArrayList<>();
        if (cookieValue == null || cookieValue.isEmpty()) {
            return emails;
        }

        try {
            String json = new String(Base64.getDecoder().decode(cookieValue));
            // Parseo manual simple
            String[] parts = json.replaceAll("[\\[\\]\"]", "").split(",");
            for (String part : parts) {
                if (!part.trim().isEmpty()) {
                    emails.add(part.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emails;
    }
}
