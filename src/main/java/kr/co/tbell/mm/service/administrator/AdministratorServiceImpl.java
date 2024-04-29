package kr.co.tbell.mm.service.administrator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.tbell.mm.dto.administrator.*;
import kr.co.tbell.mm.entity.Administrator;
import kr.co.tbell.mm.repository.administrator.AdministratorRepository;
import kr.co.tbell.mm.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService, UserDetailsService {

    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResCreateAdministrator createAdministrator(ReqCreateAdministrator reqCreateAdministrator)
            throws InstanceAlreadyExistsException {
        Optional<Administrator> adminOptional =
                administratorRepository.findByUsername(reqCreateAdministrator.getUsername());

        if (adminOptional.isPresent()) {
            throw new InstanceAlreadyExistsException("Admin already exists with username: "
                    + reqCreateAdministrator.getUsername());
        }

        Administrator admin = Administrator
                .builder()
                .username(reqCreateAdministrator.getUsername())
                .password(passwordEncoder.encode(reqCreateAdministrator.getPassword()))
                .build();

        Administrator savedAdmin = administratorRepository.save(admin);

        return new ResCreateAdministrator(savedAdmin.getId(), savedAdmin.getUsername());
    }

    @Override
    public ResLogin login(ReqLogin reqLogin, HttpServletRequest request) {
        Optional<Administrator> byUsername = administratorRepository.findByUsername(reqLogin.getUsername());
        if (byUsername.isEmpty()) {
            return null;
            // TODO: 언체크 예외로 유저네임이 없다고 던져서 받는쪽에서 공통처리로 처리할 수 있게 해보자.
        }

        Administrator administrator = byUsername.get();
        if (!passwordEncoder.matches(reqLogin.getPassword(), administrator.getPassword())) {
            return null;
            // TODO: 언체크 예외로 패스워드가 잘못됐다고 던져서 받는쪽에서 공통처리로 처리할 수 있게 해보자.
        }

        HttpSession session = request.getSession();
        session.setAttribute(Constants.SESSION_ADMIN_ID, administrator.getId());

        return new ResLogin(session.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Administrator> byUsername = administratorRepository.findByUsername(username);
        if (byUsername.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomAdministratorDetails(byUsername.get());
    }
}
