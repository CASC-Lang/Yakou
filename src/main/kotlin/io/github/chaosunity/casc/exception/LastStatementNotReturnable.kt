package io.github.chaosunity.casc.exception

import io.github.chaosunity.casc.parsing.statement.Statement

class LastStatementNotReturnable(statement: Statement) :
    RuntimeException("Statement $statement does not have value to return.")