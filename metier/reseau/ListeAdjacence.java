package metier.reseau;

import java.util.Collections;
import java.util.List;

import metier.Cuve;
import metier.Tube;

public class ListeAdjacence extends Reseau {

    public ListeAdjacence() {

        super();
    }

    // mettre dans un fichier les infos du programme
    public String serialize() {

        String sRet = "";

        List<Cuve> lstCuve = this.getEnsCuves();
        Collections.sort(lstCuve, (Cuve c1, Cuve c2) -> {
            return (ListeAdjacence.this.getAdjacences().get(c1).size() - ListeAdjacence.this.getAdjacences().get(c2).size() != 0) ? ListeAdjacence.this.getAdjacences().get(c1).size() - ListeAdjacence.this.getAdjacences().get(c2).size() : c2.getIdentifiant() - c1.getIdentifiant();
        });
        Collections.reverse(lstCuve);

        for (Cuve cuve : this.getEnsCuves()) {

            sRet += cuve.serialize() + " -> ";

            if (this.getAdjacences().get(cuve).size() == 0) {

                sRet += "X";
            }
            else {

                for (Tube tube : this.getAdjacences().get(cuve)) {

                    sRet += tube.serialize(cuve.getIdentifiant());
                    if (this.getAdjacences().get(cuve).indexOf(tube) != this.getAdjacences().get(cuve).size() - 1) sRet += ", ";
                }
            }
        
            sRet += "\n";
        }
        
        return sRet;
    }

    public static Reseau deserialize(String str) {

        str = str.replace(" ", "");
        
        Reseau reseau = new ListeAdjacence();

        for (String ligne : str.split("\n")) {

            reseau.ajouterCuve(Cuve.deserialize(ligne.split("->")[0]));
        }        

        for (String ligne : str.split("\n")) {

            if (ligne.split("->")[1].equals("X")) break;
            for (String tube : ligne.split("->")[1].split(",")) {

                tube = tube.replace("(", "").replace(")", "");
                
                Cuve cuve1 = null;
                Cuve cuve2 = null;
                for (Cuve cuve : reseau.getEnsCuves()) {

                    if (cuve.getIdentifiant() == ligne.charAt(1)) {

                        cuve1 = cuve;
                    }
                    if (cuve.getIdentifiant() == tube.charAt(0)) {

                        cuve2 = cuve;
                    }
                }
                
                boolean bOk = true;
                for (Tube t : reseau.getAdjacences().get(cuve1)) {

                    if (t.contains(cuve2)) {

                        bOk = false;
                        break;
                    }
                }

                if (bOk) {
                    reseau.ajouterTube(
                        Tube.creerTube(
                            cuve1,
                            cuve2, 
                            Integer.parseInt(tube.split("/")[1])
                        )
                    );
                }
            }
        }

        return reseau;
    }

    public static void main(String[] args) {
        
        Reseau reseau = new ListeAdjacence();
        
        for (int i = 0; i < 4; i++) {
            
            reseau.ajouterCuve(Cuve.creerCuve(300));
        }
        reseau.ajouterCuve(Cuve.creerCuve(300, 200));
        reseau.ajouterCuve(Cuve.creerCuve(300, 100));

        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(0), reseau.getEnsCuves().get(1), 10));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(0), reseau.getEnsCuves().get(2), 10));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(0), reseau.getEnsCuves().get(3), 10));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(1), reseau.getEnsCuves().get(2), 10));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(1), reseau.getEnsCuves().get(3), 10));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(2), reseau.getEnsCuves().get(3), 10));
        
        System.out.println(reseau.serialize());

        System.out.println(ListeAdjacence.deserialize(reseau.serialize()).serialize());
    }
}