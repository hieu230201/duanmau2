package views;

import com.toedter.calendar.JDateChooser;
import dao.ServiceChuyenDe;
import dao.ServiceKhoaHoc;
import model.ChuyenDe;
import model.KhoaHoc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class formKhoaHoc extends JFrame {
    ServiceKhoaHoc _list = new ServiceKhoaHoc();
    ServiceChuyenDe _listcd = new ServiceChuyenDe();
    String[] macd = new String[100000];
    DefaultTableModel _dtm;
    Calendar cld = Calendar.getInstance();
    JDateChooser khaiGiang = new JDateChooser(cld.getTime());
    JDateChooser ngayTao = new JDateChooser(cld.getTime());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    int stt = 1;
    int index = 0;
    private String user, pass;
    private int role;
    private JComboBox cbcTenChuyenDe;
    private JTabbedPane tabbedPane1;
    private JTextField txtTenChuyenDe;
    private JTextField txtHocPhi;
    private JTextField txtNguoiTao;
    private JTextField txtGio;
    private JTextArea txtGhiChu;
    private JButton btnThem;
    private JButton btnMoi;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnFirst;
    private JButton btnLast;
    private JButton btnRight;
    private JButton btnLeft;
    private JTable tblKhoaHoc;
    private JPanel main_p;
    private JPanel panelDate;
    private JPanel panelDateNgayTao;

    public formKhoaHoc() throws SQLException {
        this.setContentPane(main_p);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        pack();
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setLocationRelativeTo(null);
        khaiGiang.setDateFormatString("yyyy-MM-dd");
        ngayTao.setDateFormatString("yyyy-MM-dd");
        panelDate.add(khaiGiang);
        panelDateNgayTao.add(ngayTao);
        _dtm = (DefaultTableModel) tblKhoaHoc.getModel();
        _dtm.setColumnIdentifiers(new String[]{
                "Mã Khóa Học", "Thời Lượng", "Học Phí", "Khai Giảng", "Tạo Bởi", "Ngày Tạo"
        });
        loadTBL();
        loadcbc();
        tblKhoaHoc.setDefaultEditor(Object.class, null);
        txtTenChuyenDe.setText(String.valueOf(cbcTenChuyenDe.getSelectedItem()));
        txtNguoiTao.setEnabled(false);
        // mở chương trình và lưu giá trị
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                luuText();
                txtNguoiTao.setText(user);
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
                    ex.printStackTrace();
                }
                formChinh.setUser(user);
                formChinh.setPass(pass);
                formChinh.setRole(role);
                dispose();
            }
        });

        // click cbc để hiện ra tên
        cbcTenChuyenDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtTenChuyenDe.setText(String.valueOf(cbcTenChuyenDe.getSelectedItem()));
            }
        });

        // nút xóa form
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
                if (loi()) {
                    KhoaHoc khoaHoc = new KhoaHoc();
                    khoaHoc.setMaCD(macd[cbcTenChuyenDe.getSelectedIndex()]);
                    khoaHoc.setTenCD(cbcTenChuyenDe.getSelectedItem().toString());
                    khoaHoc.setManv(txtNguoiTao.getText());
                    khoaHoc.setHocPhi(Float.parseFloat(txtHocPhi.getText()));
                    khoaHoc.setThoiLuong(Integer.parseInt(txtGio.getText()));
                    khoaHoc.setNgayTao(simpleDateFormat.format(ngayTao.getDate()));
                    khoaHoc.setGhiChu(txtGhiChu.getText());
                    khoaHoc.setNgayKG(simpleDateFormat.format(khaiGiang.getDate()));
                    try {
                        JOptionPane.showMessageDialog(null, _list.addKhoaHoc(khoaHoc, txtNguoiTao.getText()));
                        _dtm.addRow(new Object[]{
                                stt, khoaHoc.getThoiLuong(), khoaHoc.getHocPhi(), khoaHoc.getNgayKG(), khoaHoc.getManv(), khoaHoc.getNgayTao()
                        });
                        stt++;
                        xoaForm();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // click tbl để load lên form
        tblKhoaHoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tblKhoaHoc.getSelectedRow();
                index = i;
                txtGio.setText(String.valueOf(tblKhoaHoc.getValueAt(i, 1)));
                txtHocPhi.setText(String.valueOf(tblKhoaHoc.getValueAt(i, 2)));
                khaiGiang.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(i, 3)));
                txtNguoiTao.setText(String.valueOf(tblKhoaHoc.getValueAt(i, 4)));
                ngayTao.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(i, 5)));
                try {
                    KhoaHoc kh = _list.get_list().get(i);
                    txtTenChuyenDe.setText(kh.getTenCD());
                    cbcTenChuyenDe.setSelectedItem(kh.getTenCD());
                    txtGhiChu.setText(kh.getGhiChu());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // nút sửa
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loi()) {
                    int i = tblKhoaHoc.getSelectedRow();
                    try {
                        KhoaHoc kh = _list.get_list().get(i);
                        kh.setMaCD(macd[cbcTenChuyenDe.getSelectedIndex()]);
                        kh.setManv(txtNguoiTao.getText());
                        kh.setHocPhi(Float.parseFloat(txtHocPhi.getText()));
                        kh.setThoiLuong(Integer.parseInt(txtGio.getText()));
                        kh.setNgayTao(simpleDateFormat.format(ngayTao.getDate()));
                        kh.setGhiChu(txtGhiChu.getText());
                        kh.setNgayKG(simpleDateFormat.format(khaiGiang.getDate()));
                        JOptionPane.showMessageDialog(null, _list.suaKhoaHoc(txtNguoiTao.getText(), kh));

                        _dtm.setValueAt(kh.getThoiLuong(), i, 1);
                        _dtm.setValueAt(kh.getHocPhi(), i, 2);
                        _dtm.setValueAt(kh.getNgayKG(), i, 3);
                        _dtm.setValueAt(kh.getManv(), i, 4);
                        _dtm.setValueAt(kh.getNgayTao(), i, 5);
                        xoaForm();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                }
            }
        });


        // Nút Xóa
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = tblKhoaHoc.getSelectedRow();
                try {
                    KhoaHoc kh = _list.get_list().get(i);
                    JOptionPane.showMessageDialog(null, _list.xoaKhoaHoc(kh.getMakh()));
                    _dtm.removeRow(i);
                    xoaForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //nút first
        btnFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                txtGio.setText(String.valueOf(tblKhoaHoc.getValueAt(0, 1)));
                txtHocPhi.setText(String.valueOf(tblKhoaHoc.getValueAt(0, 2)));
                khaiGiang.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(0, 3)));
                txtNguoiTao.setText(String.valueOf(tblKhoaHoc.getValueAt(0, 4)));
                ngayTao.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(0, 5)));
                try {
                    KhoaHoc kh = _list.get_list().get(0);
                    txtTenChuyenDe.setText(kh.getTenCD());
                    cbcTenChuyenDe.setSelectedItem(kh.getTenCD());
                    txtGhiChu.setText(kh.getGhiChu());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //nút last
        btnLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtGio.setText(String.valueOf(tblKhoaHoc.getValueAt(tblKhoaHoc.getRowCount() - 1, 1)));
                txtHocPhi.setText(String.valueOf(tblKhoaHoc.getValueAt(tblKhoaHoc.getRowCount() - 1, 2)));
                khaiGiang.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(tblKhoaHoc.getRowCount() - 1, 3)));
                txtNguoiTao.setText(String.valueOf(tblKhoaHoc.getValueAt(tblKhoaHoc.getRowCount() - 1, 4)));
                ngayTao.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(tblKhoaHoc.getRowCount() - 1, 5)));
                try {
                    KhoaHoc kh = _list.get_list().get(tblKhoaHoc.getRowCount() - 1);
                    txtTenChuyenDe.setText(kh.getTenCD());
                    cbcTenChuyenDe.setSelectedItem(kh.getTenCD());
                    txtGhiChu.setText(kh.getGhiChu());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // nút right
        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index >= tblKhoaHoc.getSelectedRow()) {
                    index = 0;
                }
                txtGio.setText(String.valueOf(tblKhoaHoc.getValueAt(index, 1)));
                txtHocPhi.setText(String.valueOf(tblKhoaHoc.getValueAt(index, 2)));
                khaiGiang.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(index, 3)));
                txtNguoiTao.setText(String.valueOf(tblKhoaHoc.getValueAt(index, 4)));
                ngayTao.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(index, 5)));
                try {
                    KhoaHoc kh = _list.get_list().get(index);
                    txtTenChuyenDe.setText(kh.getTenCD());
                    cbcTenChuyenDe.setSelectedItem(kh.getTenCD());
                    txtGhiChu.setText(kh.getGhiChu());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                index++;
            }
        });

        //nút left
        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < 0) {
                    index = tblKhoaHoc.getRowCount() - 1;
                }
                txtGio.setText(String.valueOf(tblKhoaHoc.getValueAt(index, 1)));
                txtHocPhi.setText(String.valueOf(tblKhoaHoc.getValueAt(index, 2)));
                khaiGiang.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(index, 3)));
                txtNguoiTao.setText(String.valueOf(tblKhoaHoc.getValueAt(index, 4)));
                ngayTao.setDateFormatString(String.valueOf(tblKhoaHoc.getValueAt(index, 5)));
                try {
                    KhoaHoc kh = _list.get_list().get(index);
                    txtTenChuyenDe.setText(kh.getTenCD());
                    cbcTenChuyenDe.setSelectedItem(kh.getTenCD());
                    txtGhiChu.setText(kh.getGhiChu());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                index--;
            }
        });
    }

    public static void main(String[] args) throws SQLException, ParseException {
        new formKhoaHoc();
    }

    // Phương thức check lỗi. Ở đây Hiếu nghĩ ra vài cái lỗi xàm nên code hơi rối để ý nhé :v
    private boolean loi() {
        if (simpleDateFormat.format(ngayTao.getDate()).isEmpty() || simpleDateFormat.format(ngayTao.getDate()).isBlank()) {
            JOptionPane.showMessageDialog(null, "Ngày tạo không được để trống", "Cảnh Báo", 2);
            return false;
        }
        String n = "\\d{4}[-|/]\\d{1,2}[-|/]\\d{1,2}";
        if (!simpleDateFormat.format(ngayTao.getDate()).matches("\\d{4}[-|/]\\d{2}[-|/]\\d{2}")) {
            JOptionPane.showMessageDialog(null, " Ngày tạo vui lòng nhập YYY/MM/DD", "Lỗi", 2);
            ngayTao.setDateFormatString("");
            return false;
        }

        if (Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(0, 4)) > 2021 || Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(0, 4)) < 1990) {
            JOptionPane.showMessageDialog(null, "năm tạo chỉ từ 1990", "Lỗi", 2);
            ngayTao.setDateFormatString("");
            return false;
        }

        if (Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(5, 7)) > 12 || Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(5, 7)) < 1) {
            JOptionPane.showMessageDialog(null, "Tháng tạo không đúng", "Lỗi", 2);
            ngayTao.setDateFormatString("");
            return false;
        }

        if (Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(8, 10)) > 31 || Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(8, 10)) < 1) {
            JOptionPane.showMessageDialog(null, "Ngày  tạo không đúng", "Lỗi", 2);
            ngayTao.setDateFormatString("");
            return false;
        }

        if (simpleDateFormat.format(khaiGiang.getDate()).isEmpty() || simpleDateFormat.format(khaiGiang.getDate()).isBlank()) {
            JOptionPane.showMessageDialog(null, "Khai giảng không được để trống", "Cảnh Báo", 2);

            return false;
        }
        if (!simpleDateFormat.format(khaiGiang.getDate()).matches("\\d{4}[-|/]\\d{2}[-|/]\\d{2}")) {
            JOptionPane.showMessageDialog(null, "Khai giảng vui lòng nhập YYY/MM/DD", "Lỗi", 2);
            return false;
        }

        if (Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(0, 4)) > Integer.parseInt(simpleDateFormat.format(khaiGiang.getDate()).substring(0, 4))) {
            JOptionPane.showMessageDialog(null, "Năm khai giảng phải lớn hơn năm tạo", "Lỗi", 2);
            return false;
        }

        if (Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(5, 7)) > Integer.parseInt(simpleDateFormat.format(khaiGiang.getDate()).substring(5, 7)) && Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(0, 4)) == Integer.parseInt(simpleDateFormat.format(khaiGiang.getDate()).substring(0, 4))) {
            JOptionPane.showMessageDialog(null, "tháng khai giảng phải lớn hơn tháng tạo", "Lỗi", 2);
            return false;
        }
        if (Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(8, 10)) > Integer.parseInt(simpleDateFormat.format(khaiGiang.getDate()).substring(8, 10)) && Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(5, 7)) == Integer.parseInt(simpleDateFormat.format(khaiGiang.getDate()).substring(5, 7)) && Integer.parseInt(simpleDateFormat.format(ngayTao.getDate()).substring(0, 4)) == Integer.parseInt(simpleDateFormat.format(khaiGiang.getDate()).substring(0, 4))) {
            JOptionPane.showMessageDialog(null, "ngày khai giảng phải lớn hơn ngày tạo", "Lỗi", 2);
            return false;
        }


        // làm nốt so sánh ngày

        if (txtHocPhi.getText().isEmpty() || txtHocPhi.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Học Phí đề không được để trống", "Cảnh Báo", 2);
            txtHocPhi.requestFocus();
            return false;
        }
        if (!txtHocPhi.getText().matches("[0-9]{1,}")) {
            JOptionPane.showMessageDialog(null, "Học phí vui lòng nhập số ", "Lỗi", 2);
            txtHocPhi.setText("");
            txtHocPhi.requestFocus();
            return false;
        }
        if (txtGio.getText().isEmpty() || txtGio.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Số giờ đề không được để trống", "Cảnh Báo", 2);
            txtGio.requestFocus();
            return false;
        }

        if (!txtGio.getText().matches("[0-9]{1,}")) {
            JOptionPane.showMessageDialog(null, "Học phí vui lòng nhập số ", "Lỗi", 2);
            txtGio.setText("");
            txtGio.requestFocus();
            return false;
        }


        return true;
    }

    // xóa form
    private void xoaForm() {

        txtHocPhi.setText("");
        txtGio.setText("");
        ngayTao.setDateFormatString("");
        txtNguoiTao.setText("");
        txtGhiChu.setText("");
        khaiGiang.setDateFormatString("");
    }

    // load chuyên đề vào cbc
    private void loadcbc() throws SQLException {
        int stt = 0;
        for (ChuyenDe a : _listcd.get_list()
        ) {
            cbcTenChuyenDe.addItem(a.getTenCD());
            macd[stt] = a.getMaCD();
            stt++;
        }
    }

    //Phương thức load tbl
    private void loadTBL() throws SQLException {
        _dtm = (DefaultTableModel) tblKhoaHoc.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }

        for (KhoaHoc a : _list.get_list()
        ) {
            _dtm.addRow(new Object[]{
                    stt, a.getThoiLuong(), a.getHocPhi(), a.getNgayKG(), a.getManv(), a.getNgayTao()
            });
            stt++;
        }
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
        if (role != 1) {
            btnXoa.setEnabled(false);
        }
        System.out.println(pass);
    }
}
