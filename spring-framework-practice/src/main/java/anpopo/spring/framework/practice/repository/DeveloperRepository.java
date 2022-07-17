package anpopo.spring.framework.practice.repository;

import anpopo.spring.framework.practice.code.StatusCode;
import anpopo.spring.framework.practice.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Optional<Developer> findByMemberId(String memberId);

    List<Developer> findAllByStatusCode(StatusCode statusCode);

}
