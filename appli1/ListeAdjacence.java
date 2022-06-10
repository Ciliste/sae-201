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

    private Map<Cuve, List<Cuve>> lstAdjacence;
    private List<Tube> ensTubes;

    public ListeAdjacence(List<Tube> ensTubes) {

        this.lstAdjacence = new HashMap<>();
        this.ensTubes = ensTubes;

        for (Cuve cuve : this.getEnsCuves()) {
            this.lstAdjacence.put(cuve, this.getCuveRelie(cuve));
        }

    }

    private List<Cuve> getCuveRelie(Cuve cuve) {
        List<Cuve> lstCuves = new ArrayList<>();

        for (Tube tube : this.ensTubes) {
            if (tube.getCuveA().equals(cuve)) {
                if (!lstCuves.contains(cuve)) {
                    lstCuves.add(tube.getCuveB());
                }
            }
            if (tube.getCuveB().equals(cuve)) {
                if (!lstCuves.contains(cuve)) {
                    lstCuves.add(tube.getCuveA());
                }
            }
        }

        return lstCuves;
    }

    public void ajouterTube(Tube tube) {

        // Verification et ajout dans la cle correspondant a la cuveA
        List<Cuve> lstCuve = this.lstAdjacence.get(tube.getCuveA());

        if (!this.lstAdjacence.get(tube.getCuveB()).contains(tube.getCuveA())) {

            lstCuve.add(tube.getCuveA());
            this.ensTubes.add(tube);

            this.lstAdjacence.put(tube.getCuveB(), lstCuve);

            // Ajout dans la cle correspondant a la cuveB
            lstCuve = this.lstAdjacence.get(tube.getCuveB());

            lstCuve.add(tube.getCuveB());
            this.lstAdjacence.put(tube.getCuveA(), lstCuve);
        }

    }

    public void supprimerTube(Tube tube) {

        // Suppression de la cuveA du Tube
        List<Cuve> lstCuve = this.lstAdjacence.get(tube.getCuveA());

        lstCuve.remove(tube.getCuveA());
        this.lstAdjacence.put(tube.getCuveB(), lstCuve);

        // Suppression de la cuveB du Tube
        lstCuve = this.lstAdjacence.get(tube.getCuveB());

        lstCuve.remove(tube.getCuveB());
        this.lstAdjacence.put(tube.getCuveA(), lstCuve);

        this.ensTubes.remove(tube);
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
        StringBuilder stringBuilder = new StringBuilder("{\n");

        // Construction de la chaine de caractères
        for (Cuve key : this.lstAdjacence.keySet()) {
            stringBuilder.append(key).append(" : [");

            for (Cuve cuve : this.lstAdjacence.get(key)) {
                stringBuilder.append("Cuve ").append(cuve.getIdentifiant()).append(", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append("], \n");
        }

        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
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

    public Map<Cuve, List<Cuve>> getLstAdjacence() {
        return this.lstAdjacence;
    }

    public static void main(String[] args) {

        List<Tube> lstTubes = new ArrayList<>();

        List<Cuve> lstCuves = new ArrayList<>();

        lstCuves.add(Cuve.creerCuve(500));
        lstCuves.add(Cuve.creerCuve(500));
        lstCuves.add(Cuve.creerCuve(500));

        lstTubes.add(Tube.creerTube(lstCuves.get(0), lstCuves.get(1), 5));
        lstTubes.add(Tube.creerTube(lstCuves.get(1), lstCuves.get(2), 8));

        System.out.println(new ListeAdjacence(lstTubes).formatToFile());
    }

}