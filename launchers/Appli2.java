package launchers;

import appli2.ihmBis.FrameVisu;

public class Appli2 extends Controleur {
    
    private Appli2() {

        new FrameVisu(this);
    }

    public static void main(String[] args) {
        
        new Appli2();
    }
}