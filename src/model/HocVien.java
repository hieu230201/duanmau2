package model;

public class HocVien extends NguoiHoc {
    private int id;
    private int maKH;
    private double diem;

    public HocVien() {
    }

    public HocVien(String maNH, String hoTen, int id, int maKH, double diem) {
        super(maNH, hoTen);
        this.id = id;
        this.maKH = maKH;
        this.diem = diem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }
}
