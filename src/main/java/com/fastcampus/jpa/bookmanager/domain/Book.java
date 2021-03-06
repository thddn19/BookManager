package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.domain.converter.BookStatusConverter;
import com.fastcampus.jpa.bookmanager.domain.listener.Auditable;
import com.fastcampus.jpa.bookmanager.repository.dto.BookStatus;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Entity 객체라고 알리기
@Where(clause = "deleted = false") // 이거 추가해버리면 deleted true만 flag해도 지워짐
//@EntityListeners(value = AuditingEntityListener.class)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;

    @OneToOne(mappedBy = "book") // 양방향 릴레이션 걸기 (책리뷰정보 ID값, Book테이블에서 안 보이게 하기)
    @ToString.Exclude // ToString의 순환참조에 의해 없는 거 참조 못하잖아
    private BookReviewInfo bookReviewInfo;

    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE} ) // INSERT에 대해서 영속성전이를 일으킴, book이 save될 때 자바 객체 상태로 있는 publisher도 같이 저장하게 해줘
    @ToString.Exclude
    private Publisher publisher;
    // private Long publisherId; 삭제

//    @ManyToMany
    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();
    //private String author;
    //private Long authorId;
        // 삭제

    // 원래는 Test에서 처럼 나열하지 않고 다음 메소드 만들어서 만듦
    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors) {
        Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }

    private boolean deleted;

    //@Convert(converter = BookStatusConverter.class) // auto-ddl에 의해 같이 생성되어서 에러 뜨기 때문에 컨버터를 정의해준다.
    private BookStatus status;
    // private int status;
//    public boolean isDisplayed() {
//        return status == 200;
//    }  // 주석처리 BookStatus가 대신함

//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;

//    @PrePersist
//    public void prePersist() {
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        this.updatedAt = LocalDateTime.now();
//    }
}
