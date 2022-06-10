package appli1;

import metier.Cuve;
import java.util.List;
import java.util.ArrayList;

import metier.Tube;

public abstract class Reseau {

    public abstract void ajouterTube(Tube tube);

    public abstract void supprimerTube(Tube tube);

    public abstract Iterable<Cuve> getEnsCuves();

    public abstract Iterable<Tube> getEnsTubes();

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
}