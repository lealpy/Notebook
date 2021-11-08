package com.lealpy.notebook

import com.lealpy.notebook.utils.AppUtils
import  org.junit.Test
import org.junit.Assert.*

class AppUtilsUnitTest {

    @Test
    fun getTimeRange_isCorrect() {
        val expectedResult = "10:00-11:00"
        val actualResult = AppUtils.getTimeRange(1636280100000)
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getDateStringByInt_isCorrect() {
        val expectedResult = "05.11.2021"
        val actualResult = AppUtils.getDateStringByInt(2021, 10, 5)
        assertEquals(expectedResult, actualResult)
    }

}