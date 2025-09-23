package com.example.gympool.service.impl;

import com.example.gympool.dto.LoginRequest;
import com.example.gympool.dto.LoginResponse;
import com.example.gympool.dto.RegisterRequest;
import com.example.gympool.entity.*;
import com.example.gympool.repository.*;
import com.example.gympool.security.JwtTokenProvider;
import com.example.gympool.service.AuthService;
import com.example.gympool.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StaffRepository staffRepository;
    private final ManagerRepository managerRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new LoginResponse(accessToken, refreshToken.getToken(), user.getRole(), user.getFullName());
    }

    @Override
    public void register(RegisterRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already taken");
        }

        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setFullName(request.getFullName());
        member.setDob(request.getDob());
        member.setGender(request.getGender());
        member.setPhone(request.getPhone());
        member.setRole("MEMBER"); // mặc định

        // các field riêng cho phép null
        member.setMembership(null);
        member.setJoinDate(null);
        member.setStatus(null);
        member.setFaceId(null);
        member.setCardId(null);

        memberRepository.save(member);  // insert vào cả users + members
    }

    @Override
    public void registerEmployee(RegisterRequest request) {
        if (request.getRole() == null) {
            throw new RuntimeException("Role is required for employee registration");
        }

        switch (request.getRole().toUpperCase()) {
            case "STAFF" -> {
                if (staffRepository.findByEmail(request.getEmail()).isPresent()) {
                    throw new RuntimeException("Email already taken");
                }

                Staff staff = new Staff();
                staff.setEmail(request.getEmail());
                staff.setPassword(passwordEncoder.encode(request.getPassword()));
                staff.setFullName(request.getFullName());
                staff.setDob(request.getDob());
                staff.setGender(request.getGender());
                staff.setPhone(request.getPhone());
                staff.setRole("STAFF");

                staff.setPosition(null);
                staff.setSpecialize(null);
                staff.setHirePrice(null);

                staffRepository.save(staff);
            }
            case "MANAGER" -> {
                if (managerRepository.findByEmail(request.getEmail()).isPresent()) {
                    throw new RuntimeException("Email already taken");
                }

                Manager manager = new Manager();
                manager.setEmail(request.getEmail());
                manager.setPassword(passwordEncoder.encode(request.getPassword()));
                manager.setFullName(request.getFullName());
                manager.setDob(request.getDob());
                manager.setGender(request.getGender());
                manager.setPhone(request.getPhone());
                manager.setRole("MANAGER");

                managerRepository.save(manager);
            }
            default -> throw new RuntimeException("Invalid role for employee registration");
        }
    }
}
