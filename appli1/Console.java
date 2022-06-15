package appli1;

import java.io.PrintWriter;
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
    private static Reseau reseau;

    private static String typeSave;

    private static String nomFichier;

    // Constructeur
    private Console() {};

    // Fabrique
    public static void affichageConsole()
    {
        /* Varibale */
        int nbCuve;
        int capacite;
        int contenu;
        int nbTube;
        int section;


        // Demande du type de réseau à créée
        System.out.print("Comment voulez sauvegardez les données (liste/matrice/matrice optimisee) : ");
        Console.typeSave = Clavier.lireString().toUpperCase();
        while (Console.typeSave.indexOf("LIST") == -1 && !Console.typeSave.equals("MATRICE") && Console.typeSave.indexOf("MATRICE OPTIMIS") == -1)
        {
            System.out.println("Erreur : saisie invalide, veuillez saisie un des choix suivant (liste/matrice/matrice optimisee)");

            System.out.print("Comment voulez sauvegardez les données : ");
            Console.typeSave = Clavier.lireString().toUpperCase();
        }

        if (Console.typeSave.indexOf("LIST") != -1)
        {
            Console.reseau = new ListeAdjacence();
        }
        else 
        {
            if (Console.typeSave.indexOf("MATRICE OPTIMIS") != -1)
            {
                Console.reseau = new MatriceOptimisee();
            }
            else
            {
                if (Console.typeSave.indexOf("MATRICE") != -1)
                {
                    Console.reseau = new MatriceCout();
                }
            }
        }


        // Demande du nombre de cuves
        System.out.print("Nombre de cuves : ");
        nbCuve = Clavier.lire_int();
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
            capacite = Clavier.lire_int();
            while (capacite < 200 || capacite > 1000)
            {
                System.out.println("Erreur : capacité invalide, veuillez choisir une capacité entre 200 et 1 000");

                System.out.format("Capacité de la Cuve %c : ", identifiant + i);
                capacite = Clavier.lire_int();
            }

            // Demande du contenu
            System.out.format("Contenu de la Cuve %c : ", identifiant + i);
            contenu = Clavier.lire_int();
            while (contenu < 0 || contenu > capacite)
            {
                System.out.println("Erreur : contenu invalide, veuillez choisir un contenu entre 0 et " + capacite);

                System.out.format("Capacité de la Cuve %c : ", identifiant + i);
                contenu = Clavier.lire_int();
            }


            reseau.ajouterCuve(Cuve.creerCuve(capacite, contenu));
        }


        // Demande du nombre de tube
        System.out.print("Nombre de tubes : ");
        nbTube = Clavier.lire_int();
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
            section = Clavier.lire_int();
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
                for (Cuve cuve : Console.reseau.getEnsCuves())
                {
                    if (cuve.getIdentifiant() == idCuve1.charAt(0)) { cuve1 = cuve; }
                    if (cuve.getIdentifiant() == idCuve2.charAt(0)) { cuve2 = cuve; }
                }

                tube = Tube.creerTube(cuve1, cuve2, section);


                // Verification de l'unicité du tube
                for (Tube tubeInFor : Console.reseau.getEnsTubes())
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

            Console.reseau.ajouterTube(tube);
        }


        boolean saveReseau = false;
        while (!saveReseau)
        {
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
                Console.nomFichier = Clavier.lireString();
                while (Console.nomFichier.equals("") && Console.nomFichier.indexOf(" ") == -1 && Console.nomFichier.indexOf(".") == -1)
                {
                    System.out.println("Erreur : nom de fichier invalide, veuillez saisie un nom de fichier sans espace et sans extension");

                    System.out.print("Nom du fichier : ");
                    Console.nomFichier = Clavier.lireString();
                }
                

                String test = Console.reseau.serialize();

                if (Console.enregistrer(test))
                    System.out.println("Sauvegarde des données réussie");
                else
                    System.out.println("Erreur : sauvegarde des données échouée");


                System.out.println(Console.reseau.toString());  // Affiche le réseau sauvegardé

                saveReseau = true;
            }
            else
            {
                System.out.println("Êtes-vous sur de vouloir annuler la sauvegarde des données (oui/non) : ");
                String annuler = Clavier.lireString().toUpperCase();
                while (annuler.indexOf("OUI") == -1 && annuler.indexOf("NON") == -1)
                {
                    System.out.println("Erreur : saisie invalide, veuillez saisie un des choix suivant (oui/non)");

                    System.out.print("Êtes-vous sur de vouloir annuler la sauvegarde des données (oui/non) : ");
                    annuler = Clavier.lireString().toUpperCase();
                }

                if (annuler.indexOf("OUI") != -1)
                    { System.out.println("Sauvegarde des données annulée"); saveReseau = true; }
                else
                    { System.out.println("Sauvegarde des données non annulée"); }
            }
        }
    }


    private static boolean enregistrer(String str)
    {
        try
        {
            PrintWriter writer = new PrintWriter(Console.nomFichier + ".data", "UTF-8");

            writer.println(str);

            writer.close();
            return true;
        }
        catch (Exception e) { System.out.println("Erreur : impossible d'enregistrer le fichier"); e.printStackTrace(); return false; }
    }



    // getters
    public static Reseau getReseau  () { return Console.reseau; }
    public static String getTypeSave() { return Console.typeSave  ; }

    public static Cuve getCuve(String id)
    {
        for (Cuve cuve : Console.reseau.getEnsCuves())
            if (cuve.getIdentifiant() == id.charAt(0))
                return cuve;

        return null;
    }

    public static Cuve getCuve(int ind) { return Console.reseau.getEnsCuves().get(ind); }
    public static Cuve getTube(int ind) { return Console.reseau.getEnsCuves().get(ind); }
}
