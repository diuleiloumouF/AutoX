package com.aiselp.autox.api

import android.util.Log
import com.aiselp.autox.engine.V8PromiseFactory
import com.caoccao.javet.annotations.V8Function
import com.caoccao.javet.interop.V8Runtime
import com.caoccao.javet.interop.converters.JavetObjectConverter
import com.caoccao.javet.values.V8Value
import com.caoccao.javet.values.reference.V8ValueFunction
import com.caoccao.javet.values.reference.V8ValueObject
import com.caoccao.javet.values.reference.V8ValuePromise
import com.stardust.autojs.runtime.exception.ScriptException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSupertypeOf


class JavaInteractor(
    val scope: CoroutineScope, private val converter: JavetObjectConverter,
    private val promiseFactory: V8PromiseFactory
) : NativeApi {
    override val moduleId: String = "java"
    private lateinit var v8Runtime: V8Runtime
    override fun install(v8Runtime: V8Runtime, global: V8ValueObject): NativeApi.BindingMode {
        this.v8Runtime = v8Runtime
        return NativeApi.BindingMode.ObjectBind
    }

    override fun recycle(v8Runtime: V8Runtime, global: V8ValueObject) {

    }

    private fun asyncInvoke(
        con: CoroutineDispatcher,
        func: () -> Any?,
    ): V8ValuePromise {
        val promiseAdapter = promiseFactory.newPromiseAdapter()
        val promise = promiseAdapter.promise
        scope.launch(con) {
            try {
                val r = func()
                promiseAdapter.resolve(r)
            } catch (e: Throwable) {
                promiseAdapter.reject(e)
            }
        }
        return promise
    }

    @V8Function
    fun callback(fn: V8ValueFunction) {
        val v8Callback = promiseFactory.eventLoopQueue.createV8Callback(fn)
        scope.launch {
            delay(2000)
            v8Callback.invoke()
        }
    }

    @V8Function
    fun loadClass(className: String): Class<*> {
        return Class.forName(className)
    }

    @V8Function
    fun invoke(vararg args: V8Value?): Any? {
        val exec = findMethod(args.toList())
        return exec()
    }

    @V8Function
    fun invokeIo(vararg args: V8Value?): V8ValuePromise {
        return asyncInvoke(Dispatchers.IO, findMethod(args.toList()))
    }


    @V8Function
    fun invokeUi(vararg args: V8Value?): V8ValuePromise {
        return asyncInvoke(Dispatchers.Main, findMethod(args.toList()))
    }


    @V8Function
    fun invokeDefault(vararg args: V8Value?): V8ValuePromise {
        return asyncInvoke(Dispatchers.Default, findMethod(args.toList()))
    }


    @V8Function
    fun printCurrentThread() {
        Log.d(TAG, "Current Thread: ${Thread.currentThread()}")
    }

    private fun findMethod(args: List<V8Value?>): () -> Any? {
        val argList = args.map { converter.toObject<Any?>(it) }
        Log.i(TAG, "invoke  $args")
        check(argList.size >= 3) { ScriptException("invoke args size must >= 3") }
        val javaObj = argList[0] as Any
        val methodName = argList[1] as String
        val javaArgs = (argList[2] as List<*>)
        val argTpyes = javaArgs.map { it?.let { it::class.createType() } }
        val method = javaObj::class.members.find {
            if (it.name == methodName) {
                val parameterTypes = it.parameters
                if (parameterTypes.size - 1 != argTpyes.size) return@find false
                if (parameterTypes.size == 1) return@find true

                for ((i, param) in parameterTypes.withIndex()) {
                    if (i == 0) {
                        //检查是否实例方法
                        if (!param.type.isSupertypeOf(javaObj::class.createType()))
                            return@find false
                        continue
                    }

                    val argType = argTpyes[i - 1] ?: if (param.type.isMarkedNullable) continue
                    else return@find false
                    if (!param.type.isSupertypeOf(argType)) return@find false
                }
                true
            } else false
        }
        checkNotNull(method) { ScriptException("method not found ${javaObj.javaClass.name}$${methodName} args: $argTpyes") }
        return { method.call(javaObj, *(javaArgs.toTypedArray())) }
    }

    companion object {
        private const val TAG = "JavaInteractor"
    }
}