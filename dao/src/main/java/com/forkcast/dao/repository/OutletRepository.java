package com.forkcast.dao.repository;

import com.forkcast.dao.entities.Outlet;
import com.forkcast.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutletRepository extends JpaRepository<Outlet, Long> {
    List<Outlet> findByOwner(User owner);
}
