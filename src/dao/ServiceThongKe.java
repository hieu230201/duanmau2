package dao;

import utils.Connect;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceThongKe {

    Connect con = new Connect();

    // load bảng thống kê bảng điểm
    public void thongKeBangDiem(int i, DefaultTableModel _dtm) throws SQLException {
        String sql = "sp_BangDiem @makh = " + i;
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();

        boolean flag = false;
        while (rs.next()) {
            _dtm.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(3) <= 5 ? "Trung Bình" :
                            (rs.getInt(3) < 8 ? "Khá" : "Giỏi")
            });
            flag = true;
        }
        if (!flag) {
            JOptionPane.showMessageDialog(null, "chưa có ai học khóa học này");
        }

    }


    // Phương thức load tbl thông kê người học
    public void thongKeNguoiHoc(DefaultTableModel _dtm) throws SQLException {
        String sql = "sp_thongkenguoihoc";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        while (rs.next()) {
            _dtm.addRow(new Object[]{
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)
            });
        }
    }

    //Phương thức thống kê doanh thu
    public void thongKeDoanhThu(int year, DefaultTableModel _dtm) throws SQLException {
        String sql = "select tencd chuyede,count (distinct kh.makh) sokh, count (hv.makh),sum(kh.hocphi) doanhthu,min(kh.hocphi) thapnhat,max(kh.hocphi) caonhat,AVG(kh.hocphi) trungbinh from khoahoc kh join hocvien hv on kh.makh = hv.makh join chuyende cd on cd.macd = kh.macd where year(ngaykg) = " + year + " group by tencd";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        boolean flag = false;
        while (rs.next()) {
            _dtm.addRow(new Object[]{
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)
            });
            flag = true;
        }
        if (!flag) {
            JOptionPane.showMessageDialog(null, "chưa có doanh thu cho năm này");
        }
    }

    // Phương thức load tbl thông kê người học
    public void thongKeDiemChuyenDe(DefaultTableModel _dtm) throws SQLException {
        String sql = "sp_thongkediem";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        while (rs.next()) {
            _dtm.addRow(new Object[]{
                    rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)
            });
        }
    }

    // lấy khóa chính của khóa học ở combobox
    public int getMaKhoaHoc(String ma, String date) throws SQLException {
        String sql = "select makh from khoahoc where macd = '" + ma + "' and ngayKG = '" + date + "'";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }
}
