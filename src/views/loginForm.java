package views;

import dao.LoginNhanVien;
import loi.Log;
import model.Nhanvien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Level;

public class loginForm extends JFrame {

    Nhanvien nv;
    LoginNhanVien loginNhanVien = new LoginNhanVien();
    int dem = 0;
    private JPanel main_p;
    private JLabel lblPic;
    private JLabel lblTitle;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnExit;
    private JButton btnLogin;
    private JLabel lblpic;
    private JCheckBox ckcRember;
    private JLabel lblPass;

    public loginForm() throws SQLException, IOException {
        Log log = new Log("hieupro.txt");
        this.setTitle("Đăng nhập");
        this.setContentPane(main_p);
        this.setBackground(new Color(255, 0, 0));
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        ckcRember.setSelected(true);
        pack();
        if (loginNhanVien.chuyen()[0] == null) {
            txtUser.setText("");
            txtPass.setText("");
        } else {
            txtUser.setText(loginNhanVien.chuyen()[0]);
            txtPass.setText(loginNhanVien.chuyen()[1]);
        }
        this.setResizable(false); // chống chỉnh sửa size frame
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loi()) {

                    try {
                        nv = loginNhanVien.login(txtUser.getText(), String.valueOf(txtPass.getPassword()));
                        if (nv == null) {
                            dem++;
                        }
                        if (dem >= 5) {
                            int i = JOptionPane.showConfirmDialog(null, "Bạn có phải là " + txtUser.getText() + " không?", "Thông Báo", JOptionPane.YES_NO_OPTION);

                            if (i == JOptionPane.YES_OPTION) {
                                formChangePass formChangePass = new formChangePass();
                                dispose();
                            }
                        }
                        if (ckcRember.isSelected()) {

                            loginNhanVien.remeber(txtUser.getText(), String.valueOf(txtPass.getPassword()));

                        } else {
                            loginNhanVien.remeber(null, null);
                        }
                        JOptionPane.showMessageDialog(null,
                                "chào mừng " + nv.getMaNhanVien() + " đến với chương trình",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        formChinh formChinh = new formChinh();
                        formChinh.setUser(txtUser.getText());
                        formChinh.setPass(String.valueOf(txtPass.getPassword()));
                        formChinh.setRole(nv.getVaiTro());
                        dispose();
                    } catch (SQLException | IOException ex) {
                        log.logger.setLevel(Level.WARNING);
                        log.logger.info(ex.getMessage());
                        log.logger.warning("nguy hiểm");
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        ex.printStackTrace(pw);
                        String sStackTrace = sw.toString(); // stack trace as a string
                        log.logger.severe(sStackTrace);


                    }

                }
            }
        });
        ckcRember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn lưu mật khẩu không?", "Thông Báo", JOptionPane.OK_CANCEL_OPTION);
                if (JOptionPane.YES_OPTION == i) {
                    try {
                        loginNhanVien.remeber(txtUser.getText(), String.valueOf(txtPass.getPassword()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ckcRember.setSelected(false);
                }
            }
        });
        lblPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                formChangePass formChangePass = new formChangePass();
                dispose();
            }
        });
    }

    private boolean loi() {
        if (txtUser.getText().isEmpty() || txtUser.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Không được để trống username");
            txtUser.setBackground(Color.red);
            return false;
        }
        if (txtPass.getText().isEmpty() || txtPass.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Không được để trống password");
            txtPass.setBackground(Color.red);
            return false;
        }
        return true;
    }


}
