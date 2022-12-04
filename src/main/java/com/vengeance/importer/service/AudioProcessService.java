package com.vengeance.importer.service;

import java.nio.file.Path;

/**
 * @author pysga
 * @created 10/15/2022 - 11:30 PM
 * @project alpha-sound-importer
 * @since 1.0
 **/
public interface AudioProcessService {

    void process(Path filePath);

}
