package uet.kltn.judgment.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Utils {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
