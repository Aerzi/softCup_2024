package com.example.backend.model.entity.pdftranslate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TextSplitter {
    public static List<String> splitTextByBytes(String text, int maxBytes, Charset charset) {
        List<String> parts = new ArrayList<>();
        int byteCount = 0;
        int lastSplit = 0;

        for (int i = 0; i < text.length(); i++) {
            String substring = text.substring(lastSplit, i + 1);
            int currentBytes = substring.getBytes(charset).length;

            if (currentBytes > maxBytes) {
                parts.add(text.substring(lastSplit, i));
                lastSplit = i;
            }
        }

        // Add remaining text
        if (lastSplit < text.length()) {
            parts.add(text.substring(lastSplit));
        }

        return parts;
    }
}
