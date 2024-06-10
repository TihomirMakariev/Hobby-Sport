package bg.tihomir.hobby.repository;

import bg.tihomir.hobby.model.dto.view.SearchHobbyView;
import bg.tihomir.hobby.model.entity.HobbyEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class HobbySpecification implements Specification<HobbyEntity> {


    private final SearchHobbyView searchHobbyView;

    public HobbySpecification(SearchHobbyView searchHobbyView) {
        this.searchHobbyView = searchHobbyView;
    }


    @Override
    public Predicate toPredicate(Root<HobbyEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {

        Predicate p = cb.conjunction();

        if (searchHobbyView.getCategory() != null) {
            p = cb.and(p, cb.equal(root.get("category").get("name"), searchHobbyView.getCategory()));
        }
        return p;
    }
}
