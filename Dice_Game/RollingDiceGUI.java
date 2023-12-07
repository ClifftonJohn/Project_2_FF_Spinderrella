//Graphical User Interface 
//Allowing users to interact with our electronic devices through graphical icons and audio indicators
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
//(*)Inherits from JFRame - part of framework Swing
//Used to create the main window/ frame of a GUI application
public class RollingDiceGUI extends JFrame {
    //Create a constructor
    public RollingDiceGUI(){
        super("Rolling Double Dices"); //Create a title
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Set default action - when user close window = exit the program = end!
        //(*) Dimension - part of Java AWT = contains (W,h) 
        setPreferredSize(new Dimension(1000,1000)); //Size of window (pixel)
        pack(); //Automatically resize window to fix with its content => Ensure the elements is showed correctly
        setResizable(false); //Pretending the ability to resize the window by unactivate its ability => User can not drag to bigger or smaller
        setLocationRelativeTo(null); //Center the virtual window as the center of your computer
        addGUiComponents();
    }
    private void addGUiComponents()
    {
        //(**) Create a JPanel
        //JPanel - A part of the Java Swing package - A container that can store a group of components - Main task: Organize components
        //JPanel() - New panel with a flow Layout
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null); //Set the "Layout manager" to null = Manually specify the size and the position of each component within the panel
        //Load and Add a Banner Image
        JLabel banner_Img = ImgService.loadImage("Dice_Game//Resource//Banner.png");
        banner_Img.setBounds(45,45,600,100); //Layout manager: setBounds(int x-coordinate, int y-coordinate, int width, int height)
        jPanel.add(banner_Img);

        //(***) Create a Dice_1 && Dice_2 && Spider_Dice
        JLabel dice_1_Img = ImgService.loadImage("Dice_Game//Resource//dice_1.png");
        dice_1_Img.setBounds(100,200,200,200);
        jPanel.add(dice_1_Img);

        JLabel dice_2_Img = ImgService.loadImage("Dice_Game//Resource//dice_1.png");
        dice_2_Img.setBounds(390, 200, 200, 200);
        jPanel.add(dice_1_Img);

        // JLabel dice_Object_Img = ImgService.loadImage("Dice_Game//Resource//dice_ant.png");
        // dice_Object_Img.SetBounds(590, 200, 200, 200);
        // JPanel.add(dice_Object_Img);


        // Create an ImageIcon
        ImageIcon buttonIcon = new ImageIcon("Dice_Game//Resource//Button_Game.png");

        //(4th*) Create a Roll Button
        Random rand = new Random();
        JButton rollButton = new JButton(buttonIcon);
        rollButton.setBounds(250, 550, 200, 50);

        //aAL = Implement an action listener to define what should be done when an user performs certain operation
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollButton.setEnabled(false); //Disable the button - likely to prevent multiple rapid clicks/ disable the button during processing

                // roll for 3 seconds
                //(5th*) Timing and Animation
                long startTime = System.currentTimeMillis(); //Set to the current time when the button is clicked
                //A new thread is created to run a task that simulates the rolling of dice - nearly 3 seconds
                Thread rollThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long endTime = System.currentTimeMillis();
                        //Inside this thread's run() method, a loop runs until the elapsed time is close to 3s
                        try{
                            while((endTime - startTime)/1000F < 3){
                                // roll dice
                                int diceOne = rand.nextInt(1, 7);
                                int diceTwo = rand.nextInt(1, 7);
                        //Creating a random string to run the object animation.
                                // update dice images
                                ImgService.updateImage(dice_1_Img, "Dice_Game//Resource//dice_" + diceOne + ".png");
                                ImgService.updateImage(dice_2_Img, "Dice_Game//Resource//dice_" + diceTwo + ".png");

                                repaint();
                                revalidate();
                                // sleep thread - a short delay (60 milliseconds) between each iteration, creating a smooth animation effect
                                Thread.sleep(60);

                                endTime = System.currentTimeMillis();

                            }
                            //The button is re-enabled after the animation is complete 
                            rollButton.setEnabled(true);
                        } catch(InterruptedException e){ //Exception Handling - Catches and handles "InterruptedException" that may occur
                            System.out.println("Threading Error: " + e);
                        }
                    }
                });
                rollThread.start(); //This code snippet concludes the setup of your Swing GUI.
            }
        });
        jPanel.add(rollButton);
//This line adds the jPanel to the content pane of the JFrame (this). The content pane is the main container where you place your user interface components.
        this.getContentPane().add(jPanel);
    }
}

