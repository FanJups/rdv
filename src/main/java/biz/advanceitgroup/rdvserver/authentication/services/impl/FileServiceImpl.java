package biz.advanceitgroup.rdvserver.authentication.services.impl;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biz.advanceitgroup.rdvserver.authentication.dto.PostFilesDto;
import biz.advanceitgroup.rdvserver.authentication.entities.File;
import biz.advanceitgroup.rdvserver.authentication.repository.FileRepository;
import biz.advanceitgroup.rdvserver.authentication.services.interfaces.FileService;


@Service
@Transactional
public class FileServiceImpl implements FileService {
	
	@Autowired
    private FileRepository fileRepository;
	
	@Override
	public File postFiles(PostFilesDto postFilesDto) 
	{
		File fileEntity = new File();
		
		fileEntity.setDocType(postFilesDto.getDocType());
		
		//fileEntity.setJobID(postFilesDto.getJobID());
		
		//fileEntity.setBidID(postFilesDto.getBidID());
		
		//fileEntity.setUserID(postFilesDto.getUserCategoryID());
		
		return fileRepository.save(fileEntity);
		
	}

}
