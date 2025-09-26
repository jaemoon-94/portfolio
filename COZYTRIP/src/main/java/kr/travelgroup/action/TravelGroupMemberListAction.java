package kr.travelgroup.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.travelgroup.dao.TravelGroupDAO;
import kr.travelgroup.dao.GroupMemberDAO;
import kr.travelgroup.vo.TravelGroupVO;
import kr.travelgroup.vo.GroupMemberVO;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;

/**
 * 동행 그룹 멤버 목록을 JSON 형식으로 반환하는 Action 클래스
 */
public class TravelGroupMemberListAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        StringBuilder sb = new StringBuilder();
        
        if(user_num == null) {
            // 로그인되지 않은 경우
            sb.append("{\"result\":\"logout\"}");
        } else {
            // 그룹 번호 가져오기
            long group_num = Long.parseLong(request.getParameter("group_num"));
            
            // 그룹 정보 가져오기
            TravelGroupDAO groupDao = TravelGroupDAO.getInstance();
            TravelGroupVO group = groupDao.getTravelGroup(group_num);
            
            if(group == null) {
                // 존재하지 않는 그룹일 경우
                sb.append("{\"result\":\"notFound\"}");
            } else {
                // 그룹 멤버 목록 가져오기
                GroupMemberDAO memberDao = GroupMemberDAO.getInstance();
                List<GroupMemberVO> memberList = memberDao.getGroupMemberList(group_num);
                
                // 사용자 정보를 포함한 멤버 목록 생성
                UserDAO userDao = UserDAO.getInstance();
                
                sb.append("{\"result\":\"success\",");
                sb.append("\"list\":[\n");
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                for(int i=0; i<memberList.size(); i++) {
                    GroupMemberVO member = memberList.get(i);
                    UserVO userVO = userDao.getUser(member.getUser_num());
                    
                    if(userVO != null) {
                        if(i > 0) sb.append(",");
                        
                        sb.append("{");
                        sb.append("\"user_num\":" + userVO.getUserNum() + ",");
                        sb.append("\"name\":\"" + userVO.getUserName() + "\",");
                        sb.append("\"photo\":\"" + (userVO.getPhoto() != null ? userVO.getPhoto() : "") + "\",");
                        sb.append("\"join_date\":\"" + dateFormat.format(member.getJoin_date()) + "\"");
                        sb.append("}");
                    }
                }
                
                sb.append("],");
                sb.append("\"creator_num\":" + group.getCreator_num());
                sb.append("}");
            }
        }
        
        out.print(sb.toString());
        out.flush();
        
        return "ajax";
    }
}
