package com.example.stock.service;

import com.example.stock.exception.impl.AlreadyExistUserException;
import com.example.stock.exception.impl.NoUserIdException;
import com.example.stock.exception.impl.WrongPasswordException;
import com.example.stock.model.Auth;
import com.example.stock.persist.entity.MemberEntity;
import com.example.stock.persist.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("couldn't find user -> "+username));
    }

    public MemberEntity register(Auth.SignUp member){
        // 중복 id 확인
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if(exists){
            throw new AlreadyExistUserException();
        }
        // 암호화된 채로 저장
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.memberRepository.save(member.toEntity());
        return result;
    }
    /**
     * 로그인 인증
     * @param member
     * @return
     */
    public MemberEntity authenticate(Auth.SignIn member){
        var user =
                this.memberRepository.findByUsername(member.getUsername())
                        .orElseThrow(NoUserIdException::new);
        if(!this.passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new WrongPasswordException();
        }
        return user;
    }
}
