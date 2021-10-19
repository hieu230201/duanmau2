package dao;

import model.NguoiHoc;
import utils.Connect;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceNguoiHoc {
    List<NguoiHoc> _list;
    serviceNhanVien _listnv = new serviceNhanVien();
    List<NguoiHoc> _lstPhanTrang;
    Connect connect = new Connect();

    public ServiceNguoiHoc() {
        _list = new ArrayList<>();
        _lstPhanTrang = new ArrayList<>();
    }


    // Phương Thức Thêm Người Học
    public String addNguoiHoc(NguoiHoc nguoiHoc) throws SQLException {
        String sql = "insert into nguoihoc values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement pm = connect.con().prepareStatement(sql);
        pm.setString(1, nguoiHoc.getMaNH());
        pm.setString(2, nguoiHoc.getHoTen());
        pm.setDate(3, Date.valueOf(nguoiHoc.getNgaySinh()));
        pm.setInt(4, nguoiHoc.getGioiTinh());
        pm.setString(5, nguoiHoc.getDienThoai());
        pm.setString(6, nguoiHoc.getEmail());
        pm.setString(7, nguoiHoc.getGhiChu());
        pm.setString(8, nguoiHoc.getMaNhanVien());
        pm.setDate(9, Date.valueOf(nguoiHoc.getNgayDangKi()));
        if (pm.executeUpdate() > 0) {
            _list.add(nguoiHoc);
            return "Thêm Người Học Thành Công";
        }
        return "Thêm Người Học Thất Bại";
    }

    // Phương Thức Sửa Người Học
    public int suaNguoiHoc(NguoiHoc nguoiHoc) throws SQLException {
        String sql = "update nguoihoc set hoten = ?, ngaysinh = ?, gioitinh = ?, dienthoai = ?, email = ?,ghichu = ?, manv = ?, ngaydk = ? where manh = ?";
        PreparedStatement pm = connect.con().prepareStatement(sql);
        pm.setString(9, nguoiHoc.getMaNH());
        pm.setString(1, nguoiHoc.getHoTen());
        pm.setDate(2, Date.valueOf(nguoiHoc.getNgaySinh()));
        pm.setInt(3, nguoiHoc.getGioiTinh());
        pm.setString(4, nguoiHoc.getDienThoai());
        pm.setString(5, nguoiHoc.getEmail());
        pm.setString(6, nguoiHoc.getGhiChu());
        pm.setString(7, nguoiHoc.getMaNhanVien());
        pm.setDate(8, Date.valueOf(nguoiHoc.getNgayDangKi()));
        if (pm.executeUpdate() > 0) {
            _list.set(getIndex(nguoiHoc.getMaNH()), nguoiHoc);
            return getIndex(nguoiHoc.getMaNH());
        }
        return -1;
    }

    // Phương Thức Xóa Người Học
    public int xoaNguoiHoc(NguoiHoc nguoiHoc) throws SQLException {
        String sql = "delete hocvien where manh = ? delete nguoihoc where manh = ?";
        PreparedStatement pm = connect.con().prepareStatement(sql);
        pm.setString(1, nguoiHoc.getMaNH());
        pm.setString(2, nguoiHoc.getMaNH());

        if (pm.executeUpdate() > 0) {
            int i = getIndex(nguoiHoc.getMaNH());
            _list.remove(getIndex(nguoiHoc.getMaNH()));
            return i;
        }
        return -1;
    }


    // trả về list
    public List<NguoiHoc> get_list() throws SQLException {
        String sql = "select * from nguoihoc";
        PreparedStatement pm = connect.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        while (rs.next()) {

            NguoiHoc nguoiHoc = new NguoiHoc(rs.getString(8), rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(9));
            _list.add(nguoiHoc);
        }
        return _list;
    }


    //đếm số người học
    public int count() throws SQLException{
        int dem = 0;
        String sql = "select count(*) from nguoihoc";
        PreparedStatement pm = connect.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        while (rs.next()){
            dem = rs.getInt(1);
        }
        return dem;
    }

    public List<NguoiHoc> loadData(int trang) throws SQLException{
        String sql = "select top 10 * from nguoihoc where manh not in (select top " + (trang*10-10) + " manh from  nguoihoc)";
        PreparedStatement pm = connect.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        _lstPhanTrang.clear();
        while (rs.next()) {

            NguoiHoc nguoiHoc = new NguoiHoc(rs.getString(8), rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(9));
            _lstPhanTrang.add(nguoiHoc);
        }
        return _lstPhanTrang;
    }


//    // trả về người đc tìm thấy
//    public int search(String ma) throws SQLException{
//        String sql = "select * from nguoihoc where manh = ?";
//        PreparedStatement pm = connect.con().prepareStatement(sql);
//        pm.setString(1,ma);
//        if(pm.executeUpdate() > 0){
//            return getIndex(ma);
//        }
//        return -1;
//    }


    // tìm kiếm và trả về vị trí của người có mã dó
    public int getIndex(String ma) {
        for (int i = 0; i < _list.size(); i++) {
            if (_list.get(i).getMaNH().equals(ma)) {
                return i;
            }
        }
        return -1;
    }


}
