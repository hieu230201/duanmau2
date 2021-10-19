use EduSys
go

create proc sp_BangDiem(@makh int)
as begin
	select
		nh.manh,
		nh.hoten,
		hv.diem
		from hocvien hv join nguoihoc nh on nh.manh = hv.manh
		where hv.makh = @makh
		order by hv.diem DESC
end
go


create proc sp_thongkediem
as begin
	select
		tencd chuyede,
		count(mahv) sohv,
		min(diem) thapnhat,
		max(diem) caonhat,
		AVG(diem) trungbinh
	from khoahoc kh
		join hocvien hv on kh.makh = hv.makh
		join chuyende cd on cd.macd = kh.macd
	group by tencd
end
go



create proc sp_thongkedoanhthuu(@year int)
as begin
	select
		tencd chuyede,
		count (distinct kh.makh) sokh,  count (distinct hv.makh) sohv,
		sum(kh.hocphi) doanhthu,
		min(kh.hocphi) thapnhat,
		max(kh.hocphi) caonhat,
		AVG(kh.hocphi) trungbinh
	from khoahoc kh
		join hocvien hv on kh.makh = hv.makh
		join chuyende cd on cd.macd = kh.macd
	where year(ngaykg) = @year
	group by tencd
end

go



create proc sp_thongkenguoihoc
as begin
	select
		year(ngaydk) nam,
		count(*) soluong,
		min(ngaydk) dautien,
		max(ngaydk) cuoicung
	from nguoihoc
	group by year(ngaydk)
end
go



