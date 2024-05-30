package dz.usthb.pfeelt.ee;

public class TransformerConfiguration {

    private double puissanceNominale;
    private double tensionPrimaire;
    private double tensionSecondaire;
    private double frequence;
    private double pertesCourtCircuit;
    private double tensionCourtCircuit;
    private double isolationCouches;
    private double largeurRefroidissement;

    private Connexion connexionPrimaire;
    private Connexion connexionSecondaire;

    private Refroidissement refroidissement;

    public TransformerConfiguration(double puissanceNominale, double tensionPrimaire, double tensionSecondaire, double frequence, double pertesCourtCircuit, double tensionCourtCircuit, double isolationCouches, double largeurRefroidissement, Connexion connexionPrimaire, Connexion connexionSecondaire, Refroidissement refroidissement) {
        this.puissanceNominale = puissanceNominale;
        this.tensionPrimaire = tensionPrimaire;
        this.tensionSecondaire = tensionSecondaire;
        this.frequence = frequence;
        this.pertesCourtCircuit = pertesCourtCircuit;
        this.tensionCourtCircuit = tensionCourtCircuit;
        this.isolationCouches = isolationCouches;
        this.largeurRefroidissement = largeurRefroidissement;
        this.connexionPrimaire = connexionPrimaire;
        this.connexionSecondaire = connexionSecondaire;
        this.refroidissement = refroidissement;
    }

    public TransformerConfiguration(double puissanceNominale, double tensionPrimaire, double tensionSecondaire, double frequence, double pertesCourtCircuit, double tensionCourtCircuit, double isolationCouches, double largeurRefroidissement, Connexion connexionPrimaire, Connexion connexionSecondaire) {
        this(puissanceNominale, tensionPrimaire, tensionSecondaire, frequence, pertesCourtCircuit, tensionCourtCircuit, isolationCouches, largeurRefroidissement, connexionPrimaire, connexionSecondaire, Refroidissement.NATUREL);
    }

    public TransformerConfiguration(double[] config, Connexion connexionPrimaire, Connexion connexionSecondaire, Refroidissement refroidissement) {
        this(config[0], config[1], config[2], config[3], config[4], config[5], config[6], config[7], connexionPrimaire, connexionSecondaire, refroidissement);
    }

    public TransformerConfiguration(double[] config, Connexion connexionPrimaire, Connexion connexionSecondaire) {
        this(config, connexionPrimaire, connexionSecondaire, Refroidissement.NATUREL);
    }

    public double getPuissanceNominale() {
        return puissanceNominale;
    }

    public void setPuissanceNominale(double puissanceNominale) {
        this.puissanceNominale = puissanceNominale;
    }

    public double getTensionPrimaire() {
        return tensionPrimaire;
    }

    public void setTensionPrimaire(double tensionPrimaire) {
        this.tensionPrimaire = tensionPrimaire;
    }

    public double getTensionSecondaire() {
        return tensionSecondaire;
    }

    public void setTensionSecondaire(double tensionSecondaire) {
        this.tensionSecondaire = tensionSecondaire;
    }

    public double getFrequence() {
        return frequence;
    }

    public void setFrequence(double frequence) {
        this.frequence = frequence;
    }

    public double getPertesCourtCircuit() {
        return pertesCourtCircuit;
    }

    public void setPertesCourtCircuit(double pertesCourtCircuit) {
        this.pertesCourtCircuit = pertesCourtCircuit;
    }

    public double getTensionCourtCircuit() {
        return tensionCourtCircuit;
    }

    public void setTensionCourtCircuit(double tensionCourtCircuit) {
        this.tensionCourtCircuit = tensionCourtCircuit;
    }

    public double getIsolationCouches() {
        return isolationCouches;
    }

    public void setIsolationCouches(double isolationCouches) {
        this.isolationCouches = isolationCouches;
    }

    public double getLargeurRefroidissement() {
        return largeurRefroidissement;
    }

    public void setLargeurRefroidissement(double largeurRefroidissement) {
        this.largeurRefroidissement = largeurRefroidissement;
    }

    public Connexion getConnexionPrimaire() {
        return connexionPrimaire;
    }

    public void setConnexionPrimaire(Connexion connexionPrimaire) {
        this.connexionPrimaire = connexionPrimaire;
    }

    public Connexion getConnexionSecondaire() {
        return connexionSecondaire;
    }

    public void setConnexionSecondaire(Connexion connexionSecondaire) {
        this.connexionSecondaire = connexionSecondaire;
    }

    public Refroidissement getRefroidissement() {
        return refroidissement;
    }

    public void setRefroidissement(Refroidissement refroidissement) {
        this.refroidissement = refroidissement;
    }

    public enum Connexion {
        DELTA, ETOILE;
    }

    public enum Refroidissement {
        NATUREL, FORCE;
    }
}
