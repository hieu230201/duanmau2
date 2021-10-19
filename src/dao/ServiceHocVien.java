package dao;

import model.HocVien;
import utils.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceHocVien {
    Connect con = new Connect();
    List<HocVien> _list;

    public ServiceHocVien() {
        _list = new ArrayList<>();
    }


    // lấy ra list học viên của 1 khóa học
    public List<HocVien> getListHocVien(String id, String date) throws SQLException {
        String sql = "select hocvien.mahv,  nguoihoc.manh, nguoihoc.hoten, hocvien.diem, khoahoc.makh from khoahoc inner join hocvien on hocvien.makh = khoahoc.makh inner join nguoihoc on hocvien.manh = nguoihoc.manh\n" +
                "   where khoahoc.ngaykg = '" + date + "' and khoahoc.macd = '" + id + "'";

        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        _list.clear();
        while (rs.next()) {
            HocVien hv = new HocVien(rs.getString(2), rs.getString(3), rs.getInt(1), rs.getInt(5), rs.getInt(4));
            _list.add(hv);
        }

        return _list;
    }

    // lấy ra list học viên của 1 khóa học
    public int getMakh(String id, String date) throws SQLException {
        String sql = "select  khoahoc.makh from khoahoc where macd = '" + id + "' and ngayKG = '" + date + "'";


        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        int maKH = -1;
        if (rs.next()) {
            maKH = rs.getInt(1);
        }

        return maKH;
    }


    // Xóa Học Viên
    public String xoaHocVien(int id) throws SQLException {
        String sql = "delete from hocvien where mahv = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setInt(1, id);
        if (pm.executeUpdate() > 0) {

            return "Xóa Thành Công";
        }
        return "Xóa thất bại";
    }


    //Cập nhật điểm cho sinh viên
    public String updateDiemHocVien(int id, int diem) throws SQLException {
        String sql = "update hocvien set diem = ? where  mahv = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setInt(2, id);
        pm.setInt(1, diem);
        if (pm.executeUpdate() > 0) {

            return "Sửa Thành Công";
        }
        return "Sửa thất bại";
    }


    // thêm học viên
    public String addHocVien(int id, int diem, String maNH) throws SQLException {
        String sql = "insert  into hocvien values  (?,?,?)";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setInt(3, diem);
        pm.setInt(1, id);
        pm.setString(2, maNH);
        if (pm.executeUpdate() > 0) {

            return "Thêm Thành Công";
        }
        return "Thêm thất bại";
    }


    //check học viên
    public boolean checkHocVien(int id, String maNH) throws SQLException {
        String sql = "select mahv from hocvien where makh = " + id + "and manh = '" + maNH + "'";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        return rs.next();
    }
}
