package views;

import loi.Log;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;

public class formChinh extends JFrame {
    private String user, pass;
    private int role;
    private JPanel main_p;
    private JPanel pnlMain;
    private JLabel lblTime;
    private JLabel lblNguoiLam;
    private JButton btnLogout;
    private JButton btnChuyenDe;
    private JButton btnKhoaHoc;
    private JButton btnHocVien;
    private JButton btnNguoiHoc;
    private JButton btnHuongDan;
    private JMenuBar mnbMain;
    private JMenu mnuHeThong;
    private JMenuItem mniDangXuat;
    private JMenuItem mniDoiPass;
    private JMenuItem mniKetThuc;
    private JMenu mnuTroGiup;
    private JMenuItem mniHuongDan;
    private JMenuItem mniGioiThieu;
    private JMenu mnuThongKe;
    private JMenuItem mniBangDiem;
    private JMenuItem mniLuongNguoiHoc;
    private JMenuItem mniDiemChuyenDe;
    private JMenuItem mniDoanhThu;
    private JMenu mnuQuanLi;
    private JMenuItem mniChuyenDe;
    private JMenuItem mniKhoaHoc;
    private JMenuItem mniNguoiHoc;
    private JMenuItem mniHocVien;
    private JMenuItem mniNhanVien;
    private JLabel lblTitle;
    private JMenuItem mniTongHop;
    private JButton btnLoi;
    private JDesktopPane desktopPane;

    public formChinh() throws IOException {
        Log log = new Log("hieupro.txt");
        this.setContentPane(main_p);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        pack();
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setLocationRelativeTo(null);
        run();

        // mở chương trình và lưu giá trị
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                luuText();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        // nút đổi mật khẩu
        mniDoiPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formDoiPass formDoiPass = new formDoiPass();
                formDoiPass.setPass(pass);
                formDoiPass.setUser(user);
                formDoiPass.setRole(role);
                dispose();
            }
        });

        // nút đăng xuất
        mniDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất không?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    try {
                        loginForm loginForm = new loginForm();
                    } catch (SQLException | IOException ex) {
                        log.logger.setLevel(Level.WARNING);
                        log.logger.info(ex.getMessage());
                        log.logger.warning("nguy hiểm");
                        log.logger.severe("cảnh cáo nhé");
                        ex.printStackTrace();
                    }
                    dispose();
                }

            }
        });

        // nút đăng xuất
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất không?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    try {
                        loginForm loginForm = new loginForm();
                    } catch (SQLException | IOException ex) {
                        log.logger.setLevel(Level.WARNING);
                        log.logger.info(ex.getMessage());
                        log.logger.warning("nguy hiểm");
                        log.logger.severe("cảnh cáo nhé");
                        ex.printStackTrace();
                    }
                    dispose();
                }
            }
        });

        //nút tắt chương trình
        mniKetThuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // nút form chuyên đề
        btnChuyenDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formChuyenDe formChuyenDe = new formChuyenDe();
                    formChuyenDe.setPass(pass);
                    formChuyenDe.setUser(user);
                    formChuyenDe.setRole(role);
                    dispose();
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }
        });

        // nút form chuyên đề
        mniChuyenDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formChuyenDe formChuyenDe = new formChuyenDe();
                    formChuyenDe.setPass(pass);
                    formChuyenDe.setUser(user);
                    formChuyenDe.setRole(role);
                    dispose();
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }
        });

        // nút vào form Khóa học
        mniKhoaHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formKhoaHoc formKhoaHoc = new formKhoaHoc();
                    formKhoaHoc.setPass(pass);
                    formKhoaHoc.setUser(user);
                    formKhoaHoc.setRole(role);
                    dispose();
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }
        });

        // nút vào form Khóa học
        btnKhoaHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formKhoaHoc formKhoaHoc = new formKhoaHoc();
                    formKhoaHoc.setPass(pass);
                    formKhoaHoc.setUser(user);
                    formKhoaHoc.setRole(role);
                    dispose();
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }
        });

        // nút nhân viên
        mniNhanVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formNhanVien formNhanVien = new formNhanVien();
                    formNhanVien.setPass(pass);
                    formNhanVien.setUser(user);
                    formNhanVien.setRole(role);
                    dispose();
                } catch (SQLException | IOException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }
        });

        // nút người học
        btnNguoiHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formNguoiHoc formNguoiHoc = new formNguoiHoc();
                    formNguoiHoc.setPass(pass);
                    formNguoiHoc.setUser(user);
                    formNguoiHoc.setRole(role);
                    dispose();
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }
        });

        // nút người học
        mniNguoiHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formNguoiHoc formNguoiHoc = new formNguoiHoc();
                    formNguoiHoc.setPass(pass);
                    formNguoiHoc.setUser(user);
                    formNguoiHoc.setRole(role);
                    dispose();
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }
        });

        //nút học viên
        btnHocVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formHocVien formHocVien = new formHocVien();
                    formHocVien.setPass(pass);
                    formHocVien.setUser(user);
                    formHocVien.setRole(role);
                    dispose();
                } catch (Exception ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }
        });

        // nút học viên
        mniHocVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formHocVien formHocVien = new formHocVien();
                    formHocVien.setPass(pass);
                    formHocVien.setUser(user);
                    formHocVien.setRole(role);
                    dispose();
                } catch (Exception ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }
        });

        //nút bảng điểm
        mniBangDiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formThongKe formThongKe = new formThongKe();
                    formThongKe.setPass(pass);
                    formThongKe.setUser(user);
                    formThongKe.setRole(role);
                    formThongKe.setTab(0);
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        //nút doanh thu
        mniDoanhThu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formThongKe formThongKe = new formThongKe();
                    formThongKe.setPass(pass);
                    formThongKe.setUser(user);
                    formThongKe.setRole(role);
                    formThongKe.setTab(3);
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // nút lượng người học
        mniLuongNguoiHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formThongKe formThongKe = new formThongKe();
                    formThongKe.setPass(pass);
                    formThongKe.setUser(user);
                    formThongKe.setRole(role);
                    formThongKe.setTab(1);
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // nút điểm chuyên đề
        mniDiemChuyenDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formThongKe formThongKe = new formThongKe();
                    formThongKe.setPass(pass);
                    formThongKe.setUser(user);
                    formThongKe.setRole(role);
                    formThongKe.setTab(2);
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // nút hướng dẫn
        btnHuongDan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("http://localhost:8080/duan_war_exploded/Servlet"));
                } catch (IOException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                } catch (URISyntaxException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }

        });

        // nút hướng dẫn
        mniHuongDan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("http://localhost:8080/duan_war_exploded/Servlet"));
                } catch (IOException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                } catch (URISyntaxException ex) {
                    log.logger.setLevel(Level.WARNING);
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");
                    log.logger.severe("cảnh cáo nhé");
                    ex.printStackTrace();
                }
            }

        });

        //Nút tổng hợp
        mniTongHop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formThongKe formThongKe = new formThongKe();
                    formThongKe.setPass(pass);
                    formThongKe.setUser(user);
                    formThongKe.setRole(role);
                    formThongKe.setTab(4);
                    dispose();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // nút giới thiệu
        mniGioiThieu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Đây là chương trình của nhóm 1");
            }
        });
        btnLoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn gửi lỗi cho admin không?", "Hỏi", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {

                    String user = "hieuop130316@gmail.com";
                    String pass = ""; // nhập pass
                    String to = "hieuntph15836@fpt.edu.vn";
                    String subject = "Lỗi Chương Trình Dự Án Mẫu";
                    String message = "Admin ơi fix lỗi cho em đi ";
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
                        MimeBodyPart messageBodyPart = new MimeBodyPart();
                        Multipart multipart = new MimeMultipart();
                        messageBodyPart = new MimeBodyPart();

                        String fileName = "hieupro.txt";
                        DataSource source = new FileDataSource("D:\\duanmau\\hieupro.txt");
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(fileName);
                        multipart.addBodyPart(messageBodyPart);
                        messagea.setContent(multipart);
                        Transport.send(messagea);
                        JOptionPane.showMessageDialog(null, "Gửi lỗi thành công");
                    } catch (Exception a) {
                        throw new RuntimeException(a);
                    }
                }
            }
        });
    }


    // Phương thức set giá trị cho 3 biến phân quyền
    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRole(int role) {
        this.role = role;
    }


    // đọc dữ liệu phân quyền lên form
    private void luuText() {

        if (role != 1) {
            mniNhanVien.setEnabled(false);
            mniDoanhThu.setEnabled(false);
        }
    }

    private void run() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int i = 90;
                while (true) {
                    Date t = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    sdf.applyPattern("HH:mm:ss");
                    String ta = sdf.format(t);
                    lblTime.setText(ta);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        thread.start();
    }

//    public static void main(String[] args) {
//        new formChinh();
//
//    }


}
