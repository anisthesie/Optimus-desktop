package dz.usthb.pfeelt.ui;

import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public enum Theme {

    CUPERTINO_DARK,
    CUPERTINO_LIGHT,
    DARCULA,
    NORD_DARK,
    NORD_LIGHT,
    PRIMER_DARK,
    PRIMER_LIGHT;

    public static void handleTheme(Controller controller, Theme theme) {
        if (controller.getClass().getResource("/dz/usthb/pfeelt/themes/" + theme.name().toLowerCase() + ".css") != null)
            controller.getScene().getStylesheets().setAll(controller.getClass().getResource("/dz/usthb/pfeelt/themes/" + theme.name().toLowerCase() + ".css").toExternalForm());
        for (MenuItem item : controller.getThemeMenu().getItems())
            ((CheckMenuItem) item).setSelected(item.getText().equalsIgnoreCase(theme.name().replace("_", " ")));
    }

}
