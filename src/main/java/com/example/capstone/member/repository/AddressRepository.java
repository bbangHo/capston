package com.example.capstone.member.repository;

import com.example.capstone.member.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
