package com.vengeance.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author pysga
 * @created 10/12/2022 - 4:54 PM
 * @project alpha-sound-importer
 * @since 1.0
 **/
@ShellComponent
@Slf4j
public class MainCommand {

    @ShellMethod(value = "Add two integers together.", key = {"sum"})
    public int add(int a, int b) {
        return a + b;
    }

    @ShellMethod(value = "Add two integers together.", key = {"meta"})
    public int printMetadata(String filePath) {
        log.info("File path: {}", filePath);
        try (InputStream input = new FileInputStream(filePath)) {

            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parsectx = new ParseContext();
            parser.parse(input, handler, metadata, parsectx);
            input.close();

// list all metadata
            String[] metadatanames = metadata.names();

            for(String name : metadatanames){
                log.info(name + ": " + metadata.get(name));
            }

// retrieve the necessary info from metadata
// names - title, xmpdm:artist etc. - mentioned below may differ based
// on the standard used for processing and storing standardized and/or
// proprietary information relating to the contents of a file.

            log.info("title: " + metadata.get("title"));
            log.info("artists: " + metadata.get("xmpdm:artist"));
            log.info("genre: " + metadata.get("xmpdm:genre"));

        } catch (IOException | TikaException | SAXException e) {
            log.error(e.getMessage());
        }
        return 0;
    }
}
