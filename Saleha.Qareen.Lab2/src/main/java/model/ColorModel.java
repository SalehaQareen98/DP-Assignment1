package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ColorModel class represents the data layer in the MVP pattern.
 * It stores RGB values and notifies listeners whenever those values change.
 * 
 * This class follows the MVP pattern by isolating data logic from the GUI and
 * providing controlled access via public getter and setter methods.
 * 
 * PropertyChangeSupport is used to notify the presenter when values change.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class ColorModel {

    private int red = 255;
    private int green = 255;
    private int blue = 255;

    /** Support for property change events to notify listeners */
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    
    // Getters
    /**
     * Gets the current red color value.
     * 
     * @return The red value (range: 1–255).
     */
    public int getRed() {
        return red;
    }

    /**
     * Gets the current green color value.
     * 
     * @return The green value (range: 1–255).
     */
    public int getGreen() {
        return green;
    }

    /**
     * Gets the current blue color value.
     * 
     * @return The blue value (range: 1–255).
     */
    public int getBlue() {
        return blue;
    }
    

    // Setters

    /**
     * Sets the red color value and fires a property change event if the value is valid.
     * 
     * @param red The new red value (range: 1–255).
     */
    public void setRed(int red) {
        if (red >= 1 && red <= 255) {
            int oldValue = this.red;
            this.red = red;
            support.firePropertyChange("red", oldValue, red); // Notify listeners about the change
        }
    }

    /**
     * Sets the green color value and fires a property change event if the value is valid.
     * 
     * @param green The new green value (range: 1–255).
     */
    public void setGreen(int green) {
        if (green >= 1 && green <= 255) {
            int oldValue = this.green;
            this.green = green;
            support.firePropertyChange("green", oldValue, green); // Notify listeners about the change
        }
    }

    /**
     * Sets the blue color value and fires a property change event if the value is valid.
     * 
     * @param blue The new blue value (range: 1–255).
     */
    public void setBlue(int blue) {
        if (blue >= 1 && blue <= 255) {
            int oldValue = this.blue;
            this.blue = blue;
            support.firePropertyChange("blue", oldValue, blue); // Notify listeners about the change
        }
    }

    // Property change listener methods
    /**
     * Adds a PropertyChangeListener to listen for color value changes.
     * 
     * @param listener The PropertyChangeListener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener from listening for color value changes.
     * 
     * @param listener The PropertyChangeListener to be removed.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
