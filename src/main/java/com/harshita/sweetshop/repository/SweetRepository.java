package com.harshita.sweetshop.repository;

import com.harshita.sweetshop.model.Sweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SweetRepository extends JpaRepository<Sweet, Long> {

}
