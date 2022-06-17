package metier;

import java.io.Serializable;
import java.util.Locale;

import common.SharedContants;

public class Cuve implements Comparable<Cuve>, Serializable {

    public enum PositionInfo {

        HAUT(0, "HAUT"),
        DROITE(1, "DROITE"),
        BAS(2, "BAS"),
        GAUCHE(3, "GAUCHE");

        private int valeur;
        private String lib;

        private PositionInfo(int valeur, String lib) {

            this.valeur = valeur;
            this.lib = lib;
        }

        public int getValeur() {

            return this.valeur;
        }

        public String getLib() {

            return this.lib;
        }

        public static PositionInfo getPositionInfo(String lib) {

            if (lib.equals(HAUT.getLib())) {

                return HAUT;
            } else if (lib.equals(DROITE.getLib())) {

                return DROITE;
            } else if (lib.equals(BAS.getLib())) {

                return BAS;
            } else if (lib.equals(GAUCHE.getLib())) {

                return GAUCHE;
            } else {

                return null;
            }
        }
    }

    // Attributs de classe
    public static final int CAPACITE_MIN = SharedContants.DonneesCuveTube.CUVE_CAPACITE_MIN.VAL;
    public static final int CAPACITE_MAX = SharedContants.DonneesCuveTube.CUVE_CAPACITE_MAX.VAL;

    private static int nbCuve = 0;

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

        Cuve temp = new Cuve(capacite, contenu, position, posInfo);
        System.out.println(temp);
        return temp;
    }

    public static Cuve deserialize(String str) {

        str = str.replace("(", "").replace(")", "").replace(" ", "");

        return new Cuve(
            str.charAt(0),
            Integer.parseInt(str.split("/")[1]),
            Double.parseDouble(str.split("/")[2]),
            new Position(
                Integer.parseInt(str.split("/")[3]), 
                Integer.parseInt(str.split("/")[4])
            ),
            PositionInfo.getPositionInfo(str.split("/")[5]).getValeur()
        );
    }

    public static void resetCompteur() {

        Cuve.nbCuve = 0;
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

    // Utilisé uniquement pour la Déserialisation
    private Cuve(char id, int capacite, double contenu, Position position, int posInfo) {

        if (id < 'A' || id > 'Z') {
            throw new IllegalArgumentException("Identifiant de cuve invalide pendant la déserialisation");
        }
        
        if ((int) (id - 'A') + 1 > Cuve.nbCuve) Cuve.nbCuve = (int) (id - 'A') + 1;
        
        this.identifiant = id;
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

    public PositionInfo getPosInfo() {

        for (PositionInfo p : PositionInfo.values()) {

            if (p.getValeur() == this.posInfo) {

                return p;
            }
        }
        
        return null;
    }

    public static char getCompteur() {
        return (char) (Cuve.nbCuve + 'A');
    }

    public Position getPosition() {

        return Position.copier(this.position);
    }

    // Setters
    public void setPosition(Position position) {

        this.position = Position.copier(position);
    }

    // toString()
    public String toString() {

        return "Cuve " + this.identifiant + " : " + this.getPosition().x() + "/" + this.getPosition().y();
    }

    public String serialize() {

        return String.format(
            Locale.ENGLISH,
            "(%c/%" + ((int)(Math.log10(Cuve.CAPACITE_MAX))+1) + "d/%" + ((int)(Math.log10(Cuve.CAPACITE_MAX))+4) + ".2f/%d/%d/%s)", 
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