namespace java com.ctrip.gs.protocol.test

include "exception.thrift"
include "base.thrift"

service TestService extends base.BaseService {
    void pong() throws (1: exception.ExceptionBase eb),
}
