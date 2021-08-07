package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;// static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save : {}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        ArrayList<Member> members = new ArrayList<>(store.values());
        return members;
    }

    public Optional<Member> findByLoginId(String loginId){
        return findAll().stream().filter(m -> m.getLoginId().equals(loginId)).findFirst();
    }

    public void clear(){
        store.clear();
    }
}
