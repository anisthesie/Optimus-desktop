module dz.usthb.pfeelt {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.opencsv;
    requires org.apache.commons.lang3;
    requires com.jthemedetector;


    opens dz.usthb.pfeelt to javafx.fxml;
    exports dz.usthb.pfeelt;
    exports dz.usthb.pfeelt.ui;
    opens dz.usthb.pfeelt.ui to javafx.fxml;
    exports dz.usthb.pfeelt.helpers;
    opens dz.usthb.pfeelt.helpers to javafx.fxml;
    opens dz.usthb.pfeelt.ee to com.google.gson;

}