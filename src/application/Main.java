package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/** Starts up application. this is the main function to run in order to start up the application.
 * current error messages: Gtk-Message: (running linux distro.
 * 16:11:34.987: Failed to load module "appmenu-gtk-module"
 * Gtk-Message: 16:11:35.130: Failed to load module "canberra-gtk-module"
 * Gtk-Message: 16:11:35.131: Failed to load module "canberra-gtk-module"
 * Error: java.lang.NullPointerException */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Gui/homeScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }
}