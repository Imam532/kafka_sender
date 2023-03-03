package com.example.kafkaeventsender.dto;

public final class Headers {
    public static final String EVENT_NAME = "X-EVENT-NAME";
    public static final String USER_ID = "X-USER-ID";
    public static final String X_TRACE_ID = "X-TRACE-ID";
    public static final String STATUS = "X-STATUS";
    public static final String APPLICATION = "X-APPLICATION-NAME";
    public static final String RESULT_STATUS = "X-RESULT-STATUS";
    public static final String TIMESTAMP = "X-TIMESTAMP";
    public static final String X_RE_TRACE_ID = "X-RE-TRACE-ID";

    public Headers() {
    }

    public final class Values {
        public static final String SUCCESS = "SUCCESS";
        public static final String ERROR = "ERROR";

        public Values() {
        }
    }
}
