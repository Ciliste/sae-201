package metier;

import java.io.Serializable;

public class Cuve implements Comparable<Cuve>, Serializable {

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

        // Retourne null si la capacite n'est pas dans la plage demandee
        if (capacite < 200 || capacite > 1000) {
            return null;
        }

        // Retourne null s'il y a deja 26 cuves ou plus
        if (Cuve.nbCuve >= 26) {
            return null;
        }

        if (contenu >= 0 || contenu > capacite) {
            return null;
        }

        return new Cuve(capacite, contenu);
    }

    // Constructeur
    private Cuve(int capacite) {
        this(capacite, 0);
    }

    private Cuve(int capacite, double contenu) {

        this.identifiant = (char) ('A' + Cuve.nbCuve++);
        this.capacite = capacite;
        this.contenu = contenu;
        this.position = null;
        this.posInfo = 0;
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

    // toString()
    public String toString() {

        return "Cuve " + this.identifiant + " : " + this.contenu + "/" + this.capacite + "L";
    }

    // Comparable
    public int compareTo(Cuve c) {

        return -(this.identifiant - c.identifiant);
    }

    public boolean equals(Cuve cuve) {
        return this.getIdentifiant() == cuve.getIdentifiant();
    }
}