package appli2.ihmBis;

import java.awt.BorderLayout;
import java.awt.Graphics;

import javax.swing.JPanel;

import common.SharedContants;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import metier.Cuve;

public class CuvePanel extends JPanel {

    public CuvePanel(JFrame frame, Cuve cuve) {

        super();

        this.setLayout(new BorderLayout());

        CercleCuve cercle = new CercleCuve(cuve.getCapacite(), cuve.getContenu());
        this.add(cercle, BorderLayout.CENTER);

        InfoCuve info = new InfoCuve(cuve);
        this.add(info, 
            switch (cuve.getPosInfo().getValeur()) {
                case 0 -> BorderLayout.NORTH;
                case 1 -> BorderLayout.EAST;
                case 2 -> BorderLayout.SOUTH;
                case 3 -> BorderLayout.WEST;
                default -> BorderLayout.NORTH;
            }
        );

        this.setOpaque(false);

        int width, height;
        

        // this.setBounds(cuve.get, 10, 100, 100);
    }

    private class CercleCuve extends JComponent {

        private int capacite;
        private double contenu;

        public CercleCuve(int capacite, double contenu) {

            super();
            this.capacite = capacite;
            this.contenu = contenu;
        }

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            this.getWidth();
        }
    }

    private class InfoCuve extends JPanel {

        public InfoCuve(Cuve cuve) {

            super();

            this.add(new JLabel(String.valueOf(cuve.getIdentifiant())));
            this.add(new JLabel((Math.round(cuve.getContenu() * 100.0)/100.0) + "/" + cuve.getCapacite()));
        }
    }
}