package com.vengeance.importer;

import com.vengeance.importer.service.DirectoryProcessService;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * @author pysga
 * @created 10/12/2022 - 4:54 PM
 * @project alpha-sound-importer
 * @since 1.0
 **/
@ShellComponent
@Log4j2
public class MainCommand {

    private final DirectoryProcessService directoryProcessService;

    @Autowired
    public MainCommand(DirectoryProcessService directoryProcessService) {
        this.directoryProcessService = directoryProcessService;
    }

    @ShellMethod(value = "Add two integers together.", key = {"sum"})
    public int add(int a, int b) {
        return a + b;
    }

    @ShellMethod(value = "Import song, artist, album from directory", key = {"meta"})
    public int printMetadata(String filePath) {
        Path rootPath = Paths.get(filePath);
        this.directoryProcessService.process(rootPath);
        return 0;
    }
}
