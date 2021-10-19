package model;

public class KhoaHoc extends ChuyenDe {
    private int makh;
    private float hocPhi;
    private int thoiLuong;
    private String ngayKG, ghiChu, manv, ngayTao;

    public KhoaHoc() {
    }

    public KhoaHoc(String maCD, String tenCD, float hocPhi, int thoiLuong, String hinh, String moTa, int makh, float hocPhi1, int thoiLuong1, String ngayKG, String ghiChu, String manv, String ngayTao) {
        super(maCD, tenCD, hocPhi, thoiLuong, hinh, moTa);
        this.makh = makh;
        this.hocPhi = hocPhi1;
        this.thoiLuong = thoiLuong1;
        this.ngayKG = ngayKG;
        this.ghiChu = ghiChu;
        this.manv = manv;
        this.ngayTao = ngayTao;
    }

    public KhoaHoc(String maCD, String ngayKG) {
        super(maCD);
        this.ngayKG = ngayKG;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    @Override
    public float getHocPhi() {
        return hocPhi;
    }

    @Override
    public void setHocPhi(float hocPhi) {
        this.hocPhi = hocPhi;
    }

    @Override
    public int getThoiLuong() {
        return thoiLuong;
    }

    @Override
    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getNgayKG() {
        return ngayKG;
    }

    public void setNgayKG(String ngayKG) {
        this.ngayKG = ngayKG;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}
