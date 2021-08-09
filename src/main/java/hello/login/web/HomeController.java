package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManger sessionManger;

    // @GetMapping("/")
    public String home() {
        return "homes";
    }

    // @GetMapping("/")
    public String loginHome(@CookieValue(name = "memberId", required = false) Long memberId, Model model){
        if(memberId == null) {
            return "homes";
        }

        // 로그인
        Member loginMember = memberRepository.findById(memberId);

        if(loginMember == null) {
            return "homes";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    // @GetMapping("/")
    public String loginHomeV2(HttpServletRequest request, Model model){

        // 세션 관리자에 저장된 회원 정보를 조회
        Member loginMember = (Member)sessionManger.getSession(request);

        if(loginMember == null) {
            return "homes";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String loginHomeV3(HttpServletRequest request, Model model){

        // 세션 관리자에 저장된 회원 정보를 조회
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "homes";
        }

        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션에 회원데이터가 없으면 home
        if(loginMember == null) {
            return "homes";
        }
        
        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}