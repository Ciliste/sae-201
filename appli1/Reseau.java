package appli1;

import metier.Cuve;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import metier.Tube;

public abstract class Reseau {

    public abstract void ajouterTube(Tube tube);

    public abstract void supprimerTube(Tube tube);

    public abstract List<Cuve> getEnsCuves();

    public List<Cuve> getTriEnsCuves() {

        List<Cuve> ensCuves = this.getEnsCuves();

        Collections.sort(ensCuves);

        return ensCuves;
    }
    public void transfere()
    {
        //en partant sur la base que Le compareTo compare par Contenu
        List<Tube> ensTube  = this.getEnsTubes();
        List<Cuve> ensCuves = this.getTriEnsCuves();
        ArrayList<Tube> ensTubeUtilisable = new ArrayList<Tube>();
        int total;
        double valeurCuve;
        Cuve cuveExterieur;
        Cuve cuveSeul;


        //Total représente la quantité de contenu que la Cuve devra donné
        total = 0;
        //Représente la valeur que la cuve peux tiré max 
        valeurCuve=0;
        cuveSeul = ensCuves.get(0);

        // On récupère tout les tube dont notre sommet fait partie
        for(Tube tubeSeul : ensTube)
        {
            //Faut encore vérifié que l'on ne transfere pas a une cuve que on a déja transfere
            if(tubeSeul.contient(cuveSeul))
            {
                if (tubeSeul.getCuveA() == cuveSeul)
                    cuveExterieur = tubeSeul.getCuveB();
                else   
                    cuveExterieur = tubeSeul.getCuveA();

                
                if (cuveExterieur.getContenu() !=0 || cuveExterieur.getContenu() >= cuveSeul.getContenu())
                    ensTubeUtilisable.add(tubeSeul);

            }
        }

        for(Tube tubeSeul : ensTubeUtilisable)
        {
            valeurCuve = Math.abs(tubeSeul.getCuveA().getContenu() -
                                    tubeSeul.getCuveB().getContenu())/2;

            if(valeurCuve <= tubeSeul.getSection())
                total += valeurCuve;
            else
                total += tubeSeul.getSection();
        }
        
        if(total <= cuveSeul.getContenu()){
            for(Tube tubeSeul : ensTubeUtilisable)
            {
                if (tubeSeul.getCuveA() == cuveSeul)
                    cuveExterieur = tubeSeul.getCuveB();
                else   
                    cuveExterieur = tubeSeul.getCuveA();

                valeurCuve = Math.abs(tubeSeul.getCuveA().getContenu() -
                                    tubeSeul.getCuveB().getContenu())/2;

                cuveSeul.retirer(valeurCuve);
                cuveExterieur.ajouter(valeurCuve);
                
            }
        }
        else
        {
            //Pas assez de jus pour tout
            for(Tube tubeSeul : ensTubeUtilisable)
            {
                if (tubeSeul.getCuveA() == cuveSeul)
                    cuveExterieur = tubeSeul.getCuveB();
                else   
                    cuveExterieur = tubeSeul.getCuveA();

                valeurCuve = Math.abs(tubeSeul.getCuveA().getContenu() -
                                    tubeSeul.getCuveB().getContenu())/2;

                valeurCuve = (valeurCuve/total)*cuveSeul.getContenu();

                cuveSeul.retirer(valeurCuve);
                cuveExterieur.ajouter(valeurCuve);
            }
        } 
    }

    public abstract List<Tube> getEnsTubes();

    public String toString() {

        String sRet = "Cuves :\n";
        for (Cuve cuve : this.getEnsCuves()) {

            sRet += cuve.toString() + "\n";
        }

        sRet += "\nTubes :\n";
        for (Tube tube : this.getEnsTubes()) {

            sRet += tube.toString() + "\n";
        }

        return sRet;
    }

    public abstract void formatToFile(String nomFichier);
}
