package views;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class loadForm extends JFrame {
    private JPanel main_p;
    private JProgressBar pgbLoad;
    private JLabel lblPic;

    public loadForm() throws SQLException, IOException {
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setContentPane(main_p);
        this.setVisible(true);
        pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fill();
        if (pgbLoad.getValue() == 100) {
            loginForm loginForm = new loginForm();
            loginForm.setVisible(true);
            dispose();
        }

    }

    public static void main(String[] args) throws SQLException, IOException {
        new loadForm();
    }

    private void fill() {
        int time = 0;
        while (time <= 100) {
            pgbLoad.setValue(time);
            pgbLoad.setString(time + "%");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time += 10;

        }
        pgbLoad.setString("Done!");
    }
}
