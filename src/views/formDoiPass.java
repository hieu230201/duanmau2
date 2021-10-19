package views;

import dao.serviceNhanVien;
import model.Nhanvien;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

public class formDoiPass extends JFrame {
    serviceNhanVien _list = new serviceNhanVien();
    private String user, pass;
    private int role;
    private JPanel main_p;
    private JTextField txtUser;
    private JPasswordField txtPassNow;
    private JPasswordField txtPassNew;
    private JPasswordField txtComfirmPassNew;
    private JButton btnYes;
    private JButton btnNo;
    private JLabel lblTitle;

    public formDoiPass() {
        this.setContentPane(main_p);
        this.setDefaultCloseOperation(2);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        pack();
        txtUser.setEnabled(false);

        // mở form sẽ lưu giá trị
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                luuText();
            }
        });

        // quá trình đóng form sẽ r form chính
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                formChinh formChinh = null;
                try {
                    formChinh = new formChinh();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                formChinh.setUser(user);
                formChinh.setPass(pass);
                formChinh.setRole(role);
                dispose();
            }
        });

        // nút thoát form
        btnNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formChinh formChinh = null;
                try {
                    formChinh = new formChinh();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                formChinh.setUser(user);
                formChinh.setPass(pass);
                formChinh.setRole(role);
                dispose();
            }
        });

        //  nút đổi pass
        btnYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (loi()) {
                    int i = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn đổi mật khẩu không?", "Thông báo", JOptionPane.YES_NO_OPTION);
                    if (i == JOptionPane.YES_OPTION) {
                        Nhanvien nv = new Nhanvien();
                        nv.setMaNhanVien(txtUser.getText());
                        nv.setMatKhau(String.valueOf(txtPassNew.getPassword()));
                        try {
                            JOptionPane.showMessageDialog(null, _list.updatePassNV(nv, String.valueOf(txtPassNow.getPassword())));
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    // check lỗi ở đổi mật khẩu
    private boolean loi() {
        if (!String.valueOf(txtPassNow.getPassword()).equals(pass)) {
            JOptionPane.showMessageDialog(null, "bạn nhập sai mật khẩu hiện tại");
            txtPassNow.setText("");
            txtPassNow.requestFocus();
            return false;
        }

        if (txtPassNew.getPassword().toString().isEmpty() || txtPassNew.getPassword().toString().isBlank()) {
            JOptionPane.showMessageDialog(null, "Không được để trống mật khẩu mới");
            txtPassNew.setText("");
            txtPassNew.requestFocus();
            return false;
        }

        if (txtComfirmPassNew.getPassword().toString().isEmpty() || txtComfirmPassNew.getPassword().toString().isBlank()) {
            JOptionPane.showMessageDialog(null, "Không được để trống xác nhận mật khẩu");
            txtComfirmPassNew.setText("");
            txtComfirmPassNew.requestFocus();
            return false;
        }

        if (!String.valueOf(txtPassNew.getPassword()).equals(String.valueOf(txtComfirmPassNew.getPassword()))) {
            JOptionPane.showMessageDialog(null, "Vui lòng xác nhận đúng mật khẩu mới");
            txtComfirmPassNew.setText("");
            txtComfirmPassNew.requestFocus();
            return false;
        }
        return true;
    }


    // Phương thức lấy giá trị
    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRole(int role) {
        this.role = role;
    }

    // phương thức đẩy giá trị vào form
    private void luuText() {
        txtUser.setText(user);
    }


//    public static void main(String[] args) {
//        new formDoiPass();
//    }
}
