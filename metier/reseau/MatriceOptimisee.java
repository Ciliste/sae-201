package metier.reseau;

import metier.Cuve;
import metier.Tube;

public class MatriceOptimisee extends Reseau {

    public MatriceOptimisee() {

        super();
    }

    public String serialize() {

        String sRet = "";

        for (Cuve cuve : this.getEnsCuves()) {
            
            sRet += cuve.serialize() + " - ";

            for (Cuve cuveBis : this.getEnsCuves()) {
                
                // Putain de Golem
                if (cuve.getIdentifiant() > cuveBis.getIdentifiant()) {

                    boolean bExiste = false;
                    for (Tube tube : this.getAdjacences().get(cuve)) {

                        if (cuve != cuveBis && tube.contains(cuveBis)) {
                            
                            sRet += String.format("%" + ((int)Math.log10(Tube.SECTION_MAX)+1) + "s ", tube.getSection());
                            bExiste = true;
                            break;
                        }
                    }

                    if (!bExiste) sRet += String.format("%" + ((int)Math.log10(Tube.SECTION_MAX)+1) + "s ", "X");
                }
            }

            sRet += "\n";
        }

        return sRet;
    }

    public static Reseau deserialize(String str) {

        return MatriceCout.deserialize(str);
    }

    public static void main(String[] args) {
        
        Reseau reseau = new MatriceOptimisee();
        
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
    }
}