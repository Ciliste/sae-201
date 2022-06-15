package appli1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.IllegalFormatException;

import javax.print.AttributeException;
import javax.swing.text.TabExpander;

import appli1.ihm.FrameCreation;
import iut.algo.Clavier;
import metier.Cuve;
import metier.Position;
import metier.Tube;
import metier.Cuve.PositionInfo;
import metier.reseau.ListeAdjacence;
import metier.reseau.MatriceCout;
import metier.reseau.MatriceOptimisee;
import metier.reseau.Reseau;


public class Controleur
{
    private static String FORMAT_KEY_WORD = "user:reseauformat";
    
    private FrameCreation frame;

    public Controleur() {
        
        this.frame = new FrameCreation(this);
    }

    public Reseau ouvrir(File file) throws IOException, IllegalArgumentException, NoSuchMethodException {

        Object attr = Files.getAttribute(Path.of(file.getAbsolutePath()), Controleur.FORMAT_KEY_WORD);
        if (attr == null)
            throw new IOException("Le fichier n'est pas un fichier reconnu");

        if (attr instanceof byte[]) {

            String format = new String((byte[]) attr);
            System.out.println(format);

            for (MethodeSauvegarde methode : MethodeSauvegarde.values()) {

                if (methode.getNom().equals(format)) {

                    try {

                        return (Reseau) methode.getClasseSauvegarde().getMethod("deserialize", String.class).invoke(null, Files.readString(Path.of(file.getAbsolutePath())));
                    }
                    catch (NoSuchMethodException | ClassCastException err) {

                        throw new NoSuchMethodException("Ce formatage est reconnu, mais il n'est pas lisible");
                    }
                    catch (IllegalAccessException | InvocationTargetException err) {

                        throw new IOException("Fichier innaccessible");
                    }
                }
            }
        }
        else {

            throw new IOException("Le format du fichier est corrompu");
        }
        
        return null;
    }

    public void sauvegarder(File file, Object[][] cuves, Object[][] tubes) {

        for (MethodeSauvegarde methode : MethodeSauvegarde.values()) {

            if (methode.getClasseSauvegarde().getName().equals(file.getName())) {

                this.sauvegarderSous(file, methode.getClasseSauvegarde(), cuves, tubes);
                break;
            }
        }
    }

    public void sauvegarderSous(File file, Class<? extends Reseau> classe, Object[][] cuves, Object[][] tubes) {

        if (cuves.length != 0 && !(cuves[0][0] instanceof Character && cuves[0][1] instanceof Integer && cuves[0][2] instanceof Double && cuves[0][3] instanceof Integer && cuves[0][4] instanceof Integer && cuves[0][5] instanceof String))
            throw new IllegalArgumentException("Les données de la matrice des cuves sont incorrectes");

        if (tubes.length != 0 && !(tubes[0][0] instanceof Character && tubes[0][1] instanceof Character && tubes[0][2] instanceof Integer))
            throw new IllegalArgumentException("Les données de la matrice des tubes sont incorrectes");

        Reseau reseau = null;
        try {

            reseau = classe.getDeclaredConstructor().newInstance();
        }
        catch (Exception err) {

            throw new IllegalArgumentException("Cette implementation d'un Réseau n'est pas valide");
        }

        for (Object[] ligne : cuves) {

            reseau.ajouterCuve(Cuve.creerCuve((Integer) ligne[1], (Double) ligne[2], new Position((Integer) ligne[3], (Integer) ligne[4]), PositionInfo.getPositionInfo((String) ligne[5]).getValeur()));
        }

        for (Object[] ligne : tubes) {

            reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get((Character) ligne[1] - 'A'), reseau.getEnsCuves().get((Character) ligne[2] - 'A'), (Integer) ligne[3]));
        }

        try {

            PrintWriter pw = new PrintWriter(file);
            pw.write(reseau.serialize());
            pw.close();

            String format = "?";
            for (MethodeSauvegarde methode : MethodeSauvegarde.values()) {

                if (methode.getClasseSauvegarde().equals(classe)) {

                    format = methode.getNom();
                    break;
                }
            }

            Files.setAttribute(Path.of(file.getAbsolutePath()), Controleur.FORMAT_KEY_WORD, format.getBytes());
        }
        catch (Exception err) {

            // ¯\_(ツ)_/¯
        }
    }

    public static void main(String[] args) {

        new Controleur();
    }

    public enum MethodeSauvegarde {

        LISTE("Liste d'ajdacence", ListeAdjacence.class),
        MATRICECOUT("Matrice de coût", MatriceCout.class),
        MATRICEOPTI("Matrice de coût optimisée", MatriceOptimisee.class);

        private String nom;
        private Class<? extends Reseau> classeSauvegarde;
        private FrameCreation frame;

        private MethodeSauvegarde(String nom, Class<? extends Reseau> classe) {

            this.nom = nom;
            this.classeSauvegarde = classe;
        }

        public String getNom() {

            return this.nom;
        }

        public Class<? extends Reseau> getClasseSauvegarde() {

            return this.classeSauvegarde;
        }

        public void sauvegarder() {

            if (this.frame == null) return;
            this.frame.sauvegarderSous(this.classeSauvegarde);
        }
    }
}