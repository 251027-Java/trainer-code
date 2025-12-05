package com.revature.ExpenseReport.Repository;

import com.revature.ExpenseReport.Model.AppUser;
import org.apache.coyote.Adapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<Adapter>
}
