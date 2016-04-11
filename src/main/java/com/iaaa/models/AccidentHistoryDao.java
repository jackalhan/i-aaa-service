package com.iaaa.models;

import com.iaaa.dto.Coordinates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by jackalhan on 4/8/16.
 */
@Transactional
public interface AccidentHistoryDao extends CrudRepository<AccidentHistory, Long>{

    @Query("SELECT a FROM AccidentHistory a WHERE a.fatalNumber = :fatalNumber and a.accidentNumber = :accidentNumber and a.insertTime >= :insertTime")
    public List<AccidentHistory> findAccidentByFatalAccidentNumberSince1Week(@Param("fatalNumber") String fatalNumber,
                                                                             @Param("accidentNumber") int accidentNumber,
                                                                             @Param("insertTime") Date insertTime);


   /* @Query("SELECT a FROM AccidentHistory a WHERE (CAST(a.lat as CHAR) LIKE  :lat %' AND CAST(a.lon as CHAR) LIKE '\" + coordinates.getLon() + \"%')\"a.fatalNumber = :fatalNumber and a.accidentNumber = :accidentNumber and a.insertTime >= :insertTime")
    public List<AccidentHistory> findAccidentByCoordinates(@Param("lat") double lat,
                                                           @Param("lon") double lon)*/

    @Query(value = "SELECT * FROM Accident_History a WHERE CAST(a.lat as CHAR) LIKE  ?1% AND CAST(a.lon as CHAR) LIKE ?2%", nativeQuery = true)
    public List<AccidentHistory> findAccidentByCoordinates(@Param("lat") double lat,
                                                           @Param("lon") double lon);

}
