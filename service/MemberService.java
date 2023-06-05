package post.study.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import post.study.dto.MemberDto;
import post.study.entity.Member;
import post.study.repository.MemberRepository;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    /**
    로그인
    회원의 아이디로 조회
     **/
//    public Member findByemailId(String emailId,String password){
//
//
//        return loginValidateByemailId(emailId,password);
//
//    }
    public Boolean loginValidateByemailId(MemberDto memberDto, String password) {

        Member findMember = memberRepository.findByemailId(memberDto.getEmailId());
        if (findMember == null)
            return false;
        if (!findMember.getPassword().equals(password)) {
            return false;
        }
//        Boolean aBoolean = validateByPassword(findMember.get(0).getPassword(), password);
//        if(aBoolean==false)
//                return null;

        return true;
    }

    //회원의 아이디를 이용해 패스워드가 알맞는지 판단
    public Boolean validateByPassword(String memberPassword, String password) {
        if (memberPassword != password)
            return false;

        return true;
    }
    /**
    회원가입
     */
    public Member join(MemberDto memberDto){
        if(validateByEmailId(memberDto.getEmailId())==false)
            return null;
        else return save(memberDto);
    }
    public Boolean validateByEmailId(String emailId){
        Member findMember = memberRepository.findByemailId(emailId);
        if(findMember==null)
            return true;

        else return false;
    }

    public Member save(MemberDto memberDto){
        Member member=new Member();
        member.setEmailId(memberDto.getEmailId());
        member.setUsername(memberDto.getUsername());
        member.setPassword(memberDto.getPassword());
        member.setAge(memberDto.getAge());
        memberRepository.save(member);
        return member;

    }
    /**
    회원 조회
     **/
    public Member findMember(String emailId){
        Member member = memberRepository.findByemailId(emailId);
        return member;

    }

}
