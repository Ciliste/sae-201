package appli2;

import java.util.ArrayList;
import java.util.List;

import metier.Cuve;
import metier.Tube;


public class TestIhm
{
    public static void main(String[] args)
    {
        Reseau     listAdj;
        
        Cuve c1 = Cuve.creerCuve(200);
        Cuve c2 = Cuve.creerCuve(300);
        Cuve c3 = Cuve.creerCuve(500);
        Cuve c4 = Cuve.creerCuve(700);
        Cuve c5 = Cuve.creerCuve(1000);
        
        Tube t1 = Tube.creerTube(c1,c2,2);
        Tube t2 = Tube.creerTube(c2,c3,4);
        Tube t3 = Tube.creerTube(c3,c4,6);
        Tube t4 = Tube.creerTube(c4,c5,8);

        List<Tube> lstTube = new ArrayList<Tube>();
        lstTube.add(t1);
        lstTube.add(t2);
        lstTube.add(t3);
        lstTube.add(t4);
        
        listAdj = new ListeAdjacence(lstTube);

        new Controleur(listAdj);
    }
}