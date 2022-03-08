package org.casc.lang.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun <T, R> List<T>.pmap(functor: suspend (T) -> R): List<R> = runBlocking {
    map { async(Dispatchers.Default) { functor(it) } }.map { it.await() }
}

fun <T, R : Any> List<T?>.pmapNotNull(functor: suspend (T) -> R): List<R> = runBlocking {
    mapNotNull { it?.let { async(Dispatchers.Default) { functor(it) } } }.map { it.await() }
}

fun <T> List<T>.pforeach(functor: suspend (T) -> Unit) = runBlocking {
    map { async(Dispatchers.Default) { functor(it) } }.map { it.await() }
}
