package post.study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import post.study.dto.MemberDto;
import post.study.dto.QuestionDto;
import post.study.entity.Member;
import post.study.entity.Question;
import post.study.service.MemberService;
import post.study.service.QuestionService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/post")
    public String post(HttpSession session, @RequestParam(required = false, defaultValue = "0", value = "page") int page, Model model) {
        int pageCount = 4;
        Member member = (Member) session.getAttribute("member");
        Page<Question> questionPage = questionService.getQuestionList(page);
        int totalPage = questionPage.getTotalPages();

        System.out.println("totalPage = " + totalPage);

        int displayPage=0;

        int startNum = 0;
        //페이지 라인
        //이전 페이지
        if(page>=10){

            model.addAttribute("prePage",9);
        }else if(page>1&&page<=9){
            model.addAttribute("prePage",page-1);
        }
        //다음 페이지
       if(page>=0 && page<=9){
           model.addAttribute("nextPage",10);
       }else if(page>=10&& page<totalPage-1){
           model.addAttribute("nextPage",page+1);
       }

       //페이지 리스트
        if(page>=0 && page<=9){
            model.addAttribute("startPage",0);
            model.addAttribute("endPage",9);
        }

        //displayPage
       displayPage=10;
        //startPage
        int startPage=(page/displayPage)*displayPage;

        //endPage

        int endPage=(page/displayPage)*displayPage+9;
        if(totalPage<endPage){
            endPage=totalPage;
        }
        System.out.println("startNum = " + startNum);
        model.addAttribute("username", member.getUsername());
        model.addAttribute("qList", questionPage.getContent());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("totalPage", totalPage-1);
        model.addAttribute("page", page);
        System.out.println("page = " + page);
        return "post/post";
    }

    @GetMapping("/write")
    public String write() {
        return "post/write";
    }

    @PostMapping("/write")
    public String save(HttpSession session, QuestionDto questionDto, Model model) {
        Member member = (Member) session.getAttribute("member");
        System.out.println("questionDto = " + questionDto);
        if (questionDto.getTitle() == null || questionDto.getContent() == null) {
            model.addAttribute("msg", "빈칸을 채워주세요");
            model.addAttribute("url", "/write");
        } else {

            questionService.save(member.getId(), questionDto);

            model.addAttribute("msg", "작성 완료");
            model.addAttribute("url", "/post");
        }

        return "popup";
    }

    @GetMapping("/content")
    public String content(HttpSession session, Long id, Model model) {
        Question question = questionService.findQuestion(id).get();
        session.setAttribute("questionId", id);
        Member member = (Member) session.getAttribute("member");
        session.setAttribute("myId", member.getId());
        model.addAttribute("id", question.getMember().getId());
        model.addAttribute("title", question.getTitle());
        model.addAttribute("content", question.getContent());
        model.addAttribute("myId", session.getAttribute("myId"));
        return "post/content";
    }

    @GetMapping("/update")
    public String update(QuestionDto questionDto, Model model) {
        model.addAttribute("id", questionDto.getId());
        model.addAttribute("title", questionDto.getTitle());
        model.addAttribute("content", questionDto.getContent());
        return "post/update";
    }

    @PostMapping("/update")
    public String update2(QuestionDto questionDto, Model model) {
        questionService.update(questionDto);
        model.addAttribute("msg", "작성 완료");
        model.addAttribute("url", "/post");
        return "popup";

    }

    @GetMapping("/delete")
    public String delete(HttpSession session, QuestionDto questionDto, Model model) {
        questionService.delete((Long) session.getAttribute("questionId"));
        model.addAttribute("msg", "글이 삭제되었습니다.");
        model.addAttribute("url", "/post");

        return "popup";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("msg", "로그아웃 되었습니다.");
        model.addAttribute("url", "/");

        return "popup";

    }
}//
