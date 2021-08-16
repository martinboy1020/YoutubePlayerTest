package com.example.baseux.components

object AnnotationParse {
    fun getAnnotatedLayout(theObject: Any): Int =
        try {
            Class.forName(theObject.javaClass.name).getAnnotation(LayoutId::class.java).value
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            throw RuntimeException("No annotated layout")
        }

    fun getAnnotatedNavHost(theObject: Any): Int =
        try {
            Class.forName(theObject.javaClass.name).getAnnotation(NavHosttId::class.java).value
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            throw RuntimeException("No nav host fragment layout")
        }
}