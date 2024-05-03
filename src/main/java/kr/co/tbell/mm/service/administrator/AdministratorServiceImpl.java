package kr.co.tbell.mm.service.administrator;

import kr.co.tbell.mm.dto.administrator.*;
import kr.co.tbell.mm.entity.administrator.Administrator;
import kr.co.tbell.mm.entity.administrator.Role;
import kr.co.tbell.mm.exception.UserAlreadyExistsException;
import kr.co.tbell.mm.repository.administrator.AdministratorRepository;
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
    public ResCreateAdministrator createAdministrator(ReqCreateAdministrator reqCreateAdministrator) {
        Optional<Administrator> adminOptional =
                administratorRepository.findByUsername(reqCreateAdministrator.getUsername());

        if (adminOptional.isPresent()) {
            throw new UserAlreadyExistsException("Admin already exists with username: "
                    + reqCreateAdministrator.getUsername());
        }

        Administrator admin = Administrator
                .builder()
                .username(reqCreateAdministrator.getUsername())
                .password(passwordEncoder.encode(reqCreateAdministrator.getPassword()))
                .role(Role.ROLE_ADMIN)
                .build();

        Administrator savedAdmin = administratorRepository.save(admin);

        return new ResCreateAdministrator(savedAdmin.getId(), savedAdmin.getUsername(), savedAdmin.getRole());
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
