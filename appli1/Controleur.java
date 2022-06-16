package appli1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;

import appli1.ihm.FrameCreation;
import metier.Cuve;
import metier.Cuve.PositionInfo;
import metier.Position;
import metier.Tube;
import metier.reseau.ListeAdjacence;
import metier.reseau.MatriceCout;
import metier.reseau.MatriceOptimisee;
import metier.reseau.Reseau;


public class Controleur
{
    private static final String FORMAT_KEY_WORD = "user:reseauformat";

    public Controleur() {

        new FrameCreation(this);
    }


    public Reseau ouvrir(File file) throws IOException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, Exception {

        System.out.println("Début ouverture de " + file.getAbsolutePath());
        Object attr = Files.getAttribute(file.toPath(), Controleur.FORMAT_KEY_WORD);
        if (attr == null)
            throw new IOException("Le fichier n'est pas un fichier reconnu");

        if (attr instanceof byte[]) {

            String format = new String((byte[]) attr);
            System.out.println(format);

            for (MethodeSauvegarde methode : MethodeSauvegarde.values()) {

                if (methode.getNom().equals(format)) {

                    try {

                        System.out.println("Retour ouverture");
                        Cuve.resetCompteur();
                        return (Reseau) methode.getClasseSauvegarde().getMethod("deserialize", String.class).invoke(null, Files.readString(Path.of(file.getAbsolutePath())));
                    }
                    catch (NoSuchMethodException | ClassCastException err) {

                        throw new NoSuchMethodException("Ce formatage est reconnu, mais il n'est pas lisible");
                    }
                    catch (IllegalAccessException err) {

                        throw new IllegalAccessException("Fichier innaccessible");
                    }
                    catch (InvocationTargetException err) {

                        throw new Exception("Erreur lors de la lecture du fichier");
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

        System.out.println("Sauvegarde");
        for (MethodeSauvegarde methode : MethodeSauvegarde.values()) {

            try {

                System.out.println("Tentative de sauvegarde avec " + methode.getNom());
                System.out.println(methode.getClasseSauvegarde().getName() + " " + new String((byte[])Files.getAttribute(Path.of(file.getAbsolutePath()), Controleur.FORMAT_KEY_WORD)));
                if (methode.getNom().equals(new String((byte[])Files.getAttribute(Path.of(file.getAbsolutePath()), Controleur.FORMAT_KEY_WORD)))) {

                    System.out.println("Sauvegarde avec " + methode.getNom());
                    this.sauvegarderSous(file, methode.getClasseSauvegarde(), cuves, tubes);
                    break;
                }
            }
            catch (IOException err) {

                err.printStackTrace();
            }
        }
    }

    public void sauvegarderSous(File file, Class<? extends Reseau> classe, Object[][] cuves, Object[][] tubes) {

        for (Object obj : cuves[0]) {

            System.out.println(obj.getClass().getName());
        }

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

        Cuve.resetCompteur();
        for (Object[] ligne : cuves) {

            System.out.println(ligne[0]);
            Cuve c = Cuve.creerCuve((Integer) ligne[1], (Double) ligne[2], new Position((Integer) ligne[3], (Integer) ligne[4]), PositionInfo.getPositionInfo((String) ligne[5]).getValeur());
            System.out.println(c);
            reseau.ajouterCuve(c);
        }

        System.out.println(reseau.getEnsCuves().size());
        for (Object[] ligne : tubes) {

            System.out.println((Character) (ligne[0]) + " " + (Character) (ligne[1]) + " / " + (int)((Character) (ligne[0])) + " " + (int)((Character) (ligne[1])));
            reseau.ajouterTube(Tube.creerTube(
                reseau.getEnsCuves().get((int)((Character) (ligne[0]) - 'A')), 
                reseau.getEnsCuves().get((int)((Character) (ligne[1]) - 'A')), 
                (Integer) ligne[2]
            ));
        }

        try {

            file.renameTo(new File(file.getAbsolutePath() + ".data"));
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


    public enum MethodeSauvegarde {

        LISTE("Liste d'adjacence", ListeAdjacence.class),
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



    public static void main(String[] args) {

        new Controleur();
    }
}