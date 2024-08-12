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


    public void updateUuid(String mid,String uuid) throws SQLException {
        String sql = "update tbl_member set uuid=? where mid=?";
        Connection conn = ConnectionUtil.INSTANCE.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, uuid);
        ps.setString(2, mid);
        ps.executeUpdate();

    }

    public MemberVO selectUUID(String uuid) throws SQLException {
        String sql = "select mid,mpw,mname, uuid from tbl_member where uuid=?";

        Connection connection = ConnectionUtil.INSTANCE.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, uuid);
        ResultSet rs = ps.executeQuery();
        rs.next();
        MemberVO memberVO = MemberVO.builder()
                .mid(rs.getString(1))
                .mpw(rs.getString(2))
                .mname(rs.getString(3))
                .uuid(rs.getString(4))
                .build();

        return memberVO;
    }
}
