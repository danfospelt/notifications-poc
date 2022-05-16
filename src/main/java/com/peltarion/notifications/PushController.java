package com.peltarion.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peltarion.notifications.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class PushController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushController.class);

    private final WebsocketClient websocketClient;

    public PushController(WebsocketClient websocketClient) {
        this.websocketClient = websocketClient;
    }

    @PostMapping("message")
    public void getMessage(@RequestBody Greeting greeting) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String message = mapper.writeValueAsString(greeting);
            websocketClient.sendMessage(message);
        } catch (Exception e) {
            LOGGER.error("Failed to serialize object: {}", greeting, e);
        }
    }
}
