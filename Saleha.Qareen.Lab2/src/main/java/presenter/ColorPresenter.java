package presenter;

import model.ColorModel;
import view.ColorView;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ColorPresenter class acts as the intermediary between the ColorModel and ColorView.
 * It listens for changes in the view and updates the model accordingly.
 * It also notifies the view when the model's RGB values are updated.
 */
public class ColorPresenter implements PropertyChangeListener {
    
    private final ColorModel model;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Constructor for the ColorPresenter.
     * Connects the model and view, ensuring proper communication via property change events.
     *
     * @param model The ColorModel instance that holds RGB values.
     * @param view The ColorView instance that displays the GUI.
     */
    public ColorPresenter(ColorModel model, ColorView view) {
        this.model = model;

        // Listen to property change events from the view
        view.addPropertyChangeListener(this);

        // Listen to property change events from the model
        model.addPropertyChangeListener(view);
    }

    /**
     * Adds a property change listener to the presenter.
     *
     * @param listener The listener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from the presenter.
     *
     * @param listener The listener to be removed.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Handles property change events.
     * Updates the model's RGB values when the view signals a change.
     * Notifies the view when the model's RGB values are updated.
     *
     * @param evt The property change event that triggered this method.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            String propertyName = evt.getPropertyName();

            // Handle red value change
            if ("redValueChanged".equals(propertyName)) {
                int newValue = (int) evt.getNewValue();
                model.setRed(newValue);
            }

            // Handle green value change
            if ("greenValueChanged".equals(propertyName)) {
                int newValue = (int) evt.getNewValue();
                model.setGreen(newValue);
            }

            // Handle blue value change
            if ("blueValueChanged".equals(propertyName)) {
                int newValue = (int) evt.getNewValue();
                model.setBlue(newValue);
            }

        } catch (Exception e) {
            System.err.println("Error handling property change event: " + e.getMessage());
        }
    }
}
