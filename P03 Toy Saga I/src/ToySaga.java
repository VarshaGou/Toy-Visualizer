//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Toy Story Drawer
// Course:   CS 300 Spring 2024
//
// Author:   Varsha Gouraram
// Email:    gouraram@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:        TA Shourjo - Helped me with my draw method
// Online Sources:  N/A
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;
import java.io.File;

import processing.core.PImage;

/**
 * This class is the main class in the ToySaga. It creates the graphical user
 * interface and implements the other classes (Furniture.java & Toy.java) in
 * order to create a user - interactive interface that models a bedroom.
 */
public class ToySaga {
    //static fields
    private static PImage backgroundImage;
    private static ArrayList<Furniture> furnitureList; //ArrayList to store furniture objects
    private static ArrayList<Toy> toyList; //ArrayList to store toy objects
    private static final String BOX_NAME = "box"; //constant name of the toy box furniture.
    private static final int MAX_TOYS_COUNT = 8; //maximum number of visible toys
    // that can be stored in toyList.

    /**
     * This method is used to initialize the data fields that were defined above.
     */
    public static void setup() {
        //initialize background image
        backgroundImage = Utility.loadImage("images" + File.separator + "background.png");

        //create ArrayLists
        furnitureList = new ArrayList<Furniture>();
        toyList = new ArrayList<Toy>();

        //Create furniture objects
        Furniture bed = new Furniture("bed", 520, 270);
        Furniture rug = new Furniture("rug", 220, 370);
        Furniture nightstand = new Furniture("nightstand", 325, 240);
        Furniture BOX_NAME = new Furniture("box", 90, 230);

        //Add created furniture objects to furniture ArrayList
        furnitureList.add(bed);
        furnitureList.add(rug);
        furnitureList.add(nightstand);
        furnitureList.add(BOX_NAME);
    }

    /**
     * This method first draws the background image to the center of the screen.
     * Updates the contents of the toyList to remove any toy which is over the box
     * furniture, and draws furniture and toy objects.
     */
    public static void draw() {
        //Draw the background image at the center of the screen
        Utility.image(backgroundImage, Utility.width() / 2, Utility.height() / 2);

        //draws the furniture objects in the ArrayList on the screen
        for (int i = 0; i < furnitureList.size(); i++) {
            if (furnitureList.get(i) != null) {
                furnitureList.get(i).draw();
            }
        }

        //draws the toy objects in the ArrayList on the screen
        for (int i = 0; i < toyList.size(); i++) {
            if (toyList.get(i) != null) {
                toyList.get(i).draw();
                //removes any toy object that intersects with the toy box
                if (toyList.get(i).isOver(getToyBox())) {
                    toyList.remove(i);
                }
            }
        }
    }

    /**
     * Finds the Furniture object with a name matching BOX_NAME or null otherwise.
     *
     * @return Furniture - the box furniture, or null if no match
     */
    public static Furniture getToyBox() {
        for (int i = 0; i < furnitureList.size(); i++) {
            //find furniture that matches BOX_NAME and is not null
            if (furnitureList.get(i).name().equals(BOX_NAME) && furnitureList.get(i) != null) {
                return furnitureList.get(i);
            }
        }
        return null;
    }

    /**
     * Returns the toy which is currently dragging.
     *
     * @return Toy - toy being dragged or null if no toy is dragging
     */
    public static Toy getDraggingToy() {
        for (int i = 0; i < toyList.size(); i++) {
            //calss isDragging() to determine if toy is being dragged
            if (toyList.get(i).isDragging()) {
                return toyList.get(i);
            }
        }
        return null;
    }

    /**
     * Called once every time the mouse is pressed. If no toy is being dragged
     * then method checks if the mouse is over a toy and starts dragging them.
     * The toy at the lowest index will be dragged.
     */
    public static void mousePressed() {
        //Check if there is a toy currently being dragged
        if (getDraggingToy() == null) {
            for (int i = 0; i < toyList.size(); i++) {
                //find which toy is under the mouse and start dragging it
                if (toyList.get(i).isOver(Utility.mouseX(), Utility.mouseY())) {
                    toyList.get(i).startDragging();
                    break;
                }
            }
        }
    }

    /**
     * Stops dragging any toy stored in the toy list.
     */
    public static void mouseReleased() {
        for (int i = 0; i < toyList.size(); i++) {
            //stops dragging every object in the Arraylist
            toyList.get(i).stopDragging();
        }
    }

    /**
     * When certain keys are pressed (c,t,r - not case-sensitive) actions like
     * create a car, create a teddybear, and rotate are preformed.
     */
    public static void keyPressed() {
        char userKeyPressed = Utility.key();
        //Not case-sensitive - converts every char to a lowercase one
        char lowerCaseUserKeyPressed = Character.toLowerCase(userKeyPressed);

        //Checks if there is still space in the ArrayList to add a toy
        if (toyList.size() <= MAX_TOYS_COUNT) {
            switch (lowerCaseUserKeyPressed) {
                //make a car
                case 'c':
                    Toy car = new Toy("car", Utility.mouseX(), Utility.mouseY());
                    toyList.add(car);
                    break;

                //make a teddy bear
                case 't':
                    Toy teddyBear = new Toy("teddyBear", Utility.mouseX(), Utility.mouseY());
                    toyList.add(teddyBear);
                    break;

                //rotate toy
                case 'r':
                    for (int i = 0; i < toyList.size(); i++) {
                        if (toyList.get(i).isOver(Utility.mouseX(), Utility.mouseY())) {
                            toyList.get(i).rotate();
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Main method is used to start the application.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        Utility.runApplication(); // starts the application
    }
}