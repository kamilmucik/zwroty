package pl.estrix.zwroty.textextractor.domain;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.Block;
import software.amazon.awssdk.services.textract.model.BlockType;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TextExtractorDomainService {

//    @Value("${aws.access.key}")
//    private String AWS_ACCESS_KEY_ID;
//    @Value("${aws.secret.key}")
//    private String AWS_SECRET_ACCESS_KEY;

    private static final String AWS_ACCESS_KEY_ID = "xxx";
    private static final String AWS_SECRET_ACCESS_KEY = "xxx";


    AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY);

    TextractClient textractClient = TextractClient.builder()
            .region(Region.EU_CENTRAL_1)
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();


    public List<String> shouldExtractTextFromImageTest(String base64Image) {
        ByteBuffer imageBytes;
        List<String> result = null;
        try (InputStream inputStream = base64ToInputStream(base64Image)) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
            result = extract(imageBytes.array());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Konwertuje tekst Base64 reprezentujący obraz na InputStream.
     *
     * @param base64Image String z obrazem zakodowanym w Base64
     * @return InputStream zawierający obraz
     */
    public static InputStream base64ToInputStream(String base64Image) {
        // Dekodowanie base64 na tablicę bajtów
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        // Zwracanie jako InputStream
        return new ByteArrayInputStream(imageBytes);
    }

    public List<String> extract(byte[] imageBytes) {
        DetectDocumentTextResponse response = textractClient.detectDocumentText(request -> request
                .document(document -> document
                        .bytes(SdkBytes.fromByteArray(imageBytes))
                        .build())
                .build());

        return transformTextDetectionResponse(response);
    }

    private List<String> transformTextDetectionResponse(DetectDocumentTextResponse response) {
        return response.blocks()
                .stream()
                .filter(block -> block.blockType().equals(BlockType.LINE))
                .map(Block::text)
                .collect(Collectors.toList());
    }


}
