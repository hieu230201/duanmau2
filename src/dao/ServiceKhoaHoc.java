package dao;

import model.ChuyenDe;
import model.KhoaHoc;
import model.Nhanvien;
import utils.Connect;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceKhoaHoc {
    List<KhoaHoc> _list;
    List<KhoaHoc> _listCbcBenHocVien;

    Connect con = new Connect();
    serviceNhanVien _listNv = new serviceNhanVien();
    ServiceChuyenDe _listcd = new ServiceChuyenDe();

    public ServiceKhoaHoc() {
        _list = new ArrayList<>();
        _listCbcBenHocVien = new ArrayList<>();
    }

    // Phương Thức Thêm Khóa Học
    public String addKhoaHoc(KhoaHoc khoaHoc, String maNV) throws SQLException {
        int check = 0;
        String sql = "INSERT INTO khoahoc VALUES (?,? ,? ,?, ?,?,?)";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, khoaHoc.getMaCD());
        pm.setFloat(2, khoaHoc.getHocPhi());
        pm.setInt(3, khoaHoc.getThoiLuong());
        pm.setDate(4, Date.valueOf(khoaHoc.getNgayKG()));
        pm.setString(5, khoaHoc.getGhiChu());
        for (Nhanvien a : _listNv.getlist()) {
            if (a.getMaNhanVien().equals(maNV)) {
                check++;
                break;
            }
        }
        if (check == 0) {
            return "Không Thấy Mã Nhân Viên Này";
        }

        pm.setString(6, maNV);
        pm.setDate(7, Date.valueOf(khoaHoc.getNgayTao()));
        if (pm.executeUpdate() > 0) {
            _list.add(khoaHoc);
            return "Thêm Khóa Học Thành Công";
        }
        return "Thêm Thất Bại";
    }


    // Phương Thức Thêm Khóa Học
    public String suaKhoaHoc(String maNV, KhoaHoc khoaHoc) throws SQLException {
        int check = 0;

        String sql = "update khoahoc set macd = ?, hocphi = ?, thoiluong = ?, ngayKG = ?, ghichu = ?, manv = ?, ngaytao = ? where makh = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, khoaHoc.getMaCD());
        pm.setFloat(2, khoaHoc.getHocPhi());
        pm.setInt(3, khoaHoc.getThoiLuong());
        pm.setDate(4, Date.valueOf(khoaHoc.getNgayKG()));
        pm.setString(5, khoaHoc.getGhiChu());
        for (Nhanvien a : _listNv.getlist()) {
            if (a.getMaNhanVien().equals(maNV)) {
                check++;
                break;
            }
        }
        if (check == 0) {
            return "Không có người tạo này";
        }

        pm.setString(6, maNV);
        pm.setDate(7, Date.valueOf(khoaHoc.getNgayTao()));
        pm.setInt(8, khoaHoc.getMakh());
        if (pm.executeUpdate() > 0) {
            _list.set(getIndex(khoaHoc.getMakh()), khoaHoc);
            return "Sửa khóa học thành công";
        }
        return "Sửa khóa học thất bại";
    }


    // Phương Thức Xóa Khóa Học
    public String xoaKhoaHoc(int ma) throws SQLException {
        String sql = "delete from hocvien where makh = ? delete from khoahoc  where makh = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setInt(1, ma);
        pm.setInt(2, ma);

        if (pm.executeUpdate() > 0) {
            int i = getIndex(ma);
            _list.remove(getIndex(ma));
            return "Xóa Thành Công";
        }
        return "Xóa Thất Bại";
    }


    //Lấy all khóa học từ csdl cho vào list
    public List<KhoaHoc> get_list() throws SQLException {
        String sql = "select * from khoahoc";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        while (rs.next()) {
            ChuyenDe b = null;
            for (ChuyenDe a : _listcd.get_list()
            ) {
                if (a.getMaCD().equals(rs.getString(2))) {
                    b = a;
                    break;
                }
            }
            KhoaHoc kh = new KhoaHoc(b.getMaCD(), b.getTenCD(), b.getHocPhi(), b.getThoiLuong(), b.getHinh(), b.getMoTa(), rs.getInt(1), rs.getFloat(3),
                    rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
            if (getIndex(kh.getMakh()) != -1) {
                continue;
            }
            _list.add(kh);
        }
        return _list;
    }


    // có tên chuyên đề

    // lấy khóa học theo mã chuyên đề
    public List<KhoaHoc> get_listCbc(String ma) throws SQLException {

        String sql = "select * from khoahoc where  macd = \'" + ma + "\'";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        _listCbcBenHocVien.clear();
        while (rs.next()) {
            KhoaHoc kh = new KhoaHoc(ma, rs.getString(5));
            _listCbcBenHocVien.add(kh);
        }
        return _listCbcBenHocVien;
    }


    // tìm khóa học theo mã
    public int getIndex(int ma) {
        for (int i = 0; i < _list.size(); i++) {
            if (_list.get(i).getMakh() == ma) {
                return i;
            }
        }
        return -1;
    }


}
