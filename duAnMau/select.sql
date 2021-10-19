
select * from chuyende
select * from khoahoc 
select hocvien.manh, hocvien.makh, diem from hocvien join khoahoc on   hocvien.makh =  khoahoc.makh  where khoahoc.makh = 24 and khoahoc.ngayKG = '2018-01-10'
select * from hocvien where makh = 24
select * from hocvien
select * from nguoihoc
select * from nhanvien
select hocvien.makh from hocvien join khoahoc on khoahoc.makh = hocvien.makh where khoahoc.macd = 'web4'
delete from hocvien join khoahoc on khoahoc.makh = hocvien.makh where khoahoc.macd = 'pro3'
delete from hocvien where makh in  (select makh from khoahoc where macd = 'web4') 
  select hocvien.mahv, nguoihoc.manh, nguoihoc.hoten, khoahoc.ngaykg from khoahoc inner join hocvien on hocvien.makh = khoahoc.makh inner join nguoihoc on hocvien.manh = nguoihoc.manh
  where khoahoc.ngaykg = '2018-01-10' and khoahoc.macd = 'JAV01'

  select tencd chuyede,count (distinct kh.makh) sokh, count (hv.makh),sum(kh.hocphi) doanhthu,min(kh.hocphi) thapnhat,max(kh.hocphi) caonhat,AVG(kh.hocphi) trungbinh from khoahoc kh join hocvien hv on kh.makh = hv.makh join chuyende cd on cd.macd = kh.macd where year(ngaykg) = 2020 group by tencd

	