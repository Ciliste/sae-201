package metier;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

public class Tube {

    public static final int SECTION_MIN = 2;
    public static final int SECTION_MAX = 10;

    private Cuve cuveA;
    private Cuve cuveB;
    private int section;

    public static Tube creerTube(Cuve cuveA, Cuve cuveB, int section) {
        if (section < 2 || section > 10) {
            return null;
        }

        if (cuveA == null || cuveB == null) {
            return null;
        }

        if(cuveA.equals(cuveB)){
            return null;
        }

        return new Tube(cuveA, cuveB, section);
    }

    private Tube(Cuve cuveA, Cuve cuveB, int section) {
        this.cuveA = cuveA;
        this.cuveB = cuveB;
        this.section = section;
    }

    public double maj() {

        double transfert = 0;

        transfert = this.cuveA.getContenu() - this.cuveB.getContenu();
        Cuve source, destination;

        // Verification de la source et de la destination
        if (transfert > 0) {

            source = this.cuveA;
            destination = this.cuveB;
        } else if (transfert < 0) {

            source = this.cuveB;
            destination = this.cuveA;
        } else {
            return 0;
        }

        if (transfert < 0) {
            transfert = -transfert;
        }

        if (transfert > this.section) {
            transfert = this.section;
        }

        // Vide la Cuve si le transfere est plus eleve que le contenu de celle-ci
        if (source.getContenu() - transfert < 0) {

            transfert = source.getContenu();
        }

        // Transfere la quantite maximum possible pour ne pas deborder
        if (destination.getContenu() + transfert > destination.getCapacite()) {

            transfert = destination.getCapacite() - destination.getContenu();
        }

        // Equilire les deux cuves
        if (source.getContenu() - transfert < destination.getContenu() + transfert) {

            transfert = (source.getContenu() - destination.getContenu()) / 2;
        }

        source.retirer(transfert);
        destination.ajouter(transfert);

        return transfert;
    }
    public boolean contient(Cuve c)
    {
    	return this.cuveA == c || this.cuveB ==c;
    }

    public Cuve getCuveA() {
        return this.cuveA;
    }

    public Cuve getCuveB() {
        return this.cuveB;
    }

    public boolean contains(Cuve cuve) {

        return this.cuveA.getIdentifiant() == cuve.getIdentifiant() || this.cuveB.getIdentifiant() == cuve.getIdentifiant();
    }

    public int getSection() {
        return this.section;
    }

    // toString()
    public String toString() {
        return "Tube : {" + this.cuveA.toString() + " - " + this.cuveB.toString() + " - " + this.section + "}";
    }

    public String serialize(char id) {

        return String.format(
            "(%c/%d)", 
            (this.cuveA.getIdentifiant() == id) ? this.cuveB.getIdentifiant() : this.cuveA.getIdentifiant(),
            this.section
        );
    }

    // TEST
    public static void main(String[] args) {

        Cuve cuveA = Cuve.creerCuve(500);
        Cuve cuveB = Cuve.creerCuve(400);
        Tube tube = Tube.creerTube(cuveA, cuveB, 10);

        cuveA.ajouter(100);
        cuveB.ajouter(205);

        for (int i = 0; i < 10; i++) {

            System.out.println(tube.toString() + " | " + tube.maj());
        }
    }
}
