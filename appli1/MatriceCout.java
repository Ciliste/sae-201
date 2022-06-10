package appli1;

import metier.Cuve;
import metier.Tube;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class MatriceCout extends Reseau {

    private int[][] matriceCout;
    private List<Tube> ensTube;

    public MatriceCout(List<Tube> ensTube) {

        this.ensTube = ensTube;

        this.matriceCout = new int[Cuve.nbCuve][Cuve.nbCuve];

        this.creerMatrice();
    }

    public void ajouterTube(Tube tube) {
        if (this.ensTube.contains(tube))
            return;
        ensTube.add(tube);
        this.creerMatrice();
    }

    public void supprimerTube(Tube tube) {
        ensTube.remove(tube);
        this.creerMatrice();
    }

    public void creerMatrice() {

        for (int x = 0; x < Cuve.nbCuve; x++) {
            for (int y = 0; y < Cuve.nbCuve; y++) {

                for (Tube tube : ensTube) {
                    if (tube.getCuveA().getIdentifiant() == (char)('A' + y) && tube.getCuveB().getIdentifiant() == (char)('A' + x) ||
                            tube.getCuveA().getIdentifiant() == (char)('A' + x) && tube.getCuveB().getIdentifiant() == (char)('A' + y)) {
                        matriceCout[y][x] = tube.getSection();
                    }
                }
            }
        }
    }

    public List<Cuve> getEnsCuves() {

        List<Cuve> lstCuve = new ArrayList<Cuve>();

        for (Tube tube : this.ensTube) {

            if (!lstCuve.contains(tube.getCuveA())) {
                lstCuve.add(tube.getCuveA());
            }
            if (!lstCuve.contains(tube.getCuveB())) {
                lstCuve.add(tube.getCuveB());
            }
        }
        return lstCuve;
    }

    public List<Tube> getEnsTubes() {
        return ensTube;
    }

    public String formatToFile() {
        StringBuilder stringBuilder = new StringBuilder("[");

        for (int[] ints : this.matriceCout) {
            stringBuilder.append(Arrays.toString(ints)).append(",\n");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    public int[][] getMatriceCout() {
        return this.matriceCout;
    }

    public static void main(String[] arg) {

        List<Tube> lstTubes = new ArrayList<>();

        List<Cuve> lstCuves = new ArrayList<>();

        lstCuves.add(Cuve.creerCuve(500));
        lstCuves.add(Cuve.creerCuve(300));
        lstCuves.add(Cuve.creerCuve(800));

        lstTubes.add(Tube.creerTube(lstCuves.get(0), lstCuves.get(1), 5));
        lstTubes.add(Tube.creerTube(lstCuves.get(1), lstCuves.get(2), 8));

        System.out.println(new MatriceCout(lstTubes).formatToFile());
    }
}