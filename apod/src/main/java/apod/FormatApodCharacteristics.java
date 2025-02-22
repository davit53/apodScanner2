package apod;

/**
 * @author Davit Najaryan
 * @date January 28, 2025
 * @description the purpose of this function is to retrive all the charactersitcs from the picure of the day and store them in vairables
 */

import org.json.JSONObject;

public class FormatApodCharacteristics extends constants 
{

    //Instance variables
    private String title;
    private String date;
    private String explanation;
    private String pictureURL;
    private String copyright;
    private String mediaType;

    /**
     * @description this funciton will format the data uisng JSON parsing
     * @param response
     */
    public FormatApodCharacteristics(String response)
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
            this.title = json.getString("title");
            this.date = json.getString("date");
            this.explanation = json.getString("explanation");
            this.pictureURL = json.getString("url");
            this.copyright = json.getString("copyright");
            this.mediaType = json.getString("media_type");;

        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        
    }

    //getter methods for returning the variables
    public String getTitle() 
    {
        return this.title;
    }

    public String getDate() 
    {
        return this.date;
    }

    public String getExplanation() 
    {
        return this.explanation;
    }

    public String getPictureURL() 
    {
        return this.pictureURL;
    }

    public String getCopyright() 
    {
        return this.copyright;
    }

    public String getMediaType() 
    {
        return this.mediaType;
    }

    /**
     * @description display the formatted data
     */
    public void displayData() 
    {
        System.out.println("--- NASA Astronomy Picture of the Day ---");
        System.out.println("Title: " + this.title);
        System.out.println("Date: " + this.date);
        System.out.println("Description: " + this.explanation);
        System.out.println("Image URL: " + this.pictureURL);
        System.out.println("Copyright: " + this.copyright);
        System.out.println("Media Type: " + this.mediaType);
    }

}

