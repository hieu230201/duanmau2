package views;

import dao.serviceNhanVien;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

public class formChangePass extends JFrame {
    serviceNhanVien _lst = new serviceNhanVien();
    int code;
    private JTextField txtEmail;
    private JButton gửiMãButton;
    private JTextField txtCode;
    private JButton btnConfirm;
    private JPanel mainPanel;
    private JPasswordField txtNewPass;
    private JPasswordField txtComfimPass;
    private JButton thoátButton;
    private JTextField txtUser;
    private JLabel lblTime;
    private JLabel lblTitle;

    public formChangePass() {
        this.setTitle("Tìm Mật Khẩu");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        if (lblTime.getText().equals("0")) {
            gửiMãButton.setEnabled(true);
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    loginForm loginForm = new loginForm();
                    dispose();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        gửiMãButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lblTime.getText().equals("0") || lblTime.getText().length() == 0) {
                    Random random = new Random();
                    code = random.nextInt(9999);
                    String user = "hieuop130316@gmail.com";
                    String pass = "hieu230201"; // nhập pass
                    String to = txtEmail.getText();
                    String subject = "Mã để đổi mật khẩu";
                    String message = "Đây là mã của bạn " + code;
                    Properties props = System.getProperties();
                    props.put("mail.smtp.user", "username");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    props.put("mail.debug", "true");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.EnableSSL.enable", "true");

                    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.setProperty("mail.smtp.socketFactory.fallback", "false");
                    props.setProperty("mail.smtp.port", "465");
                    props.setProperty("mail.smtp.socketFactory.port", "465");

                    Session sessiona = Session.getInstance(props,
                            new Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(user, pass);
                                }
                            });
                    try {
                        Message messagea = new MimeMessage(sessiona);
                        messagea.setFrom(new InternetAddress(user));
                        messagea.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                        messagea.setSubject(subject);
                        messagea.setText(message);
                        Transport.send(messagea);
                        lblTitle.setText("Chờ mã trong :");
                        JOptionPane.showMessageDialog(null, "Gửi mã thành công");
                        run();
                    } catch (Exception a) {
                        throw new RuntimeException(a);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Đợi chút rồi gửi lại email nha!");
                }
            }
        });
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loi()) {
                    try {
                        JOptionPane.showMessageDialog(null, _lst.updatePassNVQuen(txtUser.getText(), String.valueOf(txtNewPass.getPassword()), txtEmail.getText()));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        thoátButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loginForm loginForm = new loginForm();
                    dispose();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private boolean loi() {
        if (Integer.parseInt(txtCode.getText()) != code) {
            JOptionPane.showMessageDialog(null, "Mã email không đúng");
            txtCode.setText("");
            return false;
        }
        if (txtUser.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Không được để trống tài khoản");
            txtUser.requestFocus();
            return false;
        }
        if (Arrays.toString(txtNewPass.getPassword()).length() == 0) {
            JOptionPane.showMessageDialog(null, "Không được để trống mật khẩu mới");
            txtNewPass.requestFocus();
            return false;
        }
        if (Arrays.toString(txtComfimPass.getPassword()).length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng xác nhận lại mật khẩu");
            txtComfimPass.requestFocus();
            return false;
        }
        String pass = String.valueOf(txtNewPass.getPassword());
        String passNew = String.valueOf(txtComfimPass.getPassword());
        System.out.println(pass + " " + passNew);
        if (!pass.equals(passNew)) {
            JOptionPane.showMessageDialog(null, "Nhập lại mật khẩu để xác nhận");
            txtComfimPass.requestFocus();
            txtComfimPass.setText("");
            return false;
        }
        return true;
    }

    private void run() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int i = 90;
                while (i-- > 0) {

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lblTime.setText(String.valueOf(i));
                }
            }
        };
        thread.start();
    }


}
