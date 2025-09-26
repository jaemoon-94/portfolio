CREATE TABLE ctuser (
   user_num NUMBER NOT NULL,
   user_id VARCHAR2(12) UNIQUE NOT NULL,
   auth NUMBER(1) NOT NULL,
   CONSTRAINT PK_USER PRIMARY KEY (user_num)
);

CREATE TABLE user_detail (
   user_num NUMBER NOT NULL,
   user_name VARCHAR2(20) NOT NULL,
   user_pw VARCHAR2(12) NOT NULL,
   user_phone VARCHAR2(15) UNIQUE NOT NULL,
   user_email VARCHAR2(50) UNIQUE NOT NULL,
   photo VARCHAR2(400) NULL,
   reg_date DATE DEFAULT SYSDATE NOT NULL,
   gender NUMBER(1) NOT NULL,
   CONSTRAINT PK_USER_DETAIL PRIMARY KEY (user_num),
   CONSTRAINT FK_USER_TO_USER_DETAIL FOREIGN KEY (user_num) REFERENCES ctuser (user_num)
);




CREATE TABLE attraction (
   attraction_num NUMBER NOT NULL,
   region_num NUMBER NOT NULL,
   name VARCHAR2(200) NOT NULL,
   description VARCHAR2(4000) NOT NULL,
   address VARCHAR2(255) NOT NULL,
   latitude NUMBER(10,7) NULL,
   longitude NUMBER(10,7) NULL,
   image_url VARCHAR2(255) NULL,
   CONSTRAINT PK_ATTRACTION PRIMARY KEY (attraction_num),
   CONSTRAINT FK_REGION_TO_ATTRACTION FOREIGN KEY (region_num) REFERENCES region (region_num)
);

CREATE TABLE accom_reserv (
   reserv_num NUMBER NOT NULL,
   user_num NUMBER NOT NULL,
   accom_num NUMBER NOT NULL,
   reserv_code VARCHAR2(50) NOT NULL,
   CONSTRAINT PK_RESERV PRIMARY KEY (reserv_num),
   CONSTRAINT FK_USER_TO_RESERV FOREIGN KEY (user_num) REFERENCES ctuser (user_num),
   CONSTRAINT FK_ACCOM_TO_RESERV FOREIGN KEY (accom_num) REFERENCES accom (accom_num)
);

CREATE TABLE reserv_detail (
   reserv_num NUMBER NOT NULL,
   request VARCHAR2(1000) NULL,
   payment_staus NUMBER(1) NOT NULL,
   cancel_reason VARCHAR2(1000) NULL,
   cancel_date DATE NULL,
   check_in_date DATE NOT NULL,
   check_out_date DATE NOT NULL,
   reserv_status NUMBER(1) NOT NULL,
   people_count NUMBER(2) NOT NULL,
   total_price NUMBER(10) NOT NULL,
   reserv_date DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_RESERV_DETAIL PRIMARY KEY (reserv_num),
   CONSTRAINT FK_RESERV_TO_DETAIL FOREIGN KEY (reserv_num) REFERENCES accom_reserv (reserv_num)
);

CREATE TABLE wishlist (
   wish_num NUMBER NOT NULL,
   user_num NUMBER NOT NULL,
   accom_num NUMBER NOT NULL,
   CONSTRAINT PK_WISHLIST PRIMARY KEY (wish_num),
   CONSTRAINT FK_USER_TO_WISHLIST FOREIGN KEY (user_num) REFERENCES ctuser (user_num),
   CONSTRAINT FK_ACCOM_TO_WISHLIST FOREIGN KEY (accom_num) REFERENCES accom (accom_num)
);

CREATE TABLE chatroom (
   chatroom_num NUMBER NOT NULL,
   group_num NUMBER NOT NULL,
   chatroom_type NUMBER(20) NOT NULL,
   create_date DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_CHATROOM PRIMARY KEY (chatroom_num),
   CONSTRAINT FK_GROUP_TO_CHATROOM FOREIGN KEY (group_num) REFERENCES travelgroup (group_num)
);

CREATE TABLE chat_message (
   message_num NUMBER NOT NULL,
   chatroom_num NUMBER NOT NULL,
   sender_num NUMBER NOT NULL,
   message VARCHAR2(2000) NOT NULL,
   message_date DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_CHAT_MESSAGE PRIMARY KEY (message_num),
   CONSTRAINT FK_CHATROOM_TO_MESSAGE FOREIGN KEY (chatroom_num) REFERENCES chatroom (chatroom_num),
   CONSTRAINT FK_USER_TO_CHAT_MESSAGE FOREIGN KEY (sender_num) REFERENCES ctuser (user_num)
);

CREATE TABLE notification (
   noti_num NUMBER NOT NULL,
   noti_title VARCHAR2(100) NOT NULL,
   noti_content VARCHAR2(2000) NOT NULL,
   noti_date DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_NOTIFICATION PRIMARY KEY (noti_num)
);

CREATE TABLE promotion (
   promotion_num NUMBER NOT NULL,
   accom_num NUMBER NOT NULL,
   title VARCHAR2(200) NOT NULL,
   content VARCHAR2(4000) NOT NULL,
   discount_value NUMBER(10) NOT NULL,
   start_date DATE NOT NULL,
   end_date DATE NOT NULL,
   is_active NUMBER(1) NOT NULL,
   CONSTRAINT PK_PROMOTION PRIMARY KEY (promotion_num),
   CONSTRAINT FK_ACCOM_TO_PROMOTION FOREIGN KEY (accom_num) REFERENCES accom (accom_num)
);

CREATE TABLE answer (
   answer_num NUMBER NOT NULL,
   inquiry_num NUMBER NOT NULL,
   answer_content VARCHAR2(500) NOT NULL,
   answer_date DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_ANSWER PRIMARY KEY (answer_num),
   CONSTRAINT FK_INQUIRY_TO_ANSWER FOREIGN KEY (inquiry_num) REFERENCES inquiry (inquiry_num)
);

CREATE TABLE inquiry (
   inquiry_num NUMBER NOT NULL,
   user_num NUMBER NOT NULL,
   accom_num NUMBER NOT NULL,
   inq_content VARCHAR2(500) NOT NULL,
   inq_status NUMBER(1) NOT NULL,
   inq_date DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_INQUIRY PRIMARY KEY (inquiry_num),
   CONSTRAINT FK_USER_TO_INQUIRY FOREIGN KEY (user_num) REFERENCES ctuser (user_num),
   CONSTRAINT FK_ACCOM_TO_INQUIRY FOREIGN KEY (accom_num) REFERENCES accom (accom_num)
);



CREATE TABLE travelgroup (
   group_num NUMBER NOT NULL,
   creator_num NUMBER NOT NULL,
   accom_num NUMBER NULL,
   tg_title VARCHAR2(200) NOT NULL,
   content VARCHAR2(4000) NOT NULL,
   create_date DATE DEFAULT SYSDATE NOT NULL,
   travel_date_start DATE NULL,
   travel_date_end DATE NULL,
   max_member_count NUMBER(1) NULL,
   region_num NUMBER NOT NULL,
   status NUMBER(1) NOT NULL,
   CONSTRAINT PK_TRAVELGROUP PRIMARY KEY (group_num),
   CONSTRAINT FK_USER_TO_TRAVELGROUP FOREIGN KEY (creator_num) REFERENCES ctuser (user_num),
   CONSTRAINT FK_ACCOM_TO_TRAVELGROUP FOREIGN KEY (accom_num) REFERENCES accom (accom_num),
   CONSTRAINT FK_REGION_TO_TRAVELGROUP FOREIGN KEY (region_num) REFERENCES region (region_num)
);

CREATE TABLE group_member (
   member_num NUMBER NOT NULL,
   group_num NUMBER NOT NULL,
   user_num NUMBER NOT NULL,
   join_date DATE NULL,
   CONSTRAINT PK_GROUP_MEMBER PRIMARY KEY (member_num),
   CONSTRAINT FK_GROUP_TO_MEMBER FOREIGN KEY (group_num) REFERENCES travelgroup (group_num),
   CONSTRAINT FK_USER_TO_GROUP_MEMBER FOREIGN KEY (user_num) REFERENCES ctuser (user_num) ON DELETE CASCADE;
);

CREATE TABLE accom_report (
   report_num NUMBER NOT NULL,
   accom_num NUMBER NOT NULL,
   user_num NUMBER NOT NULL,
   reason VARCHAR2(500) NOT NULL,
   report_date DATE DEFAULT SYSDATE NOT NULL,
   status NUMBER(1) NOT NULL,
   CONSTRAINT PK_REPORT PRIMARY KEY (report_num),
   CONSTRAINT FK_ACCOM_TO_REPORT FOREIGN KEY (accom_num) REFERENCES accom (accom_num),
   CONSTRAINT FK_USER_TO_REPORT FOREIGN KEY (user_num) REFERENCES ctuser (user_num)
);

CREATE TABLE accom_review (
   review_num NUMBER NOT NULL,
   reserv_num NUMBER UNIQUE NOT NULL,
   rating NUMBER(1) NOT NULL,
   content VARCHAR2(2000) NOT NULL,
   review_date DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_REVIEW PRIMARY KEY (review_num),
   CONSTRAINT FK_RESERV_TO_REVIEW FOREIGN KEY (reserv_num) REFERENCES accom_reserv (reserv_num)
);

CREATE TABLE payment (
   payment_num NUMBER NOT NULL,
   reserv_num NUMBER NOT NULL,
   amount NUMBER(10) NOT NULL,
   payment_status VARCHAR2(20) NOT NULL,
   payment_method VARCHAR2(20) NOT NULL,
   payment_date DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_PAYMENT PRIMARY KEY (payment_num),
   CONSTRAINT FK_RESERV_TO_PAYMENT FOREIGN KEY (reserv_num) REFERENCES accom_reserv (reserv_num)
);

