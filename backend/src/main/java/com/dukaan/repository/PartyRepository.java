package com.dukaan.repository;

import com.dukaan.entity.Party;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByActiveTrue();
    List<Party> findByDeletedFalse();
    Page<Party> findByDeletedFalse(Pageable pageable);
    Page<Party> findByNameContainingIgnoreCaseAndDeletedFalse(String name, Pageable pageable);
    Optional<Party> findByMobile(String mobile);
}
