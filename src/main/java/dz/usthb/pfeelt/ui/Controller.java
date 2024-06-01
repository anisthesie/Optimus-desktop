package dz.usthb.pfeelt.ui;

import dz.usthb.pfeelt.ee.TransformerConfiguration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;


public class Controller {

    @FXML
    private Button calcBtn;
    @FXML
    private TextField tensionPhaseSecondaire;
    @FXML
    private TextField puissanceApparanteColonne;
    @FXML
    private TextField pertesCourtCircuit;
    @FXML
    private TextField sectionDuFer;
    @FXML
    private TextField diametreCanalFuiteRecalc;
    @FXML
    private TextField tensionCourtCircuit;
    @FXML
    private TextField puissanceNomoinale;
    @FXML
    private TextField tensionPrimaire, tensionSecondaire;
    @FXML
    private TextField tensionPhasePrimaire;
    @FXML
    private TextField sveltessRecalc;
    @FXML
    private TextField epaisseurHT;
    @FXML
    private TextField courantPhasePrimaire;
    @FXML
    private TextField courantPhaseSecondaire;
    @FXML
    private TextField diametreColonne;
    @FXML
    private TextField diametreCanalDeFuite;
    @FXML
    private TextField frequence;
    @FXML
    private TextField tensionSpireRecalc;
    @FXML
    private TextField nombreSpiresHT;
    @FXML
    private TextField tensionSpirePhase;
    @FXML
    private TextField diametreConducteurIsole;
    @FXML
    private TextField nombreSpires;
    @FXML
    private TextField inductionMagnRecalc;
    @FXML
    private TextField densiteCourant;
    @FXML
    private TextField densiteCourantRecalc;
    @FXML
    private TextField sectionConducteurPrimaire;
    @FXML
    private TextField hauteurDeBobineBT;
    @FXML
    private TextField hauteurDeBobineHT;
    @FXML
    private TextField spireParCouche;
    @FXML
    private TextField epaisseurConducteur;
    @FXML
    private TextField sectionConducteurSecondaire;
    @FXML
    private TextField nombreCouches;
    @FXML
    private TextField rogowskiRecalc;
    @FXML
    private TextField diametreMoyenBT;
    @FXML
    private TextField ukrRecalc;
    @FXML
    private TextField isolationCouches;
    @FXML
    private TextField largeurRefroidissement;
    @FXML
    private TextField longueurSpire;
    @FXML
    private TextField epaisseurRapportee;
    @FXML
    private TextField longueurTotale;
    @FXML
    private TextField resistanceCc;
    @FXML
    private TextField largeurFenetre;
    @FXML
    private TextField hauteurFenetre;
    @FXML
    private TextField sectionCulasse;
    @FXML
    private TextField longueurCulasseSansCoins;
    @FXML
    private TextField largeurCulasse;
    @FXML
    private TextField resistanceCcHt;
    @FXML
    private TextField longueurTotaleHt;
    @FXML
    private TextField longueurSpireHt;
    @FXML
    private TextField diametreMoyenHT;
    @FXML
    private TextField hauteurCulasse;
    @FXML
    private TextField poidsHt;
    @FXML
    private TextField poidsBt;
    @FXML
    private TextField poidsTotal;
    @FXML
    private TextField poidsCoins;
    @FXML
    private TextField poidsColonnes;
    @FXML
    private TextField poidsCulasses;
    @FXML
    private TextField poidsTotalCM;
    @FXML
    private TextField hauteurTcm;
    @FXML
    private TextField inductionCoins;
    @FXML
    private TextField pertesJouleBt;
    @FXML
    private TextField pertesJouleHtConnexions;
    @FXML
    private TextField pertesJouleHt;
    @FXML
    private TextField pertesJouleConnexions;
    @FXML
    private TextField pertesJouleEnroulements;
    @FXML
    private TextField pertesJouleBtConnexions;
    @FXML
    private TextField pertesSupp;
    @FXML
    private TextField pertesFerCoins;
    @FXML
    private TextField pertesFerColonnes;
    @FXML
    private TextField pertesFerCulasses;
    @FXML
    private TextField pertesFerTotales;
    @FXML
    private TextField rendementGlobal;
    @FXML
    private TextField tensionCourtCircuitVerif;
    @FXML
    private TextField pertesJouleTotales;
    @FXML
    private TextField inductionCulasse;
    @FXML
    private ChoiceBox<String> connexionPrimaire;
    @FXML
    private ChoiceBox<String> connexionSecondaire;
    @FXML
    private ChoiceBox<String> refroidissementField;
    @FXML
    private Menu themeMenu;
    @FXML
    private Label errorLabel;

    private TransformerConfiguration currentConfiguration;


    @FXML
    void calcBtnClicked(ActionEvent event) {
        EventHandler.handleBtnClicked(this);
    }


    @FXML
    void saveConfigEvent(ActionEvent event) {
        EventHandler.handleSaveConfig(this);
    }

    @FXML
    void loadConfigEvent(ActionEvent event) {
        EventHandler.handleLoadConfig(this);
    }

    @FXML
    void exportCsv(ActionEvent event) {
        EventHandler.handleCsvExport(this);
    }

    @FXML
    void exportJson(ActionEvent event) {
        EventHandler.handleExportJson(this);
    }

    @FXML
    void aPropos(ActionEvent event) {
        EventHandler.handleInfo();
    }

    @FXML
    void cupertinoDark(ActionEvent event) {
        EventHandler.handleTheme(this, Theme.CUPERTINO_DARK);
    }

    @FXML
    void cupertinoLight(ActionEvent event) {
        EventHandler.handleTheme(this, Theme.CUPERTINO_LIGHT);
    }

    @FXML
    void dracula(ActionEvent event) {
        EventHandler.handleTheme(this, Theme.DARCULA);
    }

    @FXML
    void nordDark(ActionEvent event) {
        EventHandler.handleTheme(this, Theme.NORD_DARK);
    }

    @FXML
    void nordLight(ActionEvent event) {
        EventHandler.handleTheme(this, Theme.NORD_LIGHT);
    }

    @FXML
    void primerDark(ActionEvent event) {
        EventHandler.handleTheme(this, Theme.PRIMER_DARK);
    }

    @FXML
    void primerLight(ActionEvent event) {
        EventHandler.handleTheme(this, Theme.PRIMER_LIGHT);
    }

    public Scene getScene() {
        return calcBtn.getScene();
    }

    public Menu getThemeMenu() {
        return themeMenu;
    }

    public Button getCalcBtn() {
        return calcBtn;
    }

    public TextField getTensionPhaseSecondaire() {
        return tensionPhaseSecondaire;
    }

    public TextField getPuissanceApparanteColonne() {
        return puissanceApparanteColonne;
    }

    public TextField getPertesCourtCircuit() {
        return pertesCourtCircuit;
    }

    public TextField getSectionDuFer() {
        return sectionDuFer;
    }

    public TextField getDiametreCanalFuiteRecalc() {
        return diametreCanalFuiteRecalc;
    }

    public TextField getTensionCourtCircuit() {
        return tensionCourtCircuit;
    }

    public TextField getPuissanceNomoinale() {
        return puissanceNomoinale;
    }

    public TextField getTensionPrimaire() {
        return tensionPrimaire;
    }

    public TextField getTensionSecondaire() {
        return tensionSecondaire;
    }

    public TextField getTensionPhasePrimaire() {
        return tensionPhasePrimaire;
    }

    public TextField getSveltessRecalc() {
        return sveltessRecalc;
    }

    public TextField getEpaisseurHT() {
        return epaisseurHT;
    }

    public TextField getCourantPhasePrimaire() {
        return courantPhasePrimaire;
    }

    public TextField getCourantPhaseSecondaire() {
        return courantPhaseSecondaire;
    }

    public TextField getDiametreColonne() {
        return diametreColonne;
    }

    public TextField getDiametreCanalDeFuite() {
        return diametreCanalDeFuite;
    }

    public TextField getFrequence() {
        return frequence;
    }

    public TextField getTensionSpireRecalc() {
        return tensionSpireRecalc;
    }

    public TextField getNombreSpiresHT() {
        return nombreSpiresHT;
    }

    public TextField getTensionSpirePhase() {
        return tensionSpirePhase;
    }

    public TextField getDiametreConducteurIsole() {
        return diametreConducteurIsole;
    }

    public TextField getNombreSpires() {
        return nombreSpires;
    }

    public TextField getInductionMagnRecalc() {
        return inductionMagnRecalc;
    }

    public TextField getDensiteCourant() {
        return densiteCourant;
    }

    public TextField getDensiteCourantRecalc() {
        return densiteCourantRecalc;
    }

    public TextField getSectionConducteurPrimaire() {
        return sectionConducteurPrimaire;
    }

    public TextField getHauteurDeBobineBT() {
        return hauteurDeBobineBT;
    }

    public TextField getHauteurDeBobineHT() {
        return hauteurDeBobineHT;
    }

    public TextField getSpireParCouche() {
        return spireParCouche;
    }

    public TextField getEpaisseurConducteur() {
        return epaisseurConducteur;
    }

    public TextField getSectionConducteurSecondaire() {
        return sectionConducteurSecondaire;
    }

    public TextField getNombreCouches() {
        return nombreCouches;
    }

    public TextField getRogowskiRecalc() {
        return rogowskiRecalc;
    }

    public TextField getDiametreMoyenBT() {
        return diametreMoyenBT;
    }

    public TextField getUkrRecalc() {
        return ukrRecalc;
    }

    public TextField getIsolationCouches() {
        return isolationCouches;
    }

    public TextField getLargeurRefroidissement() {
        return largeurRefroidissement;
    }

    public TextField getLongueurSpire() {
        return longueurSpire;
    }

    public TextField getEpaisseurRapportee() {
        return epaisseurRapportee;
    }

    public TextField getLongueurTotale() {
        return longueurTotale;
    }

    public TextField getResistanceCc() {
        return resistanceCc;
    }

    public TextField getLargeurFenetre() {
        return largeurFenetre;
    }

    public TextField getHauteurFenetre() {
        return hauteurFenetre;
    }

    public TextField getSectionCulasse() {
        return sectionCulasse;
    }

    public TextField getLongueurCulasseSansCoins() {
        return longueurCulasseSansCoins;
    }

    public TextField getLargeurCulasse() {
        return largeurCulasse;
    }

    public TextField getResistanceCcHt() {
        return resistanceCcHt;
    }

    public TextField getLongueurTotaleHt() {
        return longueurTotaleHt;
    }

    public TextField getLongueurSpireHt() {
        return longueurSpireHt;
    }

    public TextField getDiametreMoyenHT() {
        return diametreMoyenHT;
    }

    public TextField getHauteurCulasse() {
        return hauteurCulasse;
    }

    public TextField getPoidsHt() {
        return poidsHt;
    }

    public TextField getPoidsBt() {
        return poidsBt;
    }

    public TextField getPoidsTotal() {
        return poidsTotal;
    }

    public TextField getPoidsCoins() {
        return poidsCoins;
    }

    public TextField getPoidsColonnes() {
        return poidsColonnes;
    }

    public TextField getPoidsCulasses() {
        return poidsCulasses;
    }

    public TextField getPoidsTotalCM() {
        return poidsTotalCM;
    }

    public TextField getHauteurTcm() {
        return hauteurTcm;
    }

    public TextField getInductionCoins() {
        return inductionCoins;
    }

    public TextField getPertesJouleBt() {
        return pertesJouleBt;
    }

    public TextField getPertesJouleHtConnexions() {
        return pertesJouleHtConnexions;
    }

    public TextField getPertesJouleHt() {
        return pertesJouleHt;
    }

    public TextField getPertesJouleConnexions() {
        return pertesJouleConnexions;
    }

    public TextField getPertesJouleEnroulements() {
        return pertesJouleEnroulements;
    }

    public TextField getPertesJouleBtConnexions() {
        return pertesJouleBtConnexions;
    }

    public TextField getPertesSupp() {
        return pertesSupp;
    }

    public TextField getPertesFerCoins() {
        return pertesFerCoins;
    }

    public TextField getPertesFerColonnes() {
        return pertesFerColonnes;
    }

    public TextField getPertesFerCulasses() {
        return pertesFerCulasses;
    }

    public TextField getPertesFerTotales() {
        return pertesFerTotales;
    }

    public TextField getRendementGlobal() {
        return rendementGlobal;
    }

    public TextField getTensionCourtCircuitVerif() {
        return tensionCourtCircuitVerif;
    }

    public TextField getPertesJouleTotales() {
        return pertesJouleTotales;
    }

    public TextField getInductionCulasse() {
        return inductionCulasse;
    }

    public ChoiceBox<String> getConnexionPrimaire() {
        return connexionPrimaire;
    }

    public ChoiceBox<String> getConnexionSecondaire() {
        return connexionSecondaire;
    }

    public ChoiceBox<String> getRefroidissementField() {
        return refroidissementField;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public TransformerConfiguration getCurrentConfiguration() {
        return currentConfiguration;
    }

    public void setCurrentConfiguration(TransformerConfiguration transformerConfiguration) {
        this.currentConfiguration = transformerConfiguration;
    }
}