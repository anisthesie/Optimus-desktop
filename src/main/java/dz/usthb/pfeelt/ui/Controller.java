package dz.usthb.pfeelt.ui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.opencsv.CSVWriter;
import dz.usthb.pfeelt.ee.TransformerConfiguration;
import dz.usthb.pfeelt.helpers.EEHelpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static dz.usthb.pfeelt.helpers.EEHelpers.getDoubleFromTextField;
import static dz.usthb.pfeelt.helpers.EEHelpers.getSpecificLosses;

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

    public TransformerConfiguration currentConfiguration;

    public static Gson gson = new Gson();

    public Scene getScene() {
        return calcBtn.getScene();
    }

    public Menu getThemeMenu() {
        return themeMenu;
    }

    private TransformerConfiguration readConfig() {
        double[] parsedInputs = new double[8];
        TransformerConfiguration.Connexion connexion1 = TransformerConfiguration.Connexion.ETOILE;
        TransformerConfiguration.Connexion connexion2 = TransformerConfiguration.Connexion.ETOILE;
        TransformerConfiguration.Refroidissement refroidissement = TransformerConfiguration.Refroidissement.FORCE;

        errorLabel.setVisible(false);
        boolean error = false;
        TextField[] inputs = {puissanceNomoinale, tensionPrimaire, tensionSecondaire, frequence, pertesCourtCircuit, tensionCourtCircuit, isolationCouches, largeurRefroidissement};
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

        connexionPrimaire.setStyle("-fx-border-color: null");
        if (connexionPrimaire.getValue() != null) {
            if (connexionPrimaire.getValue().equalsIgnoreCase("delta"))
                connexion1 = TransformerConfiguration.Connexion.DELTA;
        } else {
            error = true;
            connexionPrimaire.setStyle("-fx-border-color: red");
        }

        connexionSecondaire.setStyle("-fx-border-color: null");
        if (connexionSecondaire.getValue() != null) {
            if (connexionSecondaire.getValue().equalsIgnoreCase("delta"))
                connexion2 = TransformerConfiguration.Connexion.DELTA;
        } else {
            error = true;
            connexionSecondaire.setStyle("-fx-border-color: red");
        }

        refroidissementField.setStyle("-fx-border-color: null");
        if (refroidissementField.getValue() != null) {
            if (refroidissementField.getValue().equalsIgnoreCase("naturel"))
                refroidissement = TransformerConfiguration.Refroidissement.NATUREL;
        } else {
            error = true;
            refroidissementField.setStyle("-fx-border-color: red");
        }

        errorLabel.setVisible(error);

        return error ? null : new TransformerConfiguration(parsedInputs, connexion1, connexion2, refroidissement);
    }

    @FXML
    void calcBtnClicked(ActionEvent event) {

        currentConfiguration = readConfig();
        if (currentConfiguration == null) return;


        String connexionPrimaireStr = connexionPrimaire.getValue();
        String connexionSecondaireStr = connexionSecondaire.getValue();


        double tensionPhasePrimaireDouble = 0;
        double tensionPhaseSecondaireDouble = 0;

        // Tension de phase primaire
        if (connexionPrimaireStr.equalsIgnoreCase("delta"))
            tensionPhasePrimaireDouble = getDoubleFromTextField(tensionPrimaire);
        else
            tensionPhasePrimaireDouble = getDoubleFromTextField(tensionPrimaire) / Math.sqrt(3);
        tensionPhasePrimaire.setText(String.format("%.2f", tensionPhasePrimaireDouble) + " (kV)");
        //-------------------------

        // Tension de phase secondaire
        if (connexionSecondaireStr.equalsIgnoreCase("delta"))
            tensionPhaseSecondaireDouble = getDoubleFromTextField(tensionSecondaire);
        else
            tensionPhaseSecondaireDouble = getDoubleFromTextField(tensionSecondaire) / Math.sqrt(3);

        tensionPhaseSecondaire.setText(String.format("%.2f", tensionPhaseSecondaireDouble) + " (kV)");
        //-------------------------


        // Puissance apparante par collonne
        double puissanceAppColonneDouble = getDoubleFromTextField((puissanceNomoinale)) / 3;
        puissanceApparanteColonne.setText(String.format("%.2f", puissanceAppColonneDouble) + " (kVA)");
        //---------------------------------

        // Courant de phase primaire
        double if1 = puissanceAppColonneDouble / tensionPhasePrimaireDouble;
        courantPhasePrimaire.setText(String.format("%.2f", if1) + " (A)");
        //--------------------------

        // Courant de phase secondaire
        double if2 = puissanceAppColonneDouble / tensionPhaseSecondaireDouble;
        courantPhaseSecondaire.setText(String.format("%.2f", if2) + " (A)");
        //--------------------------

        double tensionNominalePrimaire = getDoubleFromTextField(tensionPrimaire);
        double puissanceApparante = getDoubleFromTextField((puissanceNomoinale));
        double tensionCourtCircuitDouble = getDoubleFromTextField((tensionCourtCircuit));
        double pertesCourtCircuitDouble = getDoubleFromTextField(pertesCourtCircuit) / 1000;
        double a12 = EEHelpers.getA12(tensionNominalePrimaire);
        double tensionSecondaireDouble = getDoubleFromTextField(tensionSecondaire);
        double tensionPrimaireDouble = getDoubleFromTextField(tensionPrimaire);
        double frequenceDouble = getDoubleFromTextField(frequence);
        double puissanceNominaleDouble = getDoubleFromTextField(puissanceNomoinale);

        double Ar = a12 + EEHelpers.getK(puissanceAppColonneDouble, tensionNominalePrimaire) * Math.pow(puissanceAppColonneDouble, 0.25);
        double B = EEHelpers.getB(puissanceAppColonneDouble, tensionNominalePrimaire);
        double Ka = 0.95;
        double Bc = EEHelpers.getBc(puissanceApparante);
        double Ku = EEHelpers.getKu(puissanceAppColonneDouble);
        double Uk = tensionCourtCircuitDouble;
        double Uka = (pertesCourtCircuitDouble / puissanceApparante) * 100;
        double Ukr = Math.sqrt(Math.abs((Uk * Uk) - (Uka * Uka))) / 100;

        //----- Diamètre de la colonne
        double D = 1.0674 * Math.pow((Ar * B * Ka * puissanceAppColonneDouble * 10) / ((Ku * Ku) * (Bc * Bc) * Ukr), 0.25d);
        diametreColonne.setText(String.format("%.2f", D) + " (cm)");
        //-------------------


        // ---- CANAL DE FUITE D12 ------
        double a01 = EEHelpers.getA01(tensionSecondaireDouble);
        double a1 = EEHelpers.getA1(puissanceAppColonneDouble, tensionPrimaireDouble);

        double D12 = D + 2 * a01 + 2 * a1 + a12;
        diametreCanalDeFuite.setText(String.format("%.2f", D12) + " (cm)");
        //----------------------------------


        // --------- Section du fer
        double SFer = (Ku * Math.PI * D * D / 4) / 10000;
        sectionDuFer.setText(String.format("%.2f", SFer * 10000) + " (cm2)");
        //------------------------


        // -------- Tension spire par phase
        double Usp = Math.sqrt(2) * Math.PI * frequenceDouble * SFer * Bc;
        tensionSpirePhase.setText(String.format("%.2f", Usp) + " (V)");
        // -----------------------------


        // -------- Nombre de spires
        double w1 = Math.round((tensionPhasePrimaireDouble * 1000) / Usp);
        double w2 = Math.round((tensionPhaseSecondaireDouble * 1000) / Usp);

        nombreSpires.setText(String.format("%.2f", w1) + " / " + String.format("%.2f", w2));
        // -------------------------


        // ----- Tension spire par phase recalculée
        double UspR = tensionPhasePrimaireDouble * 1000 / w1;
        tensionSpireRecalc.setText(String.format("%.2f", UspR) + " (V)");
        // --------------------------------


        // ---------- Induction magnétique recalculée
        double Bcr = UspR / (4.44 * frequenceDouble * SFer);
        inductionMagnRecalc.setText(String.format("%.2f", Bcr) + " (T)");
        // --------------------------


        // -------- Densité du courant (A/mm2)
        double J = (7.34 * pertesCourtCircuitDouble * UspR) / (EEHelpers.getKpk(puissanceNominaleDouble) * puissanceNominaleDouble * D12 / 100);
        densiteCourant.setText(String.format("%.2f", J) + " (A/mm2)");
        // --------------------------


        // ----- Section conducteur primaire
        double sectionConducteurPrimaireDouble = if2 / J;
        sectionConducteurPrimaire.setText(String.format("%.2f", sectionConducteurPrimaireDouble) + " (mm2)");
        // ---------------------


        // ------ Densité courant recalculée
        double Jrc = if2 / sectionConducteurPrimaireDouble;
        densiteCourantRecalc.setText(String.format("%.3f", Jrc) + " (A/mm2)");
        //-----------------------------

        // ------ Section conducteur secondaire
        double sectionConducteurSecondaireDouble = if1 / Jrc;
        sectionConducteurSecondaire.setText(String.format("%.2f", sectionConducteurSecondaireDouble) + " (mm2)");
        // ---------------------------------

        // ---- Hauteur bobine
        double hb = Math.PI * D12 / B;
        hauteurDeBobineBT.setText(String.format("%.2f", hb) + " (cm)");
        hauteurDeBobineHT.setText(String.format("%.2f", hb) + " (cm)");
        // ---------------

        // ----- épaisseur du conducteur
        double a = sectionConducteurPrimaireDouble / (hb * 10);
        epaisseurConducteur.setText(String.format("%.2f", a) + " (mm)");
        // -----------------------------

        // diametre canal de fuite recalc-----------
        double d12r = D + 2 * a01 + 2 * a1 + a12;
        diametreCanalFuiteRecalc.setText(String.format("%.2f", d12r) + " (cm)");
        //-------------------------------

        // Sveltess recalc Br -----
        double Br = Math.PI * d12r / hb;
        sveltessRecalc.setText(String.format("%.2f", Br));
        // ----------------

        // ----------- diametre conducteur isolé
        double dprime = sectionConducteurSecondaireDouble + EEHelpers.getTg(tensionPrimaireDouble);
        diametreConducteurIsole.setText(String.format("%.2f", dprime) + " (mm)");
        //-----------------------------

        //---------------- nombre de spires enroulement haute tension
        double nombreSpiresHTDouble = Math.round(w2 * tensionPhasePrimaireDouble / tensionPhaseSecondaireDouble);
        nombreSpiresHT.setText(String.format("%.2f", nombreSpiresHTDouble));
        //-----------------------

        //---------- Spires par couche
        double spiresParCoucheDouble = ((hb * 10 - 10) / dprime) - 1;
        spireParCouche.setText(String.format("%.2f", Math.ceil(spiresParCoucheDouble)));
        //----------------------------

        //------ Nombre couches
        double nombreCouchesDouble = nombreSpiresHTDouble / spiresParCoucheDouble;
        nombreCouches.setText(String.format("%.2f", Math.ceil(nombreCouchesDouble)));
        //----------------------------


        //----- Epaisseur enroulement HT
        double sp = getDoubleFromTextField(isolationCouches);
        double sr = getDoubleFromTextField(largeurRefroidissement);
        double a2r = (dprime * nombreCouchesDouble + sp * (nombreCouchesDouble - 2) + sr) / 10;
        epaisseurHT.setText(String.format("%.2f", a2r) + " (cm)");
        //---------------------


        //---- Epaisseur rapportee canale de fuite recalculee
        double epaisseurRapporteeDouble = a12 + (a1 + a2r) / 3;
        epaisseurRapportee.setText(String.format("%.2f", epaisseurRapporteeDouble) + " (cm)");
        //----------------------

        //------- Rogowski
        double Kr = 1 - (1 / (2 * Math.PI) * ((2 * a12 + a1 + a2r) / hb));
        rogowskiRecalc.setText(String.format("%.2f", Kr));
        //---------------------

        //-------- Tension de court circuit recalculée
        double UkrRecalc = (2 * Math.PI * frequenceDouble * 4 * Math.PI * Math.pow(10, -7) * w2 * w2 * Ar * B * Kr * if2) / (1000 * tensionPhaseSecondaireDouble);
        ukrRecalc.setText(String.format("%.2f", UkrRecalc) + " %");
        //----------------------

        //---- Diametre moyen enroulement basse tension
        double dm1 = D + 2 * a01 + a1;
        diametreMoyenBT.setText(String.format("%.2f", dm1) + " (cm)");
        //-----------------

        //--- Longueur moyenne spire
        double lw1 = Math.PI * dm1 / 100;
        longueurSpire.setText(String.format("%.2f", lw1) + " (m)");
        //-------------

        //------ Longueur totale
        double L1 = w2 * lw1;
        longueurTotale.setText(String.format("%.2f", L1) + " (m/phase)");
        //-----------------

        //---- Résistance en courznt continu  BT
        double R1 = (L1 * 0.0215 * 1000) / sectionConducteurPrimaireDouble;
        resistanceCc.setText(String.format("%.2f", R1) + " (milliOhm)");
        //--------------

        //----- Diametre moyen enroulement haute tension
        double dm2 = D + 2 * a01 + 2 * a1 + 2 * a12 + a2r;
        double lw2 = Math.PI * dm2 / 100;
        double L2 = nombreSpiresHTDouble * lw2;
        double R2 = (0.0215 * L2) / sectionConducteurSecondaireDouble;

        longueurTotaleHt.setText(String.format("%.2f", L2) + " (m/phase)");
        longueurSpireHt.setText(String.format("%.2f", lw2) + " (m)");
        diametreMoyenHT.setText(String.format("%.2f", dm2) + " (cm)");
        resistanceCcHt.setText(String.format("%.2f", R2) + " (Ohm)");
        //------------------

        // -------- Largeur fenêtre
        double a22 = a1; // @TODO calculer a22 et le remplacer
        double LF = 2 * (a01 + a1 + a12 + a2r) + a22;
        largeurFenetre.setText(String.format("%.2f", LF) + " (cm)");
        //--------------

        //----- Huateur fenetre
        double l02 = a2r; // @TODO calculer l02
        double hf = 2 * l02 + hb;
        hauteurFenetre.setText(String.format("%.2f", hf) + " (cm)");
        //------------

        //------ Section de la culasse
        double scul = SFer * 1.15 * 10000;
        sectionCulasse.setText(String.format("%.2f", scul) + " (cm2)");
        //----------------

        // --------- Longueur culasse sans coins
        double lncul = LF;
        longueurCulasseSansCoins.setText(String.format("%.2f", lncul) + " (cm)");
        //-------------------------

        //-------- Largeur de la culasse
        double lrcul = 0.96 * D;
        largeurCulasse.setText(String.format("%.2f", lrcul) + " (cm)");
        //----------------

        //-------- Hauteur de la culasse
        double hcul = scul / lrcul;
        hauteurCulasse.setText(String.format("%.2f", hcul) + " (cm)");
        //----------------

        //-------- Poids du matériel basse tension
        double G1 = 8.9 * L1 * sectionConducteurPrimaireDouble / 1000;
        poidsBt.setText(String.format("%.2f", G1) + " (kg/colonne)");
        //----------------

        //-------- Poids du matériel haute tension
        double G2 = 8.9 * L2 * sectionConducteurSecondaireDouble / 1000;
        poidsHt.setText(String.format("%.2f", G2) + " (kg/colonne)");
        //----------------

        //-------- Poids total
        double Gt = 3 * (G1 + G2);
        poidsTotal.setText(String.format("%.2f", Gt) + " (kg)");
        //----------------

        //-------- Poids culasses
        double Gcul = 4 * scul * lncul * 7.65 / 1000;
        poidsCulasses.setText(String.format("%.2f", Gcul) + " (kg)");
        //----------------

        //-------- Poids colonnes
        double Gcol = 3 * SFer * 10000 * hf * 7.65 / 1000;
        poidsColonnes.setText(String.format("%.2f", Gcol) + " (kg)");
        //----------------

        //-------- Poids coins
        double Gcoins = 6 * scul * D * 7.65 / 1000;
        poidsCoins.setText(String.format("%.2f", Gcoins) + " (kg)");
        //----------------

        //-------- Poids total ciruit magnétique
        double Gtcm = Gcol + Gcul + Gcoins;
        poidsTotalCM.setText(String.format("%.2f", Gtcm) + " (kg)");
        //----------------

        //-------- Hauteur totale du circuit magnétique
        double Hcm = hf + 2 * hcul;
        hauteurTcm.setText(String.format("%.2f", Hcm) + " (cm)");
        //----------------

        //-------- Induction magnétique dans la culasse
        double bcul = Bcr / 1.15;
        inductionCulasse.setText(String.format("%.2f", bcul) + " (T)");
        //----------------

        //------- Induction magnétique dans les coins
        double bcoins = (bcul + Bcr) / 2;
        inductionCoins.setText(String.format("%.2f", bcoins) + " (T)");
        //-----------------

        //---- Pertes joule basse tension
        double pj1 = 3 * R1 * if2 * if2 / 1000;
        pertesJouleBt.setText(String.format("%.2f", pj1) + " (W)");
        //---------

        //---- Pertes joule connexions basse tension
        boolean deltaBt = connexionSecondaireStr.equalsIgnoreCase("delta");
        double coeffBt = (deltaBt ? 14 : 7.5);
        double lc1 = coeffBt * hb / 100;
        double swc1 = (deltaBt ? (sectionConducteurPrimaireDouble * Math.sqrt(3)) : sectionConducteurPrimaireDouble);
        double r1c = 0.0215 * (lc1 / swc1);
        double pjc1 = r1c * if2 * if2;
        pertesJouleBtConnexions.setText(String.format("%.2f", pjc1) + " (W)");
        //--------------------

        //---- Pertes joule haute tension
        double pj2 = 3 * R2 * if1 * if1;
        pertesJouleHt.setText(String.format("%.2f", pj2) + " (W)");
        //---------

        //---- Pertes joule connexions haute tension
        boolean deltaHt = connexionPrimaireStr.equalsIgnoreCase("delta");
        double coeffHt = (deltaHt ? 14 : 7.5);
        double lc2 = coeffHt * hb / 100;
        double swc2 = (deltaHt ? (sectionConducteurSecondaireDouble * Math.sqrt(3)) : sectionConducteurSecondaireDouble);
        double r2c = 0.0215 * (lc2 / swc2);
        double pjc2 = r2c * if1 * if1;
        pertesJouleHtConnexions.setText(String.format("%.2f", pjc2) + " (W)");
        //--------------------

        //-------- Pertes joule enroulements
        double pjte = pj1 + pj2;
        pertesJouleEnroulements.setText(String.format("%.2f", pjte) + " (W)");
        //---------------

        //---------Pertes joule connexions
        double pjtc = pjc1 + pjc2;
        pertesJouleConnexions.setText(String.format("%.2f", pjtc) + " (W)");
        //-----------------

        //---------Pertes supplementaires
        double pjs = pjte * 0.02;
        pertesSupp.setText(String.format("%.2f", pjs) + " (W)");
        //-----------------

        //---------Pertes joule totales
        double pjt = pjte + pjtc + pjs;
        pertesJouleTotales.setText(String.format("%.2f", pjt) + " (W)");
        //-----------------

        //--------- Tension court-circuit verification
        double ukaRecalc = (pjt / puissanceNominaleDouble) / 10;
        double ukRecalc = Math.sqrt(ukaRecalc * ukaRecalc + UkrRecalc * UkrRecalc);
        tensionCourtCircuitVerif.setText(String.format("%.2f", ukRecalc) + " %");
        //-----------------

        //--------- Pertes fer dans les colonnes
        double pfcol = Gcol * getSpecificLosses(Bcr);
        pertesFerColonnes.setText(String.format("%.2f", pfcol) + " (W)");
        //-----------------

        //--------- Pertes fer dans les culasses
        double pfcul = Gcul * getSpecificLosses(bcul);
        pertesFerCulasses.setText(String.format("%.2f", pfcul) + " (W)");
        //-----------------

        //--------- Pertes fer dans les coins
        double pfcoins = Gcoins * getSpecificLosses(bcoins);
        pertesFerCoins.setText(String.format("%.2f", pfcoins) + " (W)");
        //-----------------

        //--------- Pertes fer totales
        double pftot = pfcoins + pfcul + pfcol;
        pertesFerTotales.setText(String.format("%.2f", pftot) + " (W)");
        //-----------------

        //--------- Rendement
        double rendement = 100 * ((puissanceNominaleDouble * 1000 - pjt - pftot - pjs) / (puissanceNominaleDouble * 1000));
        rendementGlobal.setText(String.format("%.2f", rendement) + " %");
        //-----------------
    }

    @FXML
    void saveConfigEvent(ActionEvent event) {

        final TransformerConfiguration configuration = readConfig();

        if (configuration == null) return;

        FileChooser fileChooser = getOptimusFolder();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        fileChooser.setInitialFileName("Transformateur " + configuration.getPuissanceNominale() + " W");

        File file = fileChooser.showSaveDialog(getScene().getWindow());

        if (file != null) {
            String json = gson.toJson(currentConfiguration);

            try {
                FileWriter writer = new FileWriter(file);
                writer.write(json);
                writer.close();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de l'écriture du fichier : " + e.getMessage(), ButtonType.CLOSE).showAndWait();
            }
        }
    }

    @FXML
    void loadConfigEvent(ActionEvent event) {

        FileChooser fileChooser = getOptimusFolder();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        File file = fileChooser.showOpenDialog(getScene().getWindow());

        if (file != null) {

            try {
                FileReader reader = new FileReader(file);
                currentConfiguration = gson.fromJson(reader, TransformerConfiguration.class);
                reader.close();

                puissanceNomoinale.setText(String.valueOf(currentConfiguration.getPuissanceNominale()));
                tensionPrimaire.setText(String.valueOf(currentConfiguration.getTensionPrimaire()));
                tensionSecondaire.setText(String.valueOf(currentConfiguration.getTensionSecondaire()));
                frequence.setText(String.valueOf(currentConfiguration.getFrequence()));
                pertesCourtCircuit.setText(String.valueOf(currentConfiguration.getPertesCourtCircuit()));
                tensionCourtCircuit.setText(String.valueOf(currentConfiguration.getTensionCourtCircuit()));
                isolationCouches.setText(String.valueOf(currentConfiguration.getIsolationCouches()));
                largeurRefroidissement.setText(String.valueOf(currentConfiguration.getLargeurRefroidissement()));

                connexionPrimaire.setValue(currentConfiguration.getConnexionPrimaire() == TransformerConfiguration.Connexion.DELTA ? "Delta" : "Étoile");
                connexionSecondaire.setValue(currentConfiguration.getConnexionSecondaire() == TransformerConfiguration.Connexion.DELTA ? "Delta" : "Étoile");
                refroidissementField.setValue(currentConfiguration.getRefroidissement() == TransformerConfiguration.Refroidissement.NATUREL ? "Naturel" : "Forcé");
            } catch (JsonSyntaxException e) {
                new Alert(Alert.AlertType.ERROR, "Ce fichier ne contient pas de configuration valide : " + e.getMessage(), ButtonType.CLOSE).showAndWait();

            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de la lecture du fichier : " + e.getMessage(), ButtonType.CLOSE).showAndWait();
            }
        }
    }

    @FXML
    void exportCsv(ActionEvent event) {

        calcBtnClicked(null);

        if (currentConfiguration == null) return;

        FileChooser fileChooser = getOptimusFolder();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("Calcul transformateur " + currentConfiguration.getPuissanceNominale() + " W");

        File file = fileChooser.showSaveDialog(getScene().getWindow());

        if (file != null) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file), ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
                for (String[] line : getData(true))
                    writer.writeNext(line);

            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de l'écriture du fichier : " + e.getMessage(), ButtonType.CLOSE).showAndWait();
            }
        }
    }

    @FXML
    void exportJson(ActionEvent event) {
        calcBtnClicked(null);

        if (currentConfiguration == null) return;

        FileChooser fileChooser = getOptimusFolder();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        fileChooser.setInitialFileName("Calcul transformateur " + currentConfiguration.getPuissanceNominale() + " W");

        File file = fileChooser.showSaveDialog(getScene().getWindow());

        List<String[]> list = getData(false);
        Pair<TransformerConfiguration, List<String[]>> jsonObject = new ImmutablePair<>(currentConfiguration, list);

        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(gson.toJson(jsonObject));
                writer.close();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Une erreur s'est produite lors de l'écriture du fichier : " + e.getMessage(), ButtonType.CLOSE).showAndWait();
            }
        }
    }

    private List<String[]> getData(boolean includeInputData) {
        List<String[]> data = new ArrayList<>();

        if (includeInputData) {

            data.add(new String[]{"Puissance Nominale", currentConfiguration.getPuissanceNominale() + " (kVA)"});
            data.add(new String[]{"Tension Primaire", currentConfiguration.getTensionPrimaire() + " (kV)"});
            data.add(new String[]{"Tension Secondaire", currentConfiguration.getTensionSecondaire() + " (kV)"});
            data.add(new String[]{"Fréquence", currentConfiguration.getFrequence() + " (hz)"});
            data.add(new String[]{"Pertes en court-circuit", currentConfiguration.getPertesCourtCircuit() + " (W)"});
            data.add(new String[]{"Tension de court-circuit", currentConfiguration.getTensionCourtCircuit() + " (%)"});
            data.add(new String[]{"Isolation entre les couches", currentConfiguration.getIsolationCouches() + " (mm)"});
            data.add(new String[]{"Largeur canal de refroidissement", currentConfiguration.getLargeurRefroidissement() + " (mm)"});
            data.add(new String[]{"Connexion Primaire", currentConfiguration.getConnexionPrimaire().toString()});
            data.add(new String[]{"Connexion Secondaire", currentConfiguration.getConnexionSecondaire().toString()});
            data.add(new String[]{"Refroidissement", currentConfiguration.getRefroidissement().toString()});

            data.add(new String[]{"", ""});

        }
        data.add(new String[]{"Tension de phase primaire", tensionPhasePrimaire.getText()});
        data.add(new String[]{"Tension de phase secondaire", tensionPhaseSecondaire.getText()});
        data.add(new String[]{"Puissance apparente par colonne", puissanceApparanteColonne.getText()});
        data.add(new String[]{"Courant de phase primaire", courantPhasePrimaire.getText()});
        data.add(new String[]{"Courant de phase secondaire", courantPhaseSecondaire.getText()});
        data.add(new String[]{"Diamètre de la colonne", diametreColonne.getText()});
        data.add(new String[]{"Diamètre du canal de fuite", diametreCanalDeFuite.getText()});
        data.add(new String[]{"Section du fer", sectionDuFer.getText()});
        data.add(new String[]{"Tension de spire par phase", tensionSpirePhase.getText()});
        data.add(new String[]{"Nombre de spires", nombreSpires.getText()});
        data.add(new String[]{"Tension de spire recalculée", tensionSpireRecalc.getText()});
        data.add(new String[]{"Induction magnétique recalculée", inductionMagnRecalc.getText()});
        data.add(new String[]{"Densité du courant", densiteCourant.getText()});
        data.add(new String[]{"Densité du courant recalculée", densiteCourantRecalc.getText()});
        data.add(new String[]{"Section conducteur primaire", sectionConducteurPrimaire.getText()});
        data.add(new String[]{"Hauteur de bobine BT", hauteurDeBobineBT.getText()});
        data.add(new String[]{"Hauteur de bobine HT", hauteurDeBobineHT.getText()});
        data.add(new String[]{"Spire par couche", spireParCouche.getText()});
        data.add(new String[]{"Epaisseur du conducteur", epaisseurConducteur.getText()});
        data.add(new String[]{"Section conducteur secondaire", sectionConducteurSecondaire.getText()});
        data.add(new String[]{"Nombre de couches", nombreCouches.getText()});
        data.add(new String[]{"Facteur de Rogowski", rogowskiRecalc.getText()});
        data.add(new String[]{"Diamètre moyen BT", diametreMoyenBT.getText()});
        data.add(new String[]{"Tension de court-circuit recalculée", ukrRecalc.getText()});
        data.add(new String[]{"Isolation couches", isolationCouches.getText()});
        data.add(new String[]{"Largeur refroidissement", largeurRefroidissement.getText()});
        data.add(new String[]{"Epaisseur enroulement HT", epaisseurHT.getText()});
        data.add(new String[]{"Epaisseur rapportée canal de fuite", epaisseurRapportee.getText()});
        data.add(new String[]{"Longueur totale", longueurTotale.getText()});
        data.add(new String[]{"Résistance en courant continu BT", resistanceCc.getText()});
        data.add(new String[]{"Largeur fenêtre", largeurFenetre.getText()});
        data.add(new String[]{"Hauteur fenêtre", hauteurFenetre.getText()});
        data.add(new String[]{"Section de la culasse", sectionCulasse.getText()});
        data.add(new String[]{"Longueur culasse sans coins", longueurCulasseSansCoins.getText()});
        data.add(new String[]{"Largeur de la culasse", largeurCulasse.getText()});
        data.add(new String[]{"Résistance en courant continu HT", resistanceCcHt.getText()});
        data.add(new String[]{"Longueur totale HT", longueurTotaleHt.getText()});
        data.add(new String[]{"Longueur spire HT", longueurSpireHt.getText()});
        data.add(new String[]{"Diamètre moyen HT", diametreMoyenHT.getText()});
        data.add(new String[]{"Poids BT", poidsBt.getText()});
        data.add(new String[]{"Poids HT", poidsHt.getText()});
        data.add(new String[]{"Poids total", poidsTotal.getText()});
        data.add(new String[]{"Poids culasses", poidsCulasses.getText()});
        data.add(new String[]{"Poids colonnes", poidsColonnes.getText()});
        data.add(new String[]{"Poids coins", poidsCoins.getText()});
        data.add(new String[]{"Poids total CM", poidsTotalCM.getText()});
        data.add(new String[]{"Hauteur totale CM", hauteurTcm.getText()});
        data.add(new String[]{"Induction magnétique dans la culasse", inductionCulasse.getText()});
        data.add(new String[]{"Induction magnétique dans les coins", inductionCoins.getText()});
        data.add(new String[]{"Pertes Joule BT", pertesJouleBt.getText()});
        data.add(new String[]{"Pertes Joule connexions BT", pertesJouleBtConnexions.getText()});
        data.add(new String[]{"Pertes Joule HT", pertesJouleHt.getText()});
        data.add(new String[]{"Pertes Joule connexions HT", pertesJouleHtConnexions.getText()});
        data.add(new String[]{"Pertes Joule enroulements", pertesJouleEnroulements.getText()});
        data.add(new String[]{"Pertes Joule connexions", pertesJouleConnexions.getText()});
        data.add(new String[]{"Pertes supplémentaires", pertesSupp.getText()});
        data.add(new String[]{"Pertes fer colonnes", pertesFerColonnes.getText()});
        data.add(new String[]{"Pertes fer culasses", pertesFerCulasses.getText()});
        data.add(new String[]{"Pertes fer coins", pertesFerCoins.getText()});
        data.add(new String[]{"Pertes fer totales", pertesFerTotales.getText()});
        data.add(new String[]{"Rendement global", rendementGlobal.getText()});

        return data;
    }


    private FileChooser getOptimusFolder() {
        FileChooser fileChooser = new FileChooser();

        Path userHome = Paths.get(System.getProperty("user.home"), "Documents", "Optimus");
        File configDirectory = userHome.toFile();
        if (!configDirectory.exists())
            configDirectory.mkdirs();


        fileChooser.setInitialDirectory(configDirectory);
        return fileChooser;
    }

    @FXML
    void aPropos(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION, "Optimus est un logiciel qui permet l'étude d'un transformateur triphasé à partir de méthodes de calculs.\n Ce logiciel a été crée par Anis Mebani et Mehdi Nouri, étudiants de l'USTHB en 2024. \n © Tous droits réservés", ButtonType.CLOSE).showAndWait();
    }

    @FXML
    void cupertinoDark(ActionEvent event) {
        Theme.handleTheme(this, Theme.CUPERTINO_DARK);
    }

    @FXML
    void cupertinoLight(ActionEvent event) {
        Theme.handleTheme(this, Theme.CUPERTINO_LIGHT);
    }

    @FXML
    void dracula(ActionEvent event) {
        Theme.handleTheme(this, Theme.DARCULA);
    }

    @FXML
    void nordDark(ActionEvent event) {
        Theme.handleTheme(this, Theme.NORD_DARK);
    }

    @FXML
    void nordLight(ActionEvent event) {
        Theme.handleTheme(this, Theme.NORD_LIGHT);
    }

    @FXML
    void primerDark(ActionEvent event) {
        Theme.handleTheme(this, Theme.PRIMER_DARK);
    }

    @FXML
    void primerLight(ActionEvent event) {
        Theme.handleTheme(this, Theme.PRIMER_LIGHT);
    }


}