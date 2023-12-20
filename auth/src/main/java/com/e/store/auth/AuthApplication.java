package com.e.store.auth;

import com.e.store.auth.constant.AccountRole;
import com.e.store.auth.constant.AccountStatus;
import com.e.store.auth.constant.Const;
import com.e.store.auth.entity.Account;
import com.e.store.auth.entity.Role;
import com.e.store.auth.repositories.IAuthRepository;
import com.e.store.auth.repositories.IRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class AuthApplication {

  private static final Logger logger = LoggerFactory.getLogger(AuthApplication.class);

  public static void main(String[] args) {
    logger.info("Starting app ...");
    SpringApplication.run(AuthApplication.class, args);
  }

  @Bean
  CommandLineRunner run(
      IRoleRepository roleRepository,
      IAuthRepository authRepository,
      PasswordEncoder passwordEncoder) {
    logger.info("create account and role into table");
    return args -> {
      roleRepository.save(Role.builder().roleName(AccountRole.BUYER).build());
      roleRepository.save(Role.builder().roleName(AccountRole.STAFF).build());
      Role adminRole = roleRepository.save(Role.builder().roleName(AccountRole.ADMIN).build());
      Role superRole = roleRepository.save(Role.builder().roleName(AccountRole.SUPERADMIN).build());
      Role sellerRole = roleRepository.save(Role.builder().roleName(AccountRole.SELLER).build());

      Account adminAccount =
          Account.builder()
              .status(AccountStatus.ACTIVE)
              .email("admin@estore.com")
              .username("admin")
              .password(passwordEncoder.encode("admin"))
              .role(adminRole)
              .build();
      adminAccount.setCreateBy(Const.DEFAULT_USER);
      adminAccount.setUpdateBy(Const.DEFAULT_USER);

      Account superAccount =
          Account.builder()
              .status(AccountStatus.ACTIVE)
              .email("super@estore.com")
              .username("super")
              .password(passwordEncoder.encode("super"))
              .role(superRole)
              .build();
      superAccount.setCreateBy(Const.DEFAULT_USER);
      superAccount.setUpdateBy(Const.DEFAULT_USER);

      Account sellerAccount =
          Account.builder()
              .status(AccountStatus.ACTIVE)
              .email("seller@estore.com")
              .username("seller")
              .password(passwordEncoder.encode("123456"))
              .role(sellerRole)
              .build();
      sellerAccount.setCreateBy(Const.DEFAULT_USER);
      sellerAccount.setUpdateBy(Const.DEFAULT_USER);

      authRepository.save(adminAccount);
      authRepository.save(superAccount);
      authRepository.save(sellerAccount);
    };
  }
}
