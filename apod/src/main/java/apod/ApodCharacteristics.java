package apod;

/**
 * @author Davit Najaryan
 * @date January 28, 2025
 * @description the purpose of this function is to retrive all the charactersitcs from the picure of the day and store them in vairables
 */

import org.json.JSONObject;

public class ApodCharacteristics extends constants 
{

    //this funciton will format the data uisng JSON parsing
    public static void formatData(String response)
    {
        //check if the response is null, if it is, end function
        if (response == null) 
        {
            System.out.println("No data.");
            return;
        }

        try {
            
            // Parse JSON data
            JSONObject json = new JSONObject(response);

            //get the details
            String title = json.getString("title");
            String date = json.getString("date");
            String explanation = json.getString("explanation");
            String pictureURL = json.getString("url");
            String copyright = json.getString("copyright");
            String mediaType = json.getString("media_type");;

            //display data
            System.out.println("Title: " + title);
            System.out.println("Date: " + date);
            System.out.println("Description: " + explanation);
            System.out.println("Image URL: " + pictureURL);
            System.out.println("Copyright: " + copyright);
            System.out.println("Media Type: " + mediaType);

        } catch (Exception e) 
        {
            e.printStackTrace();
        }

    }

}

