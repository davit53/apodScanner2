/**
 * @author Davit Najaryan
 * @description 
 * @date January 28, 2025
 */

package apod;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageAnalyzer 
{
    /**
     * @description the function analyzes the image uisng google cloud vision api
     * @param imageUrl image url
     * @return results of the analysis in proper formatting
     */
    public static String analyzeImg(String imageUrl) 
    {
        try {
            // Download the image data from the URL
            URL url = new URL(imageUrl);
            ByteString imgBytes = ByteString.readFrom(url.openStream());

            // Build the image object for the API request
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feature = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
            
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feature)
                    .setImage(img)
                    .build();
            List<AnnotateImageRequest> requests = new ArrayList<>();
            requests.add(request);

            // Initialize the client that will be used to send requests. This client only needs to be created once.
            try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
                BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
                List<AnnotateImageResponse> responses = response.getResponsesList();

                StringBuilder resultBuilder = new StringBuilder("Detected: ");
                for (AnnotateImageResponse res : responses) {
                    if (res.hasError()) {
                        return "Error: " + res.getError().getMessage();
                    }
                    for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                        // Append each label with its confidence score (converted to a percentage)
                        resultBuilder.append(annotation.getDescription())
                            .append(" (")
                            .append(String.format("%.2f", annotation.getScore() * 100))
                            .append("%), ");
                    }
                }
                // Remove the trailing comma and space
                if(resultBuilder.length() > 10) {
                    resultBuilder.setLength(resultBuilder.length() - 2);
                }
                return resultBuilder.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Analysis failed: " + e.getMessage();
        }
    }
}
