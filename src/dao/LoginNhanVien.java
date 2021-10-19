package dao;

import model.Nhanvien;
import utils.Connect;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginNhanVien {
    Connect con = new Connect();
    String[] login = new String[2];

    // check xem có đúng nhân viên để đăng nhập
    public Nhanvien login(String user, String pass) throws SQLException {
        Nhanvien nv = new Nhanvien();
        String sql = "select  manv, matkhau, hoten, vaitro from nhanvien where manv = ? and matkhau = ?";
        PreparedStatement ps = con.con().prepareStatement(sql);
        ps.setString(1, user);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            nv.setMaNhanVien(rs.getString(1));
            nv.setMatKhau(rs.getString(2));
            nv.setHoTen(rs.getString(3));
            nv.setVaiTro(rs.getInt(4));
            return nv;
        } else {
            JOptionPane.showMessageDialog(null, "bạn đã nhập sai tài khoản", "Lỗi"
                    , JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }


    // hàm lưu lại nhân viên
    public void remeber(String user, String pass) throws SQLException {
        String sql = "update remeber set users = ?, pass = ? WHERE id = 1";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, user);
        pm.setString(2, pass);
        pm.executeUpdate();
    }


    // hàm lấy nhân viên đã lưu vào form
    public String[] chuyen() throws SQLException {
        String sql1 = "select * from remeber where id = 1";
        PreparedStatement pmm = con.con().prepareStatement(sql1);
        ResultSet rs = pmm.executeQuery();
        if (rs.next()) {
            login[0] = rs.getString(2);
            login[1] = rs.getString(3);
        }
        return login;
    }
}
