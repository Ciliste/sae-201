package appli2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import metier.Cuve;
import metier.Tube;




public abstract class Reseau {

    public abstract void ajouterTube(Tube tube);

    public abstract void supprimerTube(Tube tube);

    public abstract List<Cuve> getEnsCuves();

    public List<Cuve> getTriEnsCuves() {

        List<Cuve> ensCuves = this.getEnsCuves();

        Collections.sort(ensCuves);

        return ensCuves;
    }

    public abstract List<Tube> getEnsTubes();

    @Override
    public String toString() {

        String sRet = "Cuves :";
        for (Cuve cuve : this.getEnsCuves()) {

            sRet += cuve.toString() + "\n";
        }

        sRet += "\nTubes :";
        for (Tube tube : this.getEnsTubes()) {

            sRet += tube.toString() + "\n";
        }

        return sRet;
    }

    public abstract void formatToFile();

    public static Reseau parse(String file) {
        
        try {
            
        FileReader fileReader = new FileReader(file);
        Scanner sc = new Scanner(fileReader);
    
        String typeReseau = sc.nextLine();

        switch (typeReseau) {
            case "ListeAdjacence" : {
                sc.close();
                return ListeAdjacence.parse(file);   
            }

            case "MatriceCout" : {
                sc.close();
                return MatriceCout.parse(file);
            }
        }

        sc.close();

        } catch(FileNotFoundException e){
            e.printStackTrace();
        };
        
        return null;
    
    }

    public static void main(String[] args) {
        Reseau.parse("listeAdjacence.data");
    }
}
