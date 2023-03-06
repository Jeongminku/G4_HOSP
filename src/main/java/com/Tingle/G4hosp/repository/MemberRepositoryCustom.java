package com.Tingle.G4hosp.repository;

import java.util.List;

import com.Tingle.G4hosp.constant.Ward;
import com.Tingle.G4hosp.dto.AdminMainDto;
import com.Tingle.G4hosp.entity.Med;
import com.Tingle.G4hosp.entity.Member;
import com.Tingle.G4hosp.entity.MemberMed;

public interface MemberRepositoryCustom {

	AdminMainDto getdoctorcount(AdminMainDto adminMainDto);
	
	AdminMainDto getpatientcount(AdminMainDto adminMainDto);
	
	AdminMainDto gethospitalizedcount(AdminMainDto adminMainDto);
	
	List<Member> getdoctorbysearch(String searchquery);
	
	AdminMainDto viewHosptalizedlistMed(List<Med> medlist, AdminMainDto adminMainDto);
	
	AdminMainDto viewHosptalizedwardlist(AdminMainDto adminMainDto);
	
}
