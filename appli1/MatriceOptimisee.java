package appli1;

import metier.Cuve;
import metier.Tube;

import java.util.List;
import java.util.ArrayList;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

public class MatriceOptimisee extends Reseau {

    private int[][] matriceOpti;
    private List<Tube> ensTube;

    public MatriceOptimisee(List<Tube> ensTube) {

        this.ensTube = ensTube;

        this.matriceOpti = new int[Cuve.nbCuve][Cuve.nbCuve];

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

                for (Tube tube : this.ensTube) {
                    if (tube.getCuveA().getIdentifiant() == 'A' + y && tube.getCuveB().getIdentifiant() == 'A' + x) {

                        if (tube.getCuveA().getIdentifiant() < tube.getCuveB().getIdentifiant()) {
                            matriceOpti[x][y] = tube.getSection();
                        } else {
                            matriceOpti[y][x] = tube.getSection();
                        }
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

    public void formatToFile() {
        StringBuilder stringBuilder = new StringBuilder("(\n");

        for (int i = 1; i < this.matriceOpti.length; i++) {
            stringBuilder.append("(");
            for (int j = 0; j < i; j++) {
                stringBuilder.append(String.format("%2d", this.matriceOpti[i][j])).append(", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append("),\n");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        
        stringBuilder.append("\n)");

        try {
            PrintWriter printWriter = new PrintWriter(
            new OutputStreamWriter(new FileOutputStream("matriceOptimisee.txt"), "UTF8"));
            printWriter.println(stringBuilder.toString());
        
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[][] getMatriceCout() {
        return this.matriceOpti;
    }

    public static void main(String[] arg) {

        List<Tube> lstTubes = new ArrayList<>();

        List<Cuve> lstCuves = new ArrayList<>();

        lstCuves.add(Cuve.creerCuve(500));
        lstCuves.add(Cuve.creerCuve(300));
        lstCuves.add(Cuve.creerCuve(800));

        lstTubes.add(Tube.creerTube(lstCuves.get(1), lstCuves.get(0), 10));
        lstTubes.add(Tube.creerTube(lstCuves.get(1), lstCuves.get(2), 8));

        new MatriceOptimisee(lstTubes).formatToFile();
    }
}