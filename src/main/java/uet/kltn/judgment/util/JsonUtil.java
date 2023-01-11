package uet.kltn.judgment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ModelMapper modelMapper = new ModelMapper();

    public static String fromObjectToJsonString(Object object) {
        ObjectWriter ow = mapper.writer();
        try {
            return ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> fromObjectToMap(Object object) {
        try {
            return fromJsonStringToMap(fromObjectToJsonString(object));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> fromJsonStringToMap(String json) {
        if (json == null || json.isBlank()) {
            return new HashMap<>();
        }
        try {
            return mapper.readValue(json, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static <D> List<D> fromJsonStringToList(String json) {
        if (json == null || json.isBlank()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(json, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static <T> T fromJsonStringToObject(String json, Class<T> clazz) {
        return toDto(fromJsonStringToMap(json), clazz, true);
    }

    public static String fromMapToJsonString(Map map) {
        if (map.isEmpty()) {
            return null;
        }
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <D> D toDto(Object data, Class<D> clazz, boolean fromSnakeCase) {
        if (fromSnakeCase) {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        } else {
            modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.CAMEL_CASE);
        }
        if (data == null) {
            Constructor<?> constructor = null;
            try {
                constructor = clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            try {
                return (D) constructor.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (data instanceof String) {
            return fromJsonStringToObject(data.toString(), clazz);
        } else {
            return  modelMapper.map(data, clazz);
        }
    }

    public static <D> D toDto(Object data, Class<D> clazz) {
        return  toDto(data, clazz, false);
    }

    public static <D> List<D> toDtoList(List dataList, Class<D> clazz) {
        return  toDtoList(dataList, clazz, false);
    }

    public static <D> List<D> toDtoList(List dataList, Class<D> clazz, boolean fromSnakeCase) {
        return (List<D>) dataList.stream()
                .map(data -> toDto(data, clazz, fromSnakeCase))
                .collect(Collectors.toList());
    }
}
