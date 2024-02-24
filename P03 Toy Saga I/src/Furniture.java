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
// Persons:         N/A
// Online Sources:  N/A
//
///////////////////////////////////////////////////////////////////////////////

import processing.core.PImage;

import java.io.File;

/**
 * This class creates and groups together objects and methods that pertain to
 * the furniture that is displayed in the GUI. Users can't interact with furniture
 * objects.
 */
public class Furniture {
    //Instance fields
    public final PImage IMAGE; //image representing furniture item
    private String name; //name of a furniture item
    private int x; // x-position of furniture item
    private int y; // y-position of furniture item


    //Constructors

    /**
     * Public constructor that takes one string parameter and 2 int parameters
     * and uses the parameters to initialize the previously defined instance
     * fields of name, x position , y position, and Image.
     *
     * @param name - name of furniture image
     * @param x    - x position of furniture item
     * @param y    - y position of furniture item
     */
    public Furniture(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.IMAGE = Utility.loadImage("images" + File.separator + name + ".png");
    }

    /**
     * Public constructor that takes one string parameter that initializes the name
     * instance field. The rest of the instance fields are initialized to default values.
     *
     * @param name - name of the furniture image
     */
    public Furniture(String name) {
        this.name = name;
        this.x = Utility.width() / 2; //initialize to middle of screen
        this.y = Utility.height() / 2; //initialize to middle of screen
        this.IMAGE = Utility.loadImage("images" + File.separator + name + ".png");
    }

    //Class methods

    /**
     * Getter/accessor for the x position of furniture.
     *
     * @return int - x position of furniture
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter/accessor for the y position of furniture.
     *
     * @return int - y position of furniture
     */
    public int getY() {
        return this.y;
    }

    /**
     * Getter /accessor method for the name field named name().
     *
     * @return String - name of furniture
     */
    public String name() {
        return this.name;
    }

    /**
     * Calls Utility.image() to draw the Furniture object at
     * its current position.
     */
    public void draw() {
        Utility.image(IMAGE, getX(), getY());
    }
}