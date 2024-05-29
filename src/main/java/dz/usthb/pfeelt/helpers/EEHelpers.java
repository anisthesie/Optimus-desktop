package dz.usthb.pfeelt.helpers;

import javafx.scene.control.TextField;

public class EEHelpers {

    public static double getTg(double tensionNominale) {
        if (tensionNominale > 0 && tensionNominale < 15)
            return 0.64;
        if (tensionNominale >= 15 && tensionNominale < 25)
            return 0.84;
        if (tensionNominale >= 25 && tensionNominale < 35)
            return 0.84;
        if (tensionNominale >= 35 && tensionNominale < 50)
            return 0.94;
        if (tensionNominale >= 50 && tensionNominale < 65)
            return 1.24;
        if (tensionNominale >= 65 && tensionNominale < 75)
            return 1.44;
        if (tensionNominale >= 75 && tensionNominale < 85)
            return 1.44;
        if (tensionNominale >= 85 && tensionNominale < 95)
            return 1.54;
        if (tensionNominale >= 95 && tensionNominale < 110)
            return 1.64;
        if (tensionNominale >= 110)
            return 1.84;
        return 0;
    }

    public static double getKpk(double puissanceNominale) {

        if (puissanceNominale > 0 && puissanceNominale < 105)
            return 1.02;
        if (puissanceNominale >= 105 && puissanceNominale < 390)
            return 1.025;
        if (puissanceNominale >= 390 && puissanceNominale < 800)
            return 1.05;
        if (puissanceNominale >= 800 && puissanceNominale < 1300)
            return 1.06;
        if (puissanceNominale >= 1300 && puissanceNominale < 1800)
            return 1.07;
        if (puissanceNominale >= 1800 && puissanceNominale < 2250)
            return 1.075;
        if (puissanceNominale >= 2250 && puissanceNominale < 3250)
            return 1.08;
        if (puissanceNominale >= 3250 && puissanceNominale < 5000)
            return 1.09;
        if (puissanceNominale >= 5000 && puissanceNominale < 8000)
            return 1.11;
        if (puissanceNominale >= 8000)
            return 1.125;

        return 0;

    }

    public static double getA1(double puissanceApparanteColonne, double tensionPrimaire) {

        double Ka1 = 0;
        if (tensionPrimaire <= 110)
            Ka1 = 0.45;
        if (tensionPrimaire <= 35)
            Ka1 = 0.55;

        return Ka1 * Math.pow(puissanceApparanteColonne, 0.25);
    }

    public static double getA01(double tensionSecondaire) {

        if (tensionSecondaire < 2 && tensionSecondaire >= 0)
            return 0.5;
        if (tensionSecondaire >= 2 && tensionSecondaire < 4)
            return 1.2;
        if (tensionSecondaire >= 4 && tensionSecondaire < 8)
            return 1.35;
        if (tensionSecondaire >= 8 && tensionSecondaire < 12.5)
            return 1.8;
        if (tensionSecondaire >= 12.5 && tensionSecondaire < 17.5)
            return 1.9;
        if (tensionSecondaire >= 17.5 && tensionSecondaire < 27.5)
            return 2.2;
        if (tensionSecondaire >= 27.5)
            return 3;
        return 0;

    }

    public static double getKu(double puissanceApparante) {
        return getKg(puissanceApparante) * getKr();
    }

    public static double getKg(double puissanceApparanteColonne) {

        if (puissanceApparanteColonne < 5)
            return 0.786;
        if (puissanceApparanteColonne >= 5 && puissanceApparanteColonne < 15)
            return 0.886;
        if (puissanceApparanteColonne >= 15 && puissanceApparanteColonne < 45)
            return 0.91;
        if (puissanceApparanteColonne >= 45 && puissanceApparanteColonne <= 5000)
            return 0.93;
        return 0;
    }

    public static double getKr() {
        return 0.945;
    }

    public static double getBc(double puissanceApparante) {
        double result = 0;

        if (puissanceApparante >= 10 && puissanceApparante < 62.5)
            result = 1.4;
        if (puissanceApparante >= 62.5 && puissanceApparante < 282.5)
            result = 1.6;
        if (puissanceApparante >= 282.5 && puissanceApparante < 615)
            result = 1.65;
        if (puissanceApparante >= 615 && puissanceApparante <= 1000)
            result = 1.66;
        if (puissanceApparante > 1000)
            result = 1.695;

        return result;
    }

    public static double getK(double puissanceApparanteColonne, double tensionNominalePrimaire) {
        double result = 0;

        if (puissanceApparanteColonne < 100 && tensionNominalePrimaire <= 10)
            result = 0.7;

        if (tensionNominalePrimaire > 10) {
            if (puissanceApparanteColonne >= 100 && puissanceApparanteColonne < 625)
                result = 0.5;
            if (puissanceApparanteColonne >= 625 && puissanceApparanteColonne < 6550)
                result = 0.45;
            if (puissanceApparanteColonne >= 6550 && puissanceApparanteColonne <= 31500)
                result = 0.45;
        }

        return result;
    }


    public static double getB(double puissanceApparanteColonne, double tensionNominalePrimaire) {
        double result = 0;

        if (puissanceApparanteColonne >= 3 && puissanceApparanteColonne < 2000) {

            if (tensionNominalePrimaire >= 6 && tensionNominalePrimaire < 22.5)
                result = 2.575;
            if (tensionNominalePrimaire >= 22.5 && tensionNominalePrimaire <= 35)
                result = 2.633;

        } else if (puissanceApparanteColonne >= 2000 && puissanceApparanteColonne <= 20000) {

            if (tensionNominalePrimaire >= 35 && tensionNominalePrimaire < 72.5)
                result = 1.55;
            if (tensionNominalePrimaire >= 72.5 && tensionNominalePrimaire <= 110)
                result = 1.75;


        }

        return result;
    }

    public static double getSpecificLosses(double induction) {
        return 0.3118 * Math.pow(induction, 3.2203);
    }

    public static double getA12(double tensionNominalePrimaire) {

        double result = 0;

        if (tensionNominalePrimaire > 0 && tensionNominalePrimaire <= 4.5)
            result = 0.8;
        if (tensionNominalePrimaire > 4.5 && tensionNominalePrimaire <= 8)
            result = 0.8;
        if (tensionNominalePrimaire > 8 && tensionNominalePrimaire <= 12.5)
            result = 1;
        if (tensionNominalePrimaire > 12.5 && tensionNominalePrimaire <= 17.5)
            result = 1.25;
        if (tensionNominalePrimaire > 17.5 && tensionNominalePrimaire <= 27.5)
            result = 1.6;
        if (tensionNominalePrimaire > 27.5 && tensionNominalePrimaire <= 47.5)
            result = 2.4;
        if (tensionNominalePrimaire > 47.5 && tensionNominalePrimaire <= 60)
            result = 2.4;
        if (tensionNominalePrimaire > 60 && tensionNominalePrimaire < 110)
            result = 2.4;
        if (tensionNominalePrimaire >= 110 && tensionNominalePrimaire <= 165)
            result = 7;
        if (tensionNominalePrimaire > 165 && tensionNominalePrimaire <= 220)
            result = 17.5;

        return result;
    }


    public static double getDoubleFromTextField(final TextField field) {
        return getDoubleFromTextField(field, "error in: "+field.getText());
    }

    public static double getDoubleFromTextField(final TextField field, String error) {
        double result = -1;
        try {
            result = Double.parseDouble(field.getText());
        } catch (Exception e) {
            System.out.println(error);
            e.printStackTrace();
        }
        return result;

    }
}
