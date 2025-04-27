package com.pingpong;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


@RestController
public class Controller {

 private final PingPongRepository pingPongRepository;

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

 @Autowired
 private DatabaseHealthIndicator databaseHealthIndicator;

    public Controller(PingPongRepository pingPongRepository) {
        this.pingPongRepository = pingPongRepository;
    }


    @GetMapping("/pingpong")
    @Transactional
    public int getPingPong() {
        PingEntity pings = pingPongRepository.findById(1L).isPresent() ? pingPongRepository.findById(1L).get() : new PingEntity(1);
        System.out.println(pings + " are my number of pings");
        pings.setPings(pings.getPings() + 1);

        pingPongRepository.save(pings);


        return  pings.getPings();

    }

    @GetMapping("/")
    @Transactional
    public String getStuff() {

        return  "";

    }

    @GetMapping("/timestamp-health")
    public ResponseEntity<String> ingressResponse() {
        return new ResponseEntity<>("Frontend may connect", HttpStatus.OK);
    }


    @GetMapping("/health")
    public void readiness() throws SQLException {
        if(databaseHealthIndicator.health().getStatus().equals(Status.UP)) {
            logger.info("Database UP");
        } else {
            logger.info("Database DOWN");
            throw new SQLException("Database is not available");
        }
    }




}
