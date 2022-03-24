package org.casc.lang.ast

sealed interface InvokeCall {
    var retainValue: Boolean
}