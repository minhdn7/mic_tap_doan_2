package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url;

/**
 * Created by VietNH on 4/10/2017.
 */

public class ServiceUrl {
    public static final String CHECK_VERSION_URL = "/qlvb/api/checkversion/";
    public static final String LOGIN_URL = "/qlvb/api/login/";
    public static final String LOGOUT_URL = "/qlvb/api/logout/";
    public static final String CHANGE_PASSWORD_URL = "/qlvb/api/updatepassword/";
    public static final String GET_USER_INFO_URL = "/qlvb/api/getuserinfo/me/";
    public static final String GET_CONTACT_INFO_URL = "/qlvb/api/getuserinfo/{userid}/";
    public static final String GET_CONTACT_URL = "/qlvb/api/getcontacts/";
    public static final String GET_COUNT_DOC_WAIT_URL = "/qlvb/api/document/getpagingwaitingdocument/";
    public static final String GET_DOC_WAIT_URL = "/qlvb/api/document/getlistwaitingdocument/";
    public static final String GET_COUNT_DOC_PROCECSSED_URL = "/qlvb/api/document/getpagingprocesseddocument/";
    public static final String GET_DOC_PROCECSSED_URL = "/qlvb/api/document/getlistprocesseddocument/";
    public static final String CHECK_RECOVER_DOC_RECEIVE_URL = "/qlvb/api/incommingdocument/checkrecoverdocument/{id}/";
    public static final String CHECK_RECOVER_DOC_SEND_URL = "/qlvb/api/outgoingdocument/checkrecoverdocument/{id}/";
    public static final String RECOVER_DOC_RECEIVE_URL = "/qlvb/api/incommingdocument/recoverdocument/{id}/";
    public static final String RECOVER_DOC_SEND_URL = "/qlvb/api/outgoingdocument/recovertranferdocument/{id}/";
    public static final String VIEW_DIAGRAM_URL = "/qlvbdh/view_img.jsp";
    public static final String GET_COUNT_DOC_NOTIFICATION_URL = "/qlvb/api/document/getpagingnotifydocument/";
    public static final String GET_DOC_NOTIFICATION_URL = "/qlvb/api/document/getlistnotifydocument/";
    public static final String GET_COUNT_DOC_EXPIRED_URL = "/qlvb/api/document/getpagingoutdatedocument/";
    public static final String GET_DOC_EXPIRED_URL = "/qlvb/api/document/getlistoutdatedocument/";
    public static final String GET_DETAIL_DOCUMENT_WAITING_URL = "/qlvb/api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_WAITING_URL = "/qlvb/api/document/getactivitylog/{id}/";

    public static final String GET_ATTACH_FILE_DOCUMENT_WAITING_URL = "/qlvb/api/file/getfileattach/{id}/";

    public static final String GET_RELATED_DOCUMENT_WAITING_URL = "/qlvb/api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_WAITING_URL = "/qlvb/api/file/getfilerelated/{id}/";
    public static final String GET_DETAIL_DOCUMENT_PROCESSED_URL = "/qlvb/api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_PROCESSED_URL = "/qlvb/api/document/getactivitylog/{id}/";
    public static final String GET_ATTACH_FILE_DOCUMENT_PROCESSED_URL = "/qlvb/api/file/getfileattach/{id}/";
    public static final String GET_RELATED_DOCUMENT_PROCESSED_URL = "/qlvb/api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_PROCESSED_URL = "/qlvb/api/file/getfilerelated/{id}/";
    public static final String GET_DETAIL_DOCUMENT_EXPIRED_URL = "/qlvb/api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_EXPIRED_URL = "/qlvb/api/document/getactivitylog/{id}/";
    public static final String GET_ATTACH_FILE_DOCUMENT_EXPIRED_URL = "/qlvb/api/file/getfileattach/{id}/";
    public static final String GET_RELATED_DOCUMENT_EXPIRED_URL = "/qlvb/api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_EXPIRED_URL = "/qlvb/api/file/getfilerelated/{id}/";
    public static final String GET_DETAIL_DOCUMENT_NOTIFICATION_URL = "/qlvb/api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_NOTIFICATION_URL = "/qlvb/api/document/getactivitylog/{id}/";
    public static final String GET_ATTACH_FILE_DOCUMENT_NOTIFICATION_URL = "/qlvb/api/file/getfileattach/{id}/";
    public static final String GET_RELATED_DOCUMENT_NOTIFICATION_URL = "/qlvb/api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_NOTIFICATION_URL = "/qlvb/api/file/getfilerelated/{id}/";
    public static final String GET_LIST_TYPE_DOC_URL = "/qlvb/api/document/getlisttypecode/";
    public static final String GET_LIST_FIELD_DOC_URL = "/qlvb/api/document/getlistfield/";
    public static final String GET_LIST_PRIORITY_DOC_URL = "/qlvb/api/document/getlistpriority/";
    //public static final String GET_LIST_BOOK_DOC_URL = "/qlvb/api/document/getlistbook/";
    public static final String GET_LIST_TYPE_CHANGE_DOC_URL = "/qlvb/api/document/getapprovedvalue/";
    public static final String GET_COUNT_DOC_SEARCH_URL = "/qlvb/api/document/paginglookupbyparam/";
    public static final String GET_DOC_SEARCH_URL = "/qlvb/api/document/getlistlookupbyparam/";
    public static final String GET_COUNT_DOC_ADVANCE_SEARCH_URL = "/qlvb/api/document/paginglookupdocument/";
    public static final String GET_DOC_ADVANCE_SEARCH_URL = "/qlvb/api/document/lookupdocument/";
    public static final String GET_COUNT_DOC_MARK_URL = "/qlvb/api/document/pagingsigneddocument/";
    public static final String GET_DOC_MARK_URL = "/qlvb/api/document/getlistsigneddocument/";
    public static final String GET_DETAIL_DOCUMENT_MARK_URL = "/qlvb/api/document/getdetaildocument/{id}/";
    public static final String GET_LOGS_DOCUMENT_MARK_URL = "/qlvb/api/document/getactivitylog/{id}/";
    public static final String GET_ATTACH_FILE_DOCUMENT_MARK_URL = "/qlvb/api/file/getfileattach/{id}/";
    public static final String GET_RELATED_DOCUMENT_MARK_URL = "/qlvb/api/document/getdocrelated/{id}/";
    public static final String GET_RELATED_FILE_MARK_URL = "/qlvb/api/file/getfilerelated/{id}/";
    public static final String GET_SCHEDULES_URL = "/qlvb/api/schedule/getlistschedule/";
    public static final String CHECK_MARK_DOC_URL = "/qlvb/api/document/checksigneddocument/{id}/";
    public static final String MARK_DOC_URL = "/qlvb/api/document/signeddocument/{id}/";

//    public static final String SIGN_DOC_URL = "/VNPTsignature/rest/ioffice/signature";
    public static final String SIGN_DOC_URL = "/qlvb/api/document/signdocument/{docid}/";

    public static final String GET_DETAIL_SCHEDULE_MEETING_URL = "/qlvb/api/schedule/getdetailmeetingschedule/{id}/";
    public static final String GET_DETAIL_SCHEDULE_BUSSINESS_URL = "/qlvb/api/schedule/getdetailworkingschedule/{id}/";
    public static final String GET_PERSONS_PROCESS_URL = "/qlvb/api/document/getusertotranfer/";
    public static final String GET_PERSONS_SEND_URL = "/qlvb/api/document/getuserconcurrentsend/";
    public static final String GET_PERSONS_NOTIFY_URL = "/qlvb/api/getuserunitnotify/";
    public static final String GET_REPORT_DOCUMENT_URL = "/qlvb/api/report/documentreport/";
    public static final String GET_REPORT_WORK_URL = "/qlvb/api/report/jobreport/{month}/";
    public static final String CHECK_COMMENT_DOC_URL = "/qlvb/api/incommingdocument/checksendcomment/{id}/";
    public static final String COMMENT_DOC_URL = "/qlvb/api/incommingdocument/sendcomment/";
    public static final String CHANGE_DOC_SEND_URL = "/qlvb/api/outgoingdocument/tranferdocument/";
    public static final String CHANGE_DOC_RECEIVE_URL = "/qlvb/api/incommingdocument/tranferdocument/";
    public static final String CHANGE_DOC_PROCESS_URL = "/qlvb/api/document/forwarddocument/";
    public static final String CHANGE_DOC_NOTIFY_URL = "/qlvb/api/document/forwarddocument/";
    public static final String CHANGE_DOC_DIRECT_URL = "/qlvb/api/incommingdocument/promulgatedocument/";
    public static final String GET_NOTIFY_URL = "/qlvb/api/notifycation/getlistnotify/";
    public static final String READED_NOTIFY_URL = "/qlvb/api/notifycation/setreadnotify/";
    public static final String GET_JOB_POSSITION_URL = "/qlvb/api/jobposition/getlistjobposition/";
    public static final String GET_UNIT_URL = "/qlvb/api/document/getlistunit/";
    public static final String DOWNLOAD_FILE_URL = "/qlvb/api/file/download/{id}/";
    public static final String GET_FILE_URL_DOC = "/qlvb/api/generateotp/{id}/";
    public static final String GET_AVATAR_URL = "/qlvb/api/getavatar/{userid}/";
    public static final String GET_LIST_SWITCH_USER_URL = "/qlvb/api/switch/{userid}/";
    public static final String GET_CHIDAO_NHAN_URL = "/qlvb/api/information/getlistreceive/";
    public static final String GET_CHIDAO_GUI_URL = "/qlvb/api/information/getlistsend/";
    public static final String UPLOAD_FILE_URL = "/qlvb/api/file/upload/";
    public static final String CREATE_CHIDAO_URL = "/qlvb/api/information/create/";
    public static final String EDIT_CHIDAO_URL = "/qlvb/api/information/edit/";
    public static final String GET_PERSON_CHIDAO_URL = "/qlvb/api/getuserunit/";
    public static final String SAVE_PERSON_CHIDAO_URL = "/qlvb/api/information/updateemployee/";
    public static final String GET_PERSON_RECEIVE_CHIDAO_URL = "/qlvb/api/information/getlistnotify/";
    public static final String GET_PERSON_GROUP_CHIDAO_URL = "/qlvb/api/information/getgroup/";
    public static final String SEND_CHIDAO_URL = "/qlvb/api/information/send/";
    public static final String GET_FLOW_CHIDAO_URL = "/qlvb/api/information/getflow/{id}/";
    public static final String GET_WEBVIEW_FILE_CHIDAO_URL = "/qlvb/api/generateotpinformation/";
    public static final String GET_FILE_CHIDAO_URL = "/qlvb/api/information/getfiles/{id}/";
    public static final String GET_DELETE_CHIDAO_URL = "/qlvb/api/information/delete/{id}/";
    public static final String GET_DETAIL_CHIDAO_URL = "/qlvb/api/information/getdetail/{id}/";
    public static final String DOWNLOAD_FILE_CHIDAO_URL = "/qlvb/api/information/download/";
    public static final String GET_PERSON_IN_GROUP_CHIDAO_URL = "/qlvb/api/information/getuserbygroup/";
    public static final String GET_PERSON_REPLY_CHIDAO_URL = "/qlvb/api/information/getlistuser/";
    public static final String GET_PERSON_RECEIVED_CHIDAO_URL = "/qlvb/api/information/getuserreceiver/";
    public static final String FINISH_DOC_URL = "/qlvb/api/document/finish/{id}/";
    public static final String CHECK_FINISH_DOC_URL = "/qlvb/api/document/checkfinish/{id}/";
    public static final String GET_LIEN_THONG_XL_URL = "/qlvb/api/unit/getlistinternaltranfer/";
    public static final String GET_LIEN_THONG_BH_URL = "/qlvb/api/unit/getlistinternal/{id}/";
    public static final String GET_SCHEDULES_BOSS_URL = "/qlvb/api/schedule/getlistworkingschedule/";
    public static final String CHECK_CHANGE_DOC_PROCECSSED_URL = "/qlvb/api/document/checktranferadditional/{id}/";
    public static final String CHANGE_DOC_NOTIFY_XEMDB_URL = "/qlvb/api/document/tranfernotify/";
    public static final String GET_GROUP_PERSON_CN_URL = "/qlvb/api/group/getlistgroupuser/";
    public static final String GET_GROUP_PERSON_DV_URL = "/qlvb/api/group/getlistgroupunit/";
    public static final String GET_PERSONS_SEND_PROCESS_URL = "/qlvb/api/document/getlistusertranfer/";
    public static final String GET_UNIT_CLERK_URL = "/qlvb/api/document/getlistunitpublish/";
    public static final String GET_COUNT_DOCUMENT = "/qlvb/api/document/countmenu/";
    public static final String CHECK_STORE_DOC_URL = "/qlvb/api/notifycation/getdetailnotify/{id}/";
    public static final String GET_LIST_COMMENT_URL = "/qlvb/api/document/getlistcomment/{id}/{pageno}/{pagesize}/";
    public static final String SEND_LIST_COMMENT_URL = "/qlvb/api/outgoingdocument/contributecomments/";
    public static final String GET_COUNT_COMMENT_URL = "/qlvb/api/document/countmenu/";

    public static final String KY_VAN_BAN_URL = "/qlvb/api/document/signdocument/{docid}/{fileid}/";


}