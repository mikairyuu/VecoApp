package com.veco.vecoapp.utils

object MaterialTextParser {
    fun parse(text: String): List<MaterialElement> {
        val resultList = mutableListOf<MaterialElement>()
        val currentElement = StringBuilder()
        var lastElement = ' '
        var curType = MaterialElemType.B1R
        for (c in text) {
            if (c == '\n') continue
            if (c == ':') {
                if (lastElement == ':') {
                    val strType = currentElement.toString()
                    curType = convertType(strType)
                    if (curType == MaterialElemType.SPACE)
                        resultList.add(
                            MaterialElement(curType, strType.substring(1).toInt())
                        )
                    currentElement.clear()
                }
            } else if (c == ';') {
                if (lastElement == ';') {
                    resultList.add(MaterialElement(curType, currentElement.toString()))
                    currentElement.clear()
                }
            } else {
                currentElement.append(c)
            }
            lastElement = c
        }
        return resultList
    }

    private fun convertType(strType: String): MaterialElemType {
        return when (strType) {
            "b1r" -> MaterialElemType.B1R
            "b1m" -> MaterialElemType.B1M
            "img" -> MaterialElemType.IMG
            else -> {
                MaterialElemType.SPACE
            }
        }
    }
}

enum class MaterialElemType {
    B1M,
    B1R,
    IMG,
    SPACE
}

data class MaterialElement(
    val type: MaterialElemType,
    val data: Any
)
