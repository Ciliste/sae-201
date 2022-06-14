package appli1;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import metier.Cuve;
import metier.Tube;



public class MatriceOptimisee extends Reseau {

    private int[][] matriceOpti;
    private List<Tube> ensTube;

    public MatriceOptimisee(List<Tube> ensTube) {

        this.ensTube = ensTube;

        this.matriceOpti = new int[Cuve.nbCuve][Cuve.nbCuve];

        this.creerMatrice();
    }

    @Override
    public void ajouterTube(Tube tube) {
        if (this.ensTube.contains(tube))
            return;
        ensTube.add(tube);
        this.creerMatrice();
    }

    @Override
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

    @Override
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

    @Override
    public List<Tube> getEnsTubes() {
        return ensTube;
    }

    @Override
    public void formatToFile(String nomFichier) {
        StringBuilder stringBuilder = new StringBuilder(this.getClass().getSimpleName()).append("\n");

        for( Cuve tmp : this.getEnsCuves()){
            stringBuilder.append(tmp.toString()).append("\n");
        }

        stringBuilder.append("{\n");

        for (int i = 1; i < this.matriceOpti.length; i++) {
            stringBuilder.append("(");
            for (int j = 0; j < i; j++) {
                stringBuilder.append(String.format("%2d", this.matriceOpti[i][j])).append(", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append("),\n");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        
        stringBuilder.append("\n}");

        try {
            PrintWriter printWriter = new PrintWriter(
            new OutputStreamWriter(new FileOutputStream(nomFichier + ".data"), "UTF8"));
            printWriter.println(stringBuilder.toString());
        
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[][] getMatriceCout() {
        return this.matriceOpti;
    }
}