create database	EduSys
go
use EduSys
go
create table nhanvien(
	manv varchar(50) not null,
	matkhau varchar(50)not null,
	hoten nvarchar(50) not null,
	vaitro bit not null,
	primary key (manv)
 )
go
create table chuyende(
	macd varchar(50) not null,
	tencd nvarchar(50) not null,
	hocphi float not null,
	thoiluong int not null,
	hinh nvarchar (100) not null,
	mota nvarchar (255) not null,
	primary key (macd)
 )
go
create table khoahoc(
	makh int identity (1,1) not null,
	macd varchar(50) not null,
	hocphi float not null,
	thoiluong int not null,
	ngayKG date not null,
	ghichu nvarchar (50) null,
	manv varchar(50) not null,
	ngaytao date not null,
	primary key (makh),
	foreign key (macd) references chuyende (macd) on delete no action on update cascade,
	foreign key (manv) references nhanvien (manv) on delete no action on update cascade


 )
go
create table nguoihoc(
	manh varchar (7) not null,
	hoten nvarchar(50) not null,
	ngaysinh date not null,
	gioitinh bit not null,
	dienthoai nvarchar (50) not null,
	email nvarchar (50) not null,
	ghichu nvarchar (max) null,
	manv varchar(50) not null,
	ngaydk date not null,
	primary key (manh),
	foreign key (manv) references nhanvien (manv) on delete no action on update cascade

 )
go
create table hocvien(
	mahv int identity (1,1) not null,
	makh int not null,
	manh varchar (7) not null,
	diem float not null,
	primary key (mahv),
	foreign key (manh) references nguoihoc (manh) ON DELETE NO ACTION ON UPDATE NO ACTION,
	foreign key (makh) references khoahoc (makh) ON DELETE NO ACTION ON UPDATE NO ACTION
	

 )
go
