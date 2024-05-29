package com.example.capstone.member.repository;

import com.example.capstone.member.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByMember_Id(Long memberId);
}
