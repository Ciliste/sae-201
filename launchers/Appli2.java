package launchers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;

import appli1.ihm.FrameCreation;
import appli2.ihm.FrameRendu;
import metier.Cuve;
import metier.Cuve.PositionInfo;
import metier.Position;
import metier.Tube;
import metier.reseau.ListeAdjacence;
import metier.reseau.MatriceCout;
import metier.reseau.MatriceOptimisee;
import metier.reseau.Reseau;


public class Appli2 extends Controleur
{
    private static String FORMAT_KEY_WORD = "user:reseauformat";

    private FrameRendu frameRendu;

    public Appli2(Reseau reseau)
    {
        this.frameRendu = new FrameRendu(this, reseau);
    }

    public int getWidthFrame       () { return this.frameRendu.getWidth            (); }
    public int getHeightFrame      () { return this.frameRendu.getHeight           (); }
    public int getWidthPanelAction () { return this.frameRendu.getWidthPanelAction (); }
    public int getHeightPanelAction() { return this.frameRendu.getHeightPanelAction(); }


    public Reseau ouvrir(File file) throws IOException, IllegalArgumentException, NoSuchMethodException {

        Object attr = Files.getAttribute(Path.of(file.getAbsolutePath()), Appli2.FORMAT_KEY_WORD);
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

            Files.setAttribute(Path.of(file.getAbsolutePath()), Appli2.FORMAT_KEY_WORD, format.getBytes());
        }
        catch (Exception err) {

            // ¯\_(ツ)_/¯
        }
    }

    public void maj()
    {
        this.frameRendu.repaint();
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


    public static void main(String[] args) {

        Reseau     listAdj = new ListeAdjacence();
        
        Cuve c1 = Cuve.creerCuve(1000, 500, new Position(20 , 400), 0);
        Cuve c2 = Cuve.creerCuve(900 ,   0, new Position(50 , 400), 1);
        Cuve c3 = Cuve.creerCuve(200 , 190, new Position(100, 100), 2);
        Cuve c4 = Cuve.creerCuve(700 ,   0, new Position(200, 100), 3);
        //Cuve c5 = Cuve.creerCuve(1000, 1000, new Position(300, 200), 0);

        Tube t1 = Tube.creerTube(c1,c2,2);
        Tube t2 = Tube.creerTube(c2,c3,4);
        Tube t3 = Tube.creerTube(c1,c3,6);
        Tube t4 = Tube.creerTube(c4,c2,8);
        
        // Ajout des cuves au réseau
        listAdj.ajouterCuve(c1);
        listAdj.ajouterCuve(c2);
        listAdj.ajouterCuve(c3);
        listAdj.ajouterCuve(c4);
        //listAdj.ajouterCuve(c5);

        // Ajout des tubes
        listAdj.ajouterTube(t1);
        listAdj.ajouterTube(t2);
        listAdj.ajouterTube(t3);
        listAdj.ajouterTube(t4);


        new Appli2(listAdj);
    }

}