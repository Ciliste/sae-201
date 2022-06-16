package metier.reseau;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import metier.Cuve;
import metier.Tube;

import java.io.Serializable;

public abstract class Reseau implements Serializable {

    private List<Cuve> ensCuves;
    private List<Tube> ensTubes;
    
    protected Reseau() {

        this.ensCuves = new ArrayList<Cuve>();
        this.ensTubes = new ArrayList<Tube>();
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
        boolean  breakBoucle;
        for (Cuve cuve : lstCuveVisite)
        {
            for(Tube tube : this.ensTubes)
            {
                breakBoucle = false;
                if (!tube.contains(cuve))
                    continue;

                for (Tube testTube : lstTubeVisite )
                {
                    System.out.println(testTube);
                    if (tube.equals(testTube))
                        breakBoucle = true;
                }
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
                    System.out.println("modification : " + modification);
                    if(modification > tube.getSection())
                    {
                        modification = tube.getSection();
                    }
                    System.out.println("modification : " + modification);
                    if (modification > contenuCuveA + itteration[indexCuveA])
                    {
                        modification = contenuCuveA + itteration[indexCuveA];
                    }
                    System.out.println("modification : " + modification);
                    if( modification > tube.getCuveB().getCapacite() - contenuCuveB + itteration[indexCuveB])
                    {
                        modification = tube.getCuveB().getCapacite() - contenuCuveB + itteration[indexCuveB];
                    }

                    //System.out.println("modification : " + modification);
                    // if( contenuCuveB + itteration[indexCuveA] < modification)
                    // {
                    //     modification = contenuCuveA + itteration[indexCuveB];
                    // }
                    System.out.println("modification : " + modification);

                    itteration[indexCuveA] -= modification;
                    itteration[indexCuveB] += modification;
                }
                if(contenuCuveA < contenuCuveB)
                {
                    modification = contenuCuveB - contenuCuveA;
                    System.out.println("modification : " + modification);
        
                    if(modification > tube.getSection())
                    {
                        modification = tube.getSection();
                    }
                    System.out.println("modification : " + modification);

                    if (modification > contenuCuveB + itteration[indexCuveB])
                    {
                        modification = contenuCuveB + itteration[indexCuveB];
                    }
                    System.out.println("modification : " + modification);
                    if( modification > tube.getCuveA().getCapacite() - contenuCuveA + itteration[indexCuveA])
                    {
                        modification = tube.getCuveA().getCapacite() - contenuCuveA + itteration[indexCuveA];
                    }
                    //Minecraft
                    System.out.println("modification : " + modification);
                    

                    itteration[indexCuveA] += modification;
                    itteration[indexCuveB] -= modification;
                }

                System.out.println("Modification : " + modification);
                System.out.println("Cuve (A) "+tube.getCuveA() + " : " + contenuCuveA + " -> " + (contenuCuveA + itteration[indexCuveA]));
                System.out.println("Cuve (B) "+tube.getCuveB() + " : " + contenuCuveB + " -> " + (contenuCuveB + itteration[indexCuveB]));
                System.out.println("-----------------------------------------------------");

            }

        }

        for(int i = 0; i < this.ensCuves.size(); i++)
        {
            System.out.println(this.ensCuves.get(i).getIdentifiant());
            System.out.println(this.ensCuves.get(i).getCapacite());
            System.out.println(this.ensCuves.get(i).getContenu());
            System.out.println(itteration[i]);
            System.out.println("\n");
            if(itteration[i] > 0)
                this.ensCuves.get(i).ajouter(itteration[i]);
            else
                this.ensCuves.get(i).retirer(-itteration[i]);
        }   
    }

    // public void transfere()
    // {
    //     //en partant sur la base que Le compareTo compare par Contenu
    //     List<Tube> ensTube  = this.getEnsTubes();
    //     List<Cuve> ensCuves = this.getTriEnsCuves();
    //     ArrayList<Tube> ensTubeUtilisable = new ArrayList<Tube>();
    //     int total;
    //     double valeurCuve;
    //     Cuve cuveExterieur;
    //     Cuve cuveSeul;


    //     //Total représente la quantité de contenu que la Cuve devra donné
    //     total = 0;
    //     //Représente la valeur que la cuve peux tiré max 
    //     valeurCuve=0;
    //     cuveSeul = ensCuves.get(0);

    //     // On récupère tout les tube dont notre sommet fait partie
    //     for(Tube tubeSeul : ensTube)
    //     {
    //         //Faut encore vérifié que l'on ne transfere pas a une cuve que on a déja transfere
    //         if(tubeSeul.contains(cuveSeul))
    //         {
    //             if (tubeSeul.getCuveA() == cuveSeul)
    //                 cuveExterieur = tubeSeul.getCuveB();
    //             else   
    //                 cuveExterieur = tubeSeul.getCuveA();

                
    //             if (cuveExterieur.getContenu() !=0 || cuveExterieur.getContenu() >= cuveSeul.getContenu())
    //                 ensTubeUtilisable.add(tubeSeul);

    //         }
    //     }

    //     for(Tube tubeSeul : ensTubeUtilisable)
    //     {
    //         valeurCuve = Math.abs(tubeSeul.getCuveA().getContenu() -
    //                                 tubeSeul.getCuveB().getContenu())/2;

    //         if(valeurCuve <= tubeSeul.getSection())
    //             total += valeurCuve;
    //         else
    //             total += tubeSeul.getSection();
    //     }
        
    //     if(total <= cuveSeul.getContenu()){
    //         for(Tube tubeSeul : ensTubeUtilisable)
    //         {
    //             if (tubeSeul.getCuveA() == cuveSeul)
    //                 cuveExterieur = tubeSeul.getCuveB();
    //             else   
    //                 cuveExterieur = tubeSeul.getCuveA();

    //             valeurCuve = Math.abs(tubeSeul.getCuveA().getContenu() -
    //                                 tubeSeul.getCuveB().getContenu())/2;

    //             cuveSeul.retirer(valeurCuve);
    //             cuveExterieur.ajouter(valeurCuve);
                
    //         }
    //     }
    //     else
    //     {
    //         //Pas assez de jus pour tout
    //         for(Tube tubeSeul : ensTubeUtilisable)
    //         {
    //             if (tubeSeul.getCuveA() == cuveSeul)
    //                 cuveExterieur = tubeSeul.getCuveB();
    //             else   
    //                 cuveExterieur = tubeSeul.getCuveA();

    //             valeurCuve = Math.abs(tubeSeul.getCuveA().getContenu() -
    //                                 tubeSeul.getCuveB().getContenu())/2;

    //             valeurCuve = (valeurCuve/total)*cuveSeul.getContenu();

    //             cuveSeul.retirer(valeurCuve);
    //             cuveExterieur.ajouter(valeurCuve);
    //         }
    //     } 
    // }

    public List<Tube> getEnsTubes() {
        
        return this.ensTubes;
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

    public abstract String serialize();
}
