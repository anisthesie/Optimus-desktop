package dz.usthb.pfeelt.ui;

import com.google.gson.JsonSyntaxException;
import com.opencsv.CSVWriter;
import dz.usthb.pfeelt.ee.Transformer;
import dz.usthb.pfeelt.ee.TransformerConfiguration;
import dz.usthb.pfeelt.helpers.DataHelpers;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static dz.usthb.pfeelt.helpers.DataHelpers.gson;

class EventHandler {

    static void handleTheme(Controller controller, Theme theme) {
        if (controller.getClass().getResource("/dz/usthb/pfeelt/themes/" + theme.name().toLowerCase() + ".css") != null)
            controller.getScene().getStylesheets().setAll(controller.getClass().getResource("/dz/usthb/pfeelt/themes/" + theme.name().toLowerCase() + ".css").toExternalForm());
        for (MenuItem item : controller.getThemeMenu().getItems())
            ((CheckMenuItem) item).setSelected(item.getText().equalsIgnoreCase(theme.name().replace("_", " ")));
    }

    static void handleInfo() {
        new Alert(Alert.AlertType.INFORMATION, "Optimus est un logiciel qui permet l'étude d'un transformateur triphasé à partir de méthodes de calculs.\n Ce logiciel a été crée par Anis Mebani et Mehdi Nouri, étudiants de l'USTHB en 2024. \n © Tous droits réservés", ButtonType.CLOSE).showAndWait();
    }

    static void handleExportJson(Controller controller) {
        handleBtnClicked(controller);

        if (controller.getCurrentConfiguration() == null) return;

        FileChooser fileChooser = DataHelpers.getOptimusFolder();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        fileChooser.setInitialFileName("Export JSON " + controller.getCurrentConfiguration().getPuissanceNominale() + "W");

        File file = fileChooser.showSaveDialog(controller.getScene().getWindow());

        controller.getCurrentConfiguration().setResults(DataHelpers.getData(controller,false));

        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(gson.toJson(controller.getCurrentConfiguration()));
                writer.close();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de l'écriture du fichier.", ButtonType.CLOSE).showAndWait();
            }
        }
        controller.getCurrentConfiguration().clearResults();
    }
    
    static void handleCsvExport(Controller controller) {

        handleBtnClicked(controller);

        if (controller.getCurrentConfiguration() == null) return;

        FileChooser fileChooser = DataHelpers.getOptimusFolder();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("Export CSV " + controller.getCurrentConfiguration().getPuissanceNominale() + "W");

        File file = fileChooser.showSaveDialog(controller.getScene().getWindow());

        if (file != null) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file), ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
                for (String[] line : DataHelpers.getData(controller,true))
                    writer.writeNext(line);

            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de l'écriture du fichier.", ButtonType.CLOSE).showAndWait();
            }
        }
    }
    
    static void handleLoadConfig(Controller controller) {

        FileChooser fileChooser = DataHelpers.getOptimusFolder();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        File file = fileChooser.showOpenDialog(controller.getScene().getWindow());

        if (file != null) {

            try {
                FileReader reader = new FileReader(file);
                controller.setCurrentConfiguration(gson.fromJson(reader, TransformerConfiguration.class));
                reader.close();
                System.out.println(controller.getCurrentConfiguration().getSerialVersionUID_object());
                System.out.println(TransformerConfiguration.getSerialVersionUID());
                if (controller.getCurrentConfiguration() == null || controller.getCurrentConfiguration().getSerialVersionUID_object() != TransformerConfiguration.getSerialVersionUID())
                    throw new JsonSyntaxException("");
                TransformerConfiguration configuration = controller.getCurrentConfiguration();
                configuration.clearResults();

                controller.getPuissanceNomoinale().setText(String.valueOf(configuration.getPuissanceNominale()));
                controller.getTensionPrimaire().setText(String.valueOf(configuration.getTensionPrimaire()));
                controller.getTensionSecondaire().setText(String.valueOf(configuration.getTensionSecondaire()));
                controller.getFrequence().setText(String.valueOf(configuration.getFrequence()));
                controller.getPertesCourtCircuit().setText(String.valueOf(configuration.getPertesCourtCircuit()));
                controller.getTensionCourtCircuit().setText(String.valueOf(configuration.getTensionCourtCircuit()));
                controller.getIsolationCouches().setText(String.valueOf(configuration.getIsolationCouches()));
                controller.getLargeurRefroidissement().setText(String.valueOf(configuration.getLargeurRefroidissement()));

                controller.getConnexionPrimaire().setValue(configuration.getConnexionPrimaire() == TransformerConfiguration.Connexion.DELTA ? "Delta" : "Étoile");
                controller.getConnexionSecondaire().setValue(configuration.getConnexionSecondaire() == TransformerConfiguration.Connexion.DELTA ? "Delta" : "Étoile");
                controller.getRefroidissementField().setValue(configuration.getRefroidissement() == TransformerConfiguration.Refroidissement.NATUREL ? "Naturel" : "Forcé");

            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de la lecture du fichier.", ButtonType.CLOSE).showAndWait();
            } catch (JsonSyntaxException jsonSyntaxException) {
                new Alert(Alert.AlertType.ERROR, "Ce fichier ne contient pas de configuration.", ButtonType.CLOSE).showAndWait();
            }
        }
    }
    
    static void handleSaveConfig(Controller controller) {

        final TransformerConfiguration configuration = readConfig(controller);

        if (configuration == null) return;

        FileChooser fileChooser = DataHelpers.getOptimusFolder();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        fileChooser.setInitialFileName("Config " + configuration.getPuissanceNominale() + "W");

        File file = fileChooser.showSaveDialog(controller.getScene().getWindow());

        if (file != null) {
            String json = gson.toJson(controller.getCurrentConfiguration());

            try {
                FileWriter writer = new FileWriter(file);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de l'écriture du fichier.", ButtonType.CLOSE).showAndWait();
            }
        }
    }
    
    static void handleBtnClicked(Controller controller) {
        controller.setCurrentConfiguration(readConfig(controller));
        if (controller.getCurrentConfiguration() == null) return;

        Transformer.calculate(controller);
    }

    private static TransformerConfiguration readConfig(Controller controller) {
        double[] parsedInputs = new double[8];
        TransformerConfiguration.Connexion connexion1 = TransformerConfiguration.Connexion.ETOILE;
        TransformerConfiguration.Connexion connexion2 = TransformerConfiguration.Connexion.ETOILE;
        TransformerConfiguration.Refroidissement refroidissement = TransformerConfiguration.Refroidissement.FORCE;

        controller.getErrorLabel().setVisible(false);
        boolean error = false;
        TextField[] inputs = {controller.getPuissanceNomoinale(), controller.getTensionPrimaire(), controller.getTensionSecondaire(), controller.getFrequence(), controller.getPertesCourtCircuit(), controller.getTensionCourtCircuit(), controller.getIsolationCouches(), controller.getLargeurRefroidissement()};
        for (int i = 0; i < inputs.length; i++) {
            TextField currentField = inputs[i];
            double parsedDouble = -1;
            try {
                parsedDouble = Double.parseDouble(currentField.getText().replace(",", "."));
                currentField.setStyle("-fx-border-color: null");
                parsedInputs[i] = parsedDouble;
            } catch (NumberFormatException e) {
                currentField.setStyle("-fx-border-color: red");
                error = true;
            }
        }

        controller.getConnexionPrimaire().setStyle("-fx-border-color: null");
        if (controller.getConnexionPrimaire().getValue() != null) {
            if (controller.getConnexionPrimaire().getValue().equalsIgnoreCase("delta"))
                connexion1 = TransformerConfiguration.Connexion.DELTA;
        } else {
            error = true;
            controller.getConnexionPrimaire().setStyle("-fx-border-color: red");
        }

        controller.getConnexionSecondaire().setStyle("-fx-border-color: null");
        if (controller.getConnexionSecondaire().getValue() != null) {
            if (controller.getConnexionSecondaire().getValue().equalsIgnoreCase("delta"))
                connexion2 = TransformerConfiguration.Connexion.DELTA;
        } else {
            error = true;
            controller.getConnexionSecondaire().setStyle("-fx-border-color: red");
        }

        controller.getRefroidissementField().setStyle("-fx-border-color: null");
        if (controller.getRefroidissementField().getValue() != null) {
            if (controller.getRefroidissementField().getValue().equalsIgnoreCase("naturel"))
                refroidissement = TransformerConfiguration.Refroidissement.NATUREL;
        } else {
            error = true;
            controller.getRefroidissementField().setStyle("-fx-border-color: red");
        }

        controller.getErrorLabel().setVisible(error);

        return error ? null : new TransformerConfiguration(parsedInputs, connexion1, connexion2, refroidissement);
    }
}
