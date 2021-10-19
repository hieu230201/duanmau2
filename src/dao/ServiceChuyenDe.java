package dao;

import model.ChuyenDe;
import utils.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceChuyenDe {
    Connect con = new Connect();
    List<ChuyenDe> _list;
    List<ChuyenDe> _lstXoa;


    public ServiceChuyenDe() {
        _list = new ArrayList<>();
        _lstXoa = new ArrayList<>();
    }

    // Phương Thức Thêm Chuyên Đề
    public String addCD(ChuyenDe cd) throws SQLException {
        String sql = "INSERT INTO chuyende VALUES (?,? ,? ,?, ?,? , ?)";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, cd.getMaCD());
        pm.setString(2, cd.getTenCD());
        pm.setFloat(3, cd.getHocPhi());
        pm.setInt(4, cd.getThoiLuong());
        pm.setString(5, cd.getHinh());
        pm.setString(6, cd.getMoTa());
        pm.setInt(7, 1);
        if (getIndex(cd.getMaCD()) != -1) {
            return "Mã đã tồn tại";
        }
        if (pm.executeUpdate() > 0) {
            _list.add(cd);
            return "Thêm chuyên đề thành công";
        }
        return "Thêm không thành công";

    }

    // phương thức cập nhập chuyên đề
    public int updateCD(ChuyenDe cd) throws SQLException {
        String sql = "update chuyende\n" +
                "set tencd = ?, hocphi = ?, thoiluong = ?, hinh = ?, mota = ?\n" +
                "where macd = ?;";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(6, cd.getMaCD());
        pm.setString(1, cd.getTenCD());
        pm.setFloat(2, cd.getHocPhi());
        pm.setInt(3, cd.getThoiLuong());
        pm.setString(4, cd.getHinh());
        pm.setString(5, cd.getMoTa());
        if (getIndex(cd.getMaCD()) == -1) {
            return -2;
        }
        if (pm.executeUpdate() > 0) {
            int index = getIndex(cd.getMaCD());
            _list.set(getIndex(cd.getMaCD()), cd);
            return index;
        }
        return -1;

    }

    // phương thức xóa chuyên đề

    public int deleteCD(ChuyenDe cd) throws SQLException {
  //      String sql = "delete from hocvien where makh in  (select makh from khoahoc where macd = ?)  delete from khoahoc where macd = ? delete from chuyende where macd = ?";
        String sql = "update chuyende set xoa = 0 where macd = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, cd.getMaCD());
        if (getIndex(cd.getMaCD()) == -1) {
            return -2;
        }
        if (pm.executeUpdate() > 0) {
            int index = getIndex(cd.getMaCD());
            _list.remove(getIndex(cd.getMaCD()));
            return index;
        }
        return -1;

    }

    // thêm lại những chuyên đề bị xóa
    public String addLai(String ma) throws SQLException {
        //      String sql = "delete from hocvien where makh in  (select makh from khoahoc where macd = ?)  delete from khoahoc where macd = ? delete from chuyende where macd = ?";
        String sql = "update chuyende set xoa = 1 where macd = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, ma);
        if (pm.executeUpdate() > 0) {
            return "Thêm lại thành công";
        }
        return "Thêm thất bại";

    }


    // thêm lại những chuyên đề bị xóa
    public String xoaVinhVienMotChuyenDe(String ma) throws SQLException {
        String sql = "delete from hocvien where makh in  (select makh from khoahoc where macd = ?)  delete from khoahoc where macd = ? delete from chuyende where macd = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, ma);
        pm.setString(2, ma);
        pm.setString(3, ma);
        if (pm.executeUpdate() > 0) {
            return "Xóa Thành Công";
        }
        return "Thêm thất bại";

    }


    // lấy dữ liệu từ database vào list
    public List<ChuyenDe> get_list() throws SQLException {
        String sql = "select * from chuyende where xoa = 1";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        while (rs.next()) {
            ChuyenDe cd = new ChuyenDe(rs.getString(1), rs.getString(2), rs.getFloat(3),
                    rs.getInt(4), rs.getString(5), rs.getString(6));
            if (getIndex(cd.getMaCD()) != -1) {
                continue;
            }
            _list.add(cd);
        }
        return _list;
    }


    // lấy dữ liệu từ database vào list
    public List<ChuyenDe> get_listXoa() throws SQLException {
        String sql = "select * from chuyende where xoa = 0";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        _lstXoa.clear();
        while (rs.next()) {
            ChuyenDe cd = new ChuyenDe(rs.getString(1), rs.getString(2), rs.getFloat(3),
                    rs.getInt(4), rs.getString(5), rs.getString(6));
            _lstXoa.add(cd);
        }
        return _lstXoa;
    }


    // lấy ra 1 chuyên đề
    public ChuyenDe getMotThang(String ten) throws SQLException {
        String sql = "select macd from chuyende where tencd = N\'" + ten + "\'";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        ChuyenDe cd = new ChuyenDe();
        while (rs.next()) {
            cd.setMaCD(rs.getString(1));
        }
        return cd;
    }

    // Tìm vị trí của 1 chuyên đề trong mảng
    public int getIndex(String ma) {
        for (int i = 0; i < _list.size(); i++) {
            if (_list.get(i).getMaCD().equals(ma)) {
                return i;
            }
        }
        return -1;
    }


}
