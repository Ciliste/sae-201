package metier.reseau;

import metier.Cuve;
import metier.Tube;

public class MatriceCout extends Reseau {

    public MatriceCout() {

        super();
    }

    public String serialize() {

        String sRet = "";

        for (Cuve cuve : this.getEnsCuves()) {
            
            sRet += cuve.serialize() + " - ";

            for (Cuve cuveBis : this.getEnsCuves()) {

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

            sRet += "\n";
        }

        return sRet;
    }

    public static Reseau deserialize(String str) {

        MatriceCout reseau = new MatriceCout();
        
        for (String ligne : str.split("\n")) {

            reseau.ajouterCuve(Cuve.deserialize(ligne.split(" - ")[0].replace(" ", "")));
        }

        String[] lignes = str.split("\n");
        for (int cptCuve = 0; cptCuve < lignes.length; cptCuve++) {

            if (lignes[cptCuve].split(" - ").length <= 1) continue;
            String ligne = lignes[cptCuve].split(" - ")[1];
            while (ligne.contains("  ")) ligne = ligne.replace("  ", " ");
            while (ligne.startsWith(" ")) ligne = ligne.substring(1);

            String[] adjs = ligne.split(" ");
            for (int cpt = 0; cpt < adjs.length; cpt++) {

                if (adjs[cpt].equals("X") || cptCuve > cpt) continue;
                reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(cptCuve), reseau.getEnsCuves().get(cpt), Integer.parseInt(adjs[cpt])));
            }
        }
        
        return reseau;
    }

    public static void main(String[] args) {
        
        Reseau reseau = new MatriceCout();
        
        for (int i = 0; i < 4; i++) {
            
            reseau.ajouterCuve(Cuve.creerCuve(300));
        }
        reseau.ajouterCuve(Cuve.creerCuve(300, 200));
        reseau.ajouterCuve(Cuve.creerCuve(300, 100));
        
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(0), reseau.getEnsCuves().get(1), 10));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(0), reseau.getEnsCuves().get(2), 10));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(0), reseau.getEnsCuves().get(3), 10));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(1), reseau.getEnsCuves().get(2), 10));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(1), reseau.getEnsCuves().get(3), 8));
        reseau.ajouterTube(Tube.creerTube(reseau.getEnsCuves().get(2), reseau.getEnsCuves().get(3), 10));

        System.out.println(reseau.serialize());

        System.out.println(MatriceCout.deserialize(reseau.serialize()).serialize());
    }
}