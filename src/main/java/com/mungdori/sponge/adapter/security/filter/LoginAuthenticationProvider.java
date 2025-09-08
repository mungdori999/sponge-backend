package com.mungdori.sponge.adapter.security.filter;

import com.mungdori.sponge.adapter.security.LoginTypeAuthenticationToken;
import com.mungdori.sponge.adapter.security.SecurePasswordEncoder;
import com.mungdori.sponge.adapter.security.UserDetailsImpl;
import com.mungdori.sponge.application.owner.required.OwnerRepository;
import com.mungdori.sponge.application.trainer.required.TrainerRepository;
import com.mungdori.sponge.domain.shared.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mungdori.sponge.adapter.security.utils.LoginType.OWNER;
import static com.mungdori.sponge.adapter.security.utils.LoginType.TRAINER;


@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private final OwnerRepository ownerRepository;
    private final TrainerRepository trainerRepository;
    private final SecurePasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // CustomAuthenticationToken으로 캐스팅
        LoginTypeAuthenticationToken authToken = (LoginTypeAuthenticationToken) authentication;

        String email = authToken.getName();
        String password = (String) authToken.getCredentials();
        String loginType = authToken.getLoginType();

        if (loginType == null) {
            throw new BadCredentialsException("loginType is required");
        }

        UserDetailsImpl userDetails;

        if (OWNER.name().equalsIgnoreCase(loginType)) {
            userDetails = ownerRepository.findByEmail(new Email(email))
                    .map(owner -> new UserDetailsImpl(owner.getEmail().address(), owner.getPasswordHash(),
                            List.of(new SimpleGrantedAuthority(OWNER.name()))))
                    .orElseThrow(() -> new BadCredentialsException("Owner not found"));

        } else if (TRAINER.name().equalsIgnoreCase(loginType)) {
            userDetails = trainerRepository.findByEmail(new Email(email))
                    .map(trainer -> new UserDetailsImpl(trainer.getEmail().address(), trainer.getPasswordHash(),
                            List.of(new SimpleGrantedAuthority(TRAINER.name()))))
                    .orElseThrow(() -> new BadCredentialsException("Trainer not found"));
        } else {
            throw new BadCredentialsException("Invalid loginType");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // 인증 성공 → Authentication 객체 반환
        return new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LoginTypeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
