package biz.advanceitgroup.rdvserver.authentication.services.interfaces;

import java.io.IOException;

import biz.advanceitgroup.rdvserver.authentication.dto.PostFilesDto;
import biz.advanceitgroup.rdvserver.authentication.entities.File;

public interface FileService {
	
	
	
	File postFiles(PostFilesDto postFilesDto) throws IOException;

}
