package com.example.backend.codeGenerator;

import okhttp3.*;

public class JudgeTest {
    public static void main(String[] args) throws Exception {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"language_id\": 1,\r\n    \"source_code\": \"I2luY2x1ZGUgPHN0ZGlvLmg+CgppbnQgbWFpbih2b2lkKSB7CiAgY2hhciBuYW1lWzEwXTsKICBzY2FuZigiJXMiLCBuYW1lKTsKICBwcmludGYoImhlbGxvLCAlc1xuIiwgbmFtZSk7CiAgcmV0dXJuIDA7Cn0=\",\r\n    \"stdin\": \"SnVkZ2Uw\"\r\n}");
        Request request = new Request.Builder()
                .url("https://judge0-extra-ce.p.rapidapi.com/submissions?base64_encoded=true&wait=false&fields=*")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-RapidAPI-Key", "30aec1aee9msh3853a2407d66699p112776jsn580b58a4ab68")
                .addHeader("X-RapidAPI-Host", "judge0-extra-ce.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response);
        if (response.isSuccessful() && response.body() != null) {
            String responseBody = response.body().string();
            Headers headers = response.headers();
            for (String name : headers.names()) {
                System.out.println(name + ": " + headers.get(name));
            }
            System.out.println(responseBody);
        } else {
            System.err.println("Request failed with code: " + response.code());
        }
    }
}

