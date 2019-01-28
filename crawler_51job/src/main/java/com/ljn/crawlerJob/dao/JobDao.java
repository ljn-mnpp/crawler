package com.ljn.crawlerJob.dao;

import com.ljn.crawlerJob.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDao extends JpaRepository<Job,Long>{
}
