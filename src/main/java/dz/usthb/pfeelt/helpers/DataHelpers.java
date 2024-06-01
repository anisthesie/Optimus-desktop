package dz.usthb.pfeelt.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dz.usthb.pfeelt.ee.TransformerConfiguration;
import dz.usthb.pfeelt.ui.Controller;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataHelpers {


    public static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static List<String[]> getData(Controller controller, boolean includeInputData) {

        TransformerConfiguration currentConfiguration = controller.getCurrentConfiguration();
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
        data.add(new String[]{"Tension de phase primaire", controller.getTensionPhasePrimaire().getText()});
        data.add(new String[]{"Tension de phase secondaire", controller.getTensionPhaseSecondaire().getText()});
        data.add(new String[]{"Puissance apparente par colonne", controller.getPuissanceApparanteColonne().getText()});
        data.add(new String[]{"Courant de phase primaire", controller.getCourantPhasePrimaire().getText()});
        data.add(new String[]{"Courant de phase secondaire", controller.getCourantPhaseSecondaire().getText()});
        data.add(new String[]{"Diamètre de la colonne", controller.getDiametreColonne().getText()});
        data.add(new String[]{"Diamètre du canal de fuite", controller.getDiametreCanalDeFuite().getText()});
        data.add(new String[]{"Section du fer", controller.getSectionDuFer().getText()});
        data.add(new String[]{"Tension de spire par phase", controller.getTensionSpirePhase().getText()});
        data.add(new String[]{"Nombre de spires", controller.getNombreSpires().getText()});
        data.add(new String[]{"Tension de spire recalculée", controller.getTensionSpireRecalc().getText()});
        data.add(new String[]{"Induction magnétique recalculée", controller.getInductionMagnRecalc().getText()});
        data.add(new String[]{"Densité du courant", controller.getDensiteCourant().getText()});
        data.add(new String[]{"Densité du courant recalculée", controller.getDensiteCourantRecalc().getText()});
        data.add(new String[]{"Section conducteur primaire", controller.getSectionConducteurPrimaire().getText()});
        data.add(new String[]{"Hauteur de bobine BT", controller.getHauteurDeBobineBT().getText()});
        data.add(new String[]{"Hauteur de bobine HT", controller.getHauteurDeBobineHT().getText()});
        data.add(new String[]{"Spire par couche", controller.getSpireParCouche().getText()});
        data.add(new String[]{"Epaisseur du conducteur", controller.getEpaisseurConducteur().getText()});
        data.add(new String[]{"Section conducteur secondaire", controller.getSectionConducteurSecondaire().getText()});
        data.add(new String[]{"Nombre de couches", controller.getNombreCouches().getText()});
        data.add(new String[]{"Facteur de Rogowski", controller.getRogowskiRecalc().getText()});
        data.add(new String[]{"Diamètre moyen BT", controller.getDiametreMoyenBT().getText()});
        data.add(new String[]{"Tension de court-circuit recalculée", controller.getUkrRecalc().getText()});
        data.add(new String[]{"Isolation couches", controller.getIsolationCouches().getText()});
        data.add(new String[]{"Largeur refroidissement", controller.getLargeurRefroidissement().getText()});
        data.add(new String[]{"Epaisseur enroulement HT", controller.getEpaisseurHT().getText()});
        data.add(new String[]{"Epaisseur rapportée canal de fuite", controller.getEpaisseurRapportee().getText()});
        data.add(new String[]{"Longueur totale", controller.getLongueurTotale().getText()});
        data.add(new String[]{"Résistance en courant continu BT", controller.getResistanceCc().getText()});
        data.add(new String[]{"Largeur fenêtre", controller.getLargeurFenetre().getText()});
        data.add(new String[]{"Hauteur fenêtre", controller.getHauteurFenetre().getText()});
        data.add(new String[]{"Section de la culasse", controller.getSectionCulasse().getText()});
        data.add(new String[]{"Longueur culasse sans coins", controller.getLongueurCulasseSansCoins().getText()});
        data.add(new String[]{"Largeur de la culasse", controller.getLargeurCulasse().getText()});
        data.add(new String[]{"Résistance en courant continu HT", controller.getResistanceCcHt().getText()});
        data.add(new String[]{"Longueur totale HT", controller.getLongueurTotaleHt().getText()});
        data.add(new String[]{"Longueur spire HT", controller.getLongueurSpireHt().getText()});
        data.add(new String[]{"Diamètre moyen HT", controller.getDiametreMoyenHT().getText()});
        data.add(new String[]{"Poids BT", controller.getPoidsBt().getText()});
        data.add(new String[]{"Poids HT", controller.getPoidsHt().getText()});
        data.add(new String[]{"Poids total", controller.getPoidsTotal().getText()});
        data.add(new String[]{"Poids culasses", controller.getPoidsCulasses().getText()});
        data.add(new String[]{"Poids colonnes", controller.getPoidsColonnes().getText()});
        data.add(new String[]{"Poids coins", controller.getPoidsCoins().getText()});
        data.add(new String[]{"Poids total CM", controller.getPoidsTotalCM().getText()});
        data.add(new String[]{"Hauteur totale CM", controller.getHauteurTcm().getText()});
        data.add(new String[]{"Induction magnétique dans la culasse", controller.getInductionCulasse().getText()});
        data.add(new String[]{"Induction magnétique dans les coins", controller.getInductionCoins().getText()});
        data.add(new String[]{"Pertes Joule BT", controller.getPertesJouleBt().getText()});
        data.add(new String[]{"Pertes Joule connexions BT", controller.getPertesJouleBtConnexions().getText()});
        data.add(new String[]{"Pertes Joule HT", controller.getPertesJouleHt().getText()});
        data.add(new String[]{"Pertes Joule connexions HT", controller.getPertesJouleHtConnexions().getText()});
        data.add(new String[]{"Pertes Joule enroulements", controller.getPertesJouleEnroulements().getText()});
        data.add(new String[]{"Pertes Joule connexions", controller.getPertesJouleConnexions().getText()});
        data.add(new String[]{"Pertes supplémentaires", controller.getPertesSupp().getText()});
        data.add(new String[]{"Pertes fer colonnes", controller.getPertesFerColonnes().getText()});
        data.add(new String[]{"Pertes fer culasses", controller.getPertesFerCulasses().getText()});
        data.add(new String[]{"Pertes fer coins", controller.getPertesFerCoins().getText()});
        data.add(new String[]{"Pertes fer totales", controller.getPertesFerTotales().getText()});
        data.add(new String[]{"Rendement global", controller.getRendementGlobal().getText()});

        return data;
    }


    public static FileChooser getOptimusFolder() {
        FileChooser fileChooser = new FileChooser();

        Path userHome = Paths.get(System.getProperty("user.home"), "Documents", "Optimus");
        File configDirectory = userHome.toFile();
        if (!configDirectory.exists())
            configDirectory.mkdirs();

        fileChooser.setInitialDirectory(configDirectory);
        return fileChooser;
    }


}
