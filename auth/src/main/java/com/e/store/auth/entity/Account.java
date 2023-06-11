package com.e.store.auth.entity;

import com.e.store.auth.constant.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "account_status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "account")
    private AccountInfo accountInfo;

    @OneToOne(mappedBy = "account")
    private AccountPayment accountPayment;

    @OneToOne(mappedBy = "account")
    private RefreshToken refreshToken;
}
