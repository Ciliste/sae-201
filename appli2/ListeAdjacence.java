package appli2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import metier.Cuve;
import metier.Tube;



public class ListeAdjacence extends Reseau 
{

    private Map<Cuve, List<Cuve>> lstAdjacence;
    private List<Tube> ensTubes;

    public ListeAdjacence(List<Tube> ensTubes) 
    {
        this.lstAdjacence = new TreeMap<>();
        this.ensTubes = ensTubes;

        this.construireAdjacence();
    }

    private List<Cuve> getCuveRelie(Cuve cuve) 
    {
        List<Cuve> lstCuves = new ArrayList<>();

        for (Tube tube : this.ensTubes) 
        {
            if (tube.getCuveA().equals(cuve)) 
            {
                if (!lstCuves.contains(cuve)) 
                {
                    lstCuves.add(tube.getCuveB());
                }
            }
            if (tube.getCuveB().equals(cuve)) 
            {
                if (!lstCuves.contains(cuve)) 
                {
                    lstCuves.add(tube.getCuveA());
                }
            }
        }

        return lstCuves;
    }

    @Override
    public void ajouterTube(Tube tube) 
    {

        // Verification et ajout du tube dans la liste
        if (!this.lstAdjacence.get(tube.getCuveB()).contains(tube.getCuveA())) 
        {
            this.ensTubes.add(tube);
            this.construireAdjacence();
        }

    }

    @Override
    public void supprimerTube(Tube tube) 
    {
        // Suppression du tube
        this.ensTubes.remove(tube);
        this.construireAdjacence();
    }

    @Override
    public List<Cuve> getEnsCuves() 
    {
        List<Cuve> lstCuve = new ArrayList<>();

        for (Tube tube : this.ensTubes) 
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
        return this.ensTubes;
    }

    @Override
    public void formatToFile(String nomFichier)
    {
        StringBuilder stringBuilder = new StringBuilder(getClass().getSimpleName()).append("\n{\n");

        // Construction de la chaine de caractères
        for (Cuve key : this.lstAdjacence.keySet()) 
        {
            stringBuilder.append(key + " : [");

            for (Cuve cuve : this.lstAdjacence.get(key)) 
            {
                for (Tube tube : this.ensTubes) 
                {
                    if (tube.getCuveA().equals(cuve) && tube.getCuveB().equals(key) || 
                    tube.getCuveA().equals(key) && tube.getCuveB().equals(cuve)) 
                    {
                        stringBuilder.append("Cuve " + cuve.getIdentifiant() + "-" + tube.getSection() + ", ");
                    }
                }
            }

            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append("]\n");
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.append("\n}");

        // Ecriture dans le fichier 'listeAdjacence.txt'
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

    public Map<Cuve, List<Cuve>> getLstAdjacence() 
    {
        return this.lstAdjacence;
    }

    public void construireAdjacence()
    {

        for (Cuve cuve : this.getTriEnsCuves()) 
        {
            this.lstAdjacence.put(cuve, this.getCuveRelie(cuve));
        }
    }

    public static ListeAdjacence parse(String file)
    {
        try 
        {
            
            FileReader fileReader = new FileReader(file);
            Scanner sc = new Scanner(fileReader);
            String line = "";
            ArrayList<String> data = new ArrayList<>();
            ArrayList<Cuve> cuves = new ArrayList<>();
            ArrayList<Tube> tubes = new ArrayList<>();
            
            sc.nextLine();
            sc.nextLine();

            while (sc.hasNextLine())
            {
                line = sc.nextLine();

                if (line.contains("}")) break;

                data.add(line);
            }

            for (String d : data) 
            {
                cuves.add(Cuve.creerCuve(Integer.parseInt(d.split(":")[1].replace("L ", "").split("/")[1])));
            }

            for (int i = 0; i < data.size(); i++) 
            {
                String[] links = data.get(i)
                                     .split(":")[2]
                                     .replace("[", "")
                                     .replace("]", "")
                                     .replace("Cuve ", "")
                                     .replaceAll(" ", "")
                                     .split(",");
                
                for (String idCuve : links) 
                {
                    Tube tmpTube = Tube.creerTube(cuves.get(i), cuves.get(idCuve.charAt(0) - 'A'), Integer.parseInt(idCuve.split("-")[1]));
                    
                    if (tmpTube != null && !Reseau.tubeExiste(tubes, tmpTube)) 
                    {
                        tubes.add(tmpTube);
                    }
                }
            }
    
            sc.close();

            return new ListeAdjacence(tubes);
            } 
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
            };

        return null;
    }
}