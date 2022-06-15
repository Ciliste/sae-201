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
