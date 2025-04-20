package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The ColorView class represents the GUI for the Color Swatch Application.
 * It allows users to adjust RGB values and view the resulting color in a swatch panel.
 */
public class ColorView extends JFrame implements PropertyChangeListener {

    private final JTextField redField;
    private final JTextField greenField;
    private final JTextField blueField;
    private final JPanel colorSwatch; 
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Constructor for the ColorView class.
     * Initializes the GUI components and sets up event listeners.
     */
    public ColorView() {
        setTitle("Color Swatch Application");

        // Create color swatch panel
        colorSwatch = new JPanel();
        colorSwatch.setPreferredSize(new Dimension(200, 200));
        colorSwatch.setBackground(new Color(255, 255, 255));  // Default to white

        // Create text fields for RGB values
        redField = createColorField("redValueChanged");
        greenField = createColorField("greenValueChanged");
        blueField = createColorField("blueValueChanged");

        // Organize layout
        setLayout(new GridLayout(2, 1));
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Red:"));
        inputPanel.add(redField);

        inputPanel.add(new JLabel("Green:"));
        inputPanel.add(greenField);

        inputPanel.add(new JLabel("Blue:"));
        inputPanel.add(blueField);

        add(inputPanel);
        add(colorSwatch);

        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center window on screen
        setVisible(true);
    }

    /**
     * Creates a color input field with appropriate listeners for changes.
     *
     * @param label The label text for the field.
     * @param propertyName The property change event name to fire.
     * @return The created JTextField with appropriate listeners.
     */
    private JTextField createColorField(String propertyName) {
        JTextField field = new JTextField();
        field.setColumns(5);

        // Fire property change when Enter is pressed or focus is lost
        field.addActionListener(e -> fireColorChange(propertyName, field.getText()));
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                fireColorChange(propertyName, field.getText());
            }
        });

        return field;
    }

    /**
     * Fires a property change event for the corresponding color value.
     *
     * @param propertyName The property change event name.
     * @param value The string value to be converted to an integer.
     */
    private void fireColorChange(String propertyName, String value) {
        try {
            int intValue = Integer.parseInt(value.trim());

            // Ensure the value is within the valid range (1-255)
            if (intValue < 1 || intValue > 255) {
                JOptionPane.showMessageDialog(this, 
                    propertyName + " must be between 1 and 255.", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Fire the change if the value is valid
            support.firePropertyChange(propertyName, null, intValue);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                propertyName + " must be a valid number.", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the color swatch when notified by the presenter.
     *
     * @param evt The property change event that triggered this update.
     */
    @Override
public void propertyChange(PropertyChangeEvent evt) {
    try {
        int red = Integer.parseInt(redField.getText().trim());
        int green = Integer.parseInt(greenField.getText().trim());
        int blue = Integer.parseInt(blueField.getText().trim());

        if ("red".equals(evt.getPropertyName())) {
            red = (int) evt.getNewValue();  // Update red only if the event is for red
        } else if ("green".equals(evt.getPropertyName())) {
            green = (int) evt.getNewValue();  // Update green only if the event is for green
        } else if ("blue".equals(evt.getPropertyName())) {
            blue = (int) evt.getNewValue();  // Update blue only if the event is for blue
        }

        // Update the color swatch
        colorSwatch.setBackground(new Color(red, green, blue));

    } catch (NumberFormatException ex) {
        System.err.println("Error updating color swatch: " + ex.getMessage());
    }
}

    /**
     * Adds a property change listener to the view.
     *
     * @param listener The listener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from the view.
     *
     * @param listener The listener to be removed.
     */
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
