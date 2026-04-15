package com.guilhermekunz.blocodenotas.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.guilhermekunz.blocodenotas.domain.model.ChecklistItem

class NoteConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromChecklist(value: List<ChecklistItem>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toChecklist(value: String?): List<ChecklistItem>? {
        val listType = object : TypeToken<List<ChecklistItem>>() {}.type
        return gson.fromJson(value, listType)
    }
}