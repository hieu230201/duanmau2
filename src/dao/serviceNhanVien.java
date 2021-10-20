package dao;

import model.Nhanvien;
import utils.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class serviceNhanVien {

    Connect con = new Connect();
    List<Nhanvien> _list;
    List<Nhanvien> _lstXoa;


    public serviceNhanVien() {
        _list = new ArrayList<>();
        _lstXoa = new ArrayList<>();
    }


    // Thêm Nhân Viên
    public String addNV(Nhanvien nv) throws SQLException {
        String sql = "INSERT INTO nhanvien VALUES (?,? ,?,? ,?, ?)";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, nv.getMaNhanVien());
        pm.setString(2, nv.getMatKhau());
        pm.setString(3, nv.getHoTen());
        pm.setInt(4, nv.getVaiTro());
        pm.setString(5, nv.getEmail());
        pm.setInt(6,1);
        if(getIndex(nv.getMaNhanVien()) != -1){
            return "Mã nhân viên này đã tồn tại";
        }
        if (pm.executeUpdate() > 0) {
            _list.add(nv);
            return "Thêm nhân viên thành công";
        }
        return "Thêm không thành công";
    }

    //Phương thức đổi mật khẩu
    public String updatePassNV(Nhanvien nv, String ma) throws SQLException {
        String sql = "update nhanvien set matkhau = ? WHERE manv = ? and matkhau = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(2, nv.getMaNhanVien());
        pm.setString(1, nv.getMatKhau());
        pm.setString(3, ma);
        if (pm.executeUpdate() > 0) {
            return "Đổi Mật Khẩu Thành Công";
        }
        return "Đổi Mật Khẩu Thất Bại";

    }

    //Phương thức đổi pass khi quên
    public String updatePassNVQuen(String macu, String mamoi, String email) throws SQLException {
        String sql = "update nhanvien set matkhau = ? WHERE manv = ? and email = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(2, macu);
        pm.setString(1, mamoi);
        pm.setString(3, email);

        if (pm.executeUpdate() > 0) {
            return "Đổi Mật Khẩu Thành Công";
        }
        return "Đổi Mật Khẩu Thất Bại";

    }

    //Sửa Nhân Viên
    public int updateNV(Nhanvien nv) throws SQLException {
        String sql = "update nhanvien set hoten= ?, matkhau = ?, vaitro = ? WHERE manv = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(4, nv.getMaNhanVien());
        pm.setString(1, nv.getHoTen());
        pm.setString(2, nv.getMatKhau());
        pm.setInt(3, nv.getVaiTro());
        if (getIndex(nv.getMaNhanVien()) == -1) {
            return -2;
        }
        if (pm.executeUpdate() > 0) {
            int index = getIndex(nv.getMaNhanVien());
            _list.set(getIndex(nv.getMaNhanVien()), nv);
            return index;
        }
        return -1;

    }
    //Sửa Nhân Viên Đã Bị Xóa
    public String updateNVXoa(String ma) throws SQLException {
        String sql = "update nhanvien set xoa = ? WHERE manv = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setInt(1, 1);
        pm.setString(2, ma);
        if (pm.executeUpdate() > 0) {
            return "Thêm lại thành công";
        }
        return "Thêm lại thất bại";
    }


    //Xóa nhân viên
    public int deleteNV(String ma) throws SQLException {
//      String sql = "delete from hocvien where makh in  (select makh from khoahoc where manv = ?) delete from hocvien where manh in  (select manh from nguoihoc where manv = ?) delete from nguoihoc where manv = ?  delete from khoahoc where manv = ? delete from nhanvien where manv = ? ";
        String sql = "update nhanvien set  xoa = 0 WHERE manv = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, ma);
        if (getIndex(ma) == -1) {
            return -2;
        }
        if (pm.executeUpdate() > 0) {
            int index = getIndex(ma);
            _list.remove(getIndex(ma));
            return index;
        }
        return -1;

    }


    // xóa vĩnh viễn 1 nhân viên
    public String deleteForeve(String ma) throws SQLException{
        String sql = "delete from hocvien where makh in  (select makh from khoahoc where manv = ?) delete from hocvien where manh in  (select manh from nguoihoc where manv = ?) delete from nguoihoc where manv = ?  delete from khoahoc where manv = ? delete from nhanvien where manv = ? ";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, ma);
        pm.setString(2, ma);
        pm.setString(3, ma);
        pm.setString(4, ma);
        pm.setString(5, ma);
        if(pm.executeUpdate() > 0){
            return "Xóa thành công";
        }
        return "Xóa thất bại";

    }

    //Lấy danh sách nhân viên từ database
    public List<Nhanvien> getlist() throws SQLException {
        String sql = "select * from nhanvien where xoa = 1";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        while (rs.next()) {
            Nhanvien nv = new Nhanvien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getInt(4));
            if (getIndex(nv.getMaNhanVien()) != -1) {
                continue;
            } else {
                _list.add(nv);
            }
        }
        return _list;
    }


    //Lấy danh sách nhân viên đã bị xóa từ database
    public List<Nhanvien> getlistXoa() throws SQLException {
        String sql = "select * from nhanvien where xoa = 0";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        _lstXoa.clear();
        while (rs.next()) {
            Nhanvien nv = new Nhanvien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getInt(4));
            _lstXoa.add(nv);

        }
        return _lstXoa;
    }


    // Tìm nhân viên theo mã
    public int getIndex(String ma) {
        for (int i = 0; i < _list.size(); i++) {
            if (_list.get(i).getMaNhanVien().equals(ma)) {
                return i;
            }
        }
        return -1;
    }
}
