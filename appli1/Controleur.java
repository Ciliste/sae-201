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
            System.out.println("Saisir le mode de saisie des informations (console/graphique)1 : ");
            args = new String[1];
            args[0] = Clavier.lireString();
        }


        while (args[0].toUpperCase().contains("CONSOLE") && args[0].toUpperCase().contains("GRAPHIQUE"))
        {
            System.out.println("Saisir le mode de saisie des informations (console/graphique)2 : ");
            args[0] = Clavier.lireString();
        }


        new Controleur(args[0].toUpperCase());
    }
}