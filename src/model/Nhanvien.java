package model;

public class Nhanvien {
    private String maNhanVien, matKhau, hoTen, email;
    private int vaiTro;

    public Nhanvien() {
    }

    public Nhanvien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Nhanvien(String maNhanVien, String matKhau, String hoTen, String email, int vaiTro) {
        this.maNhanVien = maNhanVien;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.email = email;
        this.vaiTro = vaiTro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(int vaiTro) {
        this.vaiTro = vaiTro;
    }
}
