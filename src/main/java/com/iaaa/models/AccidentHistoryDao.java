package com.iaaa.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * Created by jackalhan on 4/8/16.
 */
@Transactional
public interface AccidentHistoryDao extends CrudRepository<AccidentHistory, Long> {

    @Query("SELECT a FROM AccidentHistory a WHERE a.fatalNumber = :fatalNumber and a.accidentNumber = :accidentNumber and a.insertTime >= :insertTime")
    public List<AccidentHistory> findAccidentByFatalAccidentNumberSince1Week(@Param("fatalNumber") int fatalNumber,
                                                                             @Param("accidentNumber") int accidentNumber,
                                                                             @Param("insertTime") Date insertTime);
}
