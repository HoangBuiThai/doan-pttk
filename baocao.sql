set serveroutput on;
/*========================================*/
/*Tao user01 (admin) voi all quyen                            */
/*========================================*/
alter session set "_ORACLE_SCRIPT"=true;
create user user01 identified by 123;
grant all privileges to user01;
/*===========================================*/

ALTER SESSION SET nls_date_format='dd-MM-yyyy';
commit;

/*==============================================================*/
/* Table: ANPHAM                                                */
/*==============================================================*/
create table ANPHAM 
(
   MAAP                 VARCHAR2(10)         not null,
   MANXB                VARCHAR2(10)         not null,
   TENAP                VARCHAR2(30),
   constraint PK_ANPHAM primary key (MAAP)
);

/*==============================================================*/
/* Index: SANXUAT_FK                                            */
/*==============================================================*/
create index SANXUAT_FK on ANPHAM (
   MANXB ASC
);

/*==============================================================*/
/* Table: CHUCVU                                                */
/*==============================================================*/
create table CHUCVU 
(
   MACV                 VARCHAR2(10)         not null,
   TENCV                VARCHAR2(30),
   constraint PK_CHUCVU primary key (MACV)
);

/*==============================================================*/
/* Table: GOM                                                   */
/*==============================================================*/
create table CTHD 
(
   MAHD                 VARCHAR2(10)         not null,
   MAAP                 VARCHAR2(10)         not null,
   NGAYKETTHUC          DATE,
   constraint PK_GOM primary key (MAHD, MAAP)
);

/*==============================================================*/
/* Index: BAO_FK                                                */
/*==============================================================*/
create index BAO_FK on CTHD (
   MAHD ASC
);

/*==============================================================*/
/* Index: NHUNG_FK                                              */
/*==============================================================*/
create index NHUNG_FK on CTHD (
   MAAP ASC
);

/*==============================================================*/
/* Table: HOADON                                                */
/*==============================================================*/
create table HOADON 
(
   MAHD                 VARCHAR2(10)         not null,
   MAKH                 VARCHAR2(10)         not null,
   MANV                 VARCHAR2(10)         not null,
   NGAYLAP              DATE,
   TONGTIEN             NUMBER(8,3),
   constraint PK_HOADON primary key (MAHD)
);

/*==============================================================*/
/* Index: MUA_FK                                                */
/*==============================================================*/
create index MUA_FK on HOADON (
   MAKH ASC
);

/*==============================================================*/
/* Index: LAP_FK                                                */
/*==============================================================*/
create index LAP_FK on HOADON (
   MANV ASC
);

/*==============================================================*/
/* Table: KHACHHANG                                             */
/*==============================================================*/
create table KHACHHANG 
(
   MAKH                 VARCHAR2(10)         not null,
   MAQUAN               VARCHAR2(3)          not null,
   HOTEN                VARCHAR2(30),
   CMND                 NUMBER(10),
   DIACHI               VARCHAR2(100),
   SODT                 NUMBER(10),
   constraint PK_KHACHHANG primary key (MAKH)
);

/*==============================================================*/
/* Table: NHANVIEN                                              */
/*==============================================================*/
create table NHANVIEN 
(
   MANV                 VARCHAR2(10)         not null,
   MAQUAN               VARCHAR2(3),
   MACV                 VARCHAR2(10)         not null,
   TENNV                VARCHAR2(30),
   NGAYVAOLAM           DATE,
   USERNAME             VARCHAR2(10),
   PASSWORD		VARCHAR2(20),
   constraint PK_NHANVIEN primary key (MANV)
);

/*==============================================================*/
/*==============================================================*/
/* Table: NHAXUATBAN                                            */
/*==============================================================*/
create table NHAXUATBAN 
(
   MANXB                VARCHAR2(10)         not null,
   TENNXB               VARCHAR2(30),
   constraint PK_NHAXUATBAN primary key (MANXB)
);

/*==============================================================*/
/* Table: QUANHUYEN                                             */
/*==============================================================*/
create table QUANHUYEN 
(
   MAQUAN               VARCHAR2(3)          not null,
   TENQUAN              VARCHAR2(15),
   constraint PK_QUANHUYEN primary key (MAQUAN)
);

alter table ANPHAM
   add constraint FK_ANPHAM_SANXUAT_NHAXUATB foreign key (MANXB)
      references NHAXUATBAN (MANXB);

alter table CTHD
   add constraint FK_CTHD_BAO_HOADON foreign key (MAHD)
      references HOADON (MAHD);

alter table CTHD
   add constraint FK_CTHD_NHUNG_ANPHAM foreign key (MAAP)
      references ANPHAM (MAAP);

alter table HOADON
   add constraint FK_HOADON_LAP_NHANVIEN foreign key (MANV)
      references NHANVIEN (MANV);

alter table HOADON
   add constraint FK_HOADON_MUA_KHACHHAN foreign key (MAKH)
      references KHACHHANG (MAKH);

alter table KHACHHANG
   add constraint FK_KHACHHAN_THUOC_QUANHUYE foreign key (MAQUAN)
      references QUANHUYEN (MAQUAN);

alter table NHANVIEN
   add constraint FK_NHANVIEN_CO_CHUCVU foreign key (MACV)
      references CHUCVU (MACV);

alter table NHANVIEN
   add constraint FK_NHANVIEN_GIAO_QUANHUYE foreign key (MAQUAN)
      references QUANHUYEN (MAQUAN);


/*==============================================================*/
/* THEM, XOA, SUA, TIM QUAN HUYEN                                          */
/*==============================================================*/
alter table QuanHuyen
  modify MaQuan varchar2(4);
alter table QuanHuyen
  modify TenQuan varchar2(16);

insert into QuanHuyen values('Q1','Quan 1');
insert into QuanHuyen values('Q2','Quan 2');
insert into QuanHuyen values('Q3','Quan 3');
insert into QuanHuyen values('Q4','Quan 4');
insert into QuanHuyen values('Q5','Quan 5');
insert into QuanHuyen values('Q6','Quan 6');
insert into QuanHuyen values('Q7','Quan 7');
insert into QuanHuyen values('Q8','Quan 8');
insert into QuanHuyen values('Q9','Quan 9');
insert into QuanHuyen values('Q10','Quan 10');
insert into QuanHuyen values('Q11','Quan 11');
insert into QuanHuyen values('Q12','Quan 12');
insert into QuanHuyen values('QTD','Quan Thu Duc');
insert into QuanHuyen values('QGV','Quan Go Vap');
insert into QuanHuyen values('QBT','Quan Binh Thanh');
insert into QuanHuyen values('QTB','Quan Tan Binh');
insert into QuanHuyen values('QTP','Quan Tan Phu');
insert into QuanHuyen values('QPN','Quan Phu Nhuan');
insert into QuanHuyen values('QBTA','Quan Binh Tan');
insert into QuanHuyen values('HCC','Huyen Cu Chi');
insert into QuanHuyen values('HBC','Huyen Binh Chanh');
insert into QuanHuyen values('HNB','Huyen Nha Be');
insert into QuanHuyen values('HCG','Huyen Can Gio');


/*==============================================================*/
/* THEM, XOA, SUA, TIM KHACH HANG                                           */
/*==============================================================*/
create sequence auto_id_khachhang
  increment by 1
  start with 1
  nomaxvalue
  nocycle
  cache 2;

alter table KhachHang
  modify MaQuan varchar2(4);

create or replace procedure themkhachhang(
  v_maquan varchar2,
  v_hoten varchar2,
  v_cmnd number,
  v_diachi varchar2,
  v_sodt number)
is
begin
    insert into KhachHang(MaKH, MaQuan, HoTen, CMND, DiaChi, SoDT) 
    values('KH'||auto_id_khachhang.nextval, v_maquan,  v_hoten, v_cmnd, v_diachi, v_sodt);
end;

/*----------test----------*/


create or replace procedure xoakhachhang(v_id varchar2)
is
begin
  delete from KhachHang
  where MaKH=v_id;
exception
  when NO_DATA_FOUND then
    dbms_output.put_line('Khach hang khong ton tai');
end;


--Sua khach hang, neu khong sua thong tin nao thi go '0' thong tin do
create or replace procedure suakhachhang (
  v_id varchar2,
  update_hoten in varchar2,
  update_cmnd in number,
  update_diachi in varchar2,
  update_sodt in number,
  update_maquan in varchar2)
is
begin
 update KhachHang
 set
 MaQuan= update_maquan,
  HoTen=update_hoten,
  CMND=update_cmnd,
  DiaChi=update_diachi,
  SoDT=update_sodt
  
 where MaKH=v_id;
exception
  when NO_DATA_FOUND then
    dbms_output.put_line('Khach hang khong ton tai');
end;

/*----------test----------*/
/*

/*=============================================================*/
/*Them Hoa don*/
create sequence auto_id_hoadon
  increment by 1
  start with 1
  nomaxvalue
  nocycle
  cache 2;

alter table HOADON
MODIFY TONGTIEN NUMBER;

alter table HOADON
ADD thanhtoan varchar2(2);

  create or replace procedure themhoadon(
  v_makh varchar2,
  v_manv varchar2,
  v_ngaylap date
  )
is
begin
  insert into HOADON(MaHD, MaKH, MaNV, NGAYLAP, TONGTIEN,THANHTOAN) 
  values('HD'||auto_id_hoadon.nextval, v_makh,  v_manv, v_ngaylap,0,0);
end;

/*Trigger Chi ti?t hóa ??n*/
CREATE OR REPLACE PROCEDURE THEMCTHD(
    v_MaHD varchar2,
    v_MaAp varchar2,
    v_Ngayketthuc date,
    v_gia number)
is
begin
    insert into CTHD values(v_MaHD,v_MaAp,v_Ngayketthuc,v_gia);
    /*update HOADON set TONGTIEN=TONGTIEN+v_gia where MAHD=v_MaHD; */
end;

CREATE OR REPLACE PROCEDURE XOACTHD(
    v_MAHD varchar2,
    v_MAAP varchar2)
is
begin
    delete from cthd where mahd=v_MAHD and maap=v_MAAP;
END;

//Thanh toán hóa ??n
CREATE OR REPLACE PROCEDURE  THANHTOANHD(
    v_MAHD varchar2
    )
IS
BEGIN
    UPDATE HOADON SET THANHTOAN='1' where MAHD=v_MAHD;
END;



/*=============================================================*/

/*==============================================================*/
/* THEM, XOA, SUA, TIM CHUC VU                                           */
/*==============================================================*/
insert into ChucVu values('QL','Quan Ly');
insert into ChucVu values('GH','Giao Hang');
insert into Chucvu values('TT','Tiep Tan');
insert into Chucvu values('QK','Quan Kho');

/*==============================================================*/
/* THEM, XOA, SUA NHAN VIEN                                           */
/*==============================================================*/
create sequence auto_id_nhanvien
  increment by 1
  start with 1
  nomaxvalue
  nocycle
  cache 2;
 
alter table NhanVien
  modify MaQuan varchar2(4);

create or replace procedure themnhanvien(
  v_maquan varchar2,
  v_macv varchar2,
  v_tennv varchar2,
  v_ngayvaolam date,
  v_username varchar2,
  v_password varchar2)
is
begin
  insert into NhanVien(MaNV, MaQuan, MaCV, TenNV, NgayVaoLam,username,password) 
  values('NV'||auto_id_nhanvien.nextval, v_maquan, v_macv, v_tennv, v_ngayvaolam,v_username,v_password);
end;



insert into Nhanvien values('NV1',NULL,'QL','ADMIN','28-05-2019','admin','1');
commit

/*----------test----------*/
/*begin
themnhanvien('Q1','GH','Do Hoang Anh Khoa','01-06-2022');
end;
select * from NhanVien;*/

--Xoa Nhan vien
create or replace procedure xoanhanvien(v_id varchar2)
is
begin
  delete from NhanVien
  where MaNV=v_id;
exception
  when NO_DATA_FOUND then
    dbms_output.put_line('Nhan vien khong ton tai');
end;

--Sua nhan vien
create or replace procedure suanhanvien (
  v_id varchar2,
  update_maquan varchar2,
  update_macv varchar2,
  update_tennv varchar2,
  update_username varchar2,
  update_password varchar2)
is
begin
 update NhanVien
 set
  MaQuan=update_maquan,
  MaCV=update_macv,
  TenNV=update_tennv,
  username=update_username,
  password=update_password
 where MaNV=v_id;
exception
  when NO_DATA_FOUND then
    dbms_output.put_line('Nhan vien khong ton tai');
end;

/*==============================================================*/
/* THEM, XOA, SUA NHA XUAT BAN                                        */
/*==============================================================*/
alter table NHAXUATBAN
add isDelete number;


--Them NXB
create or replace procedure themnxb(
  v_manxb varchar2,
  v_tennxb varchar2)
is
begin
  insert into NhaXuatBan(MaNXB, TenNXB,isDelete) 
  values(v_manxb, v_tennxb,0);
end;

/*----------test----------*/
/*begin
themnxb('NXBTRE','Nha Xuat Ban Tre');
end;
select * from NhaXuatBan;*/

--Xoa NXB
create or replace procedure xoanxb(v_id varchar2)
is
begin
  update  NHAXUATBAN set isDelete=1 where manxb=v_id;
exception
  when NO_DATA_FOUND then
    dbms_output.put_line('Nha xuat ban khong ton tai');
end;

--Sua NXB
create or replace procedure suanxb (
  v_id varchar2,
  update_tennxb varchar2)
is
begin
 update NhaXuatBan
 set
  TenNXB=update_tennxb
 where MaNXB=v_id;
exception
  when NO_DATA_FOUND then
    dbms_output.put_line('Nha xuat ban khong ton tai');
end;

/*==============================================================*/
/* THEM, XOA, SUA AN PHAM                                        */
/*==============================================================*/
create sequence auto_id_anpham
  increment by 1
  start with 1
  nomaxvalue
  nocycle
  cache 2;


--Them an pham
create or replace procedure themanpham(
  v_manxb varchar2,
  v_tenap varchar2,
  v_giaap number)
is
begin
  insert into ANPHAM(MaAP, MaNXB, TenAP,gia) 
  values('AP'||auto_id_anpham.nextval, v_manxb, v_tenap,v_giaap);
exception
  when NO_DATA_FOUND then
    dbms_output.put_line('Khong ton tai du lieu');
end;

/*----------test----------*/
/*begin
themanpham('Tre','Bao Tuoi Tre');
end;
select * from anpham;*/

--Xoa NXB
create or replace procedure xoaanpham(v_id varchar2)
is
begin
  delete from AnPham
  where MaAP=v_id;
exception
  when NO_DATA_FOUND then
    dbms_output.put_line('An pham khong ton tai');
end;

--Sua NXB
create or replace procedure suaanpham (
  v_id varchar2,
  update_manxb varchar2,
  update_tenap varchar2,
  update_giaap number)
is
begin
 update AnPham
 set
  MaNXB=update_manxb,
  TenAP=update_tenap,
  gia=update_giaap
 where MaAP=v_id;
exception
  when NO_DATA_FOUND then
    dbms_output.put_line('Khong ton tai an pham');
end;
commit
/*==============================================================*/
/* THEM, XOA, SUA GOM (CTHD)                                    */
/*==============================================================*/
alter table CTHD
  add DonGia number;
alter table ANPHAM
  add Gia number;

  /*==============================================================*/
/* CAC CAU LENH TRIGGER                                    */
/*==============================================================*/
create or replace trigger NgayKetThuc_NgayLap
  before insert or update on CTHD
    for each row
    declare v_ngaylap HoaDon.NgayLap%type;
begin
  select NgayLap into v_ngaylap
  from HoaDon HD
  where HD.MaHD=:new.MaHD;
  if (:new.NgayKetThuc < v_ngaylap) then
    RAISE_APPLICATION_ERROR(-20000,'Ngay ket thuc phai lon hon ngay lap');
  end if;
end;

create or replace trigger NgayLap_NgayKetThuc
  before insert or update on HoaDon
    for each row
    declare v_ngayketthuc CTHD.NgayKetThuc%type;
begin
  select distinct NgayKetThuc into v_ngayketthuc
  from CTHD G
  where G.MaHD=:new.MaHD;
  if (:new.NgayLap > v_ngayketthuc) then
    RAISE_APPLICATION_ERROR(-20001,'Ngay lap phai nho hon ngay ket thuc');
  end if;
end;

create or replace trigger NgayLap_NgayVaoLam
  before insert or update on HoaDon
    for each row
    declare v_ngayvaolam NhanVien.NgayVaoLam%type;
begin
  select NgayVaoLam into v_ngayvaolam
  from NhanVien NV
  where NV.MaNV=:new.MaNV;
  if(:new.NgayLap < v_ngayvaolam) then
    RAISE_APPLICATION_ERROR(-20002,'Ngay lap phai lon hon ngay vao lam');
  end if;
end;

create or replace trigger NgayVaoLam_NgayLap
  before insert or update on NhanVien
    for each row
    declare v_ngaylap HoaDon.NgayLap%type;
begin
  select MAX(distinct NgayLap) into v_ngaylap
  from HoaDon HD
  where HD.MaNV=:new.MaNV
  group by HD.NgayLap;
  if(:new.NgayVaoLam > v_ngaylap) then
    RAISE_APPLICATION_ERROR(-20003,'Ngay vao lam phai nho hon ngay lap');
  end if;
end;

/*Trigger T?ng ti?n hóa ??n*/
Create or replace trigger TRG_CTHD_INSERT
AFTER INSERT ON CTHD
FOR EACH ROW
BEGIN
    UPDATE HOADON SET TONGTIEN = TONGTIEN+:NEW.DONGIA where mahd=:NEW.MAHD;
END;

CREATE or REPLACE trigger TRG_CTHD_DELETE
AFTER DELETE ON CTHD
FOR EACH ROW
BEGIN
    UPDATE HOADON SET TONGTIEN = TONGTIEN-:OLD.DONGIA where mahd=:OLD.MAHD;
END;

  /*==============================================================*/
/* TAO BANG GIAO HANG CHO SHIPPER                                  */
/*==============================================================*/

Create or replace Procedure giaohang_table(
    giaohang_cursor OUT SYS_REFCURSOR,
    v_maquan in varchar2,
    v_tenkhach out varchar2,
    v_diachi out varchar2,
    v_tenap out varchar2
    )
As
BEGIN
    OPEN giaohang_cursor FOR
        select kh.HOTEN,kh.DIACHI,ap.TENAP into v_tenkhach,v_diachi,v_tenap
        from ((KHACHHANG kh join HOADON hd on kh.MAKH = hd.MAKH)
            join CTHD c on c.MAHD = hd.MAHD)join ANPHAM ap on ap.MAAP = c.MAAP
        where (kh.MAQUAN = v_maquan) and (c.NGAYKETTHUC > TO_DATE(SYSDATE,'DD-MM-YYYY')) and (hd.THANHTOAN=1) 
        order by kh.HOTEN;
END giaohang_table;

/* B?ng ki?m kho */

Create or replace Procedure kiemkho_table(
    kiemkho_cursor out SYS_REFCURSOR,
    v_mabao out varchar2,
    v_tenbao out varchar2,
    v_soluong number
    )
As
Begin
    OPEN kiemkho_cursor FOR
        select c.MAAP, a.TENAP, Count(c.MAAP)
        from (CTHD c join HOADON h on c.mahd=h.mahd)
            join ANPHAM a on c.maap = a.maap
        where c.NGAYKETTHUC > TO_DATE(SYSDATE,'DD-MM-YYYY') and h.THANHTOAN=1
        group by c.MAAP,a.TENAP
        order by count(c.MAAP);
END kiemkho_table;

/*----------test----------*/
/*declare
  a table_giaohang;
begin
  taothongtingiaohang('Q1',a);
end;*/

  /*==============================================================*/
/* TAO USER TIEP TAN                                */
/*==============================================================*/
create user user02 identified by 123;
grant connect to user02;
grant select, insert, update, delete on KhachHang to user02;
grant select, insert, update, delete on HoaDon to user02;
grant select, insert, update, delete on CTHD to user02;
grant select on AnPham to user02;

 /*==============================================================*/
/* TAO USER QUAN LY KHO                                */
/*==============================================================*/
create user user03 identified by 123;
grant connect to user03;
grant select on KhachHang to user03;
grant select on HoaDon to user03;
grant select on CTHD to user03;
grant select, insert, update on AnPham to user03;

 /*==============================================================*/
/* TAO USER GIAO HANG(SHIPPER)                                */
/*==============================================================*/
create user user04 identified by 123;
grant connect to user04;
grant select on KhachHang to user04;
grant select on QuanHuyen to user04;