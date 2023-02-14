package uet.kltn.judgment.constant;

import uet.kltn.judgment.util.JsonUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Constant {

    //KEY

    public static final String DEFAULT_NAME_PORT = "UNKNOWN";
    public static final String KEY_PAGING = "paging";
    public static final String KEY_PAGE = "page";
    public static final String KEY_SIZE = "size";
    public static final String KEY_ORDER_BY = "order_by";
    public static final String KEY_ASC = "asc";
    public static final String KEY_DIRECTION_DESC = "desc";
    public static final String KEY_PARAMS = "params";
    public static final String KEY_EXPRESSION = "expression";
    public static final String KEY_SEARCH_START = "start_date";
    public static final String KEY_SEARCH_END = "end_date";

    //VALUE
    public static final String VALUE_DEFAULT_PHONE_NUMBER = "0000";
    public static final int VALUE_DEFAULT_DAY_OF_WEEK = 7;
    public static final int VALUE_DEFAULT_PAGE_SIZE = 10;
    public static final int VALUE_ZOOM_NOT_CLUSTER = 13;
    public static final int VALUE_ZOOM_MAX = 18;
    public static final int VALUE_MAX_MARKER_SIZE = 100;
    public static final int VALUE_DEFAULT_PAGE = 1;
    public static final int VALUE_DEFAULT_PASSWORD_SIZE = 8;
    public static final int VALUE_12_MONTH = 12;
    public static final List<String> VALUE_DIRECTION = Arrays.asList("desc", "asc");
    public static final int VALUE_STATE_SIZE = State.values().length;
    public static final int VALUE_CODE_SIZE = 4;
    public static final int VALUE_DEFAULT_LIMIT = 1000;
    /*second from 00:00*/
    public static final int VALUE_DEFAULT_OPENING = 8 * 60 * 60;
    /*second from 00:00*/
    public static final int VALUE_DEFAULT_CLOSING = 22 * 60 * 60;
    /*second from xx:00*/
    public static final int VALUE_DEFAULT_RANGE_DISPLAY_RESERVATION = 30 * 60;
    public static final int VALUE_DEFAULT_RANGE_RESERVATION = 15 * 60;
    public static final int VALUE_DEFAULT_RANGE_DISPLAY_CHART = 30 * 60;
    /*-> Mon: 0 - Sun: 6*/
    public static final List<Integer> VALUE_DEFAULT_LIST_DAY_OF_WEEK = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
    public static final List<Integer> VALUE_DEFAULT_LIST_MONTH = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    public static final List<Integer> VALUE_DEFAULT_LIST_DAY = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31);
    public static final int VALUE_DAY_SECOND = 60 * 60 * 24;
    public static final int VALUE_HOUR_SECOND = 60 * 60;
    public static final int VALUE_MAX_RESERVATION_TIMES = 99;
    public static final int VALUE_MIN_SECOND = 60;
    public static final int VALUE_SECOND = 1;
    public static final int VALUE_ONE_YEAR_TO_SECOND = 31556926;
    /*second*/
    public static final int VALUE_DEFAULT_RANGE_START_FIND_RESERVATION = 14 * 60;
    public static final int VALUE_DEFAULT_RANGE_END_FIND_RESERVATION = 15 * 60;
    public static final int VALUE_FILE_SIZE = 2655190;
    public static final long VALUE_DAY_MS = 24 * 60 * 60 * 1000;
    public static final int VALUE_DAY_S = 24 * 60 * 60;
    public static final int VALUE_WEEK_S = 24 * 60 * 60 * 7;
    public static final int VALUE_WEEK_H = 24 * 7;
    public static final int VALUE_DAY_H = 24;
    public static final int VALUE_OTP_RETRY = 3;
    public static final List<String> VALUE_CHECK_OTP_URI = Arrays.asList("/user/change-password", "/user/key");

    //PATH
    public static final String PATH_ATTACHMENT = "attachment";

    public static final Map<String, String> MAP_MODEL_TABLE = Stream.of(new String[][]{
            {"User", "users"},
            {"Category", "categories"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static final Map<String, String> MAP_RESERVATION_STATUS = Stream.of(new String[][]{
            {"1", "Chờ xử lý"},
            {"2", "Xác nhận"},
            {"3", "Đã đến"},
            {"4", "Đang dùng"},
            {"5", "Đã xong"},
            {"6", "Khách huỷ"},
            {"7", "Từ chối"},
            {"8", "Chờ thanh toán"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static final Map<String, String> MAP_INVOICE_STATUS = Stream.of(new String[][] {
            { "1", "Chờ xử lý" },
            { "2", "Xác nhận" },
            { "3", "Đã đến" },
            { "4", "Đã chuyển" },
            { "5", "Đã xuất hóa đơn" },
            { "6", "Khách huỷ" },
            { "7", "Từ chối" },
            { "8", "Chờ thanh toán"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static final Map<Integer, List<Integer>> MAP_ITEM_TYPE = Stream.of(new String[][] {
            { "2", "[2,5]" },
            { "3", "[3,6]" },
            { "4", "[2,3]" },
    }).collect(Collectors.toMap(data -> Integer.parseInt(data[0]), data -> JsonUtil.fromJsonStringToList(data[1])));

    public static final List<String> LIST_HEADER_NAME_EXCEL_RESERVATION = Arrays.asList("STT","Tên công ty","Mã số thuế",
            "Số người","Người lớn","Trẻ em","Ngày","Giờ","Vị trí","Dịch vụ","Ghi chú","Ghi chú nội bộ");
    public static final List<String> LIST_HEADER_NAME_EXCEL_INVOICE = Arrays.asList("STT","Tên công ty","Mã số thuế",
            "Email","Thời gian","Trạng thái","Mã lịch hẹn/đơn hàng");
    public static final List<Integer> LIST_RATING_STAR = Arrays.asList(1,2,3,4,5);

    //STRING FORMAT
    public static final String FORMAT_2_DIGIT_NUMERIC = "%02d";
    public static final String FORMAT_NOT_FOUND = "%s NOT FOUND";
    public static final String FORMAT_EXIST = "%s đã tồn tại";
    public static final String FORMAT_RESERVATION_CODE_PERIODIC = "%s-" + FORMAT_2_DIGIT_NUMERIC + "-" + FORMAT_2_DIGIT_NUMERIC;
    public static final String FORMAT_RESERVATION_CODE = "%s-" + FORMAT_2_DIGIT_NUMERIC;
    public static final String FORMAT_REMOTE_ORDER_CODE = "%s-%s";
    public static final String FORMAT_PAYMENT_CODE = "%s-%s";

    // PATTERN
    public static final String PATTERN_TIME_HH = "hh";
    public static final String PATTERN_TIME_HH_mm = "HH:mm";
    public static final String PATTERN_DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_DATE_YYYY = "yyyy";
    public static final String PATTERN_DATE_MM = "MM";
    public static final String PATTERN_DATE_dd_MM_YYYY = "dd/MM/YYYY";
    public static final String PATTERN_DATE_ddMMYYYY = "ddMMYYYY";
    public static final String PATTERN_VALID_PHONE = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" +
            "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    //MSG
    public static final String MSG_INCLUDE_WHITE_SPACE = "bao gồm khoảng trắng ";
    public static final String MSG_NOT_INCLUDE_WHITE_SPACE = "không bao gồm khoảng trắng ";
    public static final String MSG_NOT_BLANK = "không được để trống";

    public static final String MSG_SIZE_GREATER = "phải có kích thước lớn hơn ";
    public static final String MSG_INVITATION_CONSUMER = "Xin chào %s, bạn vừa được %s gửi 1 lời mời trở thành khách hàng của nhà hàng %s. \n" +
            "Hãy bắt đầu tham gia bằng cách chấp nhận lời mời bên dưới.\n" +
            "%s";
    public static final Map<String, String> MAP_FIREBASE_NOTI_CONTENT = Stream.of(new String[][]{
            {"1", "Bạn có 1 đặt lịch mới từ %s"},
            {"2", "Bạn có 1 đơn hàng mới từ %s"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    public static final String MSG_NEW_ORDER = "Đơn hàng mới";

    //PATTERN
    public static final String PATTERN_EXC_WHITE_SPACE = "[^\\s]+";

    //EXCEL COLUMN IMPORT RESERVATION
    public static final int COLUMN_IMPORT_EXCEL_WEEK_DAYS = 0;
    public static final int COLUMN_IMPORT_EXCEL_DATE = 1;
    public static final int COLUMN_IMPORT_EXCEL_SESSION_OF_DAY = 2;
    public static final int COLUMN_IMPORT_EXCEL_INDEX = 3;
    public static final int COLUMN_IMPORT_EXCEL_NAME = 4;
    public static final int COLUMN_IMPORT_EXCEL_PHONE_NUMBER = 5;
    public static final int COLUMN_IMPORT_EXCEL_MAN = 6;
    public static final int COLUMN_IMPORT_EXCEL_CHILD = 7;
    public static final int COLUMN_IMPORT_EXCEL_TIME = 8;
    public static final int COLUMN_IMPORT_EXCEL_PLACE = 9;
    public static final int COLUMN_IMPORT_EXCEL_INTERNAL_NOTE = 10;
    public static final int COLUMN_IMPORT_EXCEL_STATUS = 11;
    public static final int COLUMN_IMPORT_EXCEL_INTERNAL_RESULT = 12;
    public static final int COLUMN_IMPORT_EXCEL_PLACE_UID = 14;

    //EXCEL COLUMN IMPORT ITEM
    public static final int COLUMN_IMPORT_EXCEL_ITEM_STT = 0;
    public static final int COLUMN_IMPORT_EXCEL_ITEM_CATEGORY = 1;
    public static final int COLUMN_IMPORT_EXCEL_ITEM_NAME = 2;
    public static final int COLUMN_IMPORT_EXCEL_ITEM_UNIT = 3;
    public static final int COLUMN_IMPORT_EXCEL_ITEM_DESCRIPTION = 6;
    public static final int COLUMN_IMPORT_EXCEL_ITEM_COST = 7;
    public static final int COLUMN_IMPORT_EXCEL_ITEM_STOCK = 8;
    public static final int COLUMN_IMPORT_EXCEL_ITEM_PRICE = 9;
    public static final int COLUMN_IMPORT_EXCEL_ITEM_TAX_TYPE = 10;
    public static final int COLUMN_IMPORT_EXCEL_ITEM_RESULT = 11;


    public static final String PREFIX_DATE_TIME_DEFAULT_PASS_WORD = "bglobal";
    public static final String PREFIX_YYYYMMDD = "yyyyMMdd";

    public static final Map<String, List<String>> MAP_ATTACHMENT_TYPE = new HashMap<>() {
    };

    public static final Map<String, List<Integer>> MAP_ROLE_NOTIFICATION_TYPE = new HashMap<>() {
    };

    public static final Map<String, String> MAP_RESERVATION_INFO = Stream.of(new String[][]{
            {"1", "Dịch vụ"},
            {"2", "Vị trí"},
            {"3", "Nhân viên được chỉ định"},
            {"4", "Số khách hàng"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    //NEWS
    public static final String NEWS_NOT_NULL = "%s image and post cannot at the same time";

    //STAFF
    public static final String STAFF_NOT_NULL = "%s not be NULL";
    public static final String STAFF_NOT_BE_CREATED = "end time must be greater than start time";
    public static final String NOT_FOUND = "%s not found";
    public static final String FACEBOOK_PAGE_NOT_NULL = "%s Facebook page is not linked";
    public static final String NOT_NULL = "%s not null";
    public static final String EMAIL_EXISTED = "%s existed";
    public static final String FACEBOOK_SHOP_AUTHOR = "%s is not unauthorized";
    public static final String NAME_NOT_NULL = "%s not be NULL";
    public static final String NAME_NOT_BLANK = "%s not be BLANK";
    public static final String NAME_NOT_EMPTY = "%s not be EMPTY";

    // STATE
    public static final String STATE_VALUE = "%s is not accepted";
    public static final int STATE_UN_ACCEPT = 0;

    //Length Shorten URL google map
    public static final int LENGTH_SHORTEN_GGMAP_URL = 37;

    // IS NEWS
    public static final int HAVE_NEWS = 1;
    public static final int HAVE_NOT_NEWS = 0;
    //Display Setting
    public static final String NOT_EXISTED = "%s is not existed";
    public static final String FORMAT_EXISTED = "%s is existed";
    public static final String DISPLAY_SETTING_FULL = "%s is full";

    //Shop HIGHLIGHT
    public static final int NUMBER_SHOP = 4;

    //KEY HEADER
    public static final String KEY_LANGUAGE_HEADER_HTTP_REQUEST = "location";
    public static final String KEY_LANGUAGE_HEADER_KEY_VI = "vi";
    public static final String KEY_LANGUAGE_HEADER_KEY_EN = "en";


    //KEY SUB DATA BLOG ITEM
    public static final String KEY_VIEWS = "views";
    public static final String KEY_LIKE = "like";
    public static final String KEY_SHARE = "share";

    //value number new item in category home screen
    public static final int KEY_NUMBER_NEW_BLOG_ITEM_HOME_SCREEN = 3;

    //Value of sort order
    public static final int VALUE_HOT_BLOG_ITEM_SORT_ORDER = 1;
    public static final int DEFAULT_SORT_ORDER_VALUE = 0;

    // number of hot new
    public static final int VALUE_NUMBER_HOT_BLOG_ITEM = 6;
    public static final int VALUE_NUMBER_NEW_BLOG_ITEM = 3;
    // IS NEW
    public static final int VALUE_ACTIVE_IS_NEW = 1;
    public static final int VALUE_INACTIVE_IS_NEW = 0;

    //Format date
    public static final String KEY_FORMAT_DATE_WITHOUT_TIME = "date";
    public static final String KEY_FORMAT_DATE_WITH_TIME = "time-date";
    public static final int KEY_MAX_LENGTH_SINGLE_LINE = 50;

    // Feature
    public static final int NUMBER_SORT_ORDER_FEATURE = 5;

    // Number default question in each category
    public static final int NUMBER_DEFAULT_QUESTION = 4;

    public static final String KEY_LK_TOKEN = "access_token_linkage";

    public static final String MISSING_FIELD = "Missing field(s)!";

    public static final int THREE_HOURS_SECOND = 10800;
    public static final int EARTH_RADIUS = 6371;
    public static final int M_PER_NM = 1852;
    public static final float DISTANCE_BETWEEN_POINT = 0.25F;
    public static final String AREA_WEATHER_NAME = "WEATHER_AREA";

    public static final String AREA_SEA_NAME = "SEA_AREA";
    public static final String NOTE_FOREIGN_SHIP_VIOLATION = "Tàu nước ngoài đi vào VN";
    public static final String NOTE_VIETNAM_SHIP_VIOLATION = "Tàu VN ra khỏi ranh giới";

}
