package metier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import metier.reseau.Reseau;

import metier.Cuve;

import java.io.Serializable;

public abstract class Reseau implements Serializable, Cloneable {

    private List<Cuve> ensCuves;
    private List<Tube> ensTubes;
    private Reseau precedent;
    
    protected Reseau() {

        this.ensCuves = new ArrayList<Cuve>();
        this.ensTubes = new ArrayList<Tube>();
        this.precedent = null;
    }

    public void ajouterTube(Tube tube) {
        
        if (this.ensTubes.contains(tube))
            return;
        this.ensTubes.add(tube);
    }

    public void supprimerTube(Tube tube) {
        
        this.ensTubes.remove(tube);
    }

    public void ajouterCuve(Cuve cuve) {
        
        if (this.ensCuves.contains(cuve))
            return;
        this.ensCuves.add(cuve);
    }

    public void supprimerCuve(Cuve cuve) {
        
        this.ensCuves.remove(cuve);
    }

    public List<Cuve> getEnsCuves() {
        
        return this.ensCuves;
    }

    public List<Cuve> getEnsCuvesTri() {

        List<Cuve> lstCuve = new ArrayList<Cuve>();
        lstCuve.addAll(this.ensCuves);
        Collections.sort(lstCuve);
        Collections.reverse(lstCuve);

        return lstCuve;
    }
    public void update()
    {
        
        double[] itteration = new double[this.ensCuves.size()];

        List<Tube> lstTubeVisite = new ArrayList<Tube>();
        List<Cuve> lstCuveVisite = this.getEnsCuvesTri();

        double modification;
        double contenuCuveA;
        double contenuCuveB;

        int indexCuveA;
        int indexCuveB;

        this.creePrecedent();

        boolean  breakBoucle;
        for (Cuve cuve : lstCuveVisite)
        {
            for(Tube tube : this.ensTubes)
            {
                breakBoucle = false;
                if (!tube.contains(cuve))
                    continue;

                for (Tube testTube : lstTubeVisite )
                    if (tube.equals(testTube))
                        breakBoucle = true;

                if (breakBoucle)
                    continue;

                lstTubeVisite.add(tube);
                modification = 0;
                contenuCuveA = tube.getCuveA().getContenu();
                contenuCuveB = tube.getCuveB().getContenu();

                indexCuveA = this.ensCuves.indexOf(tube.getCuveA());
                indexCuveB = this.ensCuves.indexOf(tube.getCuveB());
                if(contenuCuveA > contenuCuveB)
                {
                    modification = contenuCuveA - contenuCuveB;
                    if(modification > tube.getSection())
                        modification = tube.getSection();

                    if (modification > contenuCuveA + itteration[indexCuveA])
                        modification = contenuCuveA + itteration[indexCuveA];

                    if( modification > tube.getCuveB().getCapacite() - contenuCuveB + itteration[indexCuveB])
                        modification = tube.getCuveB().getCapacite() - contenuCuveB + itteration[indexCuveB];


                    itteration[indexCuveA] -= modification;
                    itteration[indexCuveB] += modification;
                }
                if(contenuCuveA < contenuCuveB)
                {
                    modification = contenuCuveB - contenuCuveA;
        
                    if(modification > tube.getSection())
                        modification = tube.getSection();

                    if (modification > contenuCuveB + itteration[indexCuveB])
                        modification = contenuCuveB + itteration[indexCuveB];

                    if( modification > tube.getCuveA().getCapacite() - contenuCuveA + itteration[indexCuveA])
                        modification = tube.getCuveA().getCapacite() - contenuCuveA + itteration[indexCuveA];
                    

                    itteration[indexCuveA] += modification;
                    itteration[indexCuveB] -= modification;
                }
            }
        }


        for(int i = 0; i < this.ensCuves.size(); i++)
        {
            if(itteration[i] > 0)
                this.ensCuves.get(i).ajouter(itteration[i]);
            else
                this.ensCuves.get(i).retirer(-itteration[i]);
        }
        System.out.println(this.serialize());
        System.out.println(this);
        System.out.println(precedent);
        System.out.println(this.precedent.serialize());
    }

    
    public List<Tube> getEnsTubes() { return this.ensTubes; }
    public void retour()
    {
        if(this.precedent == null)
            return;
        this.ensCuves = this.precedent.getEnsCuves();
        this.ensTubes = this.precedent.getEnsTubes();
        this.precedent = this.precedent.getPrecedent();
    }


    
    
    private void creePrecedent()
    {
        try {
            this.precedent = (Reseau) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < this.ensCuves.size(); i++)
        {
            Cuve cuve = this.ensCuves.get(i);
            this.precedent.supprimerCuve(cuve);
            this.precedent.ajouterCuve(cuve.cloneCuve());
        }
        
        for (int i = 0; i < this.ensTubes.size(); i++)
        {   Tube tube = this.ensTubes.get(i);
            this.precedent.supprimerTube(tube);
            this.precedent.ajouterTube(tube.cloneTube());
        }
        
        System.out.println("AFFICHAGE DU PRESENT ET PRECEDENT");

        System.out.println(this.serialize());
        
        
    }

    public Map<Cuve, List<Tube>> getAdjacences() {

        Map<Cuve, List<Tube>> adjacences = new HashMap<Cuve, List<Tube>>();

        for (Cuve cuve : this.ensCuves) {
            adjacences.put(cuve, new ArrayList<Tube>());
        }

        for (Tube tube : this.ensTubes) {
            if (!adjacences.get(tube.getCuveA()).contains(tube)) adjacences.get(tube.getCuveA()).add(tube);
            if (!adjacences.get(tube.getCuveB()).contains(tube)) adjacences.get(tube.getCuveB()).add(tube);
        }

        return adjacences;
    }

    public Reseau getPrecedent() {
        return this.precedent;
    }

    public abstract String serialize();
}
