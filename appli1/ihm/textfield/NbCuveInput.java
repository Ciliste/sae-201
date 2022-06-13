package appli1.ihm.textfield;

public class NbCuveInput extends AbstractInputTextField {

    public NbCuveInput() {

        super();
    }

    public boolean valeurEstValide() {

        try {
            int nbCuve = Integer.parseInt(this.getText());
            if (nbCuve < 1 || nbCuve > 26) {
                return false;
            } else {

                return true;
            }
        } catch (NumberFormatException e) {

            return false;
        }
    }

    public String getMessageErreur() {

        if (this.valeurEstValide()) {

            return null;
        }

        int nbCuve;
        try {

            nbCuve = Integer.parseInt(this.getText());
        } catch (Exception e) {

            return "La capacité doit être un nombre entier";
        }

        if (nbCuve < 1) {

            return "La capacité doit être supérieure à 0";
        }

        if (nbCuve > 26) {

            return "La capacité doit être inférieure à 26";
        }

        return null;
    }
}