package com.godrej.surveys.onboarding.controller;

import java.io.File;

//import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godrej.surveys.SftpConfig.UploadGateway;





@RestController
public class SpringSftpController {
	
	@Autowired
    private UploadGateway gateway;
	@Autowired
    private static EmbeddedSftpServerprd server;
	@Autowired
    private static Path sftpFolder;
    
	@RequestMapping("/sftpcon")
    public String sftpcon (@RequestParam("filepath") String filepath,@RequestParam("folderpath") String folderpath){
		
        try {
        	server = new EmbeddedSftpServerprd();
            server.setPort(22);
			sftpFolder = Files.createTempDirectory("GPL_SFTP_UPLOAD_TEST");
			server.afterPropertiesSet();
	        server.setHomeFolder(sftpFolder);
	        // Starting SFTP
	        if (!server.isRunning()) {
	            server.start();
	        }
	        //String filePath="D:\\Satheesh\\Projects\\Litmus World\\customer_onboarding.xlsx";
	        Path filePathObj = Paths.get(filepath);
	        // Prepare phase
	        /*Path tempFile = Files.createTempFile("UPLOAD_TEST", ".csv");*/
	        // Prerequisites
	        //assertEquals(0, Files.list(sftpFolder).count());
	        // test phase
	        System.out.println("Foder Path:-"+folderpath);
	        gateway.upload(filePathObj.toFile(),folderpath);

	        // Validation phase
	        List<Path> paths = Files.list(sftpFolder).collect(Collectors.toList());
	        //assertEquals(1, paths.size());
	        //assertEquals(tempFile.getFileName(), paths.get(0).getFileName());
	        server.stop();
		} catch (Exception e) {
			server.stop();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return "message from rest handler";
    }

	 public static void stopServer() {
	        if (server.isRunning()) {
	            server.stop();
	        }
	    }
	 public void cleanSftpFolder() throws IOException {
	        Files.walk(sftpFolder).filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);
	    }

}
