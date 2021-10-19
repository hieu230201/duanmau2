package views;

import dao.ServiceKhoaHoc;
import dao.ServiceThongKe;
import model.KhoaHoc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

public class formThongKe extends JFrame {
    ServiceThongKe _lstThongKe = new ServiceThongKe();
    ServiceKhoaHoc _lstKhoaHoc = new ServiceKhoaHoc();
    DefaultTableModel _dtmBangDiem;
    DefaultTableModel _dtmNguoiHoc;
    DefaultTableModel _dtmChuyenDe;
    DefaultTableModel _dtmDoanhThu;
    private String user, pass;
    private int role, tab;
    private JPanel main_p;
    private JTabbedPane tabbedPane1;
    private JComboBox cbcKhoaHoc;
    private JTable tblBangDiem;
    private JTable tblNguoiHoc;
    private JTable tblDiemChuyenDe;
    private JComboBox cbcNam;
    private JTable tblDoanhThu;

    public formThongKe() throws SQLException {
        this.setContentPane(main_p);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800, 600);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setLocationRelativeTo(null);
        loadCbc();


        // code tay tạo cột cho 4 bảng
        _dtmBangDiem = (DefaultTableModel) tblBangDiem.getModel();
        _dtmNguoiHoc = (DefaultTableModel) tblNguoiHoc.getModel();
        _dtmChuyenDe = (DefaultTableModel) tblDiemChuyenDe.getModel();
        _dtmDoanhThu = (DefaultTableModel) tblDoanhThu.getModel();
        _dtmBangDiem.setColumnIdentifiers(new String[]{
                "Mã Người Học", "Họ Và Tên", "Điểm", "Xếp Loại"
        });
        tblBangDiem.setModel(_dtmBangDiem);
        _dtmNguoiHoc.setColumnIdentifiers(new String[]{
                "Năm", "Số Người Học", "ĐK Sớm Nhất", "ĐK Muộn Nhất"
        });
        tblNguoiHoc.setModel(_dtmNguoiHoc);
        _dtmChuyenDe.setColumnIdentifiers(new String[]{
                "Chuyên Đề", "SL HV", "Điểm Thấp Nhất", "Điểm Cao Nhất", "Điểm TB"
        });
        tblDiemChuyenDe.setModel(_dtmChuyenDe);
        _dtmDoanhThu.setColumnIdentifiers(new String[]{
                "Chuyên Đề", "Số KH", "Số HV", "Doanh Thu", "Học Phí Thấp Nhất", "Học Phí Cao Nhất", "Học Phí Trung Bình"
        });
        tblDoanhThu.setModel(_dtmDoanhThu);
        // tới đây là hết code tay tạo cột


        // load dữ liệu lên bảng điểm
        loadTblBangDiem();

        // load dữ liệu lên bảng thống kê người học
        _lstThongKe.thongKeNguoiHoc(_dtmNguoiHoc);

        // load dữ liệu lên bảng thống kê điểm chuyên đề
        _lstThongKe.thongKeDiemChuyenDe(_dtmChuyenDe);

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

        // phương thức chọn khóa học để load lại bảng điểm
        cbcKhoaHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadTblBangDiem();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // phương thức chọn năm để load lại doanh thu
        cbcNam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (_dtmDoanhThu.getRowCount() > 0) {
                        _dtmDoanhThu.setRowCount(0);
                    }
                    _lstThongKe.thongKeDoanhThu(Integer.parseInt(String.valueOf(cbcNam.getSelectedItem())), _dtmDoanhThu);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    // phương thức load tbl bảng điểm
    private void loadTblBangDiem() throws SQLException {
        String maCD = String.valueOf(cbcKhoaHoc.getSelectedItem()).substring(0, String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf('(')).trim();
        String date = String.valueOf(cbcKhoaHoc.getSelectedItem()).substring(String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf('(') + 1, String.valueOf(cbcKhoaHoc.getSelectedItem()).indexOf(')')).trim();
        _dtmBangDiem = (DefaultTableModel) tblBangDiem.getModel();
        if (_dtmBangDiem.getRowCount() > 0) {
            _dtmBangDiem.setRowCount(0);
        }
        _lstThongKe.thongKeBangDiem(_lstThongKe.getMaKhoaHoc(maCD, date), _dtmBangDiem);

    }

//    public static void main(String[] args) throws SQLException {
//        new formThongKe();
//    }

    // phương thức load cbc
    private void loadCbc() throws SQLException {
        for (KhoaHoc a : _lstKhoaHoc.get_list()
        ) {
            cbcKhoaHoc.addItem(a.getMaCD() + "( " + a.getNgayKG() + " )");
        }
        for (int i = 1980; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            cbcNam.addItem(i);
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

    public void setTab(int tab) {
        this.tab = tab;
    }


    // đọc dữ liệu phân quyền lên form
    private void luuText() {
        System.out.println(role);

        if (tab == 0) {
            tabbedPane1.remove(3);
            tabbedPane1.remove(2);
            tabbedPane1.remove(1);
        }
        if (tab == 1) {
            tabbedPane1.remove(3);
            tabbedPane1.remove(2);
            tabbedPane1.remove(0);
        }
        if (tab == 2) {
            tabbedPane1.remove(3);
            tabbedPane1.remove(1);
            tabbedPane1.remove(0);
        }
        if (tab == 3) {
            tabbedPane1.remove(2);
            tabbedPane1.remove(1);
            tabbedPane1.remove(0);
        }
        if (tab == 4 && role != 1) {
            tabbedPane1.remove(3);
        }
        System.out.println(pass);
    }
}
