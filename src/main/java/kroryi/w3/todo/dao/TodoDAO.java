package kroryi.w3.todo.dao;

import kroryi.w3.todo.vo.TodoVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    private <R> R executeQuery(String sql,
                               PreparedStatementSetter setter,
                               ResultSetExtractor<R> extractor) throws SQLException{
        try(
                Connection con = ConnectionUtil.INSTANCE.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        )
        {
            if(setter != null){
                setter.setValues(ps);
            }
            try(ResultSet rs=ps.executeQuery()){
                return extractor.extractData(rs);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void executeUpdate(String sql,
                               PreparedStatementSetter setter) throws SQLException{
        try(
                Connection con = ConnectionUtil.INSTANCE.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        )
        {
            if(setter != null){
                setter.setValues(ps);
            }
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void insert(TodoVO vo) throws SQLException {
        String sql = "insert into " + "tbl_todo(title, dueDate, finished)" + " value(?,?,?)";
        executeUpdate(sql, ps ->{
            ps.setString(1, vo.getTitle());
            ps.setDate(2, Date.valueOf(vo.getDueDate()));
            ps.setBoolean(3, vo.isFinished());
            ps.executeUpdate();
        });
    }

    public List<TodoVO> selectAll() throws SQLException {
        String sql = "select * from tbl_todo ";
        return executeQuery(sql, null, rs->{
            List<TodoVO> list = new ArrayList<TodoVO>();
            while (rs.next()) {
                TodoVO vo = TodoVO.builder()
                        .tno(rs.getLong("tno"))
                        .title(rs.getString("title"))
                        .dueDate(rs.getDate("dueDate").toLocalDate())
                        .finished(rs.getBoolean("finished"))
                        .build();
                list.add(vo);
            }
            return list;
        });
    }

    public TodoVO selectOne(long tno) throws SQLException {
        String sql = "select * from tbl_todo where tno = ?";

        //ps -> ps는 PreparedStatement의 객체 인스턴스
        return executeQuery(sql, ps-> ps.setLong(1,tno), rs->{
            if(rs.next()){
                return TodoVO.builder()
                        .tno(rs.getLong("tno"))
                        .title(rs.getString("title"))
                        .dueDate(rs.getDate("dueDate").toLocalDate())
                        .finished(rs.getBoolean("finished"))
                        .build();
            }else {
                return null;
            }

        });
    }

    public void deleteOne(Long tno) throws SQLException {
        String sql = "delete from tbl_todo where tno = ?";
        executeUpdate(sql, ps->{
            ps.setLong(1,tno);
        });
    }

    public void updateOne(TodoVO vo) throws SQLException {
        String sql = "update tbl_todo set " +
                " title = ?," +
                " dueDate = ?, " +
                " finished = ? " +
                " where tno = ?";

        executeUpdate(sql, ps->{
            ps.setString(1, vo.getTitle());
            ps.setDate(2, Date.valueOf(vo.getDueDate()));
            ps.setBoolean(3, vo.isFinished());
            ps.setLong(4, vo.getTno());
        });
    }


    public String getTime() throws SQLException {
        String now = null;
        try (Connection connection = ConnectionUtil.INSTANCE.getConnection(); PreparedStatement ps = connection.prepareStatement("select now()"); ResultSet rs = ps.executeQuery();) {
            rs.next();
            now = rs.getString(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return now;
    }

}

interface PreparedStatementSetter {
    void setValues(PreparedStatement ps) throws SQLException;
}

interface ResultSetExtractor<R> {
    R extractData(ResultSet rs) throws SQLException;
}

