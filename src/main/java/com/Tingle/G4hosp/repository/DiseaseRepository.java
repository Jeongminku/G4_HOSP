package com.Tingle.G4hosp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Tingle.G4hosp.entity.Disease;

public interface DiseaseRepository extends JpaRepository<Disease, Long>{

	@Query(value = "select * from disease where disease.disease_name = :DNAME", nativeQuery = true)
	Disease findDiseasebyDiseasename(@Param("DNAME") String Diseasename);

	@Query(value = "select * from disease a join hospitalize_disease b on a.disease_id = b.disease_id join hospitalize c on b.hospitalize_id = c.hospitalize_id where c.member_id = :MID", nativeQuery = true)
	Disease findDiseasebyHospMemid(@Param("MID") Long memid);
	
	@Query(value = "select * from disease a join membermed b on a.med_id = b.med_id join member c on b.member_id = c.member_id where c.role ='doctor' and c.member_loginid = :DID", nativeQuery = true)
	List<Disease> findDiseaseListByDocId(@Param("DID") String doctorid);
	
	@Query(value = "select * from disease a join archive_disease b on a.disease_id = b.disease_id join archive c on b.archive_id = c.archive_id where c.archive_id = :ARCID", nativeQuery = true)
	Disease findDiseasebyArcid(@Param("ARCID") Long ardid);
	
}
