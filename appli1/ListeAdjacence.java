package appli1;

import java.util.Collections;
import java.util.List;

import metier.Cuve;
import metier.Tube;

public class ListeAdjacence extends Reseau {

    public ListeAdjacence() {

        super();
    }
    
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
}