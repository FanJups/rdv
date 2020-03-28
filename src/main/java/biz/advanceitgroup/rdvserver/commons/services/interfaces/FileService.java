package biz.advanceitgroup.rdvserver.commons.services.interfaces;

import java.io.IOException;

import biz.advanceitgroup.rdvserver.commons.entities.File;
import biz.advanceitgroup.rdvserver.commons.dto.PostFilesDto;

public interface FileService {
	
	
	
	File postFiles(PostFilesDto postFilesDto) throws IOException;

}
