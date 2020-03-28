package biz.advanceitgroup.rdvserver.jobs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.rdvserver.jobs.entities.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
