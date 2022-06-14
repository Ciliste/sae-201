package appli1;

import appli1.ihm.FrameCreation;
import iut.algo.Clavier;


public class Controleur
{

    private FrameCreation frame;

    public Controleur(String affichage)
    {

        // this.reseau = new ListeAdjacence();
        if (affichage == "GRAPHIQUE")
            this.frame = new FrameCreation(this);
        else
            Console.affichageConsole();

        
    }


    public int getWidthFrame () { return this.frame.getWidth (); }
    public int getHeightFrame() { return this.frame.getHeight(); }



    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.print("Saisir le mode de saisie des informations (console/graphique) : ");
            args = new String[1];
            args[0] = Clavier.lireString();
        }


        while (args[0].toUpperCase().indexOf("CONSOLE") == -1 && args[0].toUpperCase().indexOf("GRAPHIQUE") == -1)
        {
            System.out.print("Erreur : saisir le mode de saisie des informations (console/graphique) : ");
            args[0] = Clavier.lireString();
        }


        new Controleur(args[0].toUpperCase());
    }
}