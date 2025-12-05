package com.revature.ExpenseReport.Repository;

import com.revature.ExpenseReport.Model.AppUser;
<<<<<<< HEAD
import org.apache.coyote.Adapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<Adapter>
}
=======
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
    Optional<AppUser> findByUsername(String username);
}
>>>>>>> origin
