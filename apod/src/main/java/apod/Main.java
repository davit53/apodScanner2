package apod;

/**
 * @author Davit Najaryan
 * @description 
 * @date January 28, 2025
 */

public class main 
{

    public static void main(String[] args) 
    {
      
        //get the data from NASA api for APOD
        String apodData = constants.makeRequest();

        //call the formatting function to use JSON parsing to fomrat and display the data
        ApodCharacteristics.formatData(apodData);
    }
}
