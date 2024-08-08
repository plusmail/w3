package kroryi.w3.todo;

import kroryi.w3.todo.dao.TodoDAO;
import kroryi.w3.todo.dto.TodoDTO;
import kroryi.w3.todo.util.MapperUtil;
import kroryi.w3.todo.vo.TodoVO;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 싱글톤 클래스 사용 방법
// 객체 생성을 한번하고 있으면 기존 객체를 사용하고
// 없으면 새로 생성하는게 싱글톤 패턴
// new 연산자를 사용 안해도 됨.

@Log4j2
public enum TodoService {
    INSTANCE;

    private TodoDAO dao;
    private final ModelMapper modelMapper;

    TodoService(){
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();

    }

    public void register(TodoDTO todoDTO) throws SQLException {
        //DTO -> VO 맵핑 작업 진행
        // 지금은 DTO VO의 멤버 필드 이름이 같은 경우
        TodoVO todoVo = modelMapper.map(todoDTO, TodoVO.class);
//        System.out.println("register: " + todoVo);
        log.info("등록합니다. todo: {}", todoVo);

        dao.insert(todoVo);
// 만약 필드 명이 다르면
//        UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//        @Mapping(source = "firstName", target = "givenName")
//        @Mapping(source = "lastName", target = "familyName")
//        UserVO dtoToVo(UserDTO userDTO);
//
//        @Mapping(source = "givenName", target = "firstName")
//        @Mapping(source = "familyName", target = "lastName")
//        UserDTO voToDto(UserVO userVO);

    }


    public List<TodoDTO> listAll() throws SQLException {
        List<TodoVO> voList = dao.selectAll();

        List<TodoDTO> dtoList = voList.stream().map(
                vo -> modelMapper.map(vo, TodoDTO.class)).collect(Collectors.toList()
        );

        return dtoList;
    }

    public List<TodoDTO> getList() {
        //추후 DB에서 목록을 불러오는 로직이 작성 될 부분
        //현재는 가짜데이터
        List<TodoDTO> todoDTOS = IntStream.range(0, 10).mapToObj( (i)->{
            TodoDTO dto = new TodoDTO();
            dto.setTno((long)i);
            dto.setTitle("할일.... " +  i);
            dto.setDueDate(LocalDate.now());
            return dto;
        }).collect(Collectors.toList());

        return todoDTOS;

    }
//    private TodoDTO convertToDTO(TodoVO vo) {
//        TodoDTO dto = new TodoDTO();
//        dto.setId(vo.getId());
//        dto.setTitle(vo.getTitle());
//        dto.setDescription(vo.getDescription());
//        dto.setDueDate(vo.getDueDate());
//        dto.setCompleted(vo.isCompleted());
//        // 필요한 경우 다른 필드도 추가로 변환
//        return dto;
//    }

    public TodoDTO get(Long tno) throws SQLException {
        log.info("tno: {}", tno);
        TodoVO todoVO = dao.selectOne(tno);

        return modelMapper.map(todoVO, TodoDTO.class);
    }

    public void modify(TodoDTO todoDTO) throws SQLException {
        log.info("todoDTO : {}", todoDTO);
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        dao.updateOne(todoVO);

    }

    public void remove(Long tno) throws SQLException {
        log.info("삭제번호는: {}", tno);
        dao.deleteOne(tno);
    }

}
