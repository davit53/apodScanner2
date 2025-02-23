package apod;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author Davit Najaryan
 * @description this class will contain the API key and make a request to access the api
 * @date January 28, 2025
 */

public class constants 
{

    private static final String APOD_KEY = "https://api.nasa.gov/planetary/apod?api_key=O29C3tvln6e4tamVKaxGdXpA2UmXFHTBRnzLpE1J";


    /**
     * @return url of the key
     */
    public static String getApodKey() 
    {
        return APOD_KEY;
    }

    /**
     * @description this funciton will attempt to request the api for the data
     * @return JSON response string
     */
    public static String makeRequest()
    {

        //try methods to get the data using httpclient request
        try {

            //make http client
            HttpClient client = HttpClient.newHttpClient();

            //make http request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APOD_KEY))
                .GET()
                .build();

            //send the request and put the data in variable response
            HttpResponse<String> response;
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //if status code in between 200 and 299 means data fetch sucessfull
            if (response.statusCode() >= 200 && response.statusCode() <= 299) 
            {
                //therefore return data
                return response.body();
            } 
            //otherwise display error code
            else 
            {
                System.out.println("Error: " + response.statusCode());
            }
            
        } catch(Exception  e) 
        {
            e.printStackTrace();
        }

        return null;

    }

}