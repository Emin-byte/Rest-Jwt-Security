package ru.emin.security.jwt.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.emin.security.jwt.entity.Role;
import ru.emin.security.jwt.repository.RoleRepository;


@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}