package biz.advanceitgroup.rdvserver.commons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.commons.entities.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

}
