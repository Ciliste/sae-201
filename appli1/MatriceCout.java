package appli1;

import metier.Cuve;
import metier.Tube;

import java.util.List;
import java.util.ArrayList;

public class MatriceCout extends Reseau {

    private int[][] matriceCout;

    public MatriceCout(List<Tube> ensTube) {

        this.matriceCout = new int[Cuve.nbCuve][Cuve.nbCuve];

        for (int x = 0; x < Cuve.nbCuve; x++) {
            for (int y = 0; y < Cuve.nbCuve; y++) {

                for (Tube tube : ensTube) {

                    if (tube.getCuveA().getIdentifiant() == 'A' + x || tube.getCuveB().getIdentifiant() == 'A' + y) {
                        matriceCout[x][y] = tube.getSection();
                    } else {
                        matriceCout[x][y] = 0;
                    }
                }
            }
        }
    }

    public void ajouterTube(Tube tube) {

    }

    public void supprimerTube(Tube tube) {

    }

    public List<Cuve> getEnsCuves() {
        return;
    }

    public List<Tube> getEnsTubes() {
        return;
    }

    public String formatToFile() {
        return "";
    }

    public int[][] getMatriceCout() {
        return this.matriceCout;
    }

    public static void main(String[] arg) {
    }
}