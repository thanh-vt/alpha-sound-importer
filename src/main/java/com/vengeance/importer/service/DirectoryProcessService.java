package com.vengeance.importer.service;

import java.nio.file.Path;

/**
 * @author pysga
 * @created 10/15/2022 - 11:58 PM
 * @project alpha-sound-importer
 * @since 1.0
 **/
public interface DirectoryProcessService {

    void process(Path directoryPath);
}
