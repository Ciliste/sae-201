public class Cuve
{
    private static char CompteurId = 'A';

    private char   identifiant;

    private int    capacite;
    private double contenu;

    private int    posX;
    private int    posY;
    private char   posInfo;


    private Cuve(int capacite, int posX, int posY, char posInfo)
    {
        this.identifiant = Cuve.CompteurId++;

        this.capacite    = capacite;
        this.contenu     = 0;

        this.posX        = posX;
        this.posY        = posY;
        this.posInfo     = posInfo;
    }

    public static Cuve fabriquerCuve(int capacite, int posX, int posY, char posInfo)
    {
        /* Vérif du nombre de cuve créée */
        if (Cuve.CompteurId > 'Z')             { return null; }
        /* Vérif de la capacité de la cuve créée */
        if (capacite < 200 || capacite > 1000) { return null; }



        return new Cuve(capacite, posX, posY, posInfo);
    }
    
    public int    getNbCuveCreer() { return (int) (Cuve.CompteurId - 'A'); }
    public int    getIdentifier () { return this.identifiant;              }
    public int    getcapacite   () { return this.capaciter;                }
    public double getcontenu    () { return this.contenu;                  }
    public int    getposX       () { return this.posX;                     }
    public int    getposY       () { return this.posY;                     }
    public char   getposInfo    () { return this.posInfo;                  }

    public int    setcapacite   () { return this.capaciter;                }
    public double setcontenu    () { return this.contenu;                  }
}