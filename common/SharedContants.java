package common;

import java.io.File;
import java.nio.file.Files;

import javax.swing.filechooser.FileFilter;

public class SharedContants {
    
    public static enum FiltresFichier {

        FILTRE_FICHIER_RESEAU(new FileFilter() {

            public String getDescription() {
    
                return "Réseaux (*.data)";
            }
    
            public boolean accept(File f) {
    
                if (f.isDirectory()) {
    
                    return true;
                } 
                else {
    
                    try {
    
                        return Files.getAttribute(f.toPath(), SharedContants.FORMAT_KEY_WORD) != null;
                    }
                    catch (Exception err) {return false;}
                }
            }
        }),
        FILTRE_FICHIER_DATA(new FileFilter() {
                   
            public String getDescription() {
    
                return "Data files (*.data)";
            }
    
            public boolean accept(File f) {
    
                if (f.isDirectory()) {
    
                    return true;
                } 
                else {
    
                    try {
    
                        return f.getName().endsWith(".data");
                    }
                    catch (Exception err) {return false;}
                }
            }
        });

        private FileFilter filtre;

        private FiltresFichier(FileFilter filtre) {

            this.filtre = filtre;
        }

        public FileFilter filtre() {

            return this.filtre;
        }
    }

    public static enum StatutFichier {

        RIEN(0),
        NOUVEAU(1),
        OUVERT(2);

        public final int VAL;

        private StatutFichier(int val) {

            this.VAL = val;
        }
    }

    public static enum StatutTravail {

        AUCUN(0),
        TRAVAIL(1);

        public final int VAL;

        private StatutTravail(int val) {

            this.VAL = val;
        }
    }

    public static enum RatioAffichage {

        TAILLE_CUVE_MAX(0.20),
        TAILLE_CUVE_MIN(0.05),
        TAILLE_SECTION_MAX(0.03),
        TAILLE_SECTION_MIN(0.01);

        public final double VAL;

        private RatioAffichage(double val) {

            this.VAL = val;
        }
    }

    public static final String FORMAT_KEY_WORD = "user:reseauformat";
}