package apod;

import javax.swing.*;
import java.awt.*;
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

        //create a layout
        BorderLayout myLayout = new BorderLayout();
        setLayout(myLayout);

        //make a panel of the image
        JPanel panel = new JPanel(new BorderLayout());

        //open the image using try catch so program dosent crash
        try {

            //Read the image form the URL
            URL url = new URL(imageUrl);
            Image img = ImageIO.read(url);

            //use borderLayout to add components to specific regions
            setLayout(new BorderLayout());

            if(img != null)
            {
                ImageIcon imageIcon = new ImageIcon(img);
                displayField = new JLabel(imageIcon);

                //add the picutre to the panel and position it to the left
                panel.add(displayField, BorderLayout.WEST);

            }

        } catch (Exception e) {
            System.out.println("Image not found.");
        }

        add(panel, BorderLayout.CENTER);
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

        //make a button
        JButton button = new JButton("Analyze");

        //add the button and position it to the right
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(button);

        //position the button on the top of the border
        i.add(topPanel, BorderLayout.NORTH);

        //print the result of the image analysis
        System.out.println(resultOfAnalysis);

        i.revalidate();
        i.repaint();
        i.setVisible(true);

    }
}
