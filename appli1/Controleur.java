package appli1;

import appli1.ihm.FrameCreation;
//import iut.algo.Clavier;


public class Controleur
{

    private FrameCreation frame;

    public Controleur()
    {

        // // this.reseau = new ListeAdjacence();
        // if (affichage == "GRAPHIQUE")
            this.frame = new FrameCreation(this);
        // else
        //     Console.affichageConsole();
    }


    public int getWidthFrame () { return this.frame.getWidth (); }
    public int getHeightFrame() { return this.frame.getHeight(); }



    public static void main(String[] args)
    {
        new Controleur();
    }
}