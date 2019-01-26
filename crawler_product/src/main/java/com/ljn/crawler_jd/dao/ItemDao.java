package com.ljn.crawler_jd.dao;

import com.ljn.crawler_jd.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDao extends JpaRepository<Item,Long>{
}
