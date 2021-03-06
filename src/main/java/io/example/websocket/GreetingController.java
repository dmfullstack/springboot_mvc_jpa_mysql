package io.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(3000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @RequestMapping("/webSocket")
    public ModelAndView webSocket() {
        return new ModelAndView("webSocket");
    }

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping("/webSocket/pushData")
    @ResponseBody
    public String webSocketPushData() throws Exception {
        String text = "Goooooood";
        this.template.convertAndSend("/topic/greetings", new Greeting(text));
        return "input Data: " + text;
    }
}