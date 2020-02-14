package biz.advanceitgroup.rdvserver.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.authentication.entities.QuizzQuestionResponse;


@Repository
public interface QuizzQuestionResponseRepository extends JpaRepository<QuizzQuestionResponse, Long> {

}
