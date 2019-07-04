package com.bridgeit.fundooapp.user.repository;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.bridgeit.fundooapp.user.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

	public Optional<User> findByEmail(String email);
}
