package kr.travelgroup.action;

import java.sql.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.travelgroup.dao.TravelGroupDAO;
import kr.travelgroup.dao.GroupMemberDAO;
import kr.travelgroup.vo.TravelGroupVO;
import kr.travelgroup.vo.GroupMemberVO;
import kr.chat.dao.ChatRoomDAO;
import kr.chat.dao.ChatMemberDAO;
import kr.chat.vo.ChatRoomVO;
import kr.chat.vo.ChatMemberVO;

public class TravelGroupCreateAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // POST 방식 요청 체크
        if(!"POST".equals(request.getMethod())) {
            return "redirect:/travelgroup/form.do";
        }
        
        // 파라미터 가져오기
        long accom_num = 0L;
        if(request.getParameter("accom_num") != null && !request.getParameter("accom_num").equals("")) {
            accom_num = Long.parseLong(request.getParameter("accom_num"));
           
        }
        
        String tg_title = request.getParameter("tg_title");
        String tg_content = request.getParameter("tg_content");
        int max_member_count = Integer.parseInt(request.getParameter("max_member_count"));
        int region_num = Integer.parseInt(request.getParameter("region_num"));
        
        // 날짜 문자열을 Date 객체로 변환
        Date travel_date_start = null;
        Date travel_date_end = null;
        
        if(request.getParameter("travel_date_start") != null && !request.getParameter("travel_date_start").equals("")) {
            travel_date_start = Date.valueOf(request.getParameter("travel_date_start"));
        }
        
        if(request.getParameter("travel_date_end") != null && !request.getParameter("travel_date_end").equals("")) {
            travel_date_end = Date.valueOf(request.getParameter("travel_date_end"));
        }
        
        // TravelGroupVO 객체 생성
        TravelGroupVO group = new TravelGroupVO();
        group.setCreator_num(user_num);
        group.setAccom_num(accom_num);
        group.setTg_title(tg_title);
        group.setTg_content(tg_content);
        group.setTravel_date_start(travel_date_start);
        group.setTravel_date_end(travel_date_end);
        group.setMax_member_count(max_member_count);
        group.setRegion_num(region_num);
        group.setStatus(0); // 초기 상태 - 모집중(0)
        
        // 트랜잭션 처리를 위한 DAO 객체 생성
        TravelGroupDAO groupDao = TravelGroupDAO.getInstance();
        GroupMemberDAO memberDao = GroupMemberDAO.getInstance();
        ChatRoomDAO chatRoomDao = ChatRoomDAO.getInstance();
        ChatMemberDAO chatMemberDao = ChatMemberDAO.getInstance();
        
        try {
            // 1. 동행 그룹 생성
            groupDao.insertTravelGroup(group);
            long group_num = group.getGroup_num();
            
            // 2. 그룹 생성자를 멤버로 추가
            GroupMemberVO member = new GroupMemberVO();
            member.setGroup_num(group_num);
            member.setUser_num(user_num);
            memberDao.insertGroupMember(member);
            
            // 3. 채팅방 생성
            ChatRoomVO chatRoom = new ChatRoomVO();
            chatRoom.setGroup_num(group_num);
            chatRoom.setChatroom_type("동행");
            chatRoomDao.insertChatRoom(chatRoom);
            
            // 4. 채팅방에 생성자 추가
            ChatMemberVO chatMember = new ChatMemberVO();
            chatMember.setChatroom_num(chatRoom.getChatroom_num());
            chatMember.setUser_num(user_num);
            chatMemberDao.insertChatMember(chatMember);
            
            // 성공 메시지 설정
            request.setAttribute("notice_msg", "동행 그룹이 성공적으로 생성되었습니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/travelgroup/detail.do?group_num=" + group_num);
            
            return "common/alert_singlebutton.jsp";
            
        } catch(Exception e) {
            e.printStackTrace();
            
            // 실패 메시지 설정
            request.setAttribute("notice_msg", "동행 그룹 생성 중 오류가 발생했습니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/travelgroup/form.do");
            
            return "common/alert_singlebutton.jsp";
        }
    }
}