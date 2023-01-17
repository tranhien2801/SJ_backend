package uet.kltn.judgment.util;

import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import uet.kltn.judgment.constant.Constant;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.dto.common.ExpressionDto;
import com.google.common.base.CaseFormat;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@Component
public class Utils {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static List<Integer> getState(boolean ignoreDeleted) {
        if (ignoreDeleted) {
            return Collections.singletonList(State.ACTIVE.getId());
        } else {
            return Arrays.asList(State.ACTIVE.getId(), State.DELETE.getId());
        }
    }

    public static List<Integer> getByMoDisplay(boolean isGetAll) {
        if (isGetAll) {
            return Arrays.asList(1, 0);
        } else {
            return Collections.singletonList(1);
        }
    }

    public Map<String, Object> getExpressionAndParams(Map<String, Object> dataMap, Class<?> clazz) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> params = new HashMap<>();

        boolean isPaging = true, isDesc = true;
        int page = Constant.VALUE_DEFAULT_PAGE, size = Constant.VALUE_DEFAULT_PAGE_SIZE;
        String orderBy = "created";

        if (dataMap.containsKey(Constant.KEY_PAGING)) {
            try {
                isPaging = Integer.parseInt(dataMap.get(Constant.KEY_PAGING).toString()) == 1;
            } catch (NumberFormatException e) {

            }
            dataMap.remove(Constant.KEY_PAGING);
        }
        if (dataMap.containsKey(Constant.KEY_PAGE)) {
            try {
                page = Integer.parseInt(dataMap.get(Constant.KEY_PAGE).toString()) > 0
                        ? Integer.parseInt(dataMap.get(Constant.KEY_PAGE).toString())
                        : Constant.VALUE_DEFAULT_PAGE;
            } catch (NumberFormatException e) {

            }
            dataMap.remove(Constant.KEY_PAGE);
        }
        if (dataMap.containsKey(Constant.KEY_SIZE)) {
            try {
                size = Integer.parseInt(dataMap.get(Constant.KEY_SIZE).toString()) > 0
                        ? Integer.parseInt(dataMap.get(Constant.KEY_SIZE).toString())
                        : Constant.VALUE_DEFAULT_PAGE_SIZE;
            } catch (NumberFormatException e) {

            }
            dataMap.remove(Constant.KEY_SIZE);
        }
        if (dataMap.containsKey(Constant.KEY_ASC)) {
            isDesc = dataMap.get(Constant.KEY_ASC) != null && Integer.parseInt(dataMap.get(Constant.KEY_ASC).toString()) == 0;
            dataMap.remove(Constant.KEY_ASC);
        }
        if (clazz != null) {
            List<String> clazzField = getFields(clazz);
            if (dataMap.containsKey(Constant.KEY_ORDER_BY)) {
                orderBy = dataMap.get(Constant.KEY_ORDER_BY) != null && clazzField.contains(fromSnakeToCamel(dataMap.get(Constant.KEY_ORDER_BY).toString()))
                        ? fromSnakeToCamel(dataMap.get(Constant.KEY_ORDER_BY).toString())
                        : "created";
                dataMap.remove(Constant.KEY_ORDER_BY);
            }
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                String key = fromSnakeToCamel(entry.getKey());
                if (clazzField.contains(key)) {
                    params.put(key, "%" + entry.getValue() + "%");
                }
            }
        }

        data.put(Constant.KEY_EXPRESSION, new ExpressionDto(isPaging, size, page, orderBy, isDesc));
        data.put(Constant.KEY_PARAMS, params);
        return data;
    }

//    public static Geometry wktToGeometry(String wellKnownText) throws ParseException {
//        return new WKTReader().read(wellKnownText);
//    }
//
//    public static Geometry createCircle(double x, double y, double radius) {
//        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
//        shapeFactory.setNumPoints(32);
//        shapeFactory.setCentre(new Coordinate(x, y));
//        shapeFactory.setSize(radius * 2);
//        return shapeFactory.createCircle();
//    }

    public List<String> getFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldsName = new ArrayList<>();
        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                fieldsName.add(field.getName());
            }
        }
        fields = clazz.getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                fieldsName.add(field.getName());
            }
        }
        return fieldsName;
    }

    public static String fromSnakeToCamel(String s) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s.toLowerCase());
    }

    public Map<String, List<String>> createBadRequestErrorMsg(String dtoName, Errors errors) {
        Map<String, List<String>> err = new HashMap<>();
        List<String> messages = new ArrayList<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fe: fieldErrors) {
            messages.add(String.format("%s %s!", fe.getField(), fe.getDefaultMessage()));
        }
        err.put(dtoName, messages);
        return err;
    }

}
