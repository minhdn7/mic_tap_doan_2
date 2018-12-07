package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;

/**
 * Created by VietNH on 9/8/2017.
 */

public class Constants {
    public static final double SCHEDULE_HOUR_DEFAULT = 8;
    public static final int SCHEDULE_NUMBER_DAY_OF_WEEK_DEFAULT = 5;
    public static final String RESPONE_SUCCESS = "0";
    public static final String IS_READ = "TRUE";
    public static final String IS_NOT_READ = "FALSE";
    public static final String HAS_NOT_FILE = "none";
    public static final String HAS_FILE = "null";
    public static final String CHANGE_PASSWORD_SUCCESS = "TRUE";
    public static final int DISPLAY_RECORD_SIZE = 10;
    public static final String PAGE_NO_DEFAULT = "-1";
    public static final String PAGE_NO_ALL = "1";
    public static final int RESPONE_UNAUTHEN = 401;
    public static final int RESPONE_INVALID = 400;
    public static final int RESPONE_SUCCESS_HANDLER = 200;
    public static final String DOCUMENT_RECEIVE = "1";
    public static final String DOCUMENT_SEND = "2";
    public static final String DOCUMENT_RECEIVE_RECOVERED = "TRUE";
    public static final String DOCUMENT_RECEIVE_NOT_RECOVERED = "FALSE";
    public static final String DOCUMENT_SEND_NOT_RECOVERED = "none";
    public static final String DOCUMENT_RECOVERED_SUCCESS = "TRUE";
    public static final String CHECK_RECOVER_ACTION = "CHECKRECOVER";
    public static final String RECOVER_ACTION = "RECOVER";
    public static final String CHECK_MARK_ACTION = "CHECKMARK";
    public static final String MARK_ACTION = "MARK";
    public static final String DOCUMENT_WAITING = "DOCUMENT_WAITING";
    public static final String DOCUMENT_PROCESSED = "DOCUMENT_PROCESSED";
    public static final String DOCUMENT_NOTIFICATION = "DOCUMENT_NOTIFICATION";
    public static final String DOCUMENT_MARK = "DOCUMENT_MARK";
    public static final String DOCUMENT_EXPIRED = "DOCUMENT_EXPIRED";
    public static final int DELAY_TIME_SEARCH = 2000;

    public static final String NEW_HISTORY = "0";
    public static final String PROCESSING_HISTORY = "1";
    public static final String PROCESSED_HISTORY = "2";

    public static final String TYPE_NOTIFY_DOCUMENT = "1,2,KYVANBAN";
    public static final String TYPE_NOTIFY_WORK = "3";
    public static final String TYPE_NOTIFY_PROFILE = "4";
    public static final String TYPE_NOTIFY_SCHEDULE = "5";
    public static final String TYPE_NOTIFY_MAIL = "6";
    public static final String TYPE_NOTIFY_CHIDAO = "7";

    public static final String MARKED = "1";
    public static final String NOT_MARKED = "0";
    public static final String MARKED_SUCCESS = "true";

    public static final String FINISH_SUCCESS = "true";
    public static final String IS_FINISH = "true";

    public static final String IS_CHANGE_DOC = "true";

    public static final String SEND_RULE = "TRUE";
    public static final String SIGN_RULE = "TRUE";
    public static final String COMMENT_RULE = "true";
    public static final String RECOVER_RULE = "true";
    public static final String VIEW_COMMENT = "true";

    public static final String CHANGE_DOC_SUCCESS = "TRUE";

    public static final String SEND_TAG = "SEND_TAG";
    public static final String COMMENT_TAG = "COMMENT_TAG";
    public static final String MARK_TAG = "MARK_TAG";
    public static final String SIGN_TAG = "SIGN_TAG";
    public static final String FLOW_TAG = "FLOW_TAG";
    public static final String HISTORY_TAG = "HISTORY_TAG";

    public static final String RECOVER_TAG = "RECOVER_TAG";
    public static final String VIEW_COMMENT_TAG = "VIEW_COMMENT_TAG";

    public static final String EDIT_TAG = "EDIT_TAG";
    public static final String REMOVE_TAG = "REMOVE_TAG";

    public static final String COMMENT = "true";
    public static final String NOT_COMMENT = "false";
    public static final String COMMENTED = "OK";

    public static final String SCHEDULE_MEETING = "2";
    public static final String SCHEDULE_BUSSINESS = "1";
    public static final String SCHEDULE_LIST = "1";
    public static final String SCHEDULE_NOTIFY = "2";

    public static final String TYPE_PERSON_PROCESS = "TYPE_PERSON_PROCESS";
    public static final String TYPE_PERSON_SEND = "TYPE_PERSON_SEND";
    public static final String TYPE_PERSON_NOTIFY = "TYPE_PERSON_NOTIFY";
    public static final String TYPE_SEND_PERSON_PROCESS = "TYPE_SEND_PROCESS";
    public static final String TYPE_PERSON_DIRECT = "TYPE_PERSON_DIRECT";
    public static final String TYPE_SEND_VIEW = "TYPE_SEND_VIEW";
    public static final String TYPE_VIEW_COMMENT = "TYPE_VIEW_COMMENT";

    public static final String TYPE_SEND_PROCESS_REQUEST = "1";
    public static final String TYPE_SEND_NOTIFY_REQUEST = "0";

    public static final int TYPE_SEND_NOTIFY = 6;
    public static final int TYPE_SEND_PROCESS = 5;
    public static final int TYPE_COMMENT_VIEW = 7;

    public static final String PDF = "PDF";
    public static final String DOC = "DOC";
    public static final String DOCX = "DOCX";
    public static final String XLS = "XLS";
    public static final String XLSX = "XLSX";
    public static final String ZIP = "ZIP";
    public static final String RAR = "RAR";
    public static final String PPT = "PPT";
    public static final String PPTX = "PPTX";
    public static final String JPEG = "JPEG";
    public static final String JPG = "JPG";
    public static final String PNG = "PNG";
    public static final String GIF = "GIF";
    public static final String TIFF = "TIFF";
    public static final String BMP = "BMP";
    public static final String TXT = "TXT";
    public static final String MPP = "MPP";

    public static final String HOME_TRANGCHU = "TRANGCHU";
    public static final String HOME_VANBANDENCANXULY = "VANBANDENCANXULY";
    public static final String HOME_TRANGTINTUC = "TRANGTINTUC";
    public static final String HOME_VANBANCHOXULY = "VANBANCHOXULY";
    public static final String HOME_VANBANDAXULY = "VANBANDAXULY";
    public static final String HOME_VANABANXEMDEBIET = "VANABANXEMDEBIET";
    public static final String HOME_VANBANDENHAN = "VANBANDENHAN";
    public static final String HOME_VANBANTHONGBAO = "VANBANTHONGBAO";
    public static final String HOME_VANBANDANHDAU = "VANBANDANHDAU";
    public static final String HOME_DANHBA = "DANHBA";
    public static final String HOME_QUANLYLICHHOP = "QUANLYLICHHOP";
    public static final String HOME_BAOCAO = "BAOCAO";
    public static final String HOME_BAOCAOTHONGKE = "BAOCAOTHONGKE";
    public static final String HOME_THONGTINCHIDAO = "THONGTINCHIDAO";

    public static final String NGAY_BAN_HANH_DIALOG = "NGAY_BAN_HANH_DIALOG";
    public static final String NGAY_SOAN_THAO_DIALOG = "NGAY_SOAN_THAO_DIALOG";
    public static final String HAN_XU_LY_DIALOG = "HAN_XU_LY_DIALOG";

    //Type Kho Van ban

    public static final String CONSTANTS_VAN_BAN_DA_XU_LY = "Văn bản đã xử lý";
    public static final String CONSTANTS_VAN_BAN_XEM_DE_BIET = "Văn bản Xem để biết";
    public static final String CONSTANTS_VAN_BAN_DANH_DAU = "Văn bản đánh dấu";
    public static final String CONSTANTS_TRA_CUU_VAN_BAN = "Tra cứu văn bản";
    public static final String CONSTANTS_VAN_BAN_DEN_CHO_XU_LY = "Văn bản đến chờ xử lý";
    public static final String CONSTANTS_VAN_BAN_CHO_PHE_DUYET = "Văn bản chờ phê duyệt";
    public static final String CONSTANTS_VAN_BAN_DI = "Văn bản đi";
    public static final String CONSTANTS_VAN_BAN_DEN = "Văn bản đến";
    public static final String CONSTANTS_VAN_BAN_DEN_HAN_QUA_HAN = "Văn bản đến hạn/quá hạn";
    public static final String CONSTANTS_VAN_BAN_CHO_TONG_GIAM_DOC_XU_LY = "Văn bản chờ Tổng GĐ xử lý";

    public static final int HOME = 0;
    public static final int DOCUMENT = 1;
    public static final int CONTACT = 3;
    public static final int REPORT_EXT = 4;
    public static final int SETTING = 5;
    public static final int LOGOUT = 8;
    public static final int DOC_WAITING = 0;
    public static final int DOC_PROCESSED = 1;
    public static final int DOC_EXPIRED = 5;
    public static final int DOC_NOTIFICATION = 2;
    public static final int DOC_MARK = 3;
    public static final int DOC_SEARCH = 4;
    public static final int SETTING_PROFILE = 0;
    public static final int SETTING_CHANGE_PASSWORD = 1;
    public static final int SETTING_DEFAULT_HOME = 2;
    public static final int CHIDAO_NHAN = 0;
    public static final int CHIDAO_GUI = 1;


    public static final String CONSTANTS_TEXT_TRANG_CHU = "Trang chủ";
    public static final String CONSTANTS_TEXT_TRANG_TIN_TUC= "Trang tin tức";
    public static final String CONSTANTS_TEXT_QUAN_LY_VAN_BAN = "Quản lý văn bản";
    public static final String CONSTANTS_TEXT_DANH_BA = "Danh bạ";
    public static final String CONSTANTS_TEXT_LICH_CONG_TAC_LANH_DAO = "Lịch Công tác lãnh đạo";
    public static final String CONSTANTS_TEXT_THONG_TIN_DIEU_HANH = "Thông tin điều hành";
    public static final String CONSTANTS_TEXT_BAO_CAO_THONG_KE = "Báo cáo thống kê";
    public static final String CONSTANTS_TEXT_CAI_DAT_HE_THONG = "Cài đặt hệ thống";
    public static final String CONSTANTS_TEXT_DANG_XUAT = "Đăng xuất";


    // notification type
    public static final String CONSTANTS_NOTI_CHOXULY = "CHOXULY";
    public static final String CONSTANTS_NOTI_TRACUU = "TRACUU";
    public static final String CONSTANTS_NOTI_DAXULY = "DAXULY";
    public static final String CONSTANTS_NOTI_THONGBAO = "THONGBAO";
    public static final String CONSTANTS_NOTI_WEB = "WEB";
    public static final String CONSTANTS_NOTI_TTDH = "TTDH";

    public static final String CONSTANTS_INTENT_DETAIL_INFOR = "DetailDocumentInfo";
    public static final String CONSTANTS_INTENT_TYPE_CHANGE_DOC = "TypeChangeDocumentRequest";
    public static final String CONSTANTS_INTENT_DOC_WATTING_INFOR = "DocumentWaitingInfo";
    public static final String CONSTANTS_INTENT_DOC_SEARCH_INFOR = "DocumentSearchInfo";
    public static final String CONSTANTS_INTENT_DOC_PROCESSED_INFOR = "DocumentProcessedInfo";
    public static final String CONSTANTS_INTENT_DOC_NOTIFICATION_INFOR = "DocumentNotificationInfo";
}
