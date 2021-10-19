package views;

import dao.RendererHighlighted;
import dao.ServiceNguoiHoc;
import model.NguoiHoc;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

public class formNguoiHoc extends JFrame {
    DefaultTableModel _dtm;
    ServiceNguoiHoc _list = new ServiceNguoiHoc();
    int stt = 0;
    private String user, pass;
    private int role;
    private JPanel main_p;
    private JTabbedPane tabbedPane1;
    private JTextField txtMaNguoiHoc;
    private JTextField txtHoTen;
    private JRadioButton rdbNam;
    private JRadioButton rdnNu;
    private JTextField txtDate;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private JTextArea txtGhiChu;
    private JButton btnSua;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnMoi;
    private JLabel lblTitle;
    private JButton btnFirst;
    private JButton btnLeft;
    private JButton btnRight;
    private JButton btnLast;
    private JTable tblNguoiHoc;
    private JTextField txtTimKiem;
    private JCheckBox ckcLoad;
    private JPanel PanelCon;
    private JPanel PanelAn;
    private JPanel PanelHIen;
    private JButton btnLui;
    private JButton btnTrangDau;
    private JButton btnTien;
    private JButton btnTrangCuoi;
    private JLabel lblSoTrang;
    int soTrang , trang = 1;
    public formNguoiHoc() throws SQLException {
        this.setContentPane(main_p);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(900, 400);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setLocationRelativeTo(null);
        RendererHighlighted renderer = new RendererHighlighted(txtTimKiem);
        _dtm = (DefaultTableModel) tblNguoiHoc.getModel();
        _dtm.setColumnIdentifiers(new String[]{
                "Mã Người Học", "Họ Và Tên", "Ngày Sinh", "Giới Tính", "Điện Thoại", "Email", "Ghi Chú", "Mã Nhân Viên", "Ngày Đăng Kí"
        });
        if(_list.count() % 10 == 0){
            soTrang = _list.count() / 10;
        }else {
            soTrang = _list.count() / 10 + 1;
        }
        lblSoTrang.setText("1/"+soTrang);
        tblNguoiHoc.setModel(_dtm);
        xoaForm();
        loadtbl();
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tblNguoiHoc.getModel());

        tblNguoiHoc.setRowSorter(rowSorter);
        tblNguoiHoc.setDefaultRenderer(Object.class, renderer);

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
                    ex.printStackTrace();
                }
                formChinh.setUser(user);
                formChinh.setPass(pass);
                formChinh.setRole(role);
                dispose();
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
                    try {

                        JOptionPane.showMessageDialog(null, _list.addNguoiHoc(nguoiHoc()), "Thông Báo", 1);
                        _dtm.addRow(new Object[]{
                                nguoiHoc().getMaNH(), nguoiHoc().getHoTen(), nguoiHoc().getNgaySinh(), nguoiHoc().getGioiTinh() == 1 ? "Nam" : "Nữ", nguoiHoc().getDienThoai(), nguoiHoc().getEmail(), nguoiHoc().getGhiChu(), nguoiHoc().getMaNhanVien(), nguoiHoc().getNgayDangKi()
                        });
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    xoaForm();
                }
            }
        });

        // click để lấy load người học lên form
        tblNguoiHoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tblNguoiHoc.getSelectedRow();
                stt = i;
                txtMaNguoiHoc.setText(String.valueOf(tblNguoiHoc.getValueAt(i, 0)));
                txtHoTen.setText(String.valueOf(tblNguoiHoc.getValueAt(i, 1)));
                txtDate.setText(String.valueOf(tblNguoiHoc.getValueAt(i, 2)));
                txtSDT.setText(String.valueOf(tblNguoiHoc.getValueAt(i, 4)));
                txtEmail.setText(String.valueOf(tblNguoiHoc.getValueAt(i, 5)));

                if (String.valueOf(tblNguoiHoc.getValueAt(i, 3)).equals("Nam")) {
                    rdbNam.setSelected(true);
                } else {
                    rdnNu.setSelected(true);
                }
                if (String.valueOf(tblNguoiHoc.getValueAt(i, 6)).equals("null")) {
                    txtGhiChu.setText("");
                } else {
                    txtGhiChu.setText(String.valueOf(tblNguoiHoc.getValueAt(i, 6)));
                }


            }
        });

        // nút sửa
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loi()) {
                    try {
                        int row = _list.suaNguoiHoc(nguoiHoc());
                        if (row >= 0) {
                            JOptionPane.showMessageDialog(null, "Sửa Thành Công", "Thông Báo", 1);
                            _dtm.setValueAt(nguoiHoc().getHoTen(), row, 1);
                            //"Mã Người Học", "Họ Và Tên", "Ngày Sinh", "Giới Tính", "Điện Thoại", "Email", "Ghi Chú", "Mã Nhân Viên", "Ngày Đăng Kí"
                            _dtm.setValueAt(nguoiHoc().getNgaySinh(), row, 2);
                            _dtm.setValueAt(nguoiHoc().getGioiTinh() == 1 ? "Nam" : "Nữ", row, 3);
                            _dtm.setValueAt(nguoiHoc().getDienThoai(), row, 4);
                            _dtm.setValueAt(nguoiHoc().getEmail(), row, 5);
                            _dtm.setValueAt(nguoiHoc().getGhiChu(), row, 6);
                            _dtm.setValueAt(user, row, 7);
                            _dtm.setValueAt(String.valueOf(java.time.LocalDate.now()), row, 8);
                            xoaForm();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // nút xóa
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loi()) {

                    try {
                        int row = _list.xoaNguoiHoc(nguoiHoc());
                        if (row >= 0) {
                            JOptionPane.showMessageDialog(null, "Xóa Người Học Thành Công", "Thông Báo", 1);
                            _dtm.removeRow(row);
                            xoaForm();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // nút hiển thị thằng cuối ở bảng
        btnLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtMaNguoiHoc.setText(String.valueOf(tblNguoiHoc.getValueAt(tblNguoiHoc.getRowCount() - 1, 0)));
                txtHoTen.setText(String.valueOf(tblNguoiHoc.getValueAt(tblNguoiHoc.getRowCount() - 1, 1)));
                txtDate.setText(String.valueOf(tblNguoiHoc.getValueAt(tblNguoiHoc.getRowCount() - 1, 2)));
                txtSDT.setText(String.valueOf(tblNguoiHoc.getValueAt(tblNguoiHoc.getRowCount() - 1, 4)));
                txtEmail.setText(String.valueOf(tblNguoiHoc.getValueAt(tblNguoiHoc.getRowCount() - 1, 5)));

                if (String.valueOf(tblNguoiHoc.getValueAt(tblNguoiHoc.getRowCount() - 1, 3)).equals("Nam")) {
                    rdbNam.setSelected(true);
                } else {
                    rdnNu.setSelected(true);
                }
                if (String.valueOf(tblNguoiHoc.getValueAt(tblNguoiHoc.getRowCount() - 1, 6)).equals("null")) {
                    txtGhiChu.setText("");
                } else {
                    txtGhiChu.setText(String.valueOf(tblNguoiHoc.getValueAt(tblNguoiHoc.getRowCount() - 1, 6)));
                }

            }
        });

        // nút hiển thị thằng đầu tiên ở bảng
        btnFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtMaNguoiHoc.setText(String.valueOf(tblNguoiHoc.getValueAt(0, 0)));
                txtHoTen.setText(String.valueOf(tblNguoiHoc.getValueAt(0, 1)));
                txtDate.setText(String.valueOf(tblNguoiHoc.getValueAt(0, 2)));
                txtSDT.setText(String.valueOf(tblNguoiHoc.getValueAt(0, 4)));
                txtEmail.setText(String.valueOf(tblNguoiHoc.getValueAt(0, 5)));

                if (String.valueOf(tblNguoiHoc.getValueAt(0, 3)).equals("Nam")) {
                    rdbNam.setSelected(true);
                } else {
                    rdnNu.setSelected(true);
                }
                if (String.valueOf(tblNguoiHoc.getValueAt(0, 6)).equals("null")) {
                    txtGhiChu.setText("");
                } else {
                    txtGhiChu.setText(String.valueOf(tblNguoiHoc.getValueAt(0, 6)));
                }

            }
        });

        // nút next phải
        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stt < 0) {
                    stt = tblNguoiHoc.getRowCount() - 1;
                }
                txtMaNguoiHoc.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 0)));
                txtHoTen.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 1)));
                txtDate.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 2)));
                txtSDT.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 4)));
                txtEmail.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 5)));

                if (String.valueOf(tblNguoiHoc.getValueAt(stt, 3)).equals("Nam")) {
                    rdbNam.setSelected(true);
                } else {
                    rdnNu.setSelected(true);
                }
                if (String.valueOf(tblNguoiHoc.getValueAt(stt, 6)).equals("null")) {
                    txtGhiChu.setText("");
                } else {
                    txtGhiChu.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 6)));
                }
                stt--;
            }
        });

        // nút next trái
        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stt == tblNguoiHoc.getRowCount()) {
                    stt = 0;
                }
                txtMaNguoiHoc.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 0)));
                txtHoTen.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 1)));
                txtDate.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 2)));
                txtSDT.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 4)));
                txtEmail.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 5)));

                if (String.valueOf(tblNguoiHoc.getValueAt(stt, 3)).equals("Nam")) {
                    rdbNam.setSelected(true);
                } else {
                    rdnNu.setSelected(true);
                }
                if (String.valueOf(tblNguoiHoc.getValueAt(stt, 6)).equals("null")) {
                    txtGhiChu.setText("");
                } else {
                    txtGhiChu.setText(String.valueOf(tblNguoiHoc.getValueAt(stt, 6)));
                }
                stt++;
            }
        });


//         nút tìm kiếm
//        btnTimKiem.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (_list.getIndex(txtTimKiem.getText()) < 0) {
//                    JOptionPane.showMessageDialog(null, "Không có người học này");
//                    return;
//                }
//                txtMaNguoiHoc.setText(String.valueOf(tblNguoiHoc.getValueAt(_list.getIndex(txtTimKiem.getText()), 0)));
//                txtHoTen.setText(String.valueOf(tblNguoiHoc.getValueAt(_list.getIndex(txtTimKiem.getText()), 1)));
//                txtDate.setText(String.valueOf(tblNguoiHoc.getValueAt(_list.getIndex(txtTimKiem.getText()), 2)));
//                txtSDT.setText(String.valueOf(tblNguoiHoc.getValueAt(_list.getIndex(txtTimKiem.getText()), 4)));
//                txtEmail.setText(String.valueOf(tblNguoiHoc.getValueAt(_list.getIndex(txtTimKiem.getText()), 5)));
//
//                if (String.valueOf(tblNguoiHoc.getValueAt(_list.getIndex(txtTimKiem.getText()), 3)).equals("Nam")) {
//                    rdbNam.setSelected(true);
//                } else {
//                    rdnNu.setSelected(true);
//                }
//                if (String.valueOf(tblNguoiHoc.getValueAt(_list.getIndex(txtTimKiem.getText()), 6)).equals("null")) {
//                    txtGhiChu.setText("");
//                } else {
//                    txtGhiChu.setText(String.valueOf(tblNguoiHoc.getValueAt(_list.getIndex(txtTimKiem.getText()), 6)));
//                }
//
//
//            }
//        });

        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txtTimKiem.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtTimKiem.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        ckcLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ckcLoad.isSelected()){
                    try {
                        PanelCon.removeAll();
                        PanelCon.add(PanelHIen);
                        PanelCon.repaint();
                        PanelCon.revalidate();
                        loadtblPhanTrang();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }else {
                    try {
                        PanelCon.removeAll();
                        PanelCon.add(PanelAn);
                        PanelCon.repaint();
                        PanelCon.revalidate();
                        loadtbl();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        btnTrangDau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trang = 1;
                lblSoTrang.setText(trang+"/"+soTrang);
                try {
                    loadtblPhanTrang();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnTrangCuoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trang = soTrang;
                lblSoTrang.setText(trang+"/"+soTrang);
                try {
                    loadtblPhanTrang();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnLui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trang--;
                if(trang < 1){
                    trang = soTrang;
                }
                lblSoTrang.setText(trang+"/"+soTrang);
                try {
                    loadtblPhanTrang();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnTien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trang++;
                if(trang > soTrang){
                    trang = 1;
                }
                lblSoTrang.setText(trang+"/"+soTrang);
                try {
                    loadtblPhanTrang();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    // phương thức check lỗi
    private boolean loi() {
        if (txtMaNguoiHoc.getText().isEmpty() || txtMaNguoiHoc.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Mã không được để trống", "Cảnh Báo", 2);
            txtMaNguoiHoc.requestFocus();
            return false;
        }

        if (!txtMaNguoiHoc.getText().matches("[0-9a-zA-Z]{1,}")) {
            JOptionPane.showMessageDialog(null, "Mã vui lòng không nhập kí tự đặc biệt ", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtHoTen.getText().isEmpty() || txtHoTen.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Tên không được để trống", "Cảnh Báo", 2);
            txtHoTen.requestFocus();
            return false;
        }

        if (!txtHoTen.getText().matches("[^0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{1,}")) {
            JOptionPane.showMessageDialog(null, "Tên vui lòng chữ cái", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (txtDate.getText().isEmpty() || txtDate.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Khai giảng không được để trống", "Cảnh Báo", 2);
            txtDate.requestFocus();
            return false;
        }
        if (!txtDate.getText().matches("\\d{4}[-|/]\\d{2}[-|/]\\d{2}")) {
            JOptionPane.showMessageDialog(null, "Ngày Sinh vui lòng nhập YYY/MM/DD", "Lỗi", 2);
            txtDate.setText("");
            txtDate.requestFocus();
            return false;
        }

        if (Integer.parseInt(txtDate.getText().substring(0, 4)) > 2015 || Integer.parseInt(txtDate.getText().substring(0, 4)) < 1960) {
            JOptionPane.showMessageDialog(null, "Năm sinh chỉ từ 1960 đến 2015", "Lỗi", 2);
            txtDate.setText("");
            txtDate.requestFocus();
            return false;
        }

        if (Integer.parseInt(txtDate.getText().substring(5, 7)) > 12 || Integer.parseInt(txtDate.getText().substring(5, 7)) < 1) {
            JOptionPane.showMessageDialog(null, "Tháng sinh không đúng", "Lỗi", 2);
            txtDate.setText("");
            txtDate.requestFocus();
            return false;
        }

        if (Integer.parseInt(txtDate.getText().substring(8, 10)) > 31 || Integer.parseInt(txtDate.getText().substring(8, 10)) < 0) {
            JOptionPane.showMessageDialog(null, "Ngày  sinh không đúng", "Lỗi", 2);
            txtDate.setText("");
            txtDate.requestFocus();
            return false;
        }


        if (txtSDT.getText().isEmpty() || txtSDT.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Sđt không được để trống", "Cảnh Báo", 2);
            txtSDT.requestFocus();
            return false;
        }

        if (!txtSDT.getText().matches("0[0-9]{10}")) {
            JOptionPane.showMessageDialog(null, "Bạn đã nhập sai sđt", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (txtEmail.getText().isEmpty() || txtEmail.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Email không được để trống", "Cảnh Báo", 2);
            txtEmail.requestFocus();
            return false;
        }
        if (!txtEmail.getText().matches("^[a-zA-Z][\\w-]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$")) {
            JOptionPane.showMessageDialog(null, "Email sai định dạng", "Cảnh Báo", 2);
            txtEmail.requestFocus();
            return false;
        }
        return true;
    }

    //khởi tạo người học
    private NguoiHoc nguoiHoc() {
        int gender = 1;
        if (rdnNu.isSelected()) {
            gender = 0;
        }
        return new NguoiHoc(user, txtMaNguoiHoc.getText(), txtHoTen.getText(), txtDate.getText(), gender, txtSDT.getText(), txtEmail.getText(), txtGhiChu.getText(), String.valueOf(java.time.LocalDate.now()));
    }


    // phương thức xóa form
    private void xoaForm() {
        txtDate.setText("");
        txtEmail.setText("");
        txtMaNguoiHoc.setText("");
        txtGhiChu.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        rdbNam.setSelected(true);
    }


    // phương thức load tbl
    private void loadtbl() throws SQLException {
        _dtm = (DefaultTableModel) tblNguoiHoc.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }
        for (NguoiHoc a : _list.get_list()
        ) {
            _dtm.addRow(new Object[]{
                    a.getMaNH(), a.getHoTen(), a.getNgaySinh(), a.getGioiTinh() == 1 ? "Nam" : "Nữ", a.getDienThoai(), a.getEmail(), a.getGhiChu(), a.getMaNhanVien(), a.getNgayDangKi()
            });
        }

    }



    // phương thức load tbl theo kiểu trang
    private void loadtblPhanTrang() throws SQLException {
        _dtm = (DefaultTableModel) tblNguoiHoc.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }

        for (NguoiHoc a : _list.loadData(trang)
        ) {
            _dtm.addRow(new Object[]{
                    a.getMaNH(), a.getHoTen(), a.getNgaySinh(), a.getGioiTinh() == 1 ? "Nam" : "Nữ", a.getDienThoai(), a.getEmail(), a.getGhiChu(), a.getMaNhanVien(), a.getNgayDangKi()
            });
        }
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
        System.out.println(role);
        if (role != 1) {
            btnXoa.setEnabled(false);
        }
        System.out.println(pass);
    }

//    public static void main(String[] args) throws SQLException {
//        new formNguoiHoc();
//    }
}
