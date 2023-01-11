package uet.kltn.judgment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import uet.kltn.judgment.util.ResponseUtil;
import uet.kltn.judgment.util.Utils;

@RestController
public abstract class GenController {
    static Logger logger = LoggerFactory.getLogger(GenController.class);
    protected static ResponseUtil responseUtil;
    protected static Utils utils;
    static {
        responseUtil = new ResponseUtil();
        utils = new Utils();
    }
}
