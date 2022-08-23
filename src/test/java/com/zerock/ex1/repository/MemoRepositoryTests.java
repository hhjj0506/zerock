package com.zerock.ex1.repository;

import com.zerock.ex1.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {
    @Autowired
    MemoRepository memoRepository;

    @Test // 잘 만들어졌는지 테스트
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test // insert 테스트
    public void testInsertDummies() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }

    @Test // findById 조회 테스트
    public void testSelect() {
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("======================================");

        // findById는 Optional 타입으로 반환되기 때문에 결과가 존재하는지 체크한다
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test // getOne으로 하는 조회 테스트
    public void testSelect2() {
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("======================================");
        // getOne은 리턴값이 해당 객체인데 실제 객체가 필요한 순간까지 SQL을 실행하지는 않는다
        System.out.println(memo);
    }

    @Test // 수정 test
    public void testUpdate() {
        // 100번 객체의 내용을 수정한다.
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test // 100번 객체 삭제 테스트
    public void testDelete() {
        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        // PageRequest는 protected 생성자로 선언되어 new를 사용할 수 없어 of()를 사용한다
        // 페이지 처리는 0부터 시작하여야 한다
        // 1페이지 10개를 가져온다
        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("-------------------------------");
        System.out.println("Total Pages: " + result.getTotalPages());
        System.out.println("Total Elements: " + result.getTotalElements());
        System.out.println("Page #: " + result.getNumber());
        System.out.println("Page Size: " + result.getSize());
        System.out.println("has next page?: " + result.hasNext());
        System.out.println("first page?: " + result.isFirst());
        System.out.println("-------------------------------");
        for (Memo memo : result.getContent()) {
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }
}
