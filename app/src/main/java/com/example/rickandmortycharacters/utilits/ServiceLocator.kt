package com.example.rickandmortycharacters.utilits

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.jvm.jvmName

object ServiceLocator {

    private var serviceInstances = HashMap<String, Any>()
    private var serviceImplementationsMapping = HashMap<String, KClass<*>>()
    private var serviceInstance = Any()

    interface Service{

    }

    fun <T> get(clazz : KClass<*>) : T{
        return getService(clazz.jvmName) as T
    }

    @Synchronized
    fun set(interfaceClass : KClass<*>, implementationClass : KClass<*>){
        serviceImplementationsMapping[interfaceClass.jvmName] = implementationClass
    }

    private fun getService(name : String) : Any{
        val serviceCacheInstance = serviceInstances[name]
        if(serviceCacheInstance != null){
            return serviceCacheInstance
        }

        try{
            val clazz : KClass<*>? = if(serviceImplementationsMapping.containsKey(name)){
                serviceImplementationsMapping[name]
            }
            else{
                Class.forName(name).kotlin
            }
            try{
                serviceInstance = clazz!!.createInstance()
            }
            catch(e : NoSuchMethodException){
                throw IllegalArgumentException("Сервис без коструктора $name", e)
            }

            if(serviceInstance !is ServiceLocator.Service){
                throw IllegalArgumentException("Запрашиваемый сервис должен имплементировать IService")
            }

            serviceInstances[name] = serviceInstance
            return serviceInstance
        }
        catch (e : ClassNotFoundException){
            throw IllegalArgumentException("Запрашиваемый класс сервиса не был найден $name", e)
        }
        catch (e : Exception){
            throw IllegalArgumentException("Невозможно инициализировать сервис $name", e)
        }
    }
}