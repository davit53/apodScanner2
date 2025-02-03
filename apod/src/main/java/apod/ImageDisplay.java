package apod;

import javax.swing.*;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * @author Davit Najaryan
 * @description 
 * @date January 28, 2025
 */

public class ImageDisplay extends JFrame
{

    //Instance vairables for GUI
    JLabel displayField;

    /**
     * @description constructor for GUI
     * @param imageUrl the url for the image form nasa's api
     */
    public ImageDisplay(String imageUrl)
    {
        setTitle("APOD Image");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //open the image using try catch so program dosent crash
        try {

            //Read the image form the URL
            URL url = new URL(imageUrl);
            Image img = ImageIO.read(url);

            if(img != null)
            {
                ImageIcon imageIcon = new ImageIcon(img);
                displayField = new JLabel(imageIcon);
                add(displayField);
                pack();
            }

        } catch (Exception e) {
            System.out.println("Image not found.");
        }

        setVisible(true);

    }

    public static void main(String[] args) 
    {
      
        //get the data from NASA api for APOD
        String apodData = constants.makeRequest();

        //call the formatting function to use JSON parsing to fomrat
        FormatApodCharacteristics apod = new FormatApodCharacteristics(apodData);

        //display the data
        apod.displayData();

        ImageDisplay i = new ImageDisplay(apod.getPictureURL());

    }
}
