package com.ddadjee.common.constants;

import java.time.format.DateTimeFormatter;

/**
 * 날짜/시간 포맷 상수.
 * <p>
 * UI, 로그, 외부 연동 시 일관된 포맷 사용을 위해 제공합니다.
 */
public final class DateTimeConstants {

    private DateTimeConstants() {}

    /** 예: 2025-09-13 */
    public static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** 예: 2025-09-13 14:30 */
    public static final DateTimeFormatter DATE_TIME_MINUTE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /** 예: 2025-09-13T14:30:00Z 또는 오프셋 포함 */
    public static final DateTimeFormatter ISO_OFFSET = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    /** 예: 2025-09-13T14:30:00 */
    public static final DateTimeFormatter ISO_LOCAL = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
}

