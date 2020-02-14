package biz.advanceitgroup.rdvserver.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.authentication.entities.ReportTemplate;


@Repository
public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, Long>{

}
