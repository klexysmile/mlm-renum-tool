package mlm.tool.mungwin.com.mlmtool.utils;

import java.text.SimpleDateFormat;
import java.util.zip.DeflaterOutputStream;

public class Parameters {

    public static final String TRANSACTION_STATUS_INITIATED = "INITIATED";
    public static final String TRANSACTION_STATUS_PENDING = "PENDING";
    public static final String TRANSACTION_STATUS_CANCELLED = "CANCELLED";
    public static final String TRANSACTION_STATUS_COMPLETED = "COMPLETED";
    public static final String TRANSACTION_STATUS_PAID = "PAID";

    public static final String TRANSACTION_TYPE_REGISTRATION = "REGISTRATION";
    public static final String TRANSACTION_TYPE_PRODUCT_SALE = "SALE";
    public static final String TRANSACTION_TYPE_PRODUCT_BONUS = "BONUS";
    public static final String TRANSACTION_TYPE_PRODUCT_ACCUMULATION = "ACCUMULATION";

    public static final String BONUS_TYPE_QUALIFICATION = "QUALIFICATION BONUS";
    public static final String BONUS_TYPE_REGISTRATION = "DIRECT REFERRAL BONUS";
    public static final String BONUS_CONDITION_REGISTER_THREE = "REGISTER_THREE";

    public static final String SETTINGS_KEY_REGISTRATION_FEE = "REGISTRATION_FEE";
    public static final String CUSTOMER_LINK_PATH_NODE_DELIMITER = "#";
    public static final String CUSTOMER_LINK_PATH_ITEM_DELIMITER = "*";
    public static final Integer CUSTOMER_LINK_PATH_LENGTH = 20;

    public static final String MESSAGE_TYPE_REGISTRATION = "REGISTRATION";
    public static final String MESSAGE_TYPE_BONUS = "BONUS";

    public static final Double VALUE_REGISTRATION_FEES = 194.07;
    public static final Integer VALUE_REGISTRATION_POINTS = 2;

    public static final String PAYMENT_LOCATION_AUTOMATIC = "AUTOMATIC";
    public static final String ACCOUNT_MOVEMENT_DEBIT = "DEBIT";
    public static final String ACCOUNT_MOVEMENT_CREDIT = "CREDIT";

    public static final String CURRENCY_PAYCASH = "PCH";

    public static final String DLC_EXCHANGE = "dlc";
    public static final String DLC_MEMBER_REGISTRATION_QUEUE = "dlc-member-registration";
    public static final String DLC_MEMBER_REGISTRATION_EXCHANGE_KEY = "dlc-member-registration";
    public static final String DLC_MEMBER_REGISTRATION_RESPONSE_EXCHANGE_KEY = "dlc-member-registration-response";

    public static final SimpleDateFormat sdfMysql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

}
