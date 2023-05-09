package post.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import post.study.dto.MemberDto;
import post.study.dto.QuestionDto;
import post.study.entity.Member;
import post.study.entity.Question;
import post.study.repository.MemberRepository;
import post.study.repository.QuestionRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    /**
     *
     * 조회
     */
    public List<Question> findALl() {
        List<Question> questions = questionRepository.findAll();
        return questions;
    }


    public Question save(Long memberId, QuestionDto questionDto) {
        Member member = memberRepository.findById(memberId).get();
        Question question = new Question();
        question.setMember(member);
        question.setUsername(member.getUsername());
        question.setTitle(questionDto.getTitle());
        question.setContent(questionDto.getContent());

        questionRepository.save(question);

        return question;
    }

    public Optional<Question> findQuestion(Long id){
        Optional<Question> question = questionRepository.findById(id);
        return question;
    }

    public Question update(QuestionDto question){
        Question findQuestion = questionRepository.findById(question.getId()).get();
        findQuestion.setTitle(question.getTitle());
        findQuestion.setContent(question.getContent());
        questionRepository.save(findQuestion);
        System.out.println("findQuestion = " + findQuestion);
        return findQuestion;
    }

    public void delete(Long id){
        questionRepository.deleteById(id);

    }

    /**
     * 페이징
     */

    public Page<Question> getQuestionList(int page){
        PageRequest pageRequest=PageRequest.of(page,4,Sort.by(Sort.Direction.DESC,"id"));
        Page<Question> questionList = questionRepository.findAll(pageRequest);
        return questionList;

    }


}
