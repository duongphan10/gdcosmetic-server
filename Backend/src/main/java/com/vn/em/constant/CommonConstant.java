package com.vn.em.constant;

public class CommonConstant {

    public static final String SORT_TYPE_ASC = "ASC";
    public static final String SORT_TYPE_DESC = "DESC";
    public static final Integer PAGE_SIZE_DEFAULT = 10;

    public static final Integer ZERO_INT_VALUE = 0;
    public static final Integer ONE_INT_VALUE = 1;
    public static final Long ZERO_VALUE = 0L;
    public static final Long ONE_VALUE = 1L;

    public static final String EMPTY_STRING = "";
    public static final String BEARER_TOKEN = "Bearer";

    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE_TIME_FILE = "yyyyMMdd-HHmmss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String CONTENT_TYPE_IMAGE = "png jpg jpeg webp gif";
    public static final String CONTENT_TYPE_VIDEO = "mp4 x-msvideo x-flv x-ms-wmv quicktime x-matroska mpeg webm h264 hevc x-m4v";
    public static final String CONTENT_TYPE_AUDIO = "mpeg wav flac flac aac mp4 x-ms-wma";
    public static final String CONTENT_TYPE_DOCUMENT = "pdf vnd.openxmlformats-officedocument.wordprocessingml.document " +
            "vnd.openxmlformats-officedocument.spreadsheetml.sheet vnd.openxmlformats-officedocument.presentationml.presentation " +
            "vnd.oasis.opendocument.text vnd.oasis.opendocument.spreadsheet vnd.oasis.opendocument.presentation rtf " +
            "zip x-rar-compressed x-7z-compressed x-tar gzip x-arj x-compress json xml";
    public static final String CONTENT_TYPE_DOCUMENT_TEXT = "plain html css javascript xml csv markdown rtf x-java-source x-python x-c x-c++ x-csharp";
    public static final String UPLOAD_PATH_IMAGE = "upload/images";
    public static final String UPLOAD_PATH_FILE = "upload/files";
    public static final String COMPANY_CODE = "GD";
    public static final Integer MAX_NUMBER_IMAGE = 5;
    public static final Integer MAX_NUMBER_VIDEO = 1;
    public static final Long MAX_IMAGE_SIZE_MB = 5L;
    public static final Long MAX_VIDEO_SIZE_MB = 20L;
    public static final Long MAX_IMAGE_SIZE_BYTES = MAX_IMAGE_SIZE_MB * 1024 * 1024;
    public static final Long MAX_VIDEO_SIZE_BYTES = MAX_VIDEO_SIZE_MB * 1024 * 1024;
    public static final Long ALLOWANCE = 1000000L;
    public static final Float INSURANCE = 0.105F;
    public static final Long PERSONAL_DEDUCTION = 11000000L;
    public static final Long DEPENDENT_PERSON = 4400000L;

}
