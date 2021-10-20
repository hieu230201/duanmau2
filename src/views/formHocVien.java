package views;

import dao.*;
import model.ChuyenDe;
import model.HocVien;
import model.KhoaHoc;
import model.NguoiHoc;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;


public class formHocVien extends JFrame {
    ServiceNguoiHoc _listNguoiHoc = new ServiceNguoiHoc();
    ServiceChuyenDe _listChuyenDe = new ServiceChuyenDe();
    ServiceKhoaHoc _listKhoaHoc = new ServiceKhoaHoc();
    ServiceHocVien _listHocVien = new ServiceHocVien();
    DefaultTableModel _dtm;
    DefaultTableModel dtm;
    int maKH = -1;
    private String user, pass;
    private int role;
    private JPanel main_p;
    private JComboBox cbcChuyenDe;
    private JComboBox cbcKhoaHoc;
    private JTabbedPane tabbedPane1;
    private JTextField txtTimKiem;
    private JTable tblNguoiHoc;
    private JButton btnAddKhoaHoc;
    private JTable tblHocVien;
    private JButton btmDelete;
    private JButton btnUpdate;

    public formHocVien() throws SQLException {
        this.setContentPane(main_p);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        pack();
        this.setSize(600, 500);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setLocationRelativeTo(null);
        _dtm = (DefaultTableModel) tblNguoiHoc.getModel();
        dtm = (DefaultTableModel) tblHocVien.getModel();
        RendererHighlighted renderer = new RendererHighlighted(txtTimKiem);
        dtm.setColumnIdentifiers(new String[]{
                "STT", "Mã Học Viên", "MÃ Người Học", "Họ Tên", "Điểm"
        });
        tblHocVien.setModel(dtm);
        _dtm.setColumnIdentifiers(new String[]{
                "Mã Người Học", "Họ Và Tên", "Ngày Sinh", "Giới Tính", "Điện Thoại", "Email"
        });

        tblNguoiHoc.setModel(_dtm);
        loadCbcChuyenDe();
        loadtblNguoiHoc();
        loadCbcKhoaHoc();
        loadTblHocVien();
        tblHocVien.setDefaultEditor(Object.class, null);
        tblNguoiHoc.setDefaultRenderer(Object.class, renderer);
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tblNguoiHoc.getModel());
        tblNguoiHoc.setRowSorter(rowSorter);

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

        // nút khóa học
        cbcKhoaHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maCD = String.valueOf(cbcKhoaHoc.getSelectedItem()).substring(0, String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf('(')).trim();
                String date = String.valueOf(cbcKhoaHoc.getSelectedItem()).substring(String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf('(') + 1, String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf(')')).trim();
                System.out.println(maCD + " " + date);
                try {
                    while (dtm.getRowCount() > 0) {
                        dtm.setRowCount(0);
                    }
                    loadTblHocVien();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // nút chuyên để để chọn ra khóa học bên cbc Khóa học
        cbcChuyenDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadCbcKhoaHoc();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // xóa học viên
        btmDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = tblHocVien.getSelectedRow();
                try {
                    JOptionPane.showMessageDialog(null, _listHocVien.xoaHocVien(Integer.parseInt(String.valueOf(tblHocVien.getValueAt(i, 1)))));
                    dtm.removeRow(i);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // sửa điểm cho học viên
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = tblHocVien.getSelectedRow();

                    String input = "a";
                    while (!input.matches("[0-9]{1}")){
                        input =  JOptionPane.showInputDialog("mời bạn nhập điểm");
                        if(!input.matches("[0-9]{1}")){
                            JOptionPane.showMessageDialog(null, "Điểm phải nhập số");
                        }

                    }

                try {
                    JOptionPane.showMessageDialog(null, _listHocVien.updateDiemHocVien(Integer.parseInt(String.valueOf(tblHocVien.getValueAt(i, 1))), Integer.parseInt(input)));
                    dtm.setValueAt(Integer.parseInt(input), i , 4);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // thêm người học vào khóa học
        btnAddKhoaHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maCD = String.valueOf(cbcKhoaHoc.getSelectedItem()).substring(0, String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf('(')).trim();
                String date = String.valueOf(cbcKhoaHoc.getSelectedItem()).substring(String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf('(') + 1, String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf(')')).trim();
                int dem = 0;
                for (int row : tblNguoiHoc.getSelectedRows()) {
                    try {
                        if (!_listHocVien.checkHocVien(maKH, String.valueOf(tblNguoiHoc.getValueAt(row, 0)))) {
                            _listHocVien.addHocVien(_listHocVien.getMakh(maCD, date), 0, String.valueOf(tblNguoiHoc.getValueAt(row, 0)));
                            dem++;
                        } else {
                            JOptionPane.showMessageDialog(null, "Học Viên " + String.valueOf(tblNguoiHoc.getValueAt(row, 1)) + " đã tồn tại", "Lỗi", 2);
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(null, "Thêm thành công " + dem + " người học");
                try {
                    loadTblHocVien();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
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

            }

        });

    }

    public static void main(String[] args) throws SQLException {
        new formHocVien();
    }

    // load tbl chuyên đề
    private void loadCbcChuyenDe() throws SQLException {
        for (ChuyenDe a : _listChuyenDe.get_list()
        ) {
            cbcChuyenDe.addItem(a.getTenCD());
        }
    }

    // load cbc Khóa Học
    private void loadCbcKhoaHoc() throws SQLException {

        String name = String.valueOf(cbcChuyenDe.getSelectedItem());
        try {
            cbcKhoaHoc.removeAllItems();


            for (KhoaHoc a : _listKhoaHoc.get_listCbc(_listChuyenDe.getMotThang(name).getMaCD())
            ) {
                cbcKhoaHoc.addItem(a.getMaCD() + "( " + a.getNgayKG() + " )");
            }


            if (cbcKhoaHoc.getSelectedIndex() == -1) {
                cbcKhoaHoc.addItem("chưa có khóa học");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Phương thức load tbl học viên
    private void loadTblHocVien() throws SQLException {
        if (String.valueOf(cbcKhoaHoc.getSelectedItem()).length() == 0) {
            return;
        }
        String maCD = String.valueOf(cbcKhoaHoc.getSelectedItem()).substring(0, String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf('(')).trim();
        String date = String.valueOf(cbcKhoaHoc.getSelectedItem()).substring(String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf('(') + 1, String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf(')')).trim();
        dtm = (DefaultTableModel) tblHocVien.getModel();
        while (dtm.getRowCount() > 0) {
            dtm.setRowCount(0);
        }
        int stt = 1;
        for (HocVien a : _listHocVien.getListHocVien(maCD, date)
        ) {
            maKH = a.getMaKH();
            dtm.addRow(new Object[]{
                    stt, a.getId(), a.getMaNH(), a.getHoTen(), a.getDiem()
            });
            stt++;
        }
    }

    // phương thức load tbl Người học
    private void loadtblNguoiHoc() throws SQLException {
        _dtm = (DefaultTableModel) tblNguoiHoc.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }
        for (NguoiHoc a : _listNguoiHoc.get_list()
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
            btmDelete.setEnabled(false);
        }
        System.out.println(pass);
    }


}
