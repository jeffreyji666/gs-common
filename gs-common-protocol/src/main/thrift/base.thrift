namespace java com.ctrip.gs.protocol.base

include "exception.thrift"

service BaseService {
    void ping() throws (1: exception.ExceptionBase eb),
}
