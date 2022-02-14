package sbnri.consumer.android.util;

import android.content.Context;
import android.text.TextUtils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import sbnri.consumer.android.R;


/**
 */

public class DateTimeUtils {

    public static final String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String UTC_FORMAT_CUST_SCREENDETECTION = "yyyy-MM-dd HH:mm:ss Z";
    public static final String DIAGNOSIS_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DD_MMM_YYYY = "dd MMM yyyy";
    public static final String TIME_AND_DATE = "hh:mm a, dd MMM yyyy";
    public static final String USER_FRIENDLY_DATE = "dd MMM, yyyy";
    public static final String DATE_AND_TIME = "dd MMM yyyy | hh:mm a";
    public static final String TIME_AM_PM = "h:mm a";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String E_DD_MMM_YYY = "E, dd MMM yy";
    public static final String E_DD_MMM = "E, dd MMM";
    public static final String DD_MMM_YY = "dd MMM, yy";
    public static final String YYYY_MM_DD = "YYYY-MM-dd";

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static String getCurrentTimeHHmmss(Context context) {
        SimpleDateFormat strToDate = new SimpleDateFormat("HH:mm:ss", getAppLanguageLocale(context));
        return strToDate.format(new Date());
    }

    public static String getCurrentDate(Context context) {
        SimpleDateFormat strToDate = new SimpleDateFormat("yyyy-MM-dd", getAppLanguageLocale(context));
        return strToDate.format(new Date());
    }

    public static String getCurrentTimeStamp(Context context) {
        return getCurrentDate(context) + " " + getCurrentTimeHHmmss(context);
    }

    public static Calendar getCalendarFomString(String value, Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTimeUtils.stringToDate(value, UTC_FORMAT, context));
        return calendar;
    }

    public static Date stringToDate(String date, String pattern, Context context) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, getAppLanguageLocale(context));
        Date dateNew = new Date();
        if (date.endsWith("Z")) {
            date = date.replace("Z", "+00:00");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
        } else {
            format.setTimeZone(TimeZone.getDefault());
        }

        try {
            dateNew = format.parse(date);
            System.out.println(dateNew);
        } catch (ParseException e) {
            //Logger.e(e.getLocalizedMessage());
        }
        return dateNew;
    }

    public static String getSlotScheduleDateTime(String strDate, String time, Context context) {
        if (!TextUtils.isEmpty(strDate)) {
            SimpleDateFormat inputSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", getAppLanguageLocale(context));
            SimpleDateFormat outputSDF = new SimpleDateFormat("yyyy-MM-dd", getAppLanguageLocale(context));
            try {
                if (strDate.endsWith("Z")) {
                    strDate = strDate.replace("Z", "+00:00");
                    inputSDF.setTimeZone(TimeZone.getTimeZone("GMT"));
                } else {
                    inputSDF.setTimeZone(TimeZone.getDefault());
                }

                Date date = inputSDF.parse(strDate);
                outputSDF.setTimeZone(TimeZone.getDefault());

                String output = outputSDF.format(date);
                String timeZone = getOffset(context);

                return output + "T" + time + ".000" + timeZone.substring(0, 3) + ":" + timeZone.substring(3, timeZone.length());

            } catch (ParseException e) {
               // Logger.d(e.getLocalizedMessage());
            }
        }
        return "";
    }


    public static String getOffset(Context context) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("Z", Locale.ENGLISH);

        return date.format(currentLocalTime);
    }

    public static String getFormattedDate(String time, String inputPattern, String outputPattern, Context context) {
        if (!TextUtils.isEmpty(time)) {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, getAppLanguageLocale(context));
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, getAppLanguageLocale(context));
            try {
                if (time.endsWith("Z")) {
                    time = time.replace("Z", "+00:00");
                    inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                } else {
                    inputFormat.setTimeZone(TimeZone.getDefault());
                }

                outputFormat.setTimeZone(TimeZone.getDefault());

                Date date = inputFormat.parse(time);
                return outputFormat.format(date);

            } catch (ParseException e) {
               // Logger.d(e.getLocalizedMessage());
            }
        }
        return "";
    }

    public static String getSlotTime(String inputTime, Context context) {
        SimpleDateFormat inputSDF = new SimpleDateFormat("HH:mm:ss", getAppLanguageLocale(context));
        SimpleDateFormat outputSDF = new SimpleDateFormat("hh:mm aa", getAppLanguageLocale(context));
        try {
            Date date = inputSDF.parse(inputTime);
            return outputSDF.format(date);
        } catch (ParseException e) {
           // Logger.d(e.getLocalizedMessage());
        }
        return "";
    }

    public static String getSlotTimeWithLocale(String inputTime, Context context){
        if(TextUtils.isEmpty(inputTime)) return "";
        SimpleDateFormat inputSDF = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        SimpleDateFormat outputSDF = new SimpleDateFormat("hh:mm aa", getAppLanguageLocale(context));
        try {
            Date date = inputSDF.parse(inputTime);
            return outputSDF.format(date);
        } catch (ParseException e) {
            //Logger.d(e.getLocalizedMessage());
        }
        return "";
    }

    public static String getStartSlot(String inputTime, Context context) {
        SimpleDateFormat inputSDF = new SimpleDateFormat("HH:mm:ss", getAppLanguageLocale(context));
        SimpleDateFormat outputSDF = new SimpleDateFormat("hh", getAppLanguageLocale(context));
        try {
            Date date = inputSDF.parse(inputTime);
            return outputSDF.format(date);
        } catch (ParseException e) {
           // Logger.d(e.getLocalizedMessage());
        }
        return "";
    }

    public static String getEndSlot(String inputTime, Context context) {
        SimpleDateFormat inputSDF = new SimpleDateFormat("HH:mm:ss", getAppLanguageLocale(context));
        SimpleDateFormat outputSDF = new SimpleDateFormat("hh aa", getAppLanguageLocale(context));
        try {
            Date date = inputSDF.parse(inputTime);
            return outputSDF.format(date);
        } catch (ParseException e) {
           // Logger.d(e.getLocalizedMessage());
        }
        return "";
    }

    public static String getCurrentDateTimeWithZone(Context context) {
        SimpleDateFormat strToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", getAppLanguageLocale(context));
        String syncDate = strToDate.format(new Date());
       // Logger.i("SYNC DATE ", syncDate);
        return syncDate;
    }

    public static Date stringToDate(String date, Context context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", getAppLanguageLocale(context));
        Date dateNew = new Date();
        if (date.endsWith("Z")) {
            date = date.replace("Z", "+00:00");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
        } else {
            format.setTimeZone(TimeZone.getDefault());
        }

        try {
            dateNew = format.parse(date);
            System.out.println(dateNew);
        } catch (ParseException e) {
            //Logger.d(e.getLocalizedMessage());
        }
        return dateNew;
    }

    public static String getFormattedLocalDate(String time, String outputPattern, Context context) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(UTC_FORMAT, getAppLanguageLocale(context));
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, getAppLanguageLocale(context));

        if (!TextUtils.isEmpty(time)) {
            if (time.endsWith("Z")) {
                time = time.replace("Z", "+00:00");
                inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            } else {
                inputFormat.setTimeZone(TimeZone.getDefault());
            }

            outputFormat.setTimeZone(TimeZone.getDefault());

            try {
                Date date = inputFormat.parse(time);
                return outputFormat.format(date);

            } catch (ParseException e) {
               // Logger.d(e.getLocalizedMessage());
            }
            return "";
        }
        return "";
    }

    public static String getFormattedDate(Calendar calendar, String outputPattern, Context context) {
        if (calendar == null || outputPattern == null) return null;
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, getAppLanguageLocale(context));
        return outputFormat.format(calendar.getTime());
    }



    public static boolean isDiagnosisDoneWithin72hrs(Context context, long time) {
        boolean isDoneWithin72hrs = false;
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
            return true;
        }

        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        if (time > now || time <= 0) {
            return true;  // need confirmation
        }

        final long diff = now - time;
        if (diff <= 72 * HOUR_MILLIS) {
            return true;
        } else {
            return false;
        }
    }

    public static String getDefaultDate(Context context) {
        Calendar defaultDate = Calendar.getInstance();
        defaultDate.set(Calendar.DATE, 1);
        defaultDate.set(Calendar.MONTH, 0);
        defaultDate.set(Calendar.YEAR, 1970);
        return DateTimeUtils.getFormattedDate(defaultDate, DateTimeUtils.UTC_FORMAT, context);
    }

    public static Locale getAppLanguageLocale(Context context) {
        String appLanguage = context.getString(R.string.app_language);
        return new Locale(appLanguage);
    }

    public static long getTimeInMillisecond(Context context, String inputTime, String inputFormat) {
        SimpleDateFormat inputSDF = new SimpleDateFormat(inputFormat, getAppLanguageLocale(context));
        try {
            Date date = inputSDF.parse(inputTime);
            return date.getTime();
        } catch (ParseException e) {
           // Logger.d(e.getLocalizedMessage());
        }
        return System.currentTimeMillis();
    }
}