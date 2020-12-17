package com.self.boot.module.captche;

import com.self.boot.dto.login.WebLoginDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.captcha.servlet.MicaCaptchaServlet;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("captcha")
@RequiredArgsConstructor
public class CaptchaController {
    private final MicaCaptchaServlet micaCaptcha;

    @GetMapping(value = "generate", produces = MediaType.IMAGE_JPEG_VALUE)
    public String generateCaptcha(HttpServletResponse response) {
        return micaCaptcha.generateBase64(response);
    }

    @PostMapping("/validate/{captchaCode}")
    public boolean validateCaptcha(HttpServletResponse response,
                                   @PathVariable("captchaCode") String captchaCode,
                                   @RequestBody WebLoginDTO webLoginDTO) {
        log.debug("====>> \n {}", webLoginDTO.toString());
        if (micaCaptcha.validate(response, captchaCode)) {
            return true;
        } else {
            return false;
        }
    }

}
