package hhertout.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    static final class PingResponse {
        private String message;

        public PingResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @GetMapping("/ping")
    @ResponseBody
    public ResponseEntity<PingResponse> index() {
        PingResponse response = new PingResponse("pong");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
