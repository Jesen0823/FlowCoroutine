package com.jesen.pagingbookstore.mapper

interface Mapper<I, O> {
    // 传入一个类型，转换为另一个类型
    fun map(input: I): O
}