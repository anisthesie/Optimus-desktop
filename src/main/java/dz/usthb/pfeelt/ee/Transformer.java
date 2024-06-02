package dz.usthb.pfeelt.ee;

import dz.usthb.pfeelt.helpers.EEHelpers;
import dz.usthb.pfeelt.ui.Controller;

import static dz.usthb.pfeelt.helpers.EEHelpers.getSpecificLosses;

public class Transformer {

    public static void calculate(Controller controller) {

        TransformerConfiguration.Connexion connexionPrimaire = controller.getCurrentConfiguration().getConnexionPrimaire();
        TransformerConfiguration.Connexion connexionSecondaire = controller.getCurrentConfiguration().getConnexionSecondaire();

        double tensionCourtCircuitDouble = controller.getCurrentConfiguration().getTensionCourtCircuit();
        double pertesCourtCircuitDouble = controller.getCurrentConfiguration().getPertesCourtCircuit() / 1000;
        double tensionSecondaireDouble = controller.getCurrentConfiguration().getTensionSecondaire();
        double tensionPrimaireDouble = controller.getCurrentConfiguration().getTensionPrimaire();
        double frequenceDouble = controller.getCurrentConfiguration().getFrequence();
        double puissanceNominaleDouble = controller.getCurrentConfiguration().getPuissanceNominale();
        double sp = controller.getCurrentConfiguration().getIsolationCouches();
        double sr = controller.getCurrentConfiguration().getLargeurRefroidissement();

        double tensionPhasePrimaireDouble = 0;
        double tensionPhaseSecondaireDouble = 0;

        // Tension de phase primaire
        if (connexionPrimaire == TransformerConfiguration.Connexion.DELTA)
            tensionPhasePrimaireDouble = tensionPrimaireDouble;
        else
            tensionPhasePrimaireDouble = tensionPrimaireDouble / Math.sqrt(3);
        controller.getTensionPhasePrimaire().setText(String.format("%.2f", tensionPhasePrimaireDouble) + " (kV)");
        //-------------------------

        // Tension de phase secondaire
        if (connexionSecondaire == TransformerConfiguration.Connexion.DELTA)
            tensionPhaseSecondaireDouble = tensionSecondaireDouble;
        else
            tensionPhaseSecondaireDouble = tensionSecondaireDouble / Math.sqrt(3);
        controller.getTensionPhaseSecondaire().setText(String.format("%.2f", tensionPhaseSecondaireDouble) + " (kV)");
        //-------------------------


        // Puissance apparante par collonne
        double puissanceAppColonneDouble = puissanceNominaleDouble / 3;
        controller.getPuissanceApparanteColonne().setText(String.format("%.2f", puissanceAppColonneDouble) + " (kVA)");
        //---------------------------------

        // Courant de phase primaire
        double if1 = puissanceAppColonneDouble / tensionPhasePrimaireDouble;
        controller.getCourantPhasePrimaire().setText(String.format("%.2f", if1) + " (A)");
        //--------------------------

        // Courant de phase secondaire
        double if2 = puissanceAppColonneDouble / tensionPhaseSecondaireDouble;
        controller.getCourantPhaseSecondaire().setText(String.format("%.2f", if2) + " (A)");
        //--------------------------

        double a12 = EEHelpers.getA12(tensionPrimaireDouble);
        double Ar = a12 + EEHelpers.getK(puissanceAppColonneDouble, tensionPrimaireDouble) * Math.pow(puissanceAppColonneDouble, 0.25);
        double B = EEHelpers.getB(puissanceAppColonneDouble, tensionPrimaireDouble);
        double Ka = 0.95;
        double Bc = EEHelpers.getBc(puissanceNominaleDouble);
        double Ku = EEHelpers.getKu(puissanceAppColonneDouble);
        double Uk = tensionCourtCircuitDouble;
        double Uka = (pertesCourtCircuitDouble / puissanceNominaleDouble) * 100;
        double Ukr = Math.sqrt(Math.abs((Uk * Uk) - (Uka * Uka))) / 100;

        //----- Diamètre de la colonne
        double D = 1.0674 * Math.pow((Ar * B * Ka * puissanceAppColonneDouble * 10) / ((Ku * Ku) * (Bc * Bc) * Ukr), 0.25d);
        controller.getDiametreColonne().setText(String.format("%.2f", D) + " (cm)");
        //-------------------


        // ---- CANAL DE FUITE D12 ------
        double a01 = EEHelpers.getA01(tensionSecondaireDouble);
        double a1 = EEHelpers.getA1(puissanceAppColonneDouble, tensionPrimaireDouble);

        double D12 = D + 2 * a01 + 2 * a1 + a12;
        controller.getDiametreCanalDeFuite().setText(String.format("%.2f", D12) + " (cm)");
        //----------------------------------


        // --------- Section du fer
        double SFer = (Ku * Math.PI * D * D / 4) / 10000;
        controller.getSectionDuFer().setText(String.format("%.2f", SFer * 10000) + " (cm2)");
        //------------------------


        // -------- Tension spire par phase
        double Usp = Math.sqrt(2) * Math.PI * frequenceDouble * SFer * Bc;
        controller.getTensionSpirePhase().setText(String.format("%.2f", Usp) + " (V)");
        // -----------------------------


        // -------- Nombre de spires
        double w1 = Math.round((tensionPhasePrimaireDouble * 1000) / Usp);
        double w2 = Math.round((tensionPhaseSecondaireDouble * 1000) / Usp);

        controller.getNombreSpires().setText(String.format("%.2f", w1) + "/" + String.format("%.2f", w2));
        // -------------------------


        // ----- Tension spire par phase recalculée
        double UspR = tensionPhasePrimaireDouble * 1000 / w1;
        controller.getTensionSpireRecalc().setText(String.format("%.2f", UspR) + " (V)");
        // --------------------------------


        // ---------- Induction magnétique recalculée
        double Bcr = UspR / (4.44 * frequenceDouble * SFer);
        controller.getInductionMagnRecalc().setText(String.format("%.2f", Bcr) + " (T)");
        // --------------------------


        // -------- Densité du courant (A/mm2)
        double J = (7.34 * pertesCourtCircuitDouble * UspR) / (EEHelpers.getKpk(puissanceNominaleDouble) * puissanceNominaleDouble * D12 / 100);
        controller.getDensiteCourant().setText(String.format("%.2f", J) + " (A/mm2)");
        // --------------------------


        // ----- Section conducteur primaire
        double sectionConducteurPrimaireDouble = if2 / J;
        controller.getSectionConducteurPrimaire().setText(String.format("%.2f", sectionConducteurPrimaireDouble) + " (mm2)");
        // ---------------------


        // ------ Densité courant recalculée
        double Jrc = if2 / sectionConducteurPrimaireDouble;
        controller.getDensiteCourantRecalc().setText(String.format("%.3f", Jrc) + " (A/mm2)");
        //-----------------------------

        // ------ Section conducteur secondaire
        double sectionConducteurSecondaireDouble = if1 / Jrc;
        controller.getSectionConducteurSecondaire().setText(String.format("%.2f", sectionConducteurSecondaireDouble) + " (mm2)");
        // ---------------------------------

        // ---- Hauteur bobine
        double hb = Math.PI * D12 / B;
        controller.getHauteurDeBobineBT().setText(String.format("%.2f", hb) + " (cm)");
        controller.getHauteurDeBobineHT().setText(String.format("%.2f", hb) + " (cm)");
        // ---------------

        // ----- épaisseur du conducteur
        double a = sectionConducteurPrimaireDouble / (hb * 10);
        controller.getEpaisseurConducteur().setText(String.format("%.2f", a) + " (mm)");
        // -----------------------------

        // diametre canal de fuite recalc-----------
        double d12r = D + 2 * a01 + 2 * a1 + a12;
        controller.getDiametreCanalFuiteRecalc().setText(String.format("%.2f", d12r) + " (cm)");
        //-------------------------------

        // Sveltess recalc Br -----
        double Br = Math.PI * d12r / hb;
        controller.getSveltessRecalc().setText(String.format("%.2f", Br));
        // ----------------

        // ----------- diametre conducteur isolé
        double dprime = sectionConducteurSecondaireDouble + EEHelpers.getTg(tensionPrimaireDouble);
        controller.getDiametreConducteurIsole().setText(String.format("%.2f", dprime) + " (mm)");
        //-----------------------------

        //---------------- nombre de spires enroulement haute tension
        double nombreSpiresHTDouble = Math.round(w2 * tensionPhasePrimaireDouble / tensionPhaseSecondaireDouble);
        controller.getNombreSpiresHT().setText(String.format("%.2f", nombreSpiresHTDouble));
        //-----------------------

        //---------- Spires par couche
        double spiresParCoucheDouble = ((hb * 10 - 10) / dprime) - 1;
        controller.getSpireParCouche().setText(String.format("%.2f", Math.ceil(spiresParCoucheDouble)));
        //----------------------------

        //------ Nombre couches
        double nombreCouchesDouble = nombreSpiresHTDouble / spiresParCoucheDouble;
        controller.getNombreCouches().setText(String.format("%.2f", Math.ceil(nombreCouchesDouble)));
        //----------------------------


        //----- Epaisseur enroulement HT
        double a2r = (dprime * nombreCouchesDouble + sp * (nombreCouchesDouble - 2) + sr) / 10;
        controller.getEpaisseurHT().setText(String.format("%.2f", a2r) + " (cm)");
        //---------------------


        //---- Epaisseur rapportee canale de fuite recalculee
        double epaisseurRapporteeDouble = a12 + (a1 + a2r) / 3;
        controller.getEpaisseurRapportee().setText(String.format("%.2f", epaisseurRapporteeDouble) + " (cm)");
        //----------------------

        //------- Rogowski
        double Kr = 1 - (1 / (2 * Math.PI) * ((2 * a12 + a1 + a2r) / hb));
        controller.getRogowskiRecalc().setText(String.format("%.2f", Kr));
        //---------------------

        //-------- Tension de court circuit recalculée
        double UkrRecalc = (2 * Math.PI * frequenceDouble * 4 * Math.PI * Math.pow(10, -7) * w2 * w2 * Ar * B * Kr * if2) / (1000 * tensionPhaseSecondaireDouble);
        controller.getUkrRecalc().setText(String.format("%.2f", UkrRecalc) + " %");
        //----------------------

        //---- Diametre moyen enroulement basse tension
        double dm1 = D + 2 * a01 + a1;
        controller.getDiametreMoyenBT().setText(String.format("%.2f", dm1) + " (cm)");
        //-----------------

        //--- Longueur moyenne spire
        double lw1 = Math.PI * dm1 / 100;
        controller.getLongueurSpire().setText(String.format("%.2f", lw1) + " (m)");
        //-------------

        //------ Longueur totale
        double L1 = w2 * lw1;
        controller.getLongueurTotale().setText(String.format("%.2f", L1) + " (m/phase)");
        //-----------------

        //---- Résistance en courznt continu  BT
        double R1 = (L1 * 0.0215 * 1000) / sectionConducteurPrimaireDouble;
        controller.getResistanceCc().setText(String.format("%.2f", R1) + " (milliOhm)");
        //--------------

        //----- Diametre moyen enroulement haute tension
        double dm2 = D + 2 * a01 + 2 * a1 + 2 * a12 + a2r;
        double lw2 = Math.PI * dm2 / 100;
        double L2 = nombreSpiresHTDouble * lw2;
        double R2 = (0.0215 * L2) / sectionConducteurSecondaireDouble;

        controller.getLongueurTotaleHt().setText(String.format("%.2f", L2) + " (m/phase)");
        controller.getLongueurSpireHt().setText(String.format("%.2f", lw2) + " (m)");
        controller.getDiametreMoyenHT().setText(String.format("%.2f", dm2) + " (cm)");
        controller.getResistanceCcHt().setText(String.format("%.2f", R2) + " (Ohm)");
        //------------------

        // -------- Largeur fenêtre
        double a22 = a1; // @TODO calculer a22 et le remplacer
        double LF = 2 * (a01 + a1 + a12 + a2r) + a22;
        controller.getLargeurFenetre().setText(String.format("%.2f", LF) + " (cm)");
        //--------------

        //----- Huateur fenetre
        double l02 = a2r; // @TODO calculer l02
        double hf = 2 * l02 + hb;
        controller.getHauteurFenetre().setText(String.format("%.2f", hf) + " (cm)");
        //------------

        //------ Section de la culasse
        double scul = SFer * 1.15 * 10000;
        controller.getSectionCulasse().setText(String.format("%.2f", scul) + " (cm2)");
        //----------------

        // --------- Longueur culasse sans coins
        double lncul = LF;
        controller.getLongueurCulasseSansCoins().setText(String.format("%.2f", lncul) + " (cm)");
        //-------------------------

        //-------- Largeur de la culasse
        double lrcul = 0.96 * D;
        controller.getLargeurCulasse().setText(String.format("%.2f", lrcul) + " (cm)");
        //----------------

        //-------- Hauteur de la culasse
        double hcul = scul / lrcul;
        controller.getHauteurCulasse().setText(String.format("%.2f", hcul) + " (cm)");
        //----------------

        //-------- Poids du matériel basse tension
        double G1 = 8.9 * L1 * sectionConducteurPrimaireDouble / 1000;
        controller.getPoidsBt().setText(String.format("%.2f", G1) + " (kg/colonne)");
        //----------------

        //-------- Poids du matériel haute tension
        double G2 = 8.9 * L2 * sectionConducteurSecondaireDouble / 1000;
        controller.getPoidsHt().setText(String.format("%.2f", G2) + " (kg/colonne)");
        //----------------

        //-------- Poids total
        double Gt = 3 * (G1 + G2);
        controller.getPoidsTotal().setText(String.format("%.2f", Gt) + " (kg)");
        //----------------

        //-------- Poids culasses
        double Gcul = 4 * scul * lncul * 7.65 / 1000;
        controller.getPoidsCulasses().setText(String.format("%.2f", Gcul) + " (kg)");
        //----------------

        //-------- Poids colonnes
        double Gcol = 3 * SFer * 10000 * hf * 7.65 / 1000;
        controller.getPoidsColonnes().setText(String.format("%.2f", Gcol) + " (kg)");
        //----------------

        //-------- Poids coins
        double Gcoins = 6 * scul * D * 7.65 / 1000;
        controller.getPoidsCoins().setText(String.format("%.2f", Gcoins) + " (kg)");
        //----------------

        //-------- Poids total ciruit magnétique
        double Gtcm = Gcol + Gcul + Gcoins;
        controller.getPoidsTotalCM().setText(String.format("%.2f", Gtcm) + " (kg)");
        //----------------

        //-------- Hauteur totale du circuit magnétique
        double Hcm = hf + 2 * hcul;
        controller.getHauteurTcm().setText(String.format("%.2f", Hcm) + " (cm)");
        //----------------

        //-------- Induction magnétique dans la culasse
        double bcul = Bcr / 1.15;
        controller.getInductionCulasse().setText(String.format("%.2f", bcul) + " (T)");
        //----------------

        //------- Induction magnétique dans les coins
        double bcoins = (bcul + Bcr) / 2;
        controller.getInductionCoins().setText(String.format("%.2f", bcoins) + " (T)");
        //-----------------

        //---- Pertes joule basse tension
        double pj1 = 3 * R1 * if2 * if2 / 1000;
        controller.getPertesJouleBt().setText(String.format("%.2f", pj1) + " (W)");
        //---------

        //---- Pertes joule connexions basse tension
        boolean deltaBt = connexionSecondaire == TransformerConfiguration.Connexion.DELTA;
        double coeffBt = (deltaBt ? 14 : 7.5);
        double lc1 = coeffBt * hb / 100;
        double swc1 = (deltaBt ? (sectionConducteurPrimaireDouble * Math.sqrt(3)) : sectionConducteurPrimaireDouble);
        double r1c = 0.0215 * (lc1 / swc1);
        double pjc1 = r1c * if2 * if2;
        controller.getPertesJouleBtConnexions().setText(String.format("%.2f", pjc1) + " (W)");
        //--------------------

        //---- Pertes joule haute tension
        double pj2 = 3 * R2 * if1 * if1;
        controller.getPertesJouleHt().setText(String.format("%.2f", pj2) + " (W)");
        //---------

        //---- Pertes joule connexions haute tension
        boolean deltaHt = connexionPrimaire == TransformerConfiguration.Connexion.DELTA;
        double coeffHt = (deltaHt ? 14 : 7.5);
        double lc2 = coeffHt * hb / 100;
        double swc2 = (deltaHt ? (sectionConducteurSecondaireDouble * Math.sqrt(3)) : sectionConducteurSecondaireDouble);
        double r2c = 0.0215 * (lc2 / swc2);
        double pjc2 = r2c * if1 * if1;
        controller.getPertesJouleHtConnexions().setText(String.format("%.2f", pjc2) + " (W)");
        //--------------------

        //-------- Pertes joule enroulements
        double pjte = pj1 + pj2;
        controller.getPertesJouleEnroulements().setText(String.format("%.2f", pjte) + " (W)");
        //---------------

        //---------Pertes joule connexions
        double pjtc = pjc1 + pjc2;
        controller.getPertesJouleConnexions().setText(String.format("%.2f", pjtc) + " (W)");
        //-----------------

        //---------Pertes supplementaires
        double pjs = pjte * 0.02;
        controller.getPertesSupp().setText(String.format("%.2f", pjs) + " (W)");
        //-----------------

        //---------Pertes joule totales
        double pjt = pjte + pjtc + pjs;
        controller.getPertesJouleTotales().setText(String.format("%.2f", pjt) + " (W)");
        //-----------------

        //--------- Tension court-circuit verification
        double ukaRecalc = (pjt / puissanceNominaleDouble) / 10;
        double ukRecalc = Math.sqrt(ukaRecalc * ukaRecalc + UkrRecalc * UkrRecalc);
        controller.getTensionCourtCircuitVerif().setText(String.format("%.2f", ukRecalc) + " %");
        //-----------------

        //--------- Pertes fer dans les colonnes
        double pfcol = Gcol * getSpecificLosses(Bcr);
        controller.getPertesFerColonnes().setText(String.format("%.2f", pfcol) + " (W)");
        //-----------------

        //--------- Pertes fer dans les culasses
        double pfcul = Gcul * getSpecificLosses(bcul);
        controller.getPertesFerCulasses().setText(String.format("%.2f", pfcul) + " (W)");
        //-----------------

        //--------- Pertes fer dans les coins
        double pfcoins = Gcoins * getSpecificLosses(bcoins);
        controller.getPertesFerCoins().setText(String.format("%.2f", pfcoins) + " (W)");
        //-----------------

        //--------- Pertes fer totales
        double pftot = pfcoins + pfcul + pfcol;
        controller.getPertesFerTotales().setText(String.format("%.2f", pftot) + " (W)");
        //-----------------

        //--------- Rendement
        double rendement = 100 * ((puissanceNominaleDouble * 1000 - pjt - pftot - pjs) / (puissanceNominaleDouble * 1000));
        controller.getRendementGlobal().setText(String.format("%.2f", rendement) + " %");
        //-----------------
    }

}
