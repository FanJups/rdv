package biz.advanceitgroup.rdvserver.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.authentication.entities.Quizz;

@Repository
public interface QuizzRepository extends JpaRepository<Quizz, Long> {

}
