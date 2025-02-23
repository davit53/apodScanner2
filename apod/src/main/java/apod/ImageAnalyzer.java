/**
 * @author Davit Najaryan
 * @description this class will handle the analysis of the image 
 * @date January 28, 2025
 */
 
package apod;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.protobuf.ByteString;

import java.io.InputStream;
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
        try 
        {

            URL url;
            Image img;
            ByteString byteImage;
            Feature type;
            List<AnnotateImageRequest> API_requests_array;

            //convert string of the url to a url object readable by java
            url = new URL(imageUrl);

            //open the image to a stream
            InputStream stream = url.openStream();

            //covert the image data to ByteString (used by the Google API to represent binary data)
            byteImage = ByteString.readFrom(stream);

            //make an image object to give to the API
            //set the contect of the image to the bytes
            //this will hold the raw bytes of the image file
            img = Image.newBuilder().setContent(byteImage).build();

            //make a feature aspect to tell the API what type of analasys to perform on the image
            type = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
            
            //make the request to be sent to the google Vision API containing all the data
            AnnotateImageRequest req = AnnotateImageRequest.newBuilder()
                    .addFeatures(type) //requet label detection feature 
                    .setImage(img) //the image
                    .build();
                
            //google vision API takes multiple images at once for analasys for efficency therefore must make an array
            API_requests_array = new ArrayList<>();

            //add the current request to the array
            API_requests_array.add(req);

            return "";
        } catch (Exception e) {

            e.printStackTrace();
            return e.getMessage();

        }
    }
}
