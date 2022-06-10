package appli1;

import metier.Cuve;
import metier.Tube;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/*import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;*/

public class ListeAdjacence extends Reseau {

    private Map<Character, List<Tube>> lstAdjacence;
    private List<Tube> ensTubes;

    public ListeAdjacence(List<Tube> ensTubes) {

        this.lstAdjacence = new HashMap<>();
        this.ensTubes = ensTubes;

        for (int i = 0; i <= Cuve.nbCuve; i++) {

            this.lstAdjacence.put((char) ('A' + i), this.getTubesFromCuve((char) ('A' + i)));
        }

    }

    private List<Tube> getTubesFromCuve(char idCuve) {
        List<Tube> lstTempTube = new ArrayList<>();

        for (Tube tmpTube : ensTubes) {
            if (tmpTube.getCuveA().getIdentifiant() == idCuve) {
                lstTempTube.add(tmpTube);
            }
            if (tmpTube.getCuveB().getIdentifiant() == idCuve) {
                lstTempTube.add(tmpTube);
            }
        }

        return lstTempTube;
    }

    public void ajouterTube(Tube tube) {

        List<Tube> lstTube = this.lstAdjacence.get(tube.getCuveA().getIdentifiant());

        lstTube.add(tube);
        this.lstAdjacence.put(tube.getCuveA().getIdentifiant(), lstTube);

        lstTube = this.lstAdjacence.get(tube.getCuveB().getIdentifiant());

        lstTube.add(tube);
        this.lstAdjacence.put(tube.getCuveB().getIdentifiant(), lstTube);

    }

    public void supprimerTube(Tube tube) {

        List<Tube> lstTube = this.lstAdjacence.get(tube.getCuveA().getIdentifiant());

        lstTube.remove(tube);
        this.lstAdjacence.put(tube.getCuveA().getIdentifiant(), lstTube);

        lstTube = this.lstAdjacence.get(tube.getCuveB().getIdentifiant());

        lstTube.remove(tube);
        this.lstAdjacence.put(tube.getCuveB().getIdentifiant(), lstTube);
    }

    public List<Cuve> getEnsCuves() {
        List<Cuve> lstCuve = new ArrayList<>();

        for (Tube tube : this.ensTubes) {
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

        return this.ensTubes;
    }

    public String formatToFile() {
        StringBuilder stringBuilder = new StringBuilder("{");

        // Construction de la chaine de caractères
        for (char key : this.lstAdjacence.keySet()) {
            stringBuilder.append(key + " : " + this.lstAdjacence.get(key) + ",\n");
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.append("\n}");

        // Ecriture dans le fichier 'listeAdjacence.txt'
        /*
         * try {
         * PrintWriter printWriter = new PrintWriter(
         * new OutputStreamWriter(new FileOutputStream("listeAdjacence.txt"), "UTF8"));
         * printWriter.println(stringBuilder.toString());
         * 
         * printWriter.close();
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         */

        return stringBuilder.toString();
    }

    public Map<Character, List<Tube>> getLstAdjacence() {
        return this.lstAdjacence;
    }

    public static void main(String[] args) {

        List<Tube> lstTubes = new ArrayList<>();

        List<Cuve> lstCuves = new ArrayList<>();

        lstCuves.add(Cuve.creerCuve(100));
        lstCuves.add(Cuve.creerCuve(100));
        lstCuves.add(Cuve.creerCuve(100));

        lstTubes.add(Tube.creerTube(lstCuves.get(0), lstCuves.get(1), 5));
        lstTubes.add(Tube.creerTube(lstCuves.get(1), lstCuves.get(2), 8));

        new ListeAdjacence(lstTubes); //🍔burgir

    }

}