package com.platform.common.util;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {
    private static final Logger log = LoggerFactory.getLogger(IpUtils.class);
    private static final String UNKNOWN = "unknown";
    public static final String LOCALHOST = "127.0.0.1";

    private IpUtils() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        } else {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }

            return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
        }
    }

    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    private static boolean internalIp(byte[] addr) {
        int addrLen = 2;
        if (null != addr && addr.length >= addrLen) {
            byte b0 = addr[0];
            byte b1 = addr[1];
            switch(b0) {
                case -84:
                    if (b1 >= 16 && b1 <= 31) {
                        return true;
                    }
                    break;
                case -64:
                    if (b1 == -88) {
                        return true;
                    }
                    break;
                case 10:
                    return true;
                default:
                    return false;
            }

            return false;
        } else {
            return true;
        }
    }

    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        } else {
            byte[] bytes = new byte[4];
            String[] elements = text.split("\\.", -1);

            try {
                long l;
                switch(elements.length) {
                    case 1:
                        l = Long.parseLong(elements[0]);
                        long unsignedMinValue = 4294967295L;
                        if (l < 0L || l > unsignedMinValue) {
                            return ArrayUtils.EMPTY_BYTE_ARRAY;
                        }

                        bytes[0] = (byte)((int)(l >> 24 & 255L));
                        bytes[1] = (byte)((int)((l & 16777215L) >> 16 & 255L));
                        bytes[2] = (byte)((int)((l & 65535L) >> 8 & 255L));
                        bytes[3] = (byte)((int)(l & 255L));
                        break;
                    case 2:
                        long zero = 0L;
                        long unsignedTinyint = 255L;
                        long unsignedMediumint = 16777215L;
                        l = (long)Integer.parseInt(elements[0]);
                        if (l >= zero && l <= unsignedTinyint) {
                            bytes[0] = (byte)((int)(l & 255L));
                            l = (long)Integer.parseInt(elements[1]);
                            if (l < zero || l > unsignedMediumint) {
                                return ArrayUtils.EMPTY_BYTE_ARRAY;
                            }

                            bytes[1] = (byte)((int)(l >> 16 & 255L));
                            bytes[2] = (byte)((int)((l & 65535L) >> 8 & 255L));
                            bytes[3] = (byte)((int)(l & 255L));
                            break;
                        }

                        return ArrayUtils.EMPTY_BYTE_ARRAY;
                    case 3:
                        bytes = formatV3(elements);
                        break;
                    case 4:
                        bytes = formatV4(elements);
                        break;
                    default:
                        return ArrayUtils.EMPTY_BYTE_ARRAY;
                }

                return bytes;
            } catch (NumberFormatException var13) {
                return ArrayUtils.EMPTY_BYTE_ARRAY;
            }
        }
    }

    private static byte[] formatV3(String[] elements) {
        byte[] bytes = new byte[4];
        long zero = 0L;
        long unsignedTinyint = 255L;
        long unsignedSmallint = 65535L;
        int i = 0;

        long l;
        for(byte len = 2; i < len; ++i) {
            l = (long)Integer.parseInt(elements[i]);
            if (l < zero || l > unsignedTinyint) {
                return ArrayUtils.EMPTY_BYTE_ARRAY;
            }

            bytes[i] = (byte)((int)(l & 255L));
        }

        l = (long)Integer.parseInt(elements[2]);
        if (l >= zero && l <= unsignedSmallint) {
            bytes[2] = (byte)((int)(l >> 8 & 255L));
            bytes[3] = (byte)((int)(l & 255L));
            return bytes;
        } else {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
    }

    private static byte[] formatV4(String[] elements) {
        byte[] bytes = new byte[4];
        int i = 0;

        for(byte len = 4; i < len; ++i) {
            long l = (long)Integer.parseInt(elements[i]);
            if (l < 0L || l > 255L) {
                return ArrayUtils.EMPTY_BYTE_ARRAY;
            }

            bytes[i] = (byte)((int)(l & 255L));
        }

        return bytes;
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException var1) {
            log.error("", var1);
            return "127.0.0.1";
        }
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException var1) {
            log.error("", var1);
            return "unknown";
        }
    }
}
