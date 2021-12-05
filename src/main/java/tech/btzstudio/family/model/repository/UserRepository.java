package tech.btzstudio.family.model.repository;

import org.springframework.data.repository.CrudRepository;
import tech.btzstudio.family.model.entity.User;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);

}
