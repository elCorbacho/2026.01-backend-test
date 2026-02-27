package ipss.web2.examen.repositories;

import ipss.web2.examen.models.DemoWidget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemoWidgetRepository extends JpaRepository<DemoWidget, Long> {
    List<DemoWidget> findByActiveTrue();
}
