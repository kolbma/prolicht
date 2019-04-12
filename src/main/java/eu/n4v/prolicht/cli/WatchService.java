package eu.n4v.prolicht.cli;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO: Service to log the watched views
 */
@Service
@Slf4j
public class WatchService {

    public WatchService() {

        log.debug("initialized WatchService");
    }
}
