package bg.tihomir.hobby.repository;

import bg.tihomir.hobby.model.entity.UserRoleEntity;
import bg.tihomir.hobby.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
        UserRoleEntity findByUserRole(UserRoleEnum roleEnum);

}
