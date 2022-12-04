package com.vengeance.importer.service.impl;

import com.vengeance.importer.service.AudioProcessService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.XMPDM;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author pysga
 * @created 10/15/2022 - 11:31 PM
 * @project alpha-sound-importer
 * @since 1.0
 **/
@Log4j2
@Service
public class AudioProcessServiceImpl implements AudioProcessService {

    @Override
    @Transactional
    public void process(Path filePath) {
        log.info("File path: {}", filePath);
        try (InputStream input = Files.newInputStream(filePath)) {

            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();

            ParseContext parsectx = new ParseContext();
            parser.parse(input, handler, metadata, parsectx);
            input.close();

            // list all metadata
            //            String[] metadatanames = metadata.names();
            //
            //            for(String name : metadatanames){
            //                log.info(name + ": " + metadata.get(name));
            //            }

            // retrieve the necessary info from metadata
            // names - title, xmpdm:artist etc. - mentioned below may differ based
            // on the standard used for processing and storing standardized and/or
            // proprietary information relating to the contents of a file.

            log.info("title: " + metadata.get(DublinCore.TITLE));
            log.info("duration: " + metadata.get(XMPDM.DURATION));
            log.info("artists: " + metadata.get(XMPDM.ARTIST));
            log.info("genre: " + metadata.get(XMPDM.GENRE));
            log.info("bitrate: " + metadata.get(XMPDM.RELEASE_DATE));

            log.info("album: " + metadata.get(XMPDM.ALBUM));
            log.info("disk: " + metadata.get(XMPDM.DISC_NUMBER));
            log.info("album artists: " + metadata.get(XMPDM.ALBUM_ARTIST));
            log.info("track number: " + metadata.get(XMPDM.TRACK_NUMBER));

            log.info("bitrate: " + metadata.get(XMPDM.AUDIO_SAMPLE_RATE));

        } catch (IOException | TikaException | SAXException e) {
            log.error(e.getMessage());
        }
    }

}
