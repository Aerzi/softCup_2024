package com.example.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.config.property.SystemConfig;
import com.example.backend.model.entity.aitranslation.AITranslationDocResponse;
import com.example.backend.model.entity.aitranslation.AITranslationResponse;
import com.example.backend.model.entity.aitranslation.AITranslationText;
import com.example.backend.model.entity.pdftranslate.CustomTextExtractionStrategy;
import com.example.backend.model.entity.pdftranslate.TextSplitter;
import com.example.backend.model.request.student.pdf.PDFDownloadRequest;
import com.example.backend.service.PDFSaveService;
import com.example.backend.service.PDFTranslationService;
import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import com.itextpdf.kernel.geom.Vector;

@Service
public class PDFTranslationServiceImpl implements PDFTranslationService {
    private final SystemConfig systemConfig;
    private static final Gson gson = new Gson();
    private final PDFSaveService pdfSaveService;

    @Autowired
    public PDFTranslationServiceImpl(SystemConfig systemConfig, PDFSaveService pdfSaveService) {
        this.systemConfig = systemConfig;
        this.pdfSaveService = pdfSaveService;
    }

    // 处理请求URL，包含鉴权
    private String buildRequetUrl() {
        URL url = null;
        // 替换调schema前缀 ，原因是URL库不支持解析包含ws,wss schema的url
        String httpRequestUrl = systemConfig.getAiTranslationConfig().getRequestUrl().replace("ws://", "http://").replace("wss://", "https://");
        try {
            url = new URL(httpRequestUrl);
            //获取当前日期并格式化
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());
            //String date="Thu, 18 Nov 2021 03:05:18 GMT";
            String host = url.getHost();
           /* if (url.getPort()!=80 && url.getPort() !=443){
                host = host +":"+String.valueOf(url.getPort());
            }*/
            StringBuilder builder = new StringBuilder("host: ").append(host).append("\n").//
                    append("date: ").append(date).append("\n").//
                    append("POST ").append(url.getPath()).append(" HTTP/1.1");
            Charset charset = Charset.forName("UTF-8");
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(systemConfig.getAiTranslationConfig().getApiSecret().getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);
            //System.out.println(sha);
            String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", systemConfig.getAiTranslationConfig().getApiKey(), "hmac-sha256", "host date request-line", sha);
            String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
            return String.format("%s?authorization=%s&host=%s&date=%s", systemConfig.getAiTranslationConfig().getRequestUrl(), URLEncoder.encode(authBase), URLEncoder.encode(host), URLEncoder.encode(date));
        } catch (Exception e) {
            throw new RuntimeException("assemble requestUrl error:" + e.getMessage());
        }
    }

    private String buildParam(String text) {
        JSONObject param = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("app_id", systemConfig.getAiTranslationConfig().getAppId());
        header.put("status", 3);
        header.put("res_id", systemConfig.getAiTranslationConfig().getResId());

        JSONObject its = new JSONObject();
        its.put("from", "en");
        its.put("to", "cn");
        its.put("result", new JSONObject());

        JSONObject parameter = new JSONObject();
        parameter.put("its", its);

        JSONObject input_data = new JSONObject();
        input_data.put("encoding", "utf8");
        input_data.put("status", 3);
        input_data.put("text", Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8)));

        JSONObject payload = new JSONObject();
        payload.put("input_data", input_data);

        param.put("header", header);
        param.put("parameter", parameter);
        param.put("payload", payload);

        String jsonString = param.toJSONString();

        return jsonString;
    }

    // 读取流数据
    private String readAllBytes(InputStream is) throws IOException {
        byte[] b = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int len = 0;
        while ((len = is.read(b)) != -1) {
            sb.append(new String(b, 0, len, "utf-8"));
        }
        return sb.toString();
    }

    public String doRequest(String text) throws Exception {
        URL realUrl = new URL(buildRequetUrl());
        URLConnection connection = realUrl.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type", "application/json");
        OutputStream out = httpURLConnection.getOutputStream();
        String params = buildParam(text);
        System.out.println("params=>" + params.replaceAll(" ", ""));
        out.write(params.getBytes());
        out.flush();
        InputStream is = null;
        try {
            is = httpURLConnection.getInputStream();
        } catch (Exception e) {
            is = httpURLConnection.getErrorStream();
            throw new Exception("make request error:" + "code is " + httpURLConnection.getResponseMessage() + readAllBytes(is));
        }
        return readAllBytes(is);
    }

    @Override
    public AITranslationText translate(String text) {
        long startTime = System.currentTimeMillis();
        AITranslationText aiTranslationText = null;
        try {
            String resp = doRequest(text);
            System.out.println("resp=>" + resp);
            AITranslationResponse aiTranslationResponse = gson.fromJson(resp, AITranslationResponse.class);
            String textBase64Decode = new String(Base64.getDecoder().decode(aiTranslationResponse.getPayload().getResult().getText()), "UTF-8");
            aiTranslationText = gson.fromJson(textBase64Decode, AITranslationText.class);
            System.out.println("text字段Base64解码后=>" + aiTranslationText.getTrans_result().getDst());
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("本次调用耗时: " + (endTime - startTime) + " ms");

        return aiTranslationText;
    }

    private static List<String> splitTextByFontSize(List<TextRenderInfo> textRenderInfos) {
        List<String> paragraphs = new ArrayList<>();
        StringBuilder currentParagraph = new StringBuilder();
        Float lastFontSize = null;

        for (TextRenderInfo renderInfo : textRenderInfos) {
            Vector ascent = renderInfo.getAscentLine().getStartPoint();
            Vector descent = renderInfo.getDescentLine().getEndPoint();
            Float currentFontSize = ascent.get(1) - descent.get(1);

            // 如果字体大小变化，认为是新段落
            if (lastFontSize != null && !lastFontSize.equals(currentFontSize)) {
                paragraphs.add(currentParagraph.toString());
                currentParagraph = new StringBuilder();
            }

            currentParagraph.append(renderInfo.getText());
            lastFontSize = currentFontSize;
        }

        // 添加最后一个段落
        if (currentParagraph.length() > 0) {
            paragraphs.add(currentParagraph.toString());
        }

        return paragraphs;
    }

    @Override
    public AITranslationDocResponse translateDoc(MultipartFile file, int maxBytes, Charset charset) {
        PdfDocument pdfDoc = null;
        try {
            pdfDoc = new PdfDocument(new PdfReader(file.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> translatedParagraphs = new ArrayList<>();

        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
            CustomTextExtractionStrategy strategy = new CustomTextExtractionStrategy();
            PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i), strategy);

            List<TextRenderInfo> textRenderInfos = strategy.getTextRenderInfos();
            List<String> paragraphs = splitTextByFontSize(textRenderInfos);

            for (String paragraph : paragraphs) {
                if (paragraph.trim().isEmpty()) continue;

                List<String> parts = TextSplitter.splitTextByBytes(paragraph, maxBytes, charset);
                StringBuilder translatedParagraph = new StringBuilder();
                for (String part : parts) {
                    translatedParagraph.append(translate(part).getTrans_result().getDst());
                }

                translatedParagraphs.add(translatedParagraph.toString());
            }
        }
        pdfDoc.close();

        AITranslationDocResponse response = new AITranslationDocResponse();
        response.setTexts(translatedParagraphs);

        PDFDownloadRequest request = new PDFDownloadRequest();
        request.setResult(response.getTexts());

        String url = pdfSaveService.pdfGenerate(request);
        response.setUrl(url);

        return response;
    }

}
