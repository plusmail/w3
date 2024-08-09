package kroryi.w3.member;

import kroryi.w3.member.dao.MemberDAO;
import kroryi.w3.member.dto.MemberDTO;
import kroryi.w3.member.vo.MemberVO;
import kroryi.w3.todo.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;

@Log4j2
public enum MemberService {
    INSTANCE;

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService(){
        dao = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    public MemberDTO login(String mid, String mpw) throws SQLException {
        MemberVO vo = dao.getWithPassword(mid, mpw);
        MemberDTO dto = modelMapper.map(vo, MemberDTO.class);
        return dto;
    }

}
