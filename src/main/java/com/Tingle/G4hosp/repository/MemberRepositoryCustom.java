package com.Tingle.G4hosp.repository;

import java.util.List;

import com.Tingle.G4hosp.dto.AdminMainDto;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;

public interface MemberRepositoryCustom {

	AdminMainDto getdoctorcount();
	
	AdminMainDto getpatientcount();
	
	AdminMainDto gethospitalizedcount();
	
	List<Member> getdoctorbysearch(String searchquery);
	
}
