package pl.estrix.backend.ocr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.estrix.backend.ocr.service.dto.GetTextExtractorResultRequest;
import pl.estrix.backend.ocr.service.dto.TextExtractorPostRequest;
import pl.estrix.backend.ocr.service.dto.TextExtractorPostResponse;
import pl.estrix.backend.settings.executor.ReadSettingCommandExecutor;
import pl.estrix.common.dto.model.SettingDto;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TextExtractorService {


    private static Logger LOG = LoggerFactory.getLogger(TextExtractorService.class);

    private static final String EXTRACTOR_URL = "http://162.19.227.81:8004/extract/";
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String PATH_IDENTIFIER = "%s/%s";
    private String basePath;
    private Gson gson = new Gson();
//    private final HttpClientConnectionManager cm;
    private static final int MAX_CONNECTION_COUNT = 10;
//    private CloseableHttpClient httpClient;
    private CloseableHttpClient httpClient;

    private ObjectMapper mapper;

    @Autowired
    private ReadSettingCommandExecutor readSettingCommandExecutor;

    public TextExtractorService() {
        this.httpClient = HttpClientBuilder.create().build();
        this.mapper = new ObjectMapper();
    }

    public String getExtractedText(String requestId) {
        HttpGet httpGet = new HttpGet(EXTRACTOR_URL + requestId);
        Map responseMap =  getEntityAndReleaseConnection(httpGet, Map.class);
        try {
            GetTextExtractorResultRequest rs = gson.fromJson(mapper.writeValueAsString(responseMap).toString(), GetTextExtractorResultRequest.class);
            return rs.getWords().stream().collect(Collectors.joining(" "));
        } catch (JsonProcessingException e) {
            return "External OCR System error";
        }
    }

    public String extractTextFromImage(String imagePath) {
        SettingDto versionDirectorySettingDto = readSettingCommandExecutor.findByName("versionDirectory");
        if (versionDirectorySettingDto != null) {
            basePath = versionDirectorySettingDto.getValue();
        }

        String base64Img = changeImageToBase64(imagePath);
        String requestID = UUID.randomUUID().toString();
        TextExtractorPostRequest postRequest = TextExtractorPostRequest
                .builder()
                .requestId(requestID)
                .base64Img(base64Img)
                .build();

        HttpPost postMethod = this.buildPostMethod(EXTRACTOR_URL);

//        Gson gson = new Gson();
        String json = gson.toJson(postRequest);
        HttpEntity entity = new ByteArrayEntity(json.getBytes(), ContentType.APPLICATION_JSON);
        postMethod.setEntity(entity);

        // execute the https post.
        int httpStatus;
        this.httpClient = HttpClientBuilder.create().build();

        Map responseMap =  getEntityAndReleaseConnection(postMethod, Map.class);
//        String json = ;
        try {
            TextExtractorPostResponse rs = gson.fromJson(mapper.writeValueAsString(responseMap).toString(), TextExtractorPostResponse.class);
            return rs.getRequestId();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        try (CloseableHttpResponse resp = httpClient.execute(postMethod)) {
//            StatusLine sl = resp.getStatusLine();
//            if (sl.getStatusCode() != 200) {
////                LOG.debug("getTimestampResponse()  httpStatus KO'" + sl.getStatusCode() + "', data to be timestamped :'" +
////                        Arrays.toString(dataToBeTimestamped) + "', " + STR_STAMP_CERT_INCLUDED + "'" + includeTimestampingCertificate + "'");
////                String errorMsg = "DTSS response http status was " + sl.getStatusCode() + " for timestamping request " +
////                        Base64.encodeBytes(rfc3161TimestampingRequestBytes, Base64.DONT_BREAK_LINES);
////                throw new EnvironmentException(EnvironmentException.Code.D3S_ENV_DXS, errorMsg);
//            }
//
//            TextExtractorPostResponse rs = getEntity(resp.getEntity(), TextExtractorPostResponse.class);
//
//            return rs.getRequestId();
//        } catch (IOException e) {
//            LOG.debug("getTimestampResponse() Failed to communicate with the DTSS on URL :'" + "', timestampingRequestB64 :'" + "'");
////            String errorMsg = "Failed to communicate with the DTSS on URL " + urlStr + " for RFC3161 timestamping request " +
////                    Base64.encodeBytes(rfc3161TimestampingRequestBytes, Base64.DONT_BREAK_LINES);
////            throw new EnvironmentException(e, EnvironmentException.Code.D3S_ENV_DXS, errorMsg);
//        } catch (Exception e) {
//            final String errorMsg = "Error while getting timestamp";
//            LOG.debug("getTimestampResponse(). Error message :'" + errorMsg +  "'");
////            throw new EnvironmentException(e, EnvironmentException.Code.D3S_ENV_DXS, errorMsg);
//        }

//        return "";
    }

    private <T> T getEntityAndReleaseConnection(HttpRequestBase httpRequest, Class<T> objectClass) {
        try {

            HttpResponse httpResponse = httpClient.execute(httpRequest);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity == null) {
                throw new RuntimeException("Error retrieving results from http request");
            }
            Object result = mapper.readValue(httpEntity.getContent(), Object.class);
            if (objectClass.isInstance(result)) {
                return objectClass.cast(result);
            }
            throw new RuntimeException("Can't parse retrieved object: " + result.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            httpRequest.releaseConnection();
        }
    }
    private <T> T getEntity(HttpEntity httpEntity, Class<T> objectClass) {
        try {

//            HttpResponse httpResponse = httpClient.execute(httpRequest);
//            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity == null) {
                throw new RuntimeException("Error retrieving results from http request");
            }
            Object result = mapper.readValue(httpEntity.getContent(), objectClass);
            if (objectClass.isInstance(result)) {
                return objectClass.cast(result);
            }
            throw new RuntimeException("Can't parse retrieved object: " + result.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
//            httpRequest.releaseConnection();
        }
    }

    private String changeImageToBase64(String imagePath) {
        File file = new File(String.format(PATH_IDENTIFIER,basePath,imagePath));
        if (!file.exists()){
            return null;
        }

        byte[] fileContent = null;
        try {
            fileContent = FileUtils.readFileToByteArray(file);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpPost buildPostMethod(String requestUrl) {
        HttpPost method = new HttpPost(requestUrl);
        // Request headers
        method.addHeader("Accept", "*/*");
//        method.addHeader("User-Agent", "AHSP");
//        method.addHeader("Content-Type", "application/timestamp-query");
//        method.addHeader("Content-Transfer-Encoding", CHARSET_UTF8);
        // Parameters
//        method.setEntity(new ByteArrayEntity(rfc3161TimestampRequest));
        return method;
    }

//    public static HttpClientConnectionManager buildConnectionManager(ConnectionParameters parameters,
//                                                                     int maxConnectionCount) {
//
//        // mise a jour des parametres SSL
//        SSLConnectionSocketFactory sslsf = null;
//        if (parameters.getClientSSLParameter() != null || parameters.getServerSSLParameter() != null) {
//            ClientSSLParameter clientSSLParameter = parameters.getClientSSLParameter();
//            if (clientSSLParameter == null) {
//                clientSSLParameter = new ClientSSLParameter();
//            }
//
//            ServerSSLParameter serverSSLParameter = parameters.getServerSSLParameter();
//            if (serverSSLParameter == null) {
//                serverSSLParameter = new ServerSSLParameter();
//            }
//            // Get ssl context
//            SSLSocketFactory socketFactory;
//            try {
//                SSLContextFactory contextFactory =
//                        new MutualSSLContextFactory(clientSSLParameter, serverSSLParameter);
//                socketFactory = contextFactory.getSocketFactory();
//            } catch (GeneralSecurityException ex) {
//                throw new EnvironmentException(ex, EnvironmentException.Code.D3S_ENV_CONFIGURATION,
//                        "Error setting SSL context: %s", ex.getMessage());
//            }
//
//            HostnameVerifier hnv = getHostnameVerifier(serverSSLParameter.getHostnameVerifier());
//
//            sslsf = new SSLConnectionSocketFactory(socketFactory, hnv);
//        }
//        PlainConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
//        RegistryBuilder<ConnectionSocketFactory> rb =
//                RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainsf);
//        if (sslsf != null) {
//            rb.register("https", sslsf);
//        }
//        Registry<ConnectionSocketFactory> r = rb.build();
//        PoolingHttpClientConnectionManager poolingConnectMgr = new PoolingHttpClientConnectionManager(r);
//        poolingConnectMgr.setMaxTotal(maxConnectionCount);
//        poolingConnectMgr.setDefaultMaxPerRoute(maxConnectionCount);
//
//        // gestion du timeout
//        if (parameters.getHttpTimeout() != null) {
//            LOG.debug("HTTPTimeOut : %d", parameters.getHttpTimeout());
//            // TODO
//        }
//        return poolingConnectMgr;
//    }
}
