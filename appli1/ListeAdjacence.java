package appli1;

import metier.Cuve;
import metier.Tube;

import java.util.List;
import java.util.ArrayList;

public class ListeAdjacence extends Reseau {

    public List<Tube> ensTubes;

    public ListeAdjacence() {

        this.ensTubes = new ArrayList<>();
    }

    public void ajouterTube(Tube tube) {

        this.ensTubes.add(tube);
    }

    public void supprimerTube(Tube tube) {

        this.ensTubes.remove(tube);
    }

    @Override
    public Iterable<Cuve> getEnsCuves() {

        List<Cuve> ensCuves = new ArrayList<>();

        for (Tube tube : this.ensTubes) {

            if (!ensCuves.contains(tube.getCuveA()))
                ensCuves.add(tube.getCuveA());
            if (!ensCuves.contains(tube.getCuveB()))
                ensCuves.add(tube.getCuveB());
        }

        return ensCuves;
    }

    @Override
    public Iterable<Tube> getEnsTubes() {
        // TODO Auto-generated method stub
        return this.ensTubes;
    }
}