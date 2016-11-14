package com.cl.cron


abstract class CronFieldExpression {
    val regex: String

    def evaluate(expression: String): Boolean
}


class AllValuesExpression extends CronFieldExpression {
    val regex = """(^\*$)|(^\*/[0-5]?[0-9])|(^[0-5]?[0-9]/[0-5]?[0-9]$)"""

    def evaluate(expression: String): Boolean = expression.matches( regex )
}

class RangeValueExpression extends CronFieldExpression {
    val regex = """(^[0-5]?[0-9]/[0-5]?[0-9]$)|(^[0-5]?[0-9]-[0-5]?[0-9]$)"""

    def evaluate(expression: String): Boolean = expression.matches( regex )
}