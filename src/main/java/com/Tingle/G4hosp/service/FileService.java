package com.Tingle.G4hosp.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import groovy.util.logging.Log;

@Service
@Log
public class FileService {
	
	//파일 업로드
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileDate) throws Exception {
		
		UUID uuid = UUID.randomUUID();
		
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //확장자명
		String savedFileName = uuid.toString() + extension; //파일이름 생성
		String fileUploadFullUrl = uploadPath + "/" + savedFileName; // 업로드 경로
		
		// 생성자에 파일이 저장될 위치와 파일의 이름을 같이 넘겨 출력스트림을 만든다.
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
		fos.write(fileDate);
		fos.close();
		
		return savedFileName;
	}
}
