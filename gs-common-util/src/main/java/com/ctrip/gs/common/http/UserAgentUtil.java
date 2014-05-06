package com.ctrip.gs.common.http;

import javax.servlet.http.HttpServletRequest;

/**
 * User Agent处理帮助类。 http://nerds.palmdrive.net/useragent/code.html
 * 
 * @author MarvinZhang
 * 
 */
public final class UserAgentUtil {

    private UserAgentUtil() {
    }

    /**
     * 获取Bot名，如果是个Bot的话。
     * 
     * @param userAgent
     * @return 长度为3的字符串数组，前2个都是Bot名，如Baidu, MSNBot；第3个是带版本号的Bot名。
     */
    public static String[] getBotName(String userAgent) {
        if (userAgent == null) {
            return null;
        }
        userAgent = userAgent.toLowerCase();
        int pos = 0;
        String res = null;
        if ((pos = userAgent.indexOf("baiduspider")) > -1) {
            res = "Baidu";
            pos += -1;
        } else if ((pos = userAgent.indexOf("sosospider")) > -1) {
            res = "Soso";
            pos += -1;
        } else if ((pos = userAgent.indexOf("sogou web spider/")) > -1) {
            res = "Sogou";
            pos += 17;
        } else if ((pos = userAgent.indexOf("yodaobot")) > -1) {
            res = "Yodao";
            pos += -1;
        } else if ((pos = userAgent.indexOf("yahoo!+slurp")) > -1) {
            res = "Yahoo";
            pos += -1;
        } else if ((pos = userAgent.indexOf("msnbot/")) > -1) {
            res = "MSNBot";
            pos += 7;
        } else if ((pos = userAgent.indexOf("googlebot/")) > -1) {
            res = "Google";
            pos += 10;
        } else if ((pos = userAgent.indexOf("webcrawler/")) > -1) {
            res = "WebCrawler";
            pos += 11;
        } else if ((pos = userAgent.indexOf("inktomi")) > -1) {
            res = "Inktomi";
            pos = -1;
        } else if ((pos = userAgent.indexOf("teoma")) > -1) {
            res = "Teoma";
            pos = -1;
        } else if ((pos = userAgent.indexOf("alexa.com")) > -1) {
            res = "Alexa";
            pos = -1;
        } else if ((pos = userAgent.indexOf("crawler")) > -1) {
            res = "Crawler";
            pos = -1;
        } else if ((pos = userAgent.indexOf("bot")) > -1) {
            res = "Bot";
            pos = -1;
        }
        if (res == null) {
            return null;
        }
        return getArray(res, res, res + getVersionNumber(userAgent, pos));
    }

    /**
     * 获取操作系统。
     * 
     * @param userAgent
     * @return 长度为3的字符串数组： 1. 简称，如Win, Linux, Mac等。 2. 全称，如WinNT, Win7, Ubuntu, MacOSX等。 3. 全称加版本号。
     */
    public static String[] getOS(String userAgent) {
        String[] res = null;
        int pos;
        if (userAgent == null || userAgent.isEmpty()) {
            res = getArray("?", "?", "?");
        } else if ((pos = userAgent.indexOf("Windows-NT")) > -1) {
            res = getArray("Win", "WinNT", "Win " + getVersionNumber(userAgent, pos + 8));
        } else if (userAgent.indexOf("Windows NT") > -1) {
            // <SPAN class="codecomment"> The different versions of Windows NT
            // are decoded in the verbosity level 2</span>
            // <SPAN class="codecomment"> ie: Windows NT 5.1 = Windows XP</span>
            if ((pos = userAgent.indexOf("Windows NT 5.1")) > -1) {
                res = getArray("Win", "WinXP", "WinXP " + getVersionNumber(userAgent, pos + 7));
            } else if ((pos = userAgent.indexOf("Windows NT 6.0")) > -1) {
                res = getArray("Win", "Vista", "Vista " + getVersionNumber(userAgent, pos + 7));
            } else if ((pos = userAgent.indexOf("Windows NT 6.1")) > -1) {
                res = getArray("Win", "Win7", "Win7 " + getVersionNumber(userAgent, pos + 7));
            } else if ((pos = userAgent.indexOf("Windows NT 5.0")) > -1) {
                res = getArray("Win", "Win2000", "Win2000 " + getVersionNumber(userAgent, pos + 7));
            } else if ((pos = userAgent.indexOf("Windows NT 5.2")) > -1) {
                res = getArray("Win", "Win2003", "Win2003 " + getVersionNumber(userAgent, pos + 7));
            } else if ((pos = userAgent.indexOf("Windows NT 4.0")) > -1) {
                res = getArray("Win", "WinNT4", "WinNT4 " + getVersionNumber(userAgent, pos + 7));
            } else if ((pos = userAgent.indexOf("Windows NT)")) > -1) {
                res = getArray("Win", "WinNT", "WinNT");
            } else if ((pos = userAgent.indexOf("Windows NT;")) > -1) {
                res = getArray("Win", "WinNT", "WinNT");
            } else {
                res = getArray("Win", "WinNT?", "WinNT");
            }
        } else if (userAgent.indexOf("Win") > -1) {
            if (userAgent.indexOf("Windows") > -1) {
                if ((pos = userAgent.indexOf("Windows 98")) > -1) {
                    res = getArray("Win", "Win98", "Win98 " + getVersionNumber(userAgent, pos + 7));
                } else if ((pos = userAgent.indexOf("Windows_98")) > -1) {
                    res = getArray("Win", "Win98", "Win98 " + getVersionNumber(userAgent, pos + 8));
                } else if ((pos = userAgent.indexOf("Windows 2000")) > -1) {
                    res = getArray("Win", "Win2000", "Win2000 " + getVersionNumber(userAgent, pos + 7));
                } else if ((pos = userAgent.indexOf("Windows 95")) > -1) {
                    res = getArray("Win", "Win95", "Win95 " + getVersionNumber(userAgent, pos + 7));
                } else if ((pos = userAgent.indexOf("Windows 9x")) > -1) {
                    res = getArray("Win", "Win9x", "Win9x " + getVersionNumber(userAgent, pos + 7));
                } else if ((pos = userAgent.indexOf("Windows ME")) > -1) {
                    res = getArray("Win", "WinME", "WinME " + getVersionNumber(userAgent, pos + 7));
                } else if ((pos = userAgent.indexOf("Windows 3.1")) > -1) {
                    res = getArray("Win", "Win31", "Win31 " + getVersionNumber(userAgent, pos + 7));
                }
                // <SPAN class="codecomment"> If no version was found, rely on
                // the following code to detect "WinXX"</span>
                // <SPAN class="codecomment"> As some User-Agents include two
                // references to Windows</span>
                // <SPAN class="codecomment"> Ex: Mozilla/5.0 (Windows; U;
                // Win98; en-US; rv:1.5)</span>
            }
            if (res == null) {
                if ((pos = userAgent.indexOf("Win98")) > -1) {
                    res = getArray("Win", "Win98", "Win98 " + getVersionNumber(userAgent, pos + 3));
                } else if ((pos = userAgent.indexOf("Win31")) > -1) {
                    res = getArray("Win", "Win31", "Win31 " + getVersionNumber(userAgent, pos + 3));
                } else if ((pos = userAgent.indexOf("Win95")) > -1) {
                    res = getArray("Win", "Win95", "Win95 " + getVersionNumber(userAgent, pos + 3));
                } else if ((pos = userAgent.indexOf("Win 9x")) > -1) {
                    res = getArray("Win", "Win9x", "Win 9x " + getVersionNumber(userAgent, pos + 3));
                } else if ((pos = userAgent.indexOf("WinNT4.0")) > -1) {
                    res = getArray("Win", "WinNT4", "WinNT4.0 " + getVersionNumber(userAgent, pos + 3));
                } else if ((pos = userAgent.indexOf("WinNT")) > -1) {
                    res = getArray("Win", "WinNT", "WinNT " + getVersionNumber(userAgent, pos + 3));
                }
            }
            if (res == null) {
                if ((pos = userAgent.indexOf("Windows")) > -1) {
                    res = getArray("Win", "Win?", "Win " + getVersionNumber(userAgent, pos + 7));
                } else if ((pos = userAgent.indexOf("Win")) > -1) {
                    res = getArray("Win", "Win?", "Win " + getVersionNumber(userAgent, pos + 3));
                } else {
                    // <SPAN class="codecomment"> Should not happen at this
                    // point</span>
                    res = getArray("Win", "Win?", "Win?");
                }
            }
        } else if ((pos = userAgent.indexOf("Mac OS X")) > -1) {
            if ((userAgent.indexOf("iPhone")) > -1) {
                pos = userAgent.indexOf("iPhone OS");
                res = getArray("Mac", "MacOSX-iPhone",
                        "MacOS-iPhone " + ((pos < 0) ? "" : getVersionNumber(userAgent, pos + 9)));
            } else {
                res = getArray("Mac", "MacOSX", "MacOS " + getVersionNumber(userAgent, pos + 8));
            }
        } else if ((pos = userAgent.indexOf("Mac_PowerPC")) > -1) {
            res = getArray("Mac", "MacPPC", "MacOS " + getVersionNumber(userAgent, pos + 3));
        } else if ((pos = userAgent.indexOf("Macintosh")) > -1) {
            if (userAgent.indexOf("PPC") > -1) {
                res = getArray("Mac", "MacPPC", "MacOS?");
            } else {
                res = getArray("Mac", "Mac?", "MacOS?");
            }
        } else if ((pos = userAgent.indexOf("FreeBSD")) > -1) {
            res = getArray("*BSD", "FreeBSD", "FreeBSD " + getVersionNumber(userAgent, pos + 7));
        } else if ((pos = userAgent.indexOf("OpenBSD")) > -1) {
            res = getArray("*BSD", "OpenBSD", "OpenBSD " + getVersionNumber(userAgent, pos + 7));
        } else if ((pos = userAgent.indexOf("Linux")) > -1) {
            if ((pos = userAgent.indexOf("Ubuntu/")) > -1) {
                res = getArray("Linux", "Ubuntu", "Ubuntu " + getVersionNumber(userAgent, pos + 7));
            } else {
                res = getArray("Linux", "Linux", "Linux " + getVersionNumber(userAgent, pos + 5));
            }
        } else if ((pos = userAgent.indexOf("CentOS")) > -1) {
            res = getArray("Linux", "CentOS", "CentOS");
        } else if ((pos = userAgent.indexOf("NetBSD")) > -1) {
            res = getArray("*BSD", "NetBSD", "NetBSD " + getVersionNumber(userAgent, pos + 6));
        } else if ((pos = userAgent.indexOf("Unix")) > -1) {
            res = getArray("Linux", "Linux", "Linux " + getVersionNumber(userAgent, pos + 4));
        } else if ((pos = userAgent.indexOf("SunOS")) > -1) {
            res = getArray("Unix", "SunOS", "SunOS " + getVersionNumber(userAgent, pos + 5));
        } else if ((pos = userAgent.indexOf("IRIX")) > -1) {
            res = getArray("Unix", "IRIX", "IRIX " + getVersionNumber(userAgent, pos + 4));
        } else if ((pos = userAgent.indexOf("SonyEricsson")) > -1) {
            res = getArray("SonyEricsson", "SonyEricsson", "SonyEricsson " + getVersionNumber(userAgent, pos + 12));
        } else if ((pos = userAgent.indexOf("Nokia")) > -1) {
            res = getArray("Nokia", "Nokia", "Nokia " + getVersionNumber(userAgent, pos + 5));
        } else if ((pos = userAgent.indexOf("BlackBerry")) > -1) {
            res = getArray("BlackBerry", "BlackBerry", "BlackBerry " + getVersionNumber(userAgent, pos + 10));
        } else if ((pos = userAgent.indexOf("SymbianOS")) > -1) {
            res = getArray("SymbianOS", "SymbianOS", "SymbianOS " + getVersionNumber(userAgent, pos + 10));
        } else if ((pos = userAgent.indexOf("BeOS")) > -1) {
            res = getArray("BeOS", "BeOS", "BeOS");
        } else if ((pos = userAgent.indexOf("Nintendo Wii")) > -1) {
            res = getArray("Wii", "Wii", "Wii " + getVersionNumber(userAgent, pos + 10));
        } else if (getBotName(userAgent) != null) {
            res = getArray("Bot", "Bot", "Bot");
        } else {
            res = getArray("?", "?", "?");
        }
        return res;
    }

    /**
     * 获取浏览器。
     * 
     * @param userAgent
     * @return 长度为3的字符串数组： 1. 浏览器引擎，如MSIE, KHTML等。 2. 浏览器名称，如MSIE8, Chrome, Firefox等。 3. 浏览器名称加版本号。
     */
    public static String[] getBrowser(String userAgent) {
        String[] res = null;
        int pos;
        if (userAgent == null || userAgent.isEmpty()) {
            res = getArray("?", "?", "?");
        } else if ((pos = userAgent.indexOf("Lotus-Notes/")) > -1) {
            res = getArray("LotusNotes", "LotusNotes", "LotusNotes " + getVersionNumber(userAgent, pos + 12));
        } else if ((pos = userAgent.indexOf("Opera")) > -1) {
            res = getArray("Opera", "Opera" + getFirstVersionNumber(userAgent, pos + 5, 1), "Opera "
                    + getVersionNumber(userAgent, pos + 5));
        } else if (userAgent.indexOf("MSIE") > -1) {
            if ((pos = userAgent.indexOf("TencentTraveler")) > -1) {
                res = getArray("MSIE", "TencentTT", "TencentTT " + getVersionNumber(userAgent, pos + 15));
            } else if ((pos = userAgent.indexOf("Maxthon")) > -1) {
                res = getArray("MSIE", "Maxthon", "Maxthon " + getVersionNumber(userAgent, pos + 7));
            } else if ((pos = userAgent.indexOf("TheWorld")) > -1) {
                res = getArray("MSIE", "TheWorld", "TheWorld");
            } else if ((pos = userAgent.indexOf("360SE")) > -1) {
                res = getArray("MSIE", "360SE", "360SE");
            } else if ((pos = userAgent.indexOf("MSIE 6.0")) > -1) {
                res = getArray("MSIE", "MSIE6", "MSIE " + getVersionNumber(userAgent, pos + 4));
            } else if ((pos = userAgent.indexOf("MSIE 5.0")) > -1) {
                res = getArray("MSIE", "MSIE5", "MSIE " + getVersionNumber(userAgent, pos + 4));
            } else if ((pos = userAgent.indexOf("MSIE 5.5")) > -1) {
                res = getArray("MSIE", "MSIE5.5", "MSIE " + getVersionNumber(userAgent, pos + 4));
            } else if ((pos = userAgent.indexOf("MSIE 5.")) > -1) {
                res = getArray("MSIE", "MSIE5.x", "MSIE " + getVersionNumber(userAgent, pos + 4));
            } else if ((pos = userAgent.indexOf("MSIE 4")) > -1) {
                res = getArray("MSIE", "MSIE4", "MSIE " + getVersionNumber(userAgent, pos + 4));
            } else if ((pos = userAgent.indexOf("MSIE 7")) > -1 && userAgent.indexOf("Trident/4.0") < 0) {
                res = getArray("MSIE", "MSIE7", "MSIE " + getVersionNumber(userAgent, pos + 4));
            } else if ((pos = userAgent.indexOf("MSIE 8")) > -1 && userAgent.indexOf("Trident/4.0") > -1) {
                res = getArray("MSIE", "MSIE8", "MSIE " + getVersionNumber(userAgent, pos + 4));
            } else {
                res = getArray("MSIE", "MSIE?", "MSIE " + getVersionNumber(userAgent, userAgent.indexOf("MSIE") + 4));
            }
        } else if ((pos = userAgent.indexOf("Gecko/")) > -1) {
            res = getArray("Gecko", "", "");
            if ((pos = userAgent.indexOf("Camino/")) > -1) {
                res[1] += "Camino";
                res[2] += "Camino " + getVersionNumber(userAgent, pos + 7);
            } else if ((pos = userAgent.indexOf("Chimera/")) > -1) {
                res[1] += "Chimera";
                res[2] += "Chimera " + getVersionNumber(userAgent, pos + 8);
            } else if ((pos = userAgent.indexOf("Firebird/")) > -1) {
                res[1] += "Firebird";
                res[2] += "Firebird " + getVersionNumber(userAgent, pos + 9);
            } else if ((pos = userAgent.indexOf("Phoenix/")) > -1) {
                res[1] += "Phoenix";
                res[2] += "Phoenix " + getVersionNumber(userAgent, pos + 8);
            } else if ((pos = userAgent.indexOf("Galeon/")) > -1) {
                res[1] += "Galeon";
                res[2] += "Galeon " + getVersionNumber(userAgent, pos + 7);
            } else if ((pos = userAgent.indexOf("Firefox/")) > -1) {
                res[1] += "Firefox";
                res[2] += "Firefox " + getVersionNumber(userAgent, pos + 8);
            } else if ((pos = userAgent.indexOf("Netscape/")) > -1) {
                if ((pos = userAgent.indexOf("Netscape/6")) > -1) {
                    res[1] += "NS6";
                    res[2] += "NS " + getVersionNumber(userAgent, pos + 9);
                } else if ((pos = userAgent.indexOf("Netscape/7")) > -1) {
                    res[1] += "NS7";
                    res[2] += "NS " + getVersionNumber(userAgent, pos + 9);
                } else {
                    res[1] += "NS?";
                    res[2] += "NS " + getVersionNumber(userAgent, userAgent.indexOf("Netscape/") + 9);
                }
            }
        } else if ((pos = userAgent.indexOf("Netscape/")) > -1) {
            if ((pos = userAgent.indexOf("Netscape/4")) > -1) {
                res = getArray("NS", "NS4", "NS " + getVersionNumber(userAgent, pos + 9));
            } else {
                res = getArray("NS", "NS?", "NS " + getVersionNumber(userAgent, pos + 9));
            }
        } else if ((pos = userAgent.indexOf("Chrome/")) > -1) {
            res = getArray("KHTML", "Chrome", "Chrome " + getVersionNumber(userAgent, pos + 6));
        } else if ((pos = userAgent.indexOf("Safari/")) > -1) {
            res = getArray("KHTML", "Safari", "Safari " + getVersionNumber(userAgent, pos + 6));
        } else if ((pos = userAgent.indexOf("Konqueror/")) > -1) {
            res = getArray("KHTML", "Konqueror", "Konqueror " + getVersionNumber(userAgent, pos + 9));
        } else if ((pos = userAgent.indexOf("KHTML")) > -1) {
            res = getArray("KHTML", "KHTML?", "KHTML " + getVersionNumber(userAgent, pos + 5));
        } else if ((pos = userAgent.indexOf("NetFront")) > -1) {
            res = getArray("NetFront", "NetFront", "NetFront " + getVersionNumber(userAgent, pos + 8));
        } else {
            // <SPAN class="codecomment"> We will interpret Mozilla/4.x as Netscape
            // Communicator is and only if x</span>
            // <SPAN class="codecomment"> is not 0 or 5</span>
            if (userAgent.indexOf("Mozilla/4.") == 0 && userAgent.indexOf("Mozilla/4.0") < 0
                    && userAgent.indexOf("Mozilla/4.5 ") < 0) {
                res = getArray("Communicator", "Communicator", "Communicator " + getVersionNumber(userAgent, pos + 8));
            } else {
                String[] botName;
                if ((botName = getBotName(userAgent)) != null) {
                    return botName;
                } else {
                    return getArray("?", "?", "?");
                }
            }
        }
        return res;
    }

    /**
     * 判断是否IOS。
     * 
     * @param userAgent
     * @return
     */
    public static boolean isIosBrowser(String userAgent) {
        if (userAgent == null) {
            return false;
        }
        return userAgent.toLowerCase().contains("ipad") || userAgent.toLowerCase().contains("ipod")
                || userAgent.toLowerCase().contains("iphone");
    }

    /**
     * 获取客户端IP地址。
     * 
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        // Http cache will mask the client ip, try X-Forwarded-For first
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return "";
        } else if (ip.contains(",")) {
            // Multiple ips, left-most is the client ip address but sometimes it might be unknown
            String[] ips = ip.split(",");
            for (String s : ips) {
                if (s.indexOf('.') > 0) {
                    ip = s;
                    break;
                }
            }
        }

        return ip.trim();
    }

    /**
     * 将INT类型的IP地址转换为字符串。
     * 
     * @param i
     * @return
     */
    public static String intToIp(int i) {
        return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
    }

    /**
     * 将字符串类型的IP地址转换为INT类型。
     * 
     * @param addr
     * @return
     */
    public static int ipToInt(String addr) {
        try {
            if (addr == null || addr.isEmpty()) {
                return 0;
            }

            final String[] addressBytes = addr.split("\\.");
            int ip = 0;
            for (int i = 0; i < 4; i++) {
                ip <<= 8;
                ip |= Integer.parseInt(addressBytes[i]);
            }
            return ip;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将LONG型的IP地址转换为字符串。
     * 
     * @param i
     * @return
     */
    public static String longToIp(long i) {
        return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
    }

    /**
     * 将字符串型的IP地址转换为LONG型。
     * 
     * @param addr
     * @return
     */
    public static Long ipToLong(String addr) {
        if (addr == null || addr.isEmpty()) {
            return 0L;
        }

        final String[] addressBytes = addr.split("\\.");
        long ip = 0L;
        for (int i = 0; i < 4; i++) {
            ip <<= 8;
            ip |= Long.parseLong(addressBytes[i]);
        }
        return ip;
    }

    private static String getFirstVersionNumber(String userAgent, int position, int numDigits) {
        String ver = getVersionNumber(userAgent, position);
        if (ver == null) {
            return "";
        }
        int i = 0;
        StringBuilder res = new StringBuilder();
        while (i < ver.length() && i < numDigits) {
            res.append(ver.charAt(i));
            i++;
        }
        return res.toString();
    }

    private static String getVersionNumber(String userAgent, int position) {
        if (position < 0) {
            return "";
        }
        StringBuffer res = new StringBuffer();
        int status = 0;

        while (position < userAgent.length()) {
            char c = userAgent.charAt(position);
            switch (status) {
            case 0:
                // <SPAN class="codecomment"> No valid digits encountered
                // yet</span>
                if (c == ' ' || c == '/') {
                    break;
                }
                if (c == ';' || c == ')') {
                    return "";
                }
                status = 1;
                // fall through
            case 1:
                // <SPAN class="codecomment"> Version number in progress</span>
                if (c == ';' || c == '/' || c == ')' || c == '(' || c == '[') {
                    return res.toString().trim();
                }
                if (c == ' ') {
                    status = 2;
                }
                res.append(c);
                break;
            case 2:
                // <SPAN class="codecomment"> Space encountered - Might need to
                // end the parsing</span>
                if ((Character.isLetter(c) && Character.isLowerCase(c)) || Character.isDigit(c)) {
                    res.append(c);
                    status = 1;
                } else {
                    return res.toString().trim();
                }
                break;
            }
            position++;
        }
        return res.toString().trim();
    }

    private static String[] getArray(String a, String b, String c) {
        String[] res = new String[3];
        res[0] = a;
        res[1] = b;
        res[2] = c;
        return res;
    }
}
