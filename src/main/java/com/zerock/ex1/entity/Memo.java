package com.zerock.ex1.entity;

import lombok.*;

import javax.persistence.*;

@Entity // 엔티티 객체를 위한 anno.
@Table(name = "tbl_memo")
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {

    @Id //@Entity가 붙은 클래스에서 PK를 무조건 지정해야하는데, @Id로 지정한다
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동생성밸류인데 여기서는 PK 자동 생성. IDENTITY는 Autoincrement
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;
}
