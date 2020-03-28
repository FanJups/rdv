package biz.advanceitgroup.rdvserver.quizz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.quizz.entities.Quizz;

@Repository
public interface QuizzRepository extends JpaRepository<Quizz, Long> {

}
