package com.be.millipore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.be.millipore.beans.Content;

@Repository
public interface ContentRepo extends CrudRepository<Content, Long> {

}
