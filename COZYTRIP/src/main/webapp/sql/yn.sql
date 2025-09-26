CREATE TABLE accom (
   accom_num NUMBER NOT NULL,
   host_num NUMBER NOT NULL,
   CONSTRAINT PK_ACCOM PRIMARY KEY (accom_num),
   CONSTRAINT FK_HOST_TO_ACCOM FOREIGN KEY (host_num) REFERENCES host_detail (host_num)
);

CREATE TABLE accom_detail (
   accom_num NUMBER NOT NULL,
   region_num NUMBER NOT NULL,
   cate_num NUMBER NOT NULL,
   accom_name VARCHAR2(100) NOT NULL,
   description clob NOT NULL,
   zipcode VARCHAR2(5) NOT NULL,
   address1 VARCHAR2(120) NOT NULL,
   address2 VARCHAR2(90) NOT NULL,
   price NUMBER(10) NOT NULL,
   max_people NUMBER(2) NOT NULL,
   accom_status NUMBER(1) default 0 NOT NULL,
   accom_date DATE DEFAULT SYSDATE NOT NULL,
   accom_hits NUMBER(9) default 0 NOT NULL,
   CONSTRAINT PK_ACCOM_DETAIL PRIMARY KEY (accom_num),
   CONSTRAINT FK_ACCOM_TO_DETAIL FOREIGN KEY (accom_num) REFERENCES accom (accom_num),
   CONSTRAINT FK_CATEGORY_TO_ACCOM_DETAIL FOREIGN KEY (cate_num) REFERENCES ACCOM_CATE (cate_num),
   CONSTRAINT FK_REGION_TO_ACCOM_DETAIL FOREIGN KEY (region_num) REFERENCES region (region_num)
);
CREATE TABLE accom_cate (
   cate_num NUMBER NOT NULL,
   cate_name VARCHAR2(30) NOT NULL,
   CONSTRAINT PK_accomm_cate PRIMARY KEY (cate_num)
);
CREATE TABLE host_detail (
   host_num NUMBER NOT NULL,
   host_intro VARCHAR2(2000),
   host_date DATE DEFAULT SYSDATE NOT NULL,
   host_rating NUMBER(10),
   CONSTRAINT PK_HOST_DETAIL PRIMARY KEY (host_num),
   CONSTRAINT FK_USER_TO_HOST_DETAIL FOREIGN KEY (host_num) REFERENCES ctuser (user_num)
);

CREATE TABLE region (
   region_num NUMBER NOT NULL,
   region_name VARCHAR2(200) NOT NULL,
   CONSTRAINT PK_REGION PRIMARY KEY (region_num)
);
CREATE TABLE accom_image (
   image_num NUMBER NOT NULL,
   accom_num NUMBER NOT NULL,
   image_name VARCHAR2(255) NOT NULL,
   main NUMBER(1) NOT NULL,
   upload_date DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_ACCOM_IMAGE PRIMARY KEY (image_num),
   CONSTRAINT FK_ACCOM_TO_IMAGE FOREIGN KEY (accom_num) REFERENCES accom (accom_num)
);