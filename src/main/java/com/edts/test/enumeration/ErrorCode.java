package com.edts.test.enumeration;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SOMETHING_WENT_WRONG,
    CONCERT_ALREADY_PERFORMED,
    CONCERT_NOT_FOUND,
    MEMBER_NOT_FOUND,
    NO_BOOKING_WINDOW_AVAILABLE,
    NOT_ENOUGH_TICKET_AVAILABLE;
}
