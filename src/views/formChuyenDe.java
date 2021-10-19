package views;

import dao.RendererHighlighted;
import dao.ServiceChuyenDe;
import model.ChuyenDe;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

public class formChuyenDe extends JFrame {
    ServiceChuyenDe _list = new ServiceChuyenDe();
    DefaultTableModel _dtm;
    int stt = 0;
    String pic = "";
    private String user, pass;
    private int role;
    private JPanel main_p;
    private JLabel lblTitle;
    private JTabbedPane tabbedPane1;
    private JPanel panel_pic;
    private JTextField txtMaChuyenDe;
    private JTextField txtTenChuyenDe;
    private JTextField txtGio;
    private JTextField txtHocPhi;
    private JTextArea txtMoTa;
    private JButton btnThem;
    private JButton btnMoi;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnFirst;
    private JButton btnRight;
    private JButton btnLast;
    private JButton btnLeft;
    private JTable tblChuyenDe;
    private JLabel lblPic;
    private JButton btnLoadAnh;
    private JTextField txtTimKiem;
    private JButton btnLoadTBlXoa;
    boolean check = false;
    public formChuyenDe() throws SQLException {
        this.setContentPane(main_p);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        pack();
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setLocationRelativeTo(null);
        RendererHighlighted renderer = new RendererHighlighted(txtTimKiem);
        _dtm = (DefaultTableModel) tblChuyenDe.getModel();
        _dtm.setColumnIdentifiers(new String[]{
                "Mã Chuyên Đề", "Tên Chuyên Đề", "Học Phí", "Thời Lượng", "Hình", "Mô Tả"
        });

        tblChuyenDe.setModel(_dtm);
        loadTBL();
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tblChuyenDe.getModel());

       tblChuyenDe.setRowSorter(rowSorter);
        tblChuyenDe.setDefaultRenderer(Object.class, renderer);
        // mở chương trình và lưu giá trị
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                luuText();
            }
        });

        // tắt chương trình quay lại form chính
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


        // nút thêm
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtMaChuyenDe.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "Mã Chuyên Đề Không Được Để Trống");
                    return;
                }

                if(check){
                    try {
                        JOptionPane.showMessageDialog(null, _list.addLai(txtMaChuyenDe.getText()));
                        loadTBLXoa();
                        return;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                if (loi()) {
                    try {
                        JOptionPane.showMessageDialog(null, _list.addCD(cd()), "Thông Báo", 1);
                        _dtm.addRow(new Object[]{
                                cd().getMaCD(), cd().getTenCD(), cd().getHocPhi(), cd().getThoiLuong(), cd().getHinh(), cd().getMoTa()
                        });
                        xoaForm();
                        pic = "";
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // nút load ảnh
        btnLoadAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblPic.setIcon(image());
            }
        });

        // nút click tbl lên form
        tblChuyenDe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tblChuyenDe.getSelectedRow();
                if(check){
                    txtMaChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(i, 0)));
                    return;
                }
                stt = i;
                txtMaChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(i, 0)));
                txtTenChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(i, 1)));
                txtHocPhi.setText(String.valueOf(tblChuyenDe.getValueAt(i, 2)));
                txtGio.setText(String.valueOf(tblChuyenDe.getValueAt(i, 3)));
                try {
                    ChuyenDe sv = _list.get_list().get(i);
                    ImageIcon imageIcon = new ImageIcon(sv.getHinh());
                    Image image = imageIcon.getImage();
                    Image image1 = image.getScaledInstance(80, 80, 1000);
                    imageIcon = new ImageIcon(image1);
                    lblPic.setIcon(imageIcon);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                txtMoTa.setText(String.valueOf(tblChuyenDe.getValueAt(i, 5)));
            }
        });

        // nút sửa
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loi()) {
                    try {
                        int i = _list.updateCD(cd());
                        if (i >= 0) {
                            JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông Báo", 1);
                            _dtm.setValueAt(cd().getMaCD(), i, 0);
                            _dtm.setValueAt(cd().getTenCD(), i, 1);
                            _dtm.setValueAt(cd().getHocPhi(), i, 2);
                            _dtm.setValueAt(cd().getThoiLuong(), i, 3);
                            _dtm.setValueAt(cd().getHinh(), i, 4);
                            _dtm.setValueAt(cd().getMoTa(), i, 5);
                            xoaForm();
                            pic = "";
                        } else if (i == -2) {
                            JOptionPane.showMessageDialog(null, "Mã này không tồn tại", "Thông Báo", 2);
                            xoaForm();
                            pic = "";
                        } else {
                            JOptionPane.showMessageDialog(null, "Sửa thất bại", "Thông Báo", 2);
                            xoaForm();
                            pic = "";
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
                if(txtMaChuyenDe.getText().length() == 0){
                    JOptionPane.showMessageDialog(null, "Mã Chuyên Đề Không Được Để Trống");
                    return;
                }

                if(check){
                    try {
                        int i = JOptionPane.showConfirmDialog(null, "Nếu bạn xóa chuyên đề này sẽ xóa theo tất cả các dữ liệu quan trong có liên quan đến chuyên đề này. Bạn có chắc chắc?", "Cảnh Báo", JOptionPane.YES_NO_OPTION);
                        if(i == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, _list.xoaVinhVienMotChuyenDe(txtMaChuyenDe.getText()));
                            loadTBLXoa();
                            xoaForm();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
                if (loi()) {
                    try {
                        int i = _list.deleteCD(cd());
                        if (i >= 0) {
                            JOptionPane.showMessageDialog(null, "Xóa thành công", "Thông Báo", 1);
                            _dtm.removeRow(i);
                            xoaForm();
                            pic = "";
                        } else if (i == -2) {
                            JOptionPane.showMessageDialog(null, "Mã này không tồn tại", "Thông Báo", 2);
                            xoaForm();
                            pic = "";
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa thất bại", "Thông Báo", 2);
                            xoaForm();
                            pic = "";
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // xóa form
        btnMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xoaForm();
            }
        });

        //nút hiện thằng đầu lên form
        btnFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtMaChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(0, 0)));
                txtTenChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(0, 1)));
                txtHocPhi.setText(String.valueOf(tblChuyenDe.getValueAt(0, 2)));
                txtGio.setText(String.valueOf(tblChuyenDe.getValueAt(0, 3)));
                try {
                    ChuyenDe sv = _list.get_list().get(0);
                    ImageIcon imageIcon = new ImageIcon(sv.getHinh());
                    Image image = imageIcon.getImage();
                    Image image1 = image.getScaledInstance(80, 80, 1000);
                    imageIcon = new ImageIcon(image1);
                    lblPic.setIcon(imageIcon);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                txtMoTa.setText(String.valueOf(tblChuyenDe.getValueAt(0, 5)));
            }
        });

        //nút hiện thằng cuối lên form
        btnLast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtMaChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(tblChuyenDe.getRowCount() - 1, tblChuyenDe.getRowCount() - 1)));
                txtTenChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(tblChuyenDe.getRowCount() - 1, 1)));
                txtHocPhi.setText(String.valueOf(tblChuyenDe.getValueAt(tblChuyenDe.getRowCount() - 1, 2)));
                txtGio.setText(String.valueOf(tblChuyenDe.getValueAt(tblChuyenDe.getRowCount() - 1, 3)));
                try {
                    ChuyenDe sv = _list.get_list().get(tblChuyenDe.getRowCount() - 1);
                    ImageIcon imageIcon = new ImageIcon(sv.getHinh());
                    Image image = imageIcon.getImage();
                    Image image1 = image.getScaledInstance(80, 80, 1000);
                    imageIcon = new ImageIcon(image1);
                    lblPic.setIcon(imageIcon);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                txtMoTa.setText(String.valueOf(tblChuyenDe.getValueAt(tblChuyenDe.getRowCount() - 1, 5)));
            }
        });

        // nút sang phải
        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stt == tblChuyenDe.getRowCount()) {
                    stt = 0;
                }
                txtMaChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 0)));
                txtTenChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 1)));
                txtHocPhi.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 2)));
                txtGio.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 3)));
                try {
                    ChuyenDe sv = _list.get_list().get(stt);
                    ImageIcon imageIcon = new ImageIcon(sv.getHinh());
                    Image image = imageIcon.getImage();
                    Image image1 = image.getScaledInstance(80, 80, 1000);
                    imageIcon = new ImageIcon(image1);
                    lblPic.setIcon(imageIcon);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                txtMoTa.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 5)));
                stt++;
            }
        });


        // nút trái
        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stt < 0) {
                    stt = tblChuyenDe.getRowCount() - 1;
                }
                txtMaChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 0)));
                txtTenChuyenDe.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 1)));
                txtHocPhi.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 2)));
                txtGio.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 3)));
                try {
                    ChuyenDe sv = _list.get_list().get(stt);
                    ImageIcon imageIcon = new ImageIcon(sv.getHinh());
                    Image image = imageIcon.getImage();
                    Image image1 = image.getScaledInstance(80, 80, 1000);
                    imageIcon = new ImageIcon(image1);
                    lblPic.setIcon(imageIcon);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                txtMoTa.setText(String.valueOf(tblChuyenDe.getValueAt(stt, 5)));
                stt--;
            }
        });

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



        btnLoadTBlXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!check){
                        loadTBLXoa();
                        check = true;
                        JOptionPane.showMessageDialog(null, "Đã hiện thị những chuyên đề bị xóa");
                        btnLoadTBlXoa.setText("Hiện Thị Lại Chuyên Đề Hiện Tại");
                        btnThem.setText("Thêm Lại");
                        btnFirst.setEnabled(false);
                        btnLast.setEnabled(false);
                        btnLeft.setEnabled(false);
                        btnRight.setEnabled(false);
                        btnSua.setEnabled(false);
                        txtTenChuyenDe.setEnabled(false);
                       txtGio.setEnabled(false);
                       txtHocPhi.setEnabled(false);
                       txtMoTa.setEnabled(false);
                       btnLoadAnh.setEnabled(false);
                        return;
                    }

                    if(check){
                        loadTBL();
                        JOptionPane.showMessageDialog(null, "Đã hiện thị những chuyên đề hiện tại");
                        btnLoadTBlXoa.setText("Hiện Thị Những Chuyên đề Đã Xóa");
                        check = false;
                        btnThem.setText("Thêm");
                        btnFirst.setEnabled(true);
                        btnLast.setEnabled(true);
                        btnLeft.setEnabled(true);
                        btnRight.setEnabled(true);
                        btnSua.setEnabled(true);
                        txtGio.setEnabled(true);
                        txtHocPhi.setEnabled(true);
                        txtMoTa.setEnabled(true);
                        btnLoadAnh.setEnabled(true);
                        txtTenChuyenDe.setEnabled(true);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

//    public static void main(String[] args) throws SQLException {
//        new formChuyenDe();
//    }

    // Phương thức load chuyên đề từ list lên form
    private void loadTBL() throws SQLException {
        _dtm = (DefaultTableModel) tblChuyenDe.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }
        for (ChuyenDe a : _list.get_list()
        ) {
            _dtm.addRow(new Object[]{
                    a.getMaCD(), a.getTenCD(), String.valueOf(a.getHocPhi()).substring(0, String.valueOf(a.getHocPhi()).length() - 2), a.getThoiLuong(), a.getHinh(), a.getMoTa()
            });
        }
    }


    // Phương thức load chuyên đề từ list lên form
    private void loadTBLXoa() throws SQLException {
        _dtm = (DefaultTableModel) tblChuyenDe.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }
        for (ChuyenDe a : _list.get_listXoa()
        ) {
            _dtm.addRow(new Object[]{
                    a.getMaCD(), a.getTenCD(), String.valueOf(a.getHocPhi()).substring(0, String.valueOf(a.getHocPhi()).length() - 2), a.getThoiLuong(), a.getHinh(), a.getMoTa()
            });
        }
    }

    // khởi tạo đối tượng từ form
    private ChuyenDe cd() {
        System.out.println(String.valueOf(lblPic.getIcon()));
        return new ChuyenDe(txtMaChuyenDe.getText(), txtTenChuyenDe.getText(), Float.parseFloat(txtHocPhi.getText()), Integer.parseInt(txtGio.getText()), pic, txtMoTa.getText());
    }

    // xóa form
    private void xoaForm() {
        txtTenChuyenDe.setText("");
        txtMaChuyenDe.setText("");
        txtGio.setText("");
        txtHocPhi.setText("");
        txtMoTa.setText("");
        lblPic.setIcon(null);
    }

    //Phương thức ảnh
    public ImageIcon image() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            pic = String.valueOf(chooser.getSelectedFile());
            ImageIcon imageIcon = new ImageIcon(pic);
            Image image = imageIcon.getImage();
            Image image1 = image.getScaledInstance(80, 80, 1000);
            imageIcon = new ImageIcon(image1);
            return imageIcon;
        }
        return null;

    }

    //Phương thức check lỗi form
    private boolean loi() {
        if (txtMaChuyenDe.getText().isEmpty() || txtMaChuyenDe.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Mã không được để trống", "Cảnh Báo", 2);
            txtMaChuyenDe.requestFocus();
            return false;
        }

        if (!txtMaChuyenDe.getText().matches("[0-9a-zA-Z]{1,}")) {
            JOptionPane.showMessageDialog(null, "Mã vui lòng không nhập kí tự đặc biệt ", "Lỗi", 2);
            txtMaChuyenDe.setText("");
            txtMaChuyenDe.requestFocus();
            return false;
        }

        if (txtTenChuyenDe.getText().isEmpty() || txtTenChuyenDe.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Tên chuyên đề không được để trống", "Cảnh Báo", 2);
            txtTenChuyenDe.requestFocus();
            return false;
        }

        if (!txtTenChuyenDe.getText().matches("[^!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{1,}")) {
            JOptionPane.showMessageDialog(null, "Tên vui lòng không nhập kí tự đặc biệt ", "Lỗi", 2);
            txtTenChuyenDe.setText("");
            txtTenChuyenDe.requestFocus();
            return false;
        }
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


    // đọc dữ liệu phân quyền lên form
    private void luuText() {
        System.out.println(role);
        if (role != 1) {
            btnXoa.setEnabled(false);
        }
        System.out.println(pass);
    }
}
