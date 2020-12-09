package com.misc.ach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.misc.ach.model.DataFile;
import com.misc.ach.model.Line;
import com.misc.ach.service.AchService;
import com.misc.ach.service.CoreService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@PropertySource("classpath:custom.properties")
@Slf4j
public class AchApplication {
	
	@Value("${misc.watch.folder}")
	private String watchfolder;
	
	private static AchService achService;
	
	private static CoreService coreService;
	
	private static String staticWatchFolder;

	public static void main(String[] args) {
		SpringApplication.run(AchApplication.class, args);
		
		log.info("Folder to Watch: " + staticWatchFolder);
		watchForNewFile(staticWatchFolder);
	}
	
	@Value("${misc.watch.folder}")
	public void setStaticWatchFolder(String value) {
		AchApplication.staticWatchFolder = value;
	}
	
	@Autowired
	public void setAchService(AchService service) {
		AchApplication.achService = service;
	}
	
	@Autowired
	public void setService(CoreService service) {
		AchApplication.coreService = service;
	}

	private static void watchForNewFile(String folder) {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = FileSystems.getDefault().getPath(staticWatchFolder);
			
			WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
			
			while(true) {
				key = watcher.take();
				for(WatchEvent<?> event: key.pollEvents()) {
					Object o = event.context();
					if(o instanceof Path) {
						Path path = (Path) o;
						log.info("New File Detected: " + path);
						processFile(path);
					}
				}
				key.reset();
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void processFile(Path path) {
		try {
			DataFile dataFile = DataFile.builder().name(path.toString()).build();
			DataFile savedFile = achService.saveFile(dataFile);
			
			File file = new File(staticWatchFolder, path.toString());
			FileReader fileReader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(fileReader);
			String lineData;
			
			while((lineData = bufReader.readLine()) != null) {
				log.info("Processing Line: " + lineData);
				Line line = Line.builder().data(lineData).file(savedFile).build();
				Long coreId = coreService.submitLine(line);
				line.setCoreId(coreId);
				log.info("Got CoreID: " + coreId);
				
				achService.saveLine(line);
			}

			fileReader.close();
			bufReader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
