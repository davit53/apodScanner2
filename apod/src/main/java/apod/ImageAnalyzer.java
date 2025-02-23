/**
 * @author Davit Najaryan
 * @description this class will handle the analysis of the image 
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

           //make client to send the made reqest to the vision API
           //try with resource block used since ImageAnnotatorClient automatically calls close
            try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) 
            {

                //need 2 sperate variables: 1 for an indvidul request and 1 an array of responses as the API sends back the responses as a batch for efficency
                BatchAnnotateImagesResponse API_response; 
                List<AnnotateImageResponse> API_responses_arr;

                //send the image array to the API and recieve the response
                API_response = client.batchAnnotateImages(API_requests_array);

                //gets the reponse from the batch of responses sent by the API and stores in array
                API_responses_arr = API_response.getResponsesList();

                //string for storing the results
                StringBuilder finalResult = new StringBuilder("Detected: ");

                //loop through every image response
                for (int i = 0; i < API_responses_arr.size(); i++)
                {
                    //store the response at the current index
                    AnnotateImageResponse res = API_responses_arr.get(i);
                    
                    //process that response
                    //get the list of label annotations from the response
                    List<EntityAnnotation> annotationsArr = res.getLabelAnnotationsList();

                    //loop through the annotations list
                    for (int j = 0; j < annotationsArr.size(); j++) 
                    {
                        
                        //store the current annotation
                        EntityAnnotation currAnnotation = annotationsArr.get(j);

                        //store the description of the current annotation in a string
                        String descriptionOfCurrAnnotation = currAnnotation.getDescription();

                        //store the precentage of how sure the AI is about the image object analysis in a variable
                        float precentage = currAnnotation.getScore();
                        precentage *= 100;

                        //append the desctiption of the annotation along with the percentage
                        finalResult.append(descriptionOfCurrAnnotation);
                        finalResult.append(String.format(" (%.2f", precentage, "%)"));
                        finalResult.append("%), ");
                    }
                }

                return finalResult.toString();
            }
            
        } catch (Exception e) {

            e.printStackTrace();
            return e.getMessage();

        }
    }
}
