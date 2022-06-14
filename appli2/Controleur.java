package appli2;

import java.util.ArrayList;
import java.util.List;

import appli2.ihm.Frame;
import iut.algo.Clavier;
import metier.Cuve;
import metier.Tube;

public class Controleur {
    private Frame frame;

    public Controleur(Reseau reseau) {
        this.frame = new Frame(this, reseau);
    }

    public int getWidthFrame() {
        return this.frame.getWidth();
    }

    public int getHeightFrame() {
        return this.frame.getHeight();
    }

    public static void main(String[] args)
    {
        List<Cuve> ensCuves = new ArrayList<>();
        List<Tube> ensTubes = new ArrayList<>();

        // Paramétrage des cuves
        int nbCuve;
        do {
            System.out.println("Nombre de cuves : ");
            nbCuve = Clavier.lire_int();

        } while (nbCuve < 1 || nbCuve > 26);

        char identifiant = 'A';

        for (int i = 0; i < nbCuve; i++) {
            System.out.format("Capacité de la Cuve %c : ", identifiant + i);

            Cuve temp = Cuve.creerCuve(Clavier.lire_int());

            if (temp == null) {

                System.out.println("Erreur : la capacité n'est pas dans la plage demandée");
                i--;
            } else {
                ensCuves.add(temp);
            }

        }

        // Paramétrage des tubes
        int nbTube;
        do {
            System.out.println("Nombre de tubes : ");
            nbTube = Clavier.lire_int();

        } while (nbTube < 1 || nbTube > nbCuve * (nbCuve - 1) / 2);

        for (int i = 0; i < nbTube; i++) {

            System.out.format("Section du Tube %d : ", i + 1);
            int section = Clavier.lire_int();

            System.out.format("Identifiants cuves à relier %d : ", i + 1);
            char idCuveA = Clavier.lire_char();
            char idCuveB = Clavier.lire_char();

            if ((int) (idCuveA - 'A') <= nbCuve && (int) ('A' - idCuveB) <= nbCuve && idCuveA != idCuveB) {

                System.out.println("Les identifiants ne sont pas dans la plague demandée");
                i--;
            } else {

                // Recupération des cuves via l'identifiant
                Cuve cuveA = null;
                Cuve cuveB = null;

                for (Cuve cuveTmp : ensCuves) {
                    if (cuveTmp.getIdentifiant() == idCuveA) {
                        cuveA = cuveTmp;
                    }
                    if (cuveTmp.getIdentifiant() == idCuveB) {
                        cuveB = cuveTmp;
                    }
                }

                Tube tmp = Tube.creerTube(cuveA, cuveB, section);

                // Verification que le tube n'existe pas déja
                boolean lienUnique = true;

                for (Tube tubeTmp : ensTubes) {
                    if (tmp.getCuveA() == tubeTmp.getCuveA() && tmp.getCuveB() == tubeTmp.getCuveB() ||
                            tmp.getCuveA() == tubeTmp.getCuveB() && tmp.getCuveB() == tubeTmp.getCuveA()) {
                        lienUnique = false;
                    }
                }

                if (tmp != null && lienUnique)
                    ensTubes.add(tmp);
            }
        }
    }

}
