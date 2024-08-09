package kroryi.w3.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//(G,S,H)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String mid;
    private String mpw;
    private String mname;

}
