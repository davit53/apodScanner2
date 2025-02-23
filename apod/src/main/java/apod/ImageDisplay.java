package apod;

import javax.swing.*;
import java.awt.Image;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * @author Davit Najaryan
 * @description the is the main class which takes the image and the reuslts of the analysis and displays them to the gui
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

            //use manual positioning for the picture in the window
            setLayout(null);

            if(img != null)
            {
                ImageIcon imageIcon = new ImageIcon(img);
                displayField = new JLabel(imageIcon);

                //set picture position to top left corner
                displayField.setBounds(5, 5, imageIcon.getIconWidth(), imageIcon.getIconHeight());
                add(displayField);
                pack();

                //Allocate more space in the window to dislay analytics about the picture
                setSize(getWidth() + 100, getHeight() + 100);
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

        //make call to image anaylzer class with the url of the picture as the parameter
        String resultOfAnalysis = ImageAnalyzer.analyzeImg(apod.getPictureURL());

        //print the result of the image analysis
        JLabel resultLabel = new JLabel("RESULT OF THE ANALYSIS: " + resultOfAnalysis);
        System.out.println(resultLabel);
        
        i.add(resultLabel);
        i.setVisible(true);

    }
}
