package appli2;

import metier.Cuve;
import metier.Tube;
import metier.reseau.ListeAdjacence;
import metier.reseau.Reseau;


public class TestIhm
{
    public static void main(String[] args)
    {
        Reseau     listAdj = new ListeAdjacence();
        
        Cuve c1 = Cuve.creerCuve(200);
        Cuve c2 = Cuve.creerCuve(300);
        Cuve c3 = Cuve.creerCuve(500);
        Cuve c4 = Cuve.creerCuve(700);
        Cuve c5 = Cuve.creerCuve(1000);
        
        Tube t1 = Tube.creerTube(c1,c2,2);
        Tube t2 = Tube.creerTube(c2,c3,4);
        Tube t3 = Tube.creerTube(c3,c4,6);
        Tube t4 = Tube.creerTube(c4,c5,8);

        listAdj.ajouterTube(t1);
        listAdj.ajouterTube(t2);
        listAdj.ajouterTube(t3);
        listAdj.ajouterTube(t4);
    }
}