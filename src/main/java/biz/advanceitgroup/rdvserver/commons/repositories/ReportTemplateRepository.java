package biz.advanceitgroup.rdvserver.commons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.commons.entities.ReportTemplate;


@Repository
public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, Long>{

}
