package appli1;

import java.util.ArrayList;
import java.util.List;

import iut.algo.Clavier;
import metier.Cuve;
import metier.Tube;
import metier.reseau.ListeAdjacence;
import metier.reseau.MatriceCout;
import metier.reseau.MatriceOptimisee;
import metier.reseau.Reseau;


public class Console
{
    private static List<Cuve> ensCuves = new ArrayList<Cuve>();
    private static List<Tube> ensTubes = new ArrayList<Tube>();
    private static Reseau reseauSave;
    private static String typeSave;

    // Constructeur
    private Console() {};

    // Fabrique
    public static void affichageConsole()
    {
        // Demande du nombre de cuves
        System.out.print("Nombre de cuves : ");
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


            Console.ensCuves.add(Cuve.creerCuve(capacite, contenu));
        }


        // Demande du nombre de tube
        System.out.print("Nombre de tubes : ");
        int nbTube = Clavier.lire_int();
        while (nbTube < 0 || nbTube > (nbCuve * (nbCuve - 1) / 2))
        {
            System.out.println("Erreur : nombre invalide, veuillez choisir un nombre entre 0 et " + (nbCuve * (nbCuve - 1) / 2));

            System.out.print("Nombre de tubes : ");
            nbTube = Clavier.lire_int();
        } 


        for (int i = 0; i < nbTube; i++)
        {
            // Demande de la section des tubes
            System.out.format("Section du Tube %d : ", i + 1);
            int section = Clavier.lire_int();
            while (section < 2 || section > 10)
            {
                System.out.println("Erreur : section invalide, veuillez choisir une section entre 2 et 10");

                System.out.format("Section du Tube %d : ", i + 1);
                section = Clavier.lire_int();
            }


            Tube tube = null;
            Boolean tubeCreer;
            do
            {
                tubeCreer = true;
                // Demande de l'id de la première cuve qui sera relier au tuyau
                System.out.format("Identifiants de la première cuves à relier au tube %d : ", i + 1);
                String idCuve1 = ("" + Clavier.lire_char()).toUpperCase();
                while (idCuve1.charAt(0) < 'A' || idCuve1.charAt(0) > (char)('A' + nbCuve-1))
                {
                    System.out.println("Erreur : identifiant invalide, veuillez choisir un identifiant entre 'A' et '" + (char)('A' + nbCuve-1) + "'");

                    System.out.format("Identifiants de la première cuves à relier au tube %d : ", i + 1);
                    idCuve1 = ("" + Clavier.lire_char()).toUpperCase();
                }

                // Demande de l'id de la deucième cuve qui sera relier au tuyau
                System.out.format("Identifiants de la deuxième cuves à relier au tube %d : ", i + 1);
                String idCuve2 = ("" + Clavier.lire_char()).toUpperCase();
                while (idCuve2.charAt(0) < 'A' || idCuve2.charAt(0) > (char)('A' + nbCuve-1) || idCuve2.equals(idCuve1))
                {
                    System.out.println("Erreur : identifiant invalide, veuillez choisir un identifiant entre 'A' et '" + (char)('A' + nbCuve-1) + "' et différent de '" + idCuve1 + "'");

                    System.out.format("Identifiants de la deuxième cuves à relier au tube %d : ", i + 1);
                    idCuve2 = ("" + Clavier.lire_char()).toUpperCase();
                }

                // Récupération des cuves en fonction des identifiant rentré par l'utilisateur
                Cuve cuve1 = null, cuve2 = null;
                for (Cuve cuve : Console.ensCuves)
                {
                    if (cuve.getIdentifiant() == idCuve1.charAt(0)) { cuve1 = cuve; }
                    if (cuve.getIdentifiant() == idCuve2.charAt(0)) { cuve2 = cuve; }
                }

                tube = Tube.creerTube(cuve1, cuve2, section);


                // Verification de l'unicité du tube
                for (Tube tubeInFor : Console.ensTubes)
                {
                    if (tubeCreer)
                    {
                        if (tubeInFor.getCuveA() == tube.getCuveA() && tubeInFor.getCuveB() == tube.getCuveB() ||
                            tubeInFor.getCuveA() == tube.getCuveB() && tubeInFor.getCuveB() == tube.getCuveA())
                        {
                            System.out.println("Erreur : tube invalide, ce tube existe déjà");
                            tubeCreer = false;
                        }
                    }
                }

            }while (!tubeCreer);

            Console.ensTubes.add(tube);
        }



        // Demande de sauvegarde du réseau
        System.out.println("Voulez vous sauvegarder les données (oui/non) : ");
        String save = Clavier.lireString().toUpperCase();
        while (save.indexOf("OUI") == -1 && save.indexOf("NON") == -1)
        {
            System.out.println("Erreur : saisie invalide, veuillez saisie un des choix suivant (oui/non)");

            System.out.print("Voulez vous sauvegarder les données (oui/non) : ");
            save = Clavier.lireString().toUpperCase();
        }


        if (save.indexOf("OUI") != -1)
        {
            System.out.print("Nom du fichier : ");
            String nomFichier = Clavier.lireString();
            while (!nomFichier.equals("") || nomFichier.indexOf(" ") != -1)
            {
                System.out.println("Erreur : nom de fichier invalide, veuillez saisie un nom de fichier sans espace");

                System.out.print("Nom du fichier : ");
                nomFichier = Clavier.lireString();
            }

        

            // Demande du MODE de sauvegarde du réseau
            System.out.print("Comment voulez sauvegardez les données (liste/matrice/matrice optimisee) : ");
            Console.typeSave = Clavier.lireString().toUpperCase();
            while (Console.typeSave.indexOf("LISTE") == -1 && !Console.typeSave.equals("MATRICE") && Console.typeSave.indexOf("MATRICE OPTIMIS") == -1)
            {
                System.out.println("Erreur : saisie invalide, veuillez saisie un des choix suivant (liste/matrice/matrice optimisee)");

                System.out.print("Comment voulez sauvegardez les données : ");
                Console.typeSave = Clavier.lireString().toUpperCase();
            }

            if (Console.typeSave.indexOf("LISTE") != -1)
            {
                Console.reseauSave = new ListeAdjacence();
            }
            else 
            {
                if (Console.typeSave.indexOf("MATRICE OPTIMIS") != -1)
                {
                    Console.reseauSave = new MatriceOptimisee();
                }
                else
                {
                    if (Console.typeSave.indexOf("MATRICE") != -1)
                    {
                        Console.reseauSave = new MatriceCout();
                    }
                }
            }

            Console.reseauSave.serialize();
            System.out.println("Sauvegarde des données réussie");


            System.out.println(Console.reseauSave.toString());
        }
    }



    // getters
    public static List<Cuve> getEnsCuves() { return Console.ensCuves; }
    public static List<Tube> getEnsTubes() { return Console.ensTubes; }
    public static String     getTypeSave() { return Console.typeSave; }
}
