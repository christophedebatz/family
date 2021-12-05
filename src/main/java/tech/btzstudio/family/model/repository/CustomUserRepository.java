package tech.btzstudio.family.model.repository;

import tech.btzstudio.family.model.entity.User;

public class CustomUserRepository extends AbstractEntityRepository<User> {

    public CustomUserRepository() {
        super(User.class);
    }

}
