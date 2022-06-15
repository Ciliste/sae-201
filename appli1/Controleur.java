package appli1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.IllegalFormatException;

import javax.print.AttributeException;

import appli1.ihm.FrameCreation;
import iut.algo.Clavier;
import metier.reseau.ListeAdjacence;
import metier.reseau.MatriceCout;
import metier.reseau.MatriceOptimisee;
import metier.reseau.Reseau;


public class Controleur
{
    private static String FORMAT_KEY_WORD = "user:reseauformat";
    
    private FrameCreation frame;

    public Controleur()
    {

        // // this.reseau = new ListeAdjacence();
        // if (affichage == "GRAPHIQUE")
            this.frame = new FrameCreation(this);
        // else
        //     Console.affichageConsole();
    }


    public int getWidthFrame () { return this.frame.getWidth (); }
    public int getHeightFrame() { return this.frame.getHeight(); }

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

    public void sauvegarder(File file, Class<? extends Reseau> classe, Object[][] cuves, Object[][] tubes) {

        System.out.println("Sheeesh");
    }

    public static void main(String[] args)
    {
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