package appli1;

import java.util.ArrayList;
import java.util.List;

import iut.algo.Clavier;
import metier.Cuve;
import metier.Tube;


public class Console
{
    private Console() {};

    public static void affichageConsole()
    {
        List<Cuve> ensCuves = new ArrayList<>();
        List<Tube> ensTubes = new ArrayList<>();

        // Demande du nombre de cuves
        System.out.println("Nombre de cuves : ");
        int nbCuve = Clavier.lire_int();

        while (nbCuve < 1 || nbCuve > 26)
        {
            System.out.println("Erreur : nombre invalide, veuillez choisir un nombre entre 1 et 26");
            System.out.print("Nombre de cuves : ");
            nbCuve = Clavier.lire_int();
        }


        char identifiant = Cuve.getCompteur();
        for (int i = 0; i < nbCuve; i++)
        {
            // Demande de la capacité
            System.out.format("Capacité de la Cuve %c : ", identifiant + i);
            int capacite = Clavier.lire_int();
            while (capacite < 200 || capacite > 1000)
            {
                System.out.println("Erreur : capacité invalide, veuillez choisir une capacité entre 200 et 1 000");

                System.out.format("Capacité de la Cuve %c : ", identifiant + i);
                capacite = Clavier.lire_int();
            }

            // Demande du contenu
            System.out.format("Contenu de la Cuve %c : ", identifiant + i);
            int contenu = Clavier.lire_int();
            while (contenu < 0 || contenu > capacite)
            {
                System.out.println("Erreur : contenu invalide, veuillez choisir un contenu entre 0 et " + capacite);

                System.out.format("Capacité de la Cuve %c : ", identifiant + i);
                contenu = Clavier.lire_int();
            }


            ensCuves.add(Cuve.creerCuve(capacite, contenu));
        }


        // Demande du nombre de tube
        System.out.print("Nombre de tubes : ");
        int nbTube = Clavier.lire_int();
        while (nbTube < 1 || nbTube > nbCuve * (nbCuve - 1) / 2);
        {
            System.out.print("Nombre de tubes : ");
            nbTube = Clavier.lire_int();
        } 


        for (int i = 0; i < nbTube; i++)
        {
            System.out.format("Section du Tube %d : ", i + 1);
            int section = Clavier.lire_int();

            System.out.format("Identifiants cuves à relier %d : ", i + 1);
            char idCuveA = Clavier.lire_char();
            char idCuveB = Clavier.lire_char();

            if ((int) (idCuveA - 'A') <= nbCuve && (int) ('A' - idCuveB) <= nbCuve && idCuveA != idCuveB)
            {
                System.out.println("Les identifiants ne sont pas dans la plague demandée");
                i--;
            }
            else
            {

                // Recupération des cuves via l'identifiant
                Cuve cuveA = null;
                Cuve cuveB = null;

                for (Cuve cuveTmp : ensCuves)
                {
                    if (cuveTmp.getIdentifiant() == idCuveA)
                        cuveA = cuveTmp;

                    if (cuveTmp.getIdentifiant() == idCuveB)
                        cuveB = cuveTmp;
                }

                Tube tmp = Tube.creerTube(cuveA, cuveB, section);

                // Verification que le tube n'existe pas déja
                boolean lienUnique = true;

                for (Tube tubeTmp : ensTubes)
                {
                    if (tmp.getCuveA() == tubeTmp.getCuveA() && tmp.getCuveB() == tubeTmp.getCuveB() || tmp.getCuveA() == tubeTmp.getCuveB() && tmp.getCuveB() == tubeTmp.getCuveA())
                    {
                        lienUnique = false;
                    }
                }

                if (tmp != null && lienUnique)
                ensTubes.add(tmp);
            }
        }
    }
}
