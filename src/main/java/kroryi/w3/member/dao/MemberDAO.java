package kroryi.w3.member.dao;

import kroryi.w3.member.vo.MemberVO;
import kroryi.w3.todo.dao.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

    public MemberVO getWithPassword(String mid,String mpw) throws SQLException {
        String sql = "select mid,mpw,mname from tbl_member where mid=? and mpw=?";
        MemberVO memberVO = null;
        Connection conn = ConnectionUtil.INSTANCE.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, mid);
        ps.setString(2, mpw);
        ResultSet rs = ps.executeQuery();
        rs.next();
        memberVO = MemberVO.builder()
                .mid(rs.getString(1))
                .mpw(rs.getString(2))
                .mname(rs.getString(3))
                .build();

        return memberVO;
    }
}
