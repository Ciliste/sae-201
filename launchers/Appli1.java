package launchers;

import appli1.ihm.FrameCreation;

public class Appli1 extends Controleur {
    
    private Appli1() {

        new FrameCreation(this);
    }

    public static void main(String[] args) {
        
        new Appli1();
    }
}