package org.casc.lang.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun <T, R> List<T>.pmap(functor: suspend (T) -> R): List<R> = runBlocking {
    map { async(Dispatchers.Default) { functor(it) } }.map { it.await() }
}

fun <T, R> List<T?>.pmapNotNull(functor: suspend (T) -> R?): List<R> = runBlocking {
    map { it?.let { async(Dispatchers.Default) { functor(it) } } }.mapNotNull { it?.await() }
}

fun <T> List<T>.pforEach(functor: suspend (T) -> Unit) = runBlocking {
    map { async(Dispatchers.Default) { functor(it) } }.map { it.await() }
}
