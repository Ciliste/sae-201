package appli2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import metier.Cuve;
import metier.Tube;


public class MatriceCout extends Reseau 
{

    private int[][] matriceCout;
    private List<Tube> ensTube;

    public MatriceCout(List<Tube> ensTube)
    {
        this.ensTube = ensTube;

        this.matriceCout = new int[Cuve.nbCuve][Cuve.nbCuve];

        this.creerMatrice();
    }

    @Override
    public void ajouterTube(Tube tube) 
    {
        if (this.ensTube.contains(tube)) return;

        ensTube.add(tube);
        this.creerMatrice();
    }

    @Override
    public void supprimerTube(Tube tube) 
    {
        ensTube.remove(tube);
        this.creerMatrice();
    }

    public void creerMatrice() 
    {
        for (int x = 0; x < Cuve.nbCuve; x++) 
        {
            for (int y = 0; y < Cuve.nbCuve; y++) 
            {
                for (Tube tube : this.ensTube) 
                {
                    if (tube.getCuveA().getIdentifiant() == 'A' + y && tube.getCuveB().getIdentifiant() == 'A' + x ||
                            tube.getCuveA().getIdentifiant() == 'A' + x
                                    && tube.getCuveB().getIdentifiant() == 'A' + y) 
                                    {
                        matriceCout[y][x] = tube.getSection();
                    }
                }
            }
        }
    }

    @Override
    public List<Cuve> getEnsCuves() 
    {
        List<Cuve> lstCuve = new ArrayList<Cuve>();

        for (Tube tube : this.ensTube) 
        {

            if (!lstCuve.contains(tube.getCuveA())) 
            {
                lstCuve.add(tube.getCuveA());
            }
            if (!lstCuve.contains(tube.getCuveB())) 
            {
                lstCuve.add(tube.getCuveB());
            }
        }
        return lstCuve;
    }

    @Override
    public List<Tube> getEnsTubes() 
    {
        return ensTube;
    }

    @Override
    public void formatToFile(String nomFichier) 
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.getClass().getSimpleName()).append("\n");
        
        for (Cuve cuve : this.getTriEnsCuves()) 
        {
            stringBuilder.append(cuve.toString()).append("\n");
        }

        stringBuilder.append("{\n");

        for (int i = 0; i < this.matriceCout.length; i++) 
        {

            stringBuilder.append("(");

            for (int j = 0; j < this.matriceCout[i].length; j++) 
            {
                stringBuilder.append(String.format("%2d", this.matriceCout[i][j])).append(", ");
            }

            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append("),\n");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("\n}");

        try 
        {
            PrintWriter printWriter = new PrintWriter(
            new OutputStreamWriter(new FileOutputStream(nomFichier + ".data"), "UTF8"));

            printWriter.println(stringBuilder.toString());
        
            printWriter.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public int[][] getMatriceCout() 
    {
        return this.matriceCout;
    }

    public static MatriceCout parse(String file) 
    {
        try 
        {
            FileReader fileReader = new FileReader(file);
            Scanner sc = new Scanner(fileReader);
            String line = "";
            List<Tube> lstTubes = new ArrayList<>();
            List<Cuve> lstCuves = new ArrayList<>();

            int quantite;
            boolean matrice = false;
            int cpt = 0;

            sc.nextLine();

            while (sc.hasNextLine())
            {
                line = sc.nextLine();

                if (line.contains("}")) break;

                if (line.contains("{")) 
                {
                    matrice = true;
                    line = sc.nextLine();
                }

                if (!matrice)
                {
                    quantite = Integer.parseInt( line.split("/")[1].replace("L", "") );
                    lstCuves.add( Cuve.creerCuve(quantite) );
                }

                if (matrice)
                {
                    line = line.replace("(", "").replace(")", "").replace(" ", "");
                    
                    for (int i=0; i < lstCuves.size(); i++)
                    {
                        quantite = Integer.parseInt( line.split(",")[i] );

                        if(quantite != 0)
                        {
                            Tube tmpTube = Tube.creerTube( lstCuves.get(cpt), lstCuves.get(i), quantite);

                            if(tmpTube != null && !Reseau.tubeExiste(lstTubes, tmpTube))
                            {
                                lstTubes.add(tmpTube);
                            }
                        }
                    } 
                    cpt++;
                }
            }
    
            sc.close();

            return new MatriceCout(lstTubes);
    
        }
        catch(FileNotFoundException e)
        {
                e.printStackTrace();
        }

        return null;
    }
}