package views;

import dao.serviceNhanVien;
import loi.Log;
import model.Nhanvien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Level;

public class formNhanVien extends JFrame {
    serviceNhanVien _list = new serviceNhanVien();
    DefaultTableModel _dtm;
    int stt = 0;
    private String user, pass;
    private int role;
    private JPanel main_p;
    private JTabbedPane tabbedPane1;
    private JTable tblNhanVien;
    private JTextField txtMaNhanVien;
    private JPasswordField txtPass;
    private JPasswordField txtConFirmPass;
    private JRadioButton rdbAdmin;
    private JRadioButton rdbUser;
    private JButton btnThem;
    private JButton btnMoi;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnFirst;
    private JButton btnLast;
    private JButton btnRight;
    private JButton btnLeft;
    private JTextField txtName;
    private JTextField txtEmail;
    private JPanel PanelCon;
    private JPanel panelAn;
    private JPanel panelHien;
    private JButton btnDeleteNV;
    int them = 0;
    boolean check = false;
    public formNhanVien() throws SQLException, IOException {
        Log log = new Log("hieupro.txt");
        this.setContentPane(main_p);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        pack();
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setLocationRelativeTo(null);
        _dtm = (DefaultTableModel) tblNhanVien.getModel();

        _dtm.setColumnIdentifiers(new String[]{
                "Mã Nhân Viên", "Mật Khẩu", "Họ Tên", "email", "Vai Trò"
        });
        tblNhanVien.setModel(_dtm);
        xoaForm();
        loadtbl();

        // mở chương trình và lưu giá trị
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                luuText();
            }
        });

        //tắt chương trình quay lại form chính
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                formChinh formChinh = null;
                try {
                    formChinh = new formChinh();
                } catch (IOException ex) {
                    log.logger.setLevel(Level.WARNING);
                    JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    log.logger.severe(sStackTrace);
                }
                formChinh.setUser(user);
                formChinh.setPass(pass);
                formChinh.setRole(role);
                dispose();
            }
        });

        // nút làm mới
        btnMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xoaForm();
            }
        });

        // nút thêm
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtMaNhanVien.getText().isBlank() || txtMaNhanVien.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mã Nhân Viên Không Được Để Trống");
                    xoaForm();
                    return;
                }
                if(check){
                    try {
                        JOptionPane.showMessageDialog(null, _list.updateNVXoa(txtMaNhanVien.getText()));
                        loadtblXoa();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
                them++;
                PanelCon.removeAll();
                PanelCon.add(panelHien);
                PanelCon.repaint();
                PanelCon.revalidate();
                if(them == 1){
                    JOptionPane.showMessageDialog(null, "Vui nhập lại mật khẩu khi thêm");
                    return;
                }
                if (loi()) {
                    try {

                        JOptionPane.showMessageDialog(null, _list.addNV(nv()), "Thông Báo", 1);
                        _dtm.addRow(new Object[]{
                                nv().getMaNhanVien(), "*****", nv().getHoTen(), nv().getEmail(), nv().getVaiTro() == 1 ? "Trưởng Phòng" : "Nhân Viên"});
                        xoaForm();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                        log.logger.setLevel(Level.WARNING);
                        JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                        log.logger.info(ex.getMessage());
                        log.logger.warning("nguy hiểm");

                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        ex.printStackTrace(pw);
                        String sStackTrace = sw.toString(); // stack trace as a string
                        log.logger.severe(sStackTrace);

                    }
                }
                if(them == 2){
                    PanelCon.removeAll();
                    PanelCon.add(panelAn);
                    PanelCon.repaint();
                    PanelCon.revalidate();
                    them = 0;
                }
            }
        });

        // nút sửa
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtMaNhanVien.getText().length() == 0 || txtName.getText().length() == 0 || String.valueOf(txtPass.getPassword()).length() == 0){
                    JOptionPane.showMessageDialog(null, "Không được để trống dữ liệu");
                    return;
                }
                    try {
                        int index = _list.updateNV(nv());
                        if (index >= 0) {
                            JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông Báo", 1);
                            _dtm.setValueAt(nv().getMaNhanVien(), index, 0);
                            _dtm.setValueAt("******", index, 1);
                            _dtm.setValueAt(nv().getHoTen(), index, 2);
                            _dtm.setValueAt(nv().getEmail(), index, 3);
                            _dtm.setValueAt(nv().getVaiTro() == 1 ? "Trưởng Phòng" : "Nhân Viên", index, 4);
                            xoaForm();
                        } else if (index == -2) {
                            JOptionPane.showMessageDialog(null, "Mã này không tồn tại", "Thông Báo", 2);
                            xoaForm();
                        } else {
                            JOptionPane.showMessageDialog(null, "Sửa Nhân Viên Không Thành Công", "Thông Báo", 2);
                            xoaForm();
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                        log.logger.setLevel(Level.WARNING);
                        JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                        log.logger.info(ex.getMessage());
                        log.logger.warning("nguy hiểm");

                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        ex.printStackTrace(pw);
                        String sStackTrace = sw.toString(); // stack trace as a string
                        log.logger.severe(sStackTrace);
                    }

            }
        });

        // nút xóa
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtMaNhanVien.getText().isBlank() || txtMaNhanVien.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mã Nhân Viên Không Được Để Trống");
                    return;
                }
                if (txtMaNhanVien.getText().equals(user)) {
                    JOptionPane.showMessageDialog(null, "Không được xóa bản thân nhé!");
                    return;
                }
                if(check){
                    try {
                        int i = JOptionPane.showConfirmDialog(null, "Nếu bạn xóa nhân viên này sẽ xóa theo tất cả các dữ liệu quan trong có liên quan đến nhân viên này. Bạn có chắc chắc?", "Cảnh Báo", JOptionPane.YES_NO_OPTION);
                        if(i == JOptionPane.YES_OPTION){
                            JOptionPane.showMessageDialog(null, _list.deleteForeve(txtMaNhanVien.getText()));
                            loadtblXoa();
                            xoaForm();
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
                try {
                    int index = _list.deleteNV(txtMaNhanVien.getText());
                    if (index >= 0) {
                        JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông Báo", 1);
                        _dtm.removeRow(index);
                        xoaForm();
                    } else if (index == -2) {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên", "Thông Báo", 2);
                        xoaForm();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa Nhân Viên Không Thành Công", "Thông Báo", 2);
                        xoaForm();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Xóa Nhân Viên Không Thành Công", "Thông Báo", 2);
                    log.logger.setLevel(Level.WARNING);
                    JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    log.logger.severe(sStackTrace);
                }

            }
        });

        // lấy một nhân viên từ tbl lên form
        tblNhanVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tblNhanVien.getSelectedRow();
                try {
                    Nhanvien nv = _list.getlist().get(i);
                    txtPass.setText(nv.getMatKhau());
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    log.logger.severe(sStackTrace);
                }
                stt = i;
                txtMaNhanVien.setText(String.valueOf(tblNhanVien.getValueAt(i, 0)));
                txtName.setText(String.valueOf(tblNhanVien.getValueAt(i, 2)));

                txtEmail.setText(String.valueOf(tblNhanVien.getValueAt(i, 3)));
                if (String.valueOf(tblNhanVien.getValueAt(i, 4)).equalsIgnoreCase("Trưởng Phòng")) {
                    rdbAdmin.setSelected(true);
                } else {
                    rdbUser.setSelected(true);
                }
            }
        });

        // nút điều hướng qua nhân viên đầu tiên của tbl
        btnFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stt = 0;
                try {
                    Nhanvien nv = _list.getlist().get(0);
                    txtPass.setText(nv.getMatKhau());
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    log.logger.severe(sStackTrace);
                }
                txtMaNhanVien.setText(String.valueOf(tblNhanVien.getValueAt(0, 0)));
                txtName.setText(String.valueOf(tblNhanVien.getValueAt(0, 2)));
                txtEmail.setText(String.valueOf(tblNhanVien.getValueAt(0, 3)));
                if (String.valueOf(tblNhanVien.getValueAt(0, 3)).equalsIgnoreCase("Trưởng Phòng")) {
                    rdbAdmin.setSelected(true);
                } else {
                    rdbUser.setSelected(true);
                }
            }
        });

        // nút điều hướng qua nhân viên cuối cùng của tbl
        btnLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stt = tblNhanVien.getRowCount() - 1;
                try {
                    Nhanvien nv = _list.getlist().get(tblNhanVien.getRowCount() - 1);
                    txtPass.setText(nv.getMatKhau());
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    log.logger.severe(sStackTrace);
                }
                txtMaNhanVien.setText(String.valueOf(tblNhanVien.getValueAt(tblNhanVien.getRowCount() - 1, 0)));
                txtName.setText(String.valueOf(tblNhanVien.getValueAt(tblNhanVien.getRowCount() - 1, 2)));
                txtEmail.setText(String.valueOf(tblNhanVien.getValueAt(tblNhanVien.getRowCount() - 1, 3)));
                if (String.valueOf(tblNhanVien.getValueAt(tblNhanVien.getRowCount() - 1, 3)).equalsIgnoreCase("Trưởng Phòng")) {
                    rdbAdmin.setSelected(true);
                } else {
                    rdbUser.setSelected(true);
                }
            }
        });

        // nút điều hướng qua Phải
        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stt == tblNhanVien.getRowCount()) {
                    stt = 0;
                }
                try {
                    Nhanvien nv = _list.getlist().get(stt);
                    txtPass.setText(nv.getMatKhau());
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    log.logger.severe(sStackTrace);
                }
                txtMaNhanVien.setText(String.valueOf(tblNhanVien.getValueAt(stt, 0)));
                txtName.setText(String.valueOf(tblNhanVien.getValueAt(stt, 2)));
                txtEmail.setText(String.valueOf(tblNhanVien.getValueAt(stt, 3)));
                if (String.valueOf(tblNhanVien.getValueAt(stt, 3)).equalsIgnoreCase("Trưởng Phòng")) {
                    rdbAdmin.setSelected(true);
                } else {
                    rdbUser.setSelected(true);
                }
                stt++;

            }
        });


        // nút điều hướng qua trái
        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stt < 0) {
                    stt = tblNhanVien.getRowCount() - 1;
                }
                try {
                    Nhanvien nv = _list.getlist().get(stt);
                    txtPass.setText(nv.getMatKhau());
                } catch (SQLException ex) {
                    log.logger.setLevel(Level.WARNING);
                    JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
                    log.logger.info(ex.getMessage());
                    log.logger.warning("nguy hiểm");

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    String sStackTrace = sw.toString(); // stack trace as a string
                    log.logger.severe(sStackTrace);
                }
                txtMaNhanVien.setText(String.valueOf(tblNhanVien.getValueAt(stt, 0)));
                txtName.setText(String.valueOf(tblNhanVien.getValueAt(stt, 2)));
                txtEmail.setText(String.valueOf(tblNhanVien.getValueAt(stt, 3)));
                if (String.valueOf(tblNhanVien.getValueAt(stt, 3)).equalsIgnoreCase("Trưởng Phòng")) {
                    rdbAdmin.setSelected(true);
                } else {
                    rdbUser.setSelected(true);
                }
                stt--;
            }
        });

        // hiện bảng nhân viên xóa
        btnDeleteNV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!check){
                        loadtblXoa();
                        check = true;
                        JOptionPane.showMessageDialog(null, "Đã hiện thị những nhân viên bị xóa");
                        btnDeleteNV.setText("Hiện Thị Lại Nhân Viên Đang Làm");
                        btnThem.setText("Thêm Lại");
                        btnFirst.setEnabled(false);
                        btnLast.setEnabled(false);
                        btnLeft.setEnabled(false);
                        btnRight.setEnabled(false);
                        btnSua.setEnabled(false);
                        txtName.setEnabled(false);
                        txtEmail.setEnabled(false);
                        txtPass.setEnabled(false);
                        return;
                    }

                    if(check){
                        loadtbl();
                        JOptionPane.showMessageDialog(null, "Đã hiện thị những nhân viên đang làm");
                        btnDeleteNV.setText("Hiện Thị Những Nhân Viên Đã Xóa");
                        check = false;
                        btnThem.setText("Thêm");
                        btnFirst.setEnabled(true);
                        btnLast.setEnabled(true);
                        btnLeft.setEnabled(true);
                        btnRight.setEnabled(true);
                        btnSua.setEnabled(true);
                        txtName.setEnabled(true);
                        txtEmail.setEnabled(true);
                        txtPass.setEnabled(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

//    public static void main(String[] args) throws SQLException, IOException {
//        new formNhanVien();
//    }

    //Phương thức đọc form lấy ra nhân viên
    private Nhanvien nv() {
        return new Nhanvien(txtMaNhanVien.getText(), String.valueOf(txtPass.getPassword()), txtName.getText(), txtEmail.getText(), (rdbAdmin.isSelected() ? 1 : 0));
    }

    // Load table
    private void loadtbl() throws SQLException {
        _dtm = (DefaultTableModel) tblNhanVien.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }
        for (Nhanvien nv : _list.getlist()
        ) {
            _dtm.addRow(new Object[]{
                    nv.getMaNhanVien(), "*******", nv.getHoTen(), nv.getEmail(), nv.getVaiTro() == 1 ? "Trưởng Phòng" : "Nhân Viên"});
        }
    }


    // Load table những nhân viên đã xóa
    private void loadtblXoa() throws SQLException {
        _dtm = (DefaultTableModel) tblNhanVien.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }
        for (Nhanvien nv : _list.getlistXoa()
        ) {
            _dtm.addRow(new Object[]{
                    nv.getMaNhanVien(), nv.getMatKhau(), nv.getHoTen(), nv.getEmail(), nv.getVaiTro() == 1 ? "Trưởng Phòng" : "Nhân Viên"});
        }
    }

    // Xóa Form
    private void xoaForm() {
        txtEmail.setText("");
        txtMaNhanVien.setText("");
        txtName.setText("");
        txtPass.setText("");
        txtConFirmPass.setText("");
        rdbAdmin.setSelected(true);
    }

    //check lỗi
    private boolean loi() {
        if (txtMaNhanVien.getText().isEmpty() || txtMaNhanVien.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Mã không được để trống", "Cảnh Báo", 2);
            txtMaNhanVien.requestFocus();
            return false;
        }

        if (!txtMaNhanVien.getText().matches("[0-9a-zA-Z]{1,}")) {
            JOptionPane.showMessageDialog(null, "Mã vui lòng không nhập kí tự đặc biệt ", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtEmail.getText().length() > 0) {
            System.out.println(txtEmail.getText());
            if (!txtEmail.getText().matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$")) {
                JOptionPane.showMessageDialog(null, "Email không đúng", "Cảnh Báo", 2);
                txtEmail.requestFocus();
                return false;
            }

        }
        if (txtName.getText().isEmpty() || txtName.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Tên không được để trống", "Cảnh Báo", 2);
            txtName.requestFocus();
            return false;
        }

        if (!txtName.getText().matches("[^0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{1,}")) {
            JOptionPane.showMessageDialog(null, "Tên vui lòng chữ cái", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtPass.getText().isEmpty() || txtPass.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Mật khẩu không được để trống", "Cảnh Báo", 2);
            txtPass.requestFocus();
            return false;
        }

        if (!txtPass.getText().matches("[0-9a-zA-Z]{1,}")) {
            JOptionPane.showMessageDialog(null, "Mật khẩu vui lòng nhập chữ cái không dấu và số", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtConFirmPass.getText().isEmpty() || txtConFirmPass.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Vui lòng xác nhận", "Cảnh Báo", 2);
            txtConFirmPass.requestFocus();
            return false;
        }
        if (!String.valueOf(txtPass.getPassword()).equalsIgnoreCase(String.valueOf(txtConFirmPass.getPassword()))) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng mật khẩu", "Cảnh Báo", 2);
            txtConFirmPass.setText("");
            txtConFirmPass.requestFocus();
            return false;
        }
        return true;
    }

    // Phương thức set để phân quyền
    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRole(int role) {
        this.role = role;
    }

    // đọc dữ liệu lên form
    private void luuText() {
        System.out.println(role);
        System.out.println(pass);
    }
}
