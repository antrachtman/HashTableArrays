/*
 * A hashtable made of arrays.
 */
package hashtable;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.*;
import java.io.*;

/**
 *
 * @author DakiDozer
 */
public class HashTable {

    //Used to get the location of the mouse after the timer goes off.
    public static class GetLocation {

        private boolean timerDone = false;

        public void finished() {
            timerDone = true;
        }

        public boolean isTimerDone() {
            return timerDone;
        }
    }

    //Randomly capitalizes input.
    public static void randomCapitalize(Robot robot, boolean activated) {
        if (Math.random() > .5 && activated) {
            robot.keyPress(16);//Shift
        }
    }

    public static void main(String[] args) throws AWTException, InterruptedException {
        //Table should be a power of 2
        //HTable(M, N)
        HTable HashTable = new HTable(2, 2);

        String filename = "";
        BufferedReader br = null;

        try {
            //System.out.println(new File(".").getAbsoluteFile());
            br = new BufferedReader(new FileReader(new File(filename)));
            String inputLine = null;
            while ((inputLine = br.readLine()) != null) {
                HashTable.addEntry(inputLine);
            }
        } catch (IOException ex) {
            System.err.println("An IOException was caught!\n" + ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                System.err.println("An IOException was caught!\n" + ex);
            }
        }

        System.out.println("All words loaded into hashtable!");
        
        //For 512 by 512 "grid" 
        int x = 0;
        int y = 0;

        //This can be used to track the position of the mouse on the screen.
        //Mainly used for automated Java testing.
        Robot robot = new Robot();

        //Holds the x and y coordinates of the mouse after 5 seconds.
        int xCoord = 0;
        int yCoord = 0;
        //Call my class getLocation
        GetLocation getLocation = new GetLocation();

        System.out.println("Click on whatever you want to insert the text into. You have 5 seconds.");

        //Create a timer
        Timer timer = new Timer();
        //Schedule a task to be executed once.
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Tell the program that we have a location now.
                getLocation.finished();
            }
        }, 5 * 1000//,anotherTime -> You'd put this extra time here if you wanted the event to happen every X milliseconds.
        );

        while (getLocation.isTimerDone() == false) {
            //Get the mouse position
            Point lastPosition = MouseInfo.getPointerInfo().getLocation();
            Point currentPosition = MouseInfo.getPointerInfo().getLocation();

            if (!(lastPosition.equals(currentPosition))) {
                //Keep setting the new mouse position.
                xCoord = MouseInfo.getPointerInfo().getLocation().x;
                yCoord = MouseInfo.getPointerInfo().getLocation().y;
                lastPosition = MouseInfo.getPointerInfo().getLocation();
            }
        }

        //Move the mouse to where it is (seems redundant).
        robot.mouseMove(xCoord, yCoord);

        //Clicks the mouse.
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        //Used to pause the chatbot for *roughly/not exactly* X milliseconds.
        int restInterval = 1;
        //Used for the test_ program bit that was commented out.
        String numStr;
        //Used to hold a character from the substring of textToWrite found below
        char c;
        //Used to hold the char ASCII character code.
        int charInt;

        //Pick a delay time.
        boolean realisticDelay = false;
        boolean smallDelay = true;
        boolean superShort = false;

        if (realisticDelay == true) {
            restInterval = 1000;
        } else if (smallDelay == true) {
            restInterval = 500;
        } else if (superShort == true) {
            restInterval = 50;
        }

        //This must be all capitals because of how the JVM(?) interprets ASCII character key codes(?).
        //Get random emotes using ;0 (semicolon zero)
        //After program use, hit left SHIFT since the robot doesn't always let go.
        String textToWrite = "NoDice";
        boolean validWord = false;

        while (true) {
            while (validWord == false) {
                x = (int) Math.floor((Math.random() * HashTable.getXDim()));
                y = (int) Math.floor((Math.random() * HashTable.getYDim()));
                if (HashTable.getElement(x, y) != null) {
                    textToWrite = HashTable.getElement(x, y).toUpperCase();
                    validWord = true;
                }
            }
            //O(textToWrite)
            validWord = false;
            for (int i = 0; i < textToWrite.length(); i++) {
                //Get the character at position i
                c = textToWrite.charAt(i);
                //Use type casting to get the ASCII value of a char http://www.studytonight.com/java/type-casting-in-java
                charInt = (int) c;
                randomCapitalize(robot, false);
                robot.keyPress(charInt);
                robot.keyRelease(charInt);
                robot.keyRelease(16); //Release shift
            }
            robot.keyPress(10); //Enter aka Carriage return

            if (realisticDelay == true) {
                Thread.sleep(restInterval);
                if (Math.random() < .5 && restInterval <= 1500) {
                    restInterval += Math.floor(Math.random() * 100.0);
                } else if (restInterval >= 700) {
                    restInterval -= Math.floor(Math.random() * 100.0);
                }
            }
            if (smallDelay == true) {
                Thread.sleep(restInterval);
                if (Math.random() < .5 && restInterval <= 500) {
                    restInterval += Math.floor(Math.random() * 100.0);
                } else if (restInterval >= 100) {
                    restInterval -= Math.floor(Math.random() * 100.0);
                }
            } else if (superShort == true) {
                Thread.sleep(restInterval);
                if (Math.random() < .5 && restInterval <= 100) {
                    restInterval += Math.floor(Math.random() * 10.0);
                } else if (restInterval >= 1) {
                    restInterval -= Math.floor(Math.random() * 10.0);
                }
                if (restInterval < 1) {
                    restInterval = 1;
                }
            }
        }

        //HashTable.addEntry("text to add");
        /*
         for(int x = 0; x < HashTable.getXDim(); x++){
         for(int y = 0; y < HashTable.getYDim(); y++){
         if(HashTable.getElement(x,y)!= null)
         System.out.println(HashTable.getElement(x,y));
         }
         }
         HashTable.getDimensions();
         */
    }

}
