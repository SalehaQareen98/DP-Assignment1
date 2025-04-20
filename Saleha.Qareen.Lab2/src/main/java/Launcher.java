import javax.swing.SwingUtilities;
import model.ColorModel;
import view.ColorView;
import presenter.ColorPresenter;

/**
 * The Launcher class initializes the Color Swatch Application.
 * It creates instances of the Model, View, and Presenter following the MVP pattern.
 */
public class Launcher {

    /**
     * The entry point of the application.
     * Ensures the GUI runs on the Event Dispatch Thread (EDT) using SwingUtilities.invokeLater().
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Instantiate the model
                ColorModel model = new ColorModel();
                
                // Instantiate the view
                ColorView view = new ColorView();
                
                // Instantiate the presenter, linking model and view
                new ColorPresenter(model, view);
            }
        });
    }
}
