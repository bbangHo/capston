package com.example.capstone.member.repository;

import com.example.capstone.member.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Query("SELECT a FROM Alarm a where a.member.id = :memberId and a.isConfirmed = false ORDER BY a.createdAt ASC ")
    Page<Alarm> searchNotConfirmedAlarm(Long memberId , Pageable pageable);

    @Query("select count(*) from Alarm a where a.member.id = :memberId and a.isConfirmed = false")
    Integer countAllNotConfirmedAlarm(Long memberId);

}
