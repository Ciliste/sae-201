package appli1;

import metier.Cuve;
import metier.Tube;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;

public class ListeAdjacence extends Reseau {

    private Map<Cuve, List<Cuve>> lstAdjacence;
    private List<Tube> ensTubes;

    public ListeAdjacence(List<Tube> ensTubes) {

        this.lstAdjacence = new HashMap<>();
        this.ensTubes = ensTubes;

        this.construireAdjacence();

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

        // Verification et ajout du tube dans la liste
        if (!this.lstAdjacence.get(tube.getCuveB()).contains(tube.getCuveA())) {

            this.ensTubes.add(tube);
            this.construireAdjacence();
        }

    }

    public void supprimerTube(Tube tube) {

        // Suppression du tube
        this.ensTubes.remove(tube);
        this.construireAdjacence();
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

    public void formatToFile() {
        StringBuilder stringBuilder = new StringBuilder("{\n");

        // Construction de la chaine de caractères
        for (Cuve key : this.lstAdjacence.keySet()) {
            stringBuilder.append(key + " : [");

            for (Cuve cuve : this.lstAdjacence.get(key)) {
                stringBuilder.append("Cuve " + cuve.getIdentifiant() + ", ");
            }
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append("], \n");
        }

        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
        stringBuilder.append("\n}");

        // Ecriture dans le fichier 'listeAdjacence.txt'
        try {
            PrintWriter printWriter = new PrintWriter(
            new OutputStreamWriter(new FileOutputStream("listeAdjacence.txt"), "UTF8"));
            printWriter.println(stringBuilder.toString());
        
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Cuve, List<Cuve>> getLstAdjacence() {
        return this.lstAdjacence;
    }

    public void construireAdjacence() {

        for (Cuve cuve : this.getEnsCuves()) {
            this.lstAdjacence.put(cuve, this.getCuveRelie(cuve));
        }
    }

    public static void main(String[] args) {

        List<Tube> lstTubes = new ArrayList<>();

        List<Cuve> lstCuves = new ArrayList<>();

        lstCuves.add(Cuve.creerCuve(500));
        lstCuves.add(Cuve.creerCuve(300));
        lstCuves.add(Cuve.creerCuve(800));

        lstTubes.add(Tube.creerTube(lstCuves.get(0), lstCuves.get(1), 5));
        lstTubes.add(Tube.creerTube(lstCuves.get(1), lstCuves.get(2), 8));

        new ListeAdjacence(lstTubes).formatToFile();
    }

}