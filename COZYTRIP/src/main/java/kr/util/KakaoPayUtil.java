package kr.util;

import java.io.*;
import java.net.*;
import java.util.*;

public class KakaoPayUtil {

    private static final String HOST = "https://kapi.kakao.com";
    private static final String ADMIN_KEY = "c6ead0b2e84b575a174ec484c9764b4e";
    private static final String CID = "TC0ONETIME";
    
    public static Map<String, String> kakaoPayReady(
            String orderId,
            String itemName,
            int quantity,
            int totalAmount,
            String approvalUrl,
            String cancelUrl,
            String failUrl
    ) throws Exception {
        // 요청 파라미터 만들기
        String params = "cid=" + CID
            + "&partner_order_id=" + orderId
            + "&partner_user_id=COZYTRIP"
            + "&item_name=" + URLEncoder.encode(itemName, "UTF-8")
            + "&quantity=" + quantity
            + "&total_amount=" + totalAmount
            + "&tax_free_amount=0"
            + "&approval_url=" + URLEncoder.encode(approvalUrl, "UTF-8")
            + "&cancel_url=" + URLEncoder.encode(cancelUrl, "UTF-8")
            + "&fail_url=" + URLEncoder.encode(failUrl, "UTF-8");
            
        // 카카오페이 API 호출
        String response = callKakaoPayApi("/v1/payment/ready", params);
        
        // 응답에서 필요한 값 추출
        Map<String, String> resultMap = new HashMap<String, String>();
        
        String tid = getValueFromJson(response, "tid");
        String nextRedirectPcUrl = getValueFromJson(response, "next_redirect_pc_url");
        
        if (tid == null || nextRedirectPcUrl == null) {
            throw new Exception("카카오페이 응답에서 필수 값을 찾을 수 없습니다");
        }
        
        resultMap.put("tid", tid);
        resultMap.put("next_redirect_pc_url", nextRedirectPcUrl);
        
        return resultMap;
    }
    
    public static Map<String, String> kakaoPayApprove(
            String tid,
            String orderId,
            String pgToken
    ) throws Exception {
        // 요청 파라미터 만들기
        String params = "cid=" + CID
            + "&tid=" + tid
            + "&partner_order_id=" + orderId
            + "&partner_user_id=COZYTRIP"
            + "&pg_token=" + pgToken;
            
        // 카카오페이 API 호출
        String response = callKakaoPayApi("/v1/payment/approve", params);
        
        // 응답에서 필요한 값 추출
        Map<String, String> resultMap = new HashMap<String, String>();
        
        String aid = getValueFromJson(response, "aid");
        String paymentMethodType = getValueFromJson(response, "payment_method_type");
        
        if (aid == null) {
            throw new Exception("카카오페이 승인 응답에서 aid를 찾을 수 없습니다");
        }
        
        resultMap.put("aid", aid);
        if (paymentMethodType != null) {
            resultMap.put("payment_method_type", paymentMethodType);
        }
        
        return resultMap;
    }
    
    private static String callKakaoPayApi(String path, String params) throws Exception {
        URL url = new URL(HOST + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "KakaoAK " + ADMIN_KEY);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);
        
        System.out.println("요청: " + params);
        
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(params);
        writer.flush();
        writer.close();
        
        int responseCode = conn.getResponseCode();
        System.out.println("응답 코드: " + responseCode);
        
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(responseCode == 200 ? conn.getInputStream() : conn.getErrorStream()));
        
        StringBuilder response = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        String responseBody = response.toString();
        System.out.println("응답: " + responseBody);
        
        if (responseCode != 200) {
            throw new Exception("카카오페이 API 오류: " + responseBody);
        }
        
        return responseBody;
    }
    
    private static String getValueFromJson(String json, String key) {
        String searchKey = "\"" + key + "\":\"";
        int start = json.indexOf(searchKey);
        
        if (start == -1) {
            System.out.println(key + " 못찾음");
            return null;
        }
        
        start = start + searchKey.length();
        int end = json.indexOf('"', start);
        
        if (end == -1) {
            System.out.println(key + " 값 형식 이상함");
            return null;
        }
        
        String value = json.substring(start, end);
        System.out.println(key + ": " + value);
        return value;
    }
    
    // JSON 객체에서 값 추출
    private static String extractValue(String json, String key) {
        String keyWithQuotes = "\"" + key + "\":\"";
        int start = json.indexOf(keyWithQuotes);
        
        if (start == -1) {
            System.out.println("JSON에서 키 '" + key + "'를 찾을 수 없습니다.");
            return null;
        }
        
        start += keyWithQuotes.length();
        int end = json.indexOf('"', start);
        
        if (end == -1) {
            System.out.println("값 형식이 올바르지 않습니다.");
            return null;
        }
        
        return json.substring(start, end);
    }
    
    // 중괄호 매칭 찾기
    private static int findMatchingBrace(String json, int startIndex) {
        try {
            // 공백 건너뛰기
            while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
                startIndex++;
            }
            
            if (startIndex >= json.length() || json.charAt(startIndex) != '{') {
                return -1;
            }
            
            int count = 1;
            for (int i = startIndex + 1; i < json.length(); i++) {
                if (json.charAt(i) == '{') {
                    count++;
                } else if (json.charAt(i) == '}') {
                    count--;
                    if (count == 0) {
                        return i;
                    }
                }
            }
            
            return -1;
        } catch (Exception e) {
            System.err.println("중괄호 매칭 찾기 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}