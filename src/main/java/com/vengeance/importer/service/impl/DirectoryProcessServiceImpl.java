package com.vengeance.importer.service.impl;

import com.vengeance.importer.service.AudioProcessService;
import com.vengeance.importer.service.DirectoryProcessService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pysga
 * @created 10/15/2022 - 11:58 PM
 * @project alpha-sound-importer
 * @since 1.0
 **/
@Log4j2
@Service
public class DirectoryProcessServiceImpl implements DirectoryProcessService {

    private final AudioProcessService audioProcessService;

    @Autowired
    public DirectoryProcessServiceImpl(AudioProcessService audioProcessService) {
        this.audioProcessService = audioProcessService;
    }

    @SneakyThrows
    @Override
    public void process(Path directoryPath) {
        log.debug("Process begin for path {}", directoryPath);
        try (Stream<Path> stream = Files.list(directoryPath)) {
            stream
                .forEach(path -> {
                    if (Files.isDirectory(path)) {
                        this.process(path);
                    } else {
//                        this.audioProcessService.process(path);
                        log.info(path);
                    }
                });
        } catch (Exception ex) {
            log.error("Process error in path {}", directoryPath);
            log.error("Process error", ex);
        } finally {
            log.debug("Process end for path {}", directoryPath);
        }
    }

}
