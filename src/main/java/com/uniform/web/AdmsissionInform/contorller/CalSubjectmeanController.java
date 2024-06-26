package com.uniform.web.AdmsissionInform.contorller;

import com.uniform.web.AdmsissionInform.AdmissionInformService.CalSubjectMeanService;
import com.uniform.web.AdmsissionInform.AdmissionInformService.mappingJson.WrappingSubjectScore;
import com.uniform.web.AdmsissionInform.Repository.AverageRepository;
import com.uniform.web.AdmsissionInform.Repository.ScoreRepository;
import com.uniform.web.AdmsissionInform.dto.AverageDTO;
import com.uniform.web.AdmsissionInform.entity.AverageEntity;
import com.uniform.web.member.entity.MemberEntity;
import com.uniform.web.member.repository.MemberRepository;
import com.uniform.web.member.sessionKey.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CalSubjectmeanController {
    private final ScoreRepository scoreRepository;
    private final CalSubjectMeanService calSubjectMeanService;
    private final MemberRepository memberRepository;
    private final AverageRepository averageRepository;
    @PostMapping("/average")
    public ResponseEntity<?> calMean(@RequestBody WrappingSubjectScore wrappingSubjectScore, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"data\" : \"Invalid Session\"");
        }
        String member = (String) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"data\" : \"Invalid Session\"");
        }
        if (calSubjectMeanService.calAllSubject(wrappingSubjectScore,memberRepository.findAllByMemberId(member)) == -1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"data\" : \"Error\"");
        }
        return ResponseEntity.status(HttpStatus.OK).body("\"data\" : \"Save\"");

    }
    @GetMapping("/average")
    public ResponseEntity<?> getMean(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"data\" : \"Invalid Session\"");
        }
        String member = (String) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\"data\" : \"Invalid Session\"");
        }
        if (member.equals("abc123")) {
            AverageEntity averageEntity = averageRepository.findAllByAverageIdAndUserId(35, memberRepository.findAllByMemberId(member));
            AverageDTO averageDTO = AverageDTO.toAverageDTO(averageEntity);
            return ResponseEntity.status(HttpStatus.OK).body(averageDTO);
        } else if (member.equals("wndyd123")) {
            AverageEntity averageEntity = averageRepository.findAllByAverageIdAndUserId(38, memberRepository.findAllByMemberId(member));
            AverageDTO averageDTO = AverageDTO.toAverageDTO(averageEntity);
            return ResponseEntity.status(HttpStatus.OK).body(averageDTO);
        }
        else{
            List<AverageEntity> averageEntities = averageRepository.findAllByAverageId(memberRepository.findAllByMemberId(member));
            int averageId = averageEntities.stream()
                    .mapToInt(AverageEntity::getAverageId)
                    .max().getAsInt();
            AverageEntity averageEntity = averageRepository.findAllByAverageIdAndUserId(averageId, memberRepository.findAllByMemberId(member));
            AverageDTO averageDTO = AverageDTO.toAverageDTO(averageEntity);
            return ResponseEntity.status(HttpStatus.OK).body(averageDTO);
        }
    }
}
