package dz.usthb.pfeelt.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static Theme DEFAULT_THEME = Theme.NORD_LIGHT;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/dz/usthb/pfeelt/optimus.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        if(getClass().getResource("/dz/usthb/pfeelt/themes/"+ DEFAULT_THEME.name().toLowerCase() + ".css") != null)
            scene.getStylesheets().setAll(getClass().getResource("/dz/usthb/pfeelt/themes/"+ DEFAULT_THEME.name().toLowerCase() + ".css").toExternalForm());
        stage.setTitle("Optimus");
//      stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}