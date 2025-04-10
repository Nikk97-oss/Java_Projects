import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Finestra extends JFrame{


    public Finestra (int w, int h) {
        setSize(w,h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); //Disabilitazione layout manager per usare setBound

        //Ricavo dimenzioni schermo PC
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //Ricavo coordinate per centrare finestra al centro dello schermo
        int x = (screenSize.width - WIDTH) / 2;
        int y = (screenSize.height - HEIGHT) / 2;

        setLocation(x, y);


        //Creazione Button Start per avviare nuova partita
        JButton startButton = new JButton("Start");
        startButton.setBounds((w-90)/2,50,90,30);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FinestraPartita newgame = new FinestraPartita (w,h);
                newgame.setVisible(true);
            }
        });
        add(startButton);

        //Creazione Button Close per uscire
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(10,180,90,30);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(closeButton);


    }



}
