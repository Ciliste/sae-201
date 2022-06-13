package appli1.ihm.textfield;

import javax.swing.JTextField;

public abstract class AbstractInputTextField extends JTextField {

    public AbstractInputTextField() {

        super(10);
    }

    public abstract boolean valeurEstValide();

    public abstract String getMessageErreur();
}