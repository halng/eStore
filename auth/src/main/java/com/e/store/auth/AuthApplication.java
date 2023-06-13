package com.e.store.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.e.store.auth.constant.AccountRole;
import com.e.store.auth.constant.AccountStatus;
import com.e.store.auth.constant.StringConst;
import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.Role;
import com.e.store.auth.repositories.IAuthRepository;
import com.e.store.auth.repositories.IRoleRepository;

@SpringBootApplication
public class AuthApplication {

    public static void main (String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    CommandLineRunner run (IRoleRepository roleRepository, IAuthRepository authRepository,
                           PasswordEncoder passwordEncoder) {
        return args -> {
            roleRepository.save(Role.builder().roleName(AccountRole.BUYER).build());
            roleRepository.save(Role.builder().roleName(AccountRole.SELLER).build());
            roleRepository.save(Role.builder().roleName(AccountRole.STAFF).build());
            Role adminRole = roleRepository.save(Role.builder().roleName(AccountRole.ADMIN).build());
            Role superRole = roleRepository.save(Role.builder().roleName(AccountRole.SUPERADMIN).build());

            Account adminAccount = Account.builder().status(AccountStatus.ACTIVE).email("admin@estore.com").username(
                "admin").password(passwordEncoder.encode("admin")).role(adminRole).build();
            adminAccount.setCreateBy(StringConst.DEFAULT_USER);
            adminAccount.setUpdateBy(StringConst.DEFAULT_USER);

            Account superAccount = Account.builder().status(AccountStatus.ACTIVE).email("super@estore.com").username(
                "super").password(passwordEncoder.encode("super")).role(superRole).build();
            superAccount.setCreateBy(StringConst.DEFAULT_USER);
            superAccount.setUpdateBy(StringConst.DEFAULT_USER);

            authRepository.save(adminAccount);
            authRepository.save(superAccount);
        };
    }

}
