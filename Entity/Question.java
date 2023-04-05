package post.study.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter


public class Question {
    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;
    private String title;
    private String content;
    private String username;
    @CreationTimestamp
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;




    public void setMember(Member member) {
        this.member = member;
        member.getQuestionList().add(this);
    }


}
