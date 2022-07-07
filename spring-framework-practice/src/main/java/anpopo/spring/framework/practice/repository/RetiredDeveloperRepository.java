package anpopo.spring.framework.practice.repository;

import anpopo.spring.framework.practice.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper, Long> {
}
