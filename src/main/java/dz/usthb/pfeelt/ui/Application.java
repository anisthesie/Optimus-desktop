package dz.usthb.pfeelt.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    public static Theme DEFAULT_THEME = Theme.CUPERTINO_DARK;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/dz/usthb/pfeelt/optimus.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        if (getClass().getResource("/dz/usthb/pfeelt/themes/" + DEFAULT_THEME.name().toLowerCase() + ".css") != null)
            scene.getStylesheets().setAll(getClass().getResource("/dz/usthb/pfeelt/themes/" + DEFAULT_THEME.name().toLowerCase() + ".css").toExternalForm());

        if (getClass().getResource("/dz/usthb/pfeelt/icons/icon.png") != null)
        stage.getIcons().setAll(new Image(getClass().getResourceAsStream("/dz/usthb/pfeelt/icons/icon.png")));
        stage.setTitle("Optimus");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}