package metier;

import java.util.Locale;

public class Cuve implements Comparable<Cuve> {

    public enum PositionInfo {

        HAUT(0, "HAUT"),
        DROITE(1, "DROITE"),
        BAS(2, "BAS"),
        GAUCHE(3, "GAUCHE");

        private int valeur;
        private String lib;

        private PositionInfo(int valeur, String lib) {
        }

        public int getValeur() {

            return this.valeur;
        }

        public String getLib() {

            return this.lib;
        }
    }

    // Attributs de classe
    public static final int CAPACITE_MIN = 200;
    public static final int CAPACITE_MAX = 1000;

    public static int nbCuve = 0;

    // Attributs d'instance
    private char identifiant;
    private int capacite;
    private double contenu;
    private Position position;
    private int posInfo;

    // Fabrique
    public static Cuve creerCuve(int capacite) {

        return Cuve.creerCuve(capacite, 0);
    }

    public static Cuve creerCuve(int capacite, double contenu) {

        return new Cuve(capacite, contenu);
    }

    public static Cuve creerCuve(int capacite, double contenu, Position position, int posInfo) {

        // Retourne null si la capacite n'est pas dans la plage demandee
        if (capacite < 200 || capacite > 1000) {

            return null;
        }

        // Retourne null s'il y a deja 26 cuves ou plus
        if (Cuve.nbCuve >= 26) {

            return null;
        }

        // Retourne null si le contenu est négatif ou superieur à la capacite
        if (contenu < 0 || contenu > capacite) {

            return null;
        }

        return new Cuve(capacite, contenu, position, posInfo);
    }

    // Constructeur
    private Cuve(int capacite) {
        this(capacite, 0);
    }

    private Cuve(int capacite, double contenu) {

        this(capacite, contenu, new Position(0, 0), 0);
    }

    private Cuve(int capacite, double contenu, Position position, int posInfo) {

        this.identifiant = (char) ('A' + Cuve.nbCuve++);
        this.capacite = capacite;
        this.contenu = contenu;
        this.position = Position.copier(position);
        this.posInfo = posInfo;
    }

    // Methodes
    public void ajouter(double transfert) {

        if (transfert < 0) {

            throw new IllegalArgumentException("Le transfert ne peut pas etre négatif");
        }

        if (this.contenu + transfert > this.capacite) {

            throw new IllegalArgumentException("Le transfert ne peut pas dépasser la capacité");
        }

        this.contenu += transfert;
    }

    public void retirer(double transfert) {

        if (transfert < 0) {

            throw new IllegalArgumentException("Le transfert ne peut pas etre négatif");
        }

        if (this.contenu - transfert < 0) {

            throw new IllegalArgumentException("La capacité ne peut pas dépasser le seuil nul");
        }

        this.contenu -= transfert;

    }

    // Getters
    public char getIdentifiant() {

        return this.identifiant;
    }

    public int getCapacite() {

        return this.capacite;
    }

    public double getContenu() {

        return this.contenu;
    }

    public static char getCompteur() {
        return (char) (Cuve.nbCuve + 'A');
    }

    // toString()
    public String toString() {

        return "Cuve " + this.identifiant + " : " + this.contenu + "/" + this.capacite + "L";
    }

    public String serialize() {

        return String.format(
            Locale.ENGLISH,
            "(%c/%" + ((int)(Math.log10(Cuve.CAPACITE_MAX))+1) + "d/%" + ((int)(Math.log10(Cuve.CAPACITE_MAX))+3) + ".2f/%d/%d/%s)", 
            this.identifiant, this.capacite, this.contenu, this.position.x(), this.position.y(), PositionInfo.values()[this.posInfo].name()
        );
    }

    // Comparable
    public int compareTo(Cuve c) {

        if (this.contenu < c.contenu) {

            return -1;
        }
        else if (this.contenu > c.contenu) {

            return 1;
        }
        else {

            return c.identifiant - this.identifiant;
        }
    }
}