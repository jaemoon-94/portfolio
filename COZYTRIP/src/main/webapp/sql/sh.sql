CREATE TABLE chat_member (
    chatmember_num NUMBER NOT NULL PRIMARY KEY,
    chatroom_num NUMBER NOT NULL,
    user_num NUMBER NOT NULL,
    join_date DATE NOT NULL,
    CONSTRAINT fk_chatroom_to_chatmember FOREIGN KEY (chatroom_num) REFERENCES chatroom(chatroom_num),
    CONSTRAINT fk_user_to_chatmember FOREIGN KEY (user_num) REFERENCES ctuser(user_num) ON DELETE CASCADE;
);