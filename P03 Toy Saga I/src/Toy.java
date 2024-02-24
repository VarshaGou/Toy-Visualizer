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
// Persons:         TA Shourjo - Helped me debug my isOver methods
// Online Sources:  N/A
//
///////////////////////////////////////////////////////////////////////////////

import processing.core.PImage;

import java.io.File;

/**
 * This class creates and groups together objects and methods that pertain to
 * the toys that are displayed in the GUI. The available toys are cars and teddy bears.
 * Users can interact with toys objects.
 */
public class Toy {
    //instance fields
    public final PImage IMAGE; //image representing furniture item
    private int x; // x-position of toy item
    private int y; // y-position of toy item
    private boolean isDragging;
    private int rotations;

    //Constructors

    /**
     * Public constructor that takes a string parameter and two int parameters.
     * Initializes the name of the image, and the x and y positions of the toy instance
     * fields defined above using the parameters. The rest of the instance fields
     * are initialized to default values.
     *
     * @param name - name of toy image
     * @param x    - x position of toy
     * @param y    - y position of toy
     */
    public Toy(String name, int x, int y) {
        this.x = x;
        this.y = y;
        this.IMAGE = Utility.loadImage("images" + File.separator + name + ".png");
        this.isDragging = false; //default value
        this.rotations = 0; //default value
    }

    /**
     * Public constructor that takes one string parameter for name and initializes the
     * name instance field with that parameter. The rest of the instance fields are
     * initialized to default values.
     *
     * @param name - name of toy image
     */
    public Toy(String name) {
        this.x = Utility.width() / 2; //initialize to middle of screen
        this.y = Utility.height() / 2; //initialize to middle of screen
        this.IMAGE = Utility.loadImage("images" + File.separator + name + ".png");
        this.isDragging = false; //default value
        this.rotations = 0; //default value
    }

    //Public instance methods

    /**
     * Getter that returns the x position of a toy.
     *
     * @return int - x position of toy
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter that returns the y position of a toy.
     *
     * @return int - y position of toy
     */
    public int getY() {
        return this.y;
    }

    /**
     * Setter method that sets the current x position of a toy to
     * the one defined in the parameter x.
     *
     * @param x - x position to set with
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter method that sets the current y position of a toy to
     * the one defined in the parameter x.
     *
     * @param y - y position to set with
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter method that returns the number of rotations that have been
     * done to a toy.
     *
     * @return int - number of rotations
     */
    public int getRotationsCount() {
        return this.rotations;
    }

    /**
     * Getter method that determines if a toy is being dragged or not.
     *
     * @return boolean - True if toy is dragging, false otherwise
     */
    public boolean isDragging() {
        return this.isDragging;
    }

    /**
     * Setter method that sets the isDragging method to true when a toy needs to be
     * dragged.
     */
    public void startDragging() {
        this.isDragging = true;
    }

    /**
     * Setter method that sets the isDragging method to false when a toy needs to stop
     * being dragged.
     */
    public void stopDragging() {
        this.isDragging = false;
    }

    /**
     * Increments the rotations field when called.
     */
    public void rotate() {
        this.rotations++;
    }

    /**
     * Adds the parameters dy and dy to the x and y positons of a toy respectively in order
     * to move the toy around the screen. If x and y are out of bound for the window they
     * will be set back to 0 (if they are negative) or to the width and height of the
     * background.
     *
     * @param dx - the amount added to x position
     * @param dy - the amount added to y position
     */
    public void move(int dx, int dy) {
        //add dx and dy to the x and y positions
        this.x += dx;
        this.y += dy;

        //set the x position to the end of the screen if x is out of bounds (positive)
        if (this.x > Utility.width()) {
            this.x = Utility.width();
            //set x position to 0 if x is out of bounds (negative)
        } else if (this.x < 0) {
            this.x = 0;
        }

        //set the y position to the end of the screen if y is out of bounds (positive)
        if (this.y > Utility.height()) {
            this.y = Utility.height();
            //set x position to 0 if x is out of bounds (negative)
        } else if (this.y < 0) {
            this.y = 0;
        }
    }

    /**
     * Draws the newly moved toy image onto the screen by calling the move
     * method on the difference between where the mouse was previously and
     * where it is after it moved.
     */
    public void draw() {
        //set variables to the position of the mouse
        int dx = Utility.mouseX();
        int dy = Utility.mouseY();

        if (isDragging) {
            //find how much the mouse has moved
            dx -= Utility.pmouseX();
            dy -= Utility.pmouseY();
            //call move method with the position mouse has moved
            move(dx, dy);
        }
        //draw toy on screen
        drawToyImage();
    }

    /**
     * Helper method to draw an image accounting for any rotations to the screen.
     * The implementation of this method is fully provided in the write-up.
     */
    private void drawToyImage() {
        Utility.pushMatrix();
        Utility.translate(x, y);
        Utility.rotate(this.rotations * Utility.PI / 2);
        Utility.image(IMAGE, 0.0f, 0.0f);
        Utility.popMatrix();
    }

    /**
     * Checks if the toy in question is over a point (x,y).
     *
     * @param x - x position being checked
     * @param y - y position being checked
     * @return boolean - true if toy is over the point (x,y), false otherwise
     */
    public boolean isOver(int x, int y) {
        //Find the center coordinates of the toy
        double xPosSymbol = getX();
        double yPosSymbol = getY();

        // variables for the lower and upper bounds
        double xLowerBound;
        double xUpperBound;
        double yLowerBound;
        double yUpperBound;

        //variables for the newly rotates width and height
        double rotatedWidth;
        double rotatedHeight;

        //assign width to width and height to height if the number of rotations are even.
        if (getRotationsCount() % 2 == 0) {
            rotatedWidth = IMAGE.width;
            rotatedHeight = IMAGE.height;

            //calculate the lower and upper bounds for x and y
            xLowerBound = xPosSymbol - (rotatedWidth / 2.0);
            xUpperBound = xPosSymbol + (rotatedWidth / 2.0);
            yLowerBound = yPosSymbol - (rotatedHeight / 2.0);
            yUpperBound = yPosSymbol + (rotatedHeight / 2.0);

            // for odd number of rotations swap width and height
        } else {
            rotatedWidth = IMAGE.height;
            rotatedHeight = IMAGE.width;

            //calculate the lower and upper bounds for x and y
            xLowerBound = xPosSymbol - (rotatedWidth / 2.0);
            xUpperBound = xPosSymbol + (rotatedWidth / 2.0);
            yLowerBound = yPosSymbol - (rotatedHeight / 2.0);
            yUpperBound = yPosSymbol + (rotatedHeight / 2.0);
        }

        // Find out if x and y are in teh bounds calculated above
        if (x <= xUpperBound && y >= yLowerBound && y <= yUpperBound && x >= xLowerBound) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the toy object in question is intersecting with the furniture object that is
     * being passed through as a parameter.
     *
     * @param other - furniture that is being checked
     * @return boolean - true if toy intersects with furniture, false otherwise
     */
    public boolean isOver(Furniture other) {
        //define variables for width and height
        double imageWidth = 0.0;
        double imageHeight = 0.0;
        int otherImageWidth = other.IMAGE.width;
        int otherImageHeight = other.IMAGE.height;

        //Calculate the bounds of the furniture object
        double rightFurniture = other.getX() + (otherImageWidth / 2.0);
        double leftFurniture = other.getX() - (otherImageWidth / 2.0);
        double topFurniture = other.getY() + (otherImageHeight / 2.0);
        double bottomFurniture = other.getY() - (otherImageHeight / 2.0);

        //define variables for toy object bounds
        double rightToy;
        double leftToy;
        double topToy;
        double bottomToy;

        //even number of rotations = normal width and height assignment
        if (getRotationsCount() % 2 == 0) {
            imageWidth = IMAGE.width;
            imageHeight = IMAGE.height;

            //Calculate toy bounds
            rightToy = this.getX() + (imageWidth / 2.0);
            leftToy = this.getX() - (imageWidth / 2.0);
            bottomToy = this.getY() - (imageHeight / 2.0);
            topToy = this.getY() + (imageHeight / 2.0);

            //odd number of rotations = height and width swapped
        } else {
            imageWidth = IMAGE.height;
            imageHeight = IMAGE.width;

            //Calculate toy bounds
            rightToy = this.getX() + (imageWidth / 2.0);
            leftToy = this.getX() - (imageWidth / 2.0);
            bottomToy = this.getY() - (imageHeight / 2.0);
            topToy = this.getY() + (imageHeight / 2.0);
        }

        //checks if area is 0, no intersection
        if (rightToy == leftToy || leftFurniture == rightFurniture
                || topToy == bottomToy || topFurniture == bottomFurniture) {
            return false;
        }

        //checks if two objects are to the left of each other without intersecting
        if (rightToy < leftFurniture || rightFurniture < leftToy) {
            return false;
        }

        //checks if the two objects are above each other without intersecting
        if (bottomToy > topFurniture || bottomFurniture > topToy) {
            return false;
        }
        return true;
    }
}