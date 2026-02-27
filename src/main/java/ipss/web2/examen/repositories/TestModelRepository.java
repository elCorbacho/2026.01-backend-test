package ipss.web2.examen.repositories;

import ipss.web2.examen.models.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestModelRepository extends JpaRepository<TestModel, Long> {
    List<TestModel> findByActiveTrue();
}
