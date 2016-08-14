package com.cl

import com.cl.url.{CLUrl, Query}

class  Monitor(val url :CLUrl) {

    def execute():MonitorExecutionResult = {
        return new MonitorExecutionResult
    }
}

class MonitorExecutionResult {

    def compare(anotherResult:MonitorExecutionResult):MonitorExecutionResult = {
        new MonitorExecutionResult
    }
}
