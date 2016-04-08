package com.iaaa.models;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by jackalhan on 4/8/16.
 */
@Transactional
public interface AccidentHistoryDao extends CrudRepository<AccidentHistory, Long> {


}
