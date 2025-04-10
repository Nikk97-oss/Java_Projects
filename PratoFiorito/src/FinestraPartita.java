import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class FinestraPartita extends JFrame {

    private static final int Grid_size=10;
    private static final int Num_Mines=15;
    private JButton[][] buttons=new JButton[Grid_size][Grid_size];
    private boolean[][] mines=new boolean[Grid_size][Grid_size];
    private boolean[][] revealed=new boolean[Grid_size][Grid_size];
    private boolean gameover=false;

    public FinestraPartita(int w, int h) {
        setLayout(null);
        JFrame titolo=new JFrame("Prato Fiorito");
        titolo.setSize(w,h);
        titolo.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(Grid_size,Grid_size));

        InitializeMines();

        /*Creazione pulsanti all'interno della griglia Panel creata sopra.
        Attraverso i for creo un nuovo pulsante che assegno alla mia matrice precedentemente
        dichiarata*/
        for (int i=0;i<Grid_size;i++) {
            for (int j=0;j<Grid_size;j++) {
                JButton button=new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 10));
                button.setMargin(new Insets(2, 2, 2, 2));
                button.setFocusPainted(false);
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.TOP);

                buttons[i][j]=button;

                final int row=i;
                final int col=j;

                button.addActionListener(e -> handleClick(row,col));
                panel.add(button);
            }
        }

        titolo.add(panel);
        titolo.setVisible(true);
    }


    /* Inizializzazione delle mine all'interno della matrice in modo randomico*/
    private void InitializeMines() {
        Random random=new Random();
        int placedMines=0;

        while(placedMines < Num_Mines) {
            int row=random.nextInt(Grid_size);
            int col=random.nextInt(Grid_size);

            if(!mines[row][col]) {
                mines[row][col]=true;
                placedMines++;
            }
        }
    }


    /* Gestisco l'evento della pressione dei pulsanti che si trovano disposti a matrice
    *  all'interno del campo */
    private void handleClick(int row, int col) {

        if (gameover || revealed[row][col]) {
            return;
        }

        if (mines[row][col]) {
            buttons[row][col].setText("💣");
            buttons[row][col].setBackground(Color.RED);
            gameOver();
        }
        else{
            int mineCount=countAdjiacentMines(row,col);
            buttons[row][col].setText(String.valueOf(mineCount));
            buttons[row][col].setBackground(Color.LIGHT_GRAY);

            if (mineCount==0)
                revealeAdjacentCells(row,col);
        }


    }



    //Metodo che conta le bombe adiacenti alla casella selezionata
    //L'intero calcolato verrà mostrato nel pulsante cliccato
    private int countAdjiacentMines(int row, int col) {
        int count =0;

        for (int i=-1;i<1;i++) {
            for (int j=-1;j<1;j++) {
                int r=row+i;
                int c=col+j;

                if (r>=0 && r<Grid_size && c>=0 && c<Grid_size && mines[r][c]) {
                    count++;
                }
            }
        }
        return count;
    }


    /*Metodo che rileva le caseele adiacenti senza mine*/
    private void revealeAdjacentCells(int row, int col) {
        for (int i=-1;i<1;i++) {
            for (int j=-1;j<1;j++) {
                int r=row+i;
                int c=col+j;

                if (r>=0 && r<Grid_size && c>=0 && c<Grid_size && !mines[r][c]) {
                    handleClick(r,c);
                }
            }
        }
    }


    //Metodo Game Over, crea una finestra di dialogo che riferisce che la partita
    //è stata persa, inoltre rileva tutte le bombe presenti nel "campo"
    private void gameOver() {
        gameover=true;
        JOptionPane.showMessageDialog(null,"GAME OVER");

        for (int r=0;r<Grid_size;r++) {
            for (int c=0;c<Grid_size;c++) {
                if (mines[r][c]) {
                    buttons[r][c].setText("💣");
                    buttons[r][c].setBackground(Color.RED);
                }
            }
        }
    }

    //Metodo che verifica la vittoria
    private void checkWin(){
        for (int row = 0; row < Grid_size; row++) {
            for (int col = 0; col < Grid_size; col++) {
                if (!mines[row][col] && !revealed[row][col]) {
                    return;
                }
            }
        }

        gameover=true;
        JOptionPane.showMessageDialog(null, "Hai vinto!", "Vittoria", JOptionPane.INFORMATION_MESSAGE);
    }


}
